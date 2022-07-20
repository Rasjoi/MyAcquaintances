package coursework;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Database {

    /**
     * Записать список знакомых в базу данных
     * @param acquaintances список знакомых
     * @throws IOException файл недоступен
     */
    public static void WriteToDatabase(ArrayList<Acquaintance> acquaintances) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("database/database.json"), acquaintances);
    }

    /**
     * Получить список знакомых из базы данных и считать в список
     * @return список знакомых
     * @throws IOException файл недоступен
     */
    public static ArrayList<Acquaintance> LoadFromDatabase() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("database/database.json"), ArrayList.class);
    }
}
