package coursework;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    private static Acquaintances acquaintances = new Acquaintances();
    public static Acquaintances getAcquaintances() {
        return acquaintances;
    }

    public static void main(String[] args) throws IOException, IllegalArgumentException {
        try {
            acquaintances.setAcquaintancesList(Database.LoadFromDatabase());
        }
        catch (Exception e){
            acquaintances = new Acquaintances();
        }
        startServer();
    }

    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        server.createContext("/back", new Server());
        server.start();
        System.out.println(System.lineSeparator() + "Server started at:\tlocalhost:8001");
    }
}
