package coursework;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;

public class Handling {
    /**
     * Получить строкой знакомых
     * @return список всех знакомых или ответ об ошибке чтения
     * @throws IOException
     */
    public static String show() throws IOException {
        try {
            return new BufferedReader(new FileReader(new File("database/database.json"))).readLine();
        } catch (IOException e) {
            System.out.println("Файл базы данных недоступен");
        }
        return "{\"showed_successfully\": false}";
    }

    /**
     * Добавить нового знакомого, полученного от сервера, в базу данных
     * @param acquaintanceJSON
     * @return успешно или неуспешно добавлено
     * @throws IOException
     */
    public static String add(String acquaintanceJSON) throws IOException, IllegalArgumentException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Acquaintance acquaintance = objectMapper.readValue(acquaintanceJSON, Acquaintance.class);
            Main.getAcquaintances().add(acquaintance);
            Database.WriteToDatabase(Main.getAcquaintances().getAcquaintancesList());
            return "{\"added_successfully\": true}";
        } catch (Exception e) {
            System.out.println("Попытка добавить знакомого с некорректно введенными данными");
            return "{\"added_successfully\": false}";
        }
    }

    public static String edit(String acquaintanceJSON) throws IOException, IllegalArgumentException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode acquaintanceJsonNode = objectMapper.readTree(acquaintanceJSON);
            int id = Integer.parseInt(acquaintanceJsonNode.get("id").asText());
            Acquaintance acquaintance = objectMapper.treeToValue(acquaintanceJsonNode.get("acquaintance"), Acquaintance.class);
            Main.getAcquaintances().set(id, acquaintance);
            Database.WriteToDatabase(Main.getAcquaintances().getAcquaintancesList());
            return "{\"edited_successfully\": true}";
        } catch (Exception e) {
            System.out.println("Попытка отредактировать знакомого с некорректно введенными данными");
            return "{\"added_successfully\": false}";
        }
    }

    public static String delete(String ListJSON) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Main.getAcquaintances().setAcquaintancesList(objectMapper.readValue(ListJSON, ArrayList.class));
            Database.WriteToDatabase(Main.getAcquaintances().getAcquaintancesList());
            return "{\"deleted_successfully\": true}";
        } catch (IOException e) {
            System.out.println("Файл базы данных недоступен");
            return "{\"deleted_successfully\": false}";
        }
    }
}
