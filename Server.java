import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.nio.file.Files;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        server.createContext("/", new StaticFileHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        
        System.out.println("=========================================");
        System.out.println(" DODOLAN GITAR Web Server is Running!");
        System.out.println(" Server started at: http://localhost:" + port);
        System.out.println(" Press Ctrl+C to stop.");
        System.out.println("=========================================");
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String path = t.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }
            
            // Dapatkan current working directory
            String root = System.getProperty("user.dir");
            File file = new File(root, path);
            
            if (file.exists() && !file.isDirectory()) {
                String mime = getMimeType(path);
                t.getResponseHeaders().set("Content-Type", mime);
                t.sendResponseHeaders(200, file.length());
                
                try (OutputStream os = t.getResponseBody()) {
                    Files.copy(file.toPath(), os);
                }
            } else {
                String response = "404 (Not Found)\nFile not found: " + path;
                t.sendResponseHeaders(404, response.length());
                try (OutputStream os = t.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
        
        private String getMimeType(String path) {
            if (path.endsWith(".html") || path.endsWith(".htm")) return "text/html; charset=UTF-8";
            if (path.endsWith(".css")) return "text/css; charset=UTF-8";
            if (path.endsWith(".js")) return "application/javascript; charset=UTF-8";
            if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
            if (path.endsWith(".png")) return "image/png";
            return "text/plain";
        }
    }
}
