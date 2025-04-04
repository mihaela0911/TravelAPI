package trevelroutes.server;

import com.sun.net.httpserver.HttpServer;
import trevelroutes.server.handler.RoutesHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class RoutesHttpsServer {
    private final HttpServer server;
    private int port = 8080;

    public RoutesHttpsServer(int port) throws IOException {
        validatePort(port);
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(port), 0);

        this.server.createContext("/routes", new RoutesHttpHandler());
        server.setExecutor(Executors.newCachedThreadPool());
    }

    public RoutesHttpsServer() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.server.createContext("/routes", new RoutesHttpHandler());
        server.setExecutor(Executors.newCachedThreadPool());
    }

    private void validatePort(int port) {
        if(port < 0) {
            throw new IllegalArgumentException("Port cannot be negative number. ");
        }
    }

    public void start() {
        server.start();
        System.out.println("Server working...");
    }

    public void stop() {
        server.stop(0);
    }
}
