import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class CalculatorHttpServer {

    public static double calculate(double a, double b, String operation) {

        switch (operation) {

            case "add":
                return a + b;

            case "subtract":
                return a - b;

            case "multiply":
                return a * b;

            case "divide":
                if (b == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return a / b;

            default:
                throw new IllegalArgumentException("Invalid operation");
        }
    }

    public static void main(String[] args) throws IOException {

        HttpServer server =
                HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/calculate",
                CalculatorHttpServer::handleRequest);

        server.setExecutor(null);

        System.out.println(
                "Calculator HTTP Server started on port 8080");

        server.start();
    }

    private static void handleRequest(HttpExchange exchange)
            throws IOException {

        URI uri = exchange.getRequestURI();

        Map<String, String> params =
                parseQuery(uri.getQuery());

        String operation = params.get("operation");
        String aValue = params.get("a");
        String bValue = params.get("b");

        String response;

        try {

            double a = Double.parseDouble(aValue);
            double b = Double.parseDouble(bValue);

            double result =
                    calculate(a, b, operation);

            response = "Result: " + result;

        } catch (Exception e) {

            response = "Error: " + e.getMessage();
        }

        exchange.sendResponseHeaders(
                200, response.length());

        OutputStream output =
                exchange.getResponseBody();

        output.write(response.getBytes());

        output.close();
    }

    private static Map<String, String> parseQuery(
            String query) {

        Map<String, String> params =
                new HashMap<>();

        if (query != null) {

            String[] pairs =
                    query.split("&");

            for (String pair : pairs) {

                String[] keyValue =
                        pair.split("=");

                if (keyValue.length == 2) {

                    params.put(
                            keyValue[0],
                            keyValue[1]);
                }
            }
        }

        return params;
    }
}
