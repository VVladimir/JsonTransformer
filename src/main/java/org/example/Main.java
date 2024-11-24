package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        new ObjectMapper();
        var s = """
                {
                    "title": "Goodbye!",
                    "author": null,
                    "tags": [
                      "example",
                      "sample"
                    ],
                    "tablerons": [
                    {"name": "red"
                     },
                    {"name": "blue"}
                    ],
                    "content": "This will be unchanged"
                }        
                """;

        var patch = """
                    [
                        { "op": "replace", "path": "/title", "value": "Hello!"},
                        { "op": "remove", "path": "/author/familyName"},
                        { "op": "add", "path": "/phoneNumber", "value": "+01-123-456-7890"},
                        { "op": "replace", "path": "/author/givenName", "value": "Петров"},
                   
                        { "op": "replace", "path": "/tags", "value": ["example"]}
                        
                    ]
                """;


        var patch2 = """
                {
                    "title": "Hello2!",
                    "author": {
                      "familyName": "Васильев"
                    },
                    "tablerons": [
                    {
                    "name": "tabl",
                    "color": {"code": 6, "color": "yellow"}
                    }
                    ],
                    "phoneNumber": "+01-123-456-7890",
                    "tags": ["example2"]
                }   
                """;

        try {
            Customer customer = getCustomer(s, patch);


//            Customer customer2 = getCustomerMerge(s, patch2);
            System.out.println(customer);

        } catch (IOException | JsonPatchException e) {
            throw new RuntimeException(e);
        }

    }

    private static Customer getCustomer(String s, String patch) throws IOException, JsonPatchException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonPatch jsonPatch = JsonPatch.fromJson(objectMapper.readTree(patch));

        var patched = jsonPatch.apply(objectMapper.readTree(s));

        Customer customer = objectMapper.treeToValue(patched, Customer.class);
        return customer;
    }

    private static Customer getCustomerMerge(String s, String patch) throws IOException, JsonPatchException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonMergePatch jsonPatch = JsonMergePatch.fromJson(objectMapper.readTree(patch));

        var patched = jsonPatch.apply(objectMapper.readTree(s));

        Customer customer = objectMapper.treeToValue(patched, Customer.class);
        return customer;
    }
}