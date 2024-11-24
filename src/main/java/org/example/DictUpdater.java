package org.example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DictUpdater {

    public static void buildDict(Map<String, Object> data, String path, Object val) {
        String[] keys = path.split("/"); // Разбираем путь на составляющие

        for (int i = 1; i < keys.length; i++) {
            String key = keys[i];

            // Проверка на наличие индекса в формате key[index]
            Pattern pattern = Pattern.compile("(.+)\\[(\\d+)\\]$");
            Matcher matcher = pattern.matcher(key);

            if (matcher.find()) {
                // Работа с элементами списка
                String listKey = matcher.group(1);
                int idx = Integer.parseInt(matcher.group(2));

                // Если ключа 'listKey' нет в данных, создаем его как список
                if (!data.containsKey(listKey)) {
                    data.put(listKey, new ArrayList<Object>());
                }
                List<Object> list = (List<Object>) data.get(listKey);

                // Расширяем список до нужной длины
                while (list.size() <= idx) {
                    list.add(null);
                }

                if (i == keys.length - 1) {
                    // Устанавливаем значение
                    list.set(idx, val);
                } else {
                    // Если есть необходимость, создаем пустой словарь
                    if (list.get(idx) == null) {
                        list.set(idx, new HashMap<String, Object>());
                    }

                    data = (Map<String, Object>) list.get(idx);
                }
            } else {
                // Работа с обычными ключами словаря
                if (i == keys.length - 1) {
                    // Устанавливаем значение
                    data.put(key, val);
                } else {
                    if (!data.containsKey(key)) {
                        data.put(key, new HashMap<String, Object>());
                    }
                    data = (Map<String, Object>) data.get(key);
                }
            }
        }
    }

    public static void main(String[] args) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", 0);
        Map<String, Object> options = new HashMap<>();
        options.put("option_id", 1);
        data.put("options", options);

        Object[][] newAttrs = {
                {"/options/new_dict/option_id/еще одна вложенность/", "новое значение"},
                {"/options/id", "new_val"},
                {"/depots[0]/depot_id", 4},
                {"/id", "Замена значения"},
        };

        // Обработка обновления значений
        for (Object[] newAttr : newAttrs) {
            String newPath = (String) newAttr[0];
            Object newValue = newAttr[1];
            buildDict(data, newPath, newValue);
        }

        // Проверка результата
        System.out.println(data);
    }
}
