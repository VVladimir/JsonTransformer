package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.gson.*;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GsonTesterMap {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String args[]) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String categoryPath = "/options/penalty/close_location_groups/";
        String parameterName = "per_extra_vehicle";
        int parameterValue = 7;

        String jsonString = """
                {
                	"name": "Ben Johnson",
                	"age": 21,
                	"verified": false,
                	"marks": [
                		100
                	],
                	"options": {
                		"name": "somename"
                		
                	}
                }
                """;

        JsonElement rootElement = JsonParser.parseString(jsonString);

        String[] splittedPath = categoryPath.split("/");

        Arrays.stream(splittedPath).toList().forEach(s -> System.out.println(" :: " + "|" + s + "|"));
        if (!rootElement.isJsonObject())
            return;
        JsonObject rootJson = rootElement.getAsJsonObject();

        List<String> notExistsPath = getNotExistsPath(splittedPath, rootJson);
        List<String> existsPath = getExistsPath(splittedPath, rootJson);
        JsonObject newRootJson = getRootObject(rootJson, existsPath);
        JsonObject innerObject = getInnerObject(rootJson, notExistsPath, existsPath);
        JsonObject leafObject = null;
        if(notExistsPath.isEmpty()) {
            leafObject = getLeafObject(innerObject, parameterName, parameterValue);
        } else {
            leafObject = getNewLeafObject(parameterName, parameterValue);
        }
        List<String> reversedList = Lists.reverse(notExistsPath);

        for (int i = 0; i < reversedList.size(); i++) {
            if (i == 0) {
                innerObject.add(reversedList.get(i), leafObject);
                System.out.println("set leaf obj:" + leafObject);
            } else if (i == reversedList.size() - 1) {
                newRootJson.add(reversedList.get(i), innerObject);
            } else {
                innerObject.add(reversedList.get(i), innerObject);
            }
        }


        UserDto userDto = gson.fromJson(rootJson, UserDto.class);
        ;
        System.out.println("=======================");
        System.out.println(gson.toJson(rootJson));
        System.out.println("=======================");
        JsonElement options = rootJson.get("options");
        JsonElement penalty = options.getAsJsonObject().get("penalty");
        JsonElement close_location_groups = penalty.getAsJsonObject().get("close_location_groups");
        JsonElement per_extra_vehicle = close_location_groups.getAsJsonObject().get("per_extra_vehicle");
        var asInt = per_extra_vehicle.getAsInt();

        System.out.println("per_extra_vehicle: " + asInt);
        JsonElement verifiedNode = rootJson.get("verified");
        System.out.println("Verified: " + (verifiedNode.getAsBoolean() ? "Yes" : "No"));
        JsonArray marks = rootJson.getAsJsonArray("marks");

    }

    private static JsonObject getInnerObject(JsonObject rootJson, List<String> notExistsPath, List<String> existsPath) {
        if (existsPath.isEmpty())
            return new JsonObject();
        else if (notExistsPath.isEmpty())
            return getRootObject(rootJson, existsPath);
        else
            return new JsonObject();
    }


    private static JsonObject getRootObject(JsonObject rootJson, List<String> existsPath) {
        for (String path : existsPath) {
            var jsonElement = rootJson.get(path);
            if (jsonElement.isJsonObject())
                rootJson = jsonElement.getAsJsonObject();
            else if (jsonElement.isJsonNull())
                rootJson = new JsonObject();
        }
        return rootJson;
    }

    private static JsonObject getLeafObject(JsonObject leafObject, String parameterName, int parameterValue) {
        leafObject.addProperty(parameterName, parameterValue);
        return leafObject;
    }

    private static JsonObject getNewLeafObject(String parameterName, int parameterValue) {
        JsonObject o = new JsonObject();
        o.addProperty(parameterName, parameterValue);
        return o;
    }

    private static List<String> getNotExistsPath(String[] path, JsonObject rootObject) {
        List<String> list = new ArrayList<>();

        for (int i = 1; i < path.length; i++) {
            if (!rootObject.has(path[i])) {
                list.add(path[i]);
            } else if (rootObject.get(path[i]).isJsonObject()) {
                rootObject = rootObject.get(path[i]).getAsJsonObject();
            } else if (rootObject.get(path[i]).isJsonNull()) {
                rootObject = new JsonObject();
            }
        }
        return list;
    }

    private static List<String> getExistsPath(String[] path, JsonObject rootObject) {
        List<String> list = new ArrayList<>();

        for (int i = 1; i < path.length; i++) {
            if (rootObject.has(path[i])) {
                list.add(path[i]);
                if (rootObject.get(path[i]).isJsonObject())
                    rootObject = rootObject.get(path[i]).getAsJsonObject();
                else if (rootObject.get(path[i]).isJsonNull())
                    rootObject = new JsonObject();
            }
        }
        return list;
    }

    private static boolean exsistsPath(String[] split, int s, String jsonString) {
        int e = 0;
        String path = "$";
        do {
            if (!split[e].isBlank()) {
                path += ".";
                path += split[e];
            }
            e++;
        }
        while (e <= s);

        System.out.println("exsistsPath:" + path);

        DocumentContext jsonContext = JsonPath.parse(jsonString);
        try {
            Object name = jsonContext.read("$.name");
            Object options = jsonContext.read("$.options");
            Object ob = jsonContext.read(path);
            if (ob != null)
                return true;
        } catch (Exception ex) {
            return false;
        }

        return false;
    }
}