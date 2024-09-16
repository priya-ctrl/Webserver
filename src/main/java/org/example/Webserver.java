package org.example;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Webserver {
    private static final String TASK_ENDPOINT = "/task";
    private static final String STATUS_EXPERIMENT = "/status";
    private final int port;
    private HttpServer server;

    public Webserver(int port){
        this.port = port;
    }
    //start server initialise all the end points
    public void startServer(){
        try{
            this.server = HttpServer.create(new InetSocketAddress(port),0);
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
        HttpContext statusContext = server.createContext(STATUS_EXPERIMENT);
        HttpContext taskConnect = server.createContext(TASK_ENDPOINT);
        statusContext.setHandler(this::handleStatusCheckRequest);
    }
    private void handleTaskRequest(HttpExchange exchange) throws IOException{
        if(! exchange.getRequestMethod().equalsIgnoreCase("post")){
            exchange.close();
            return;
        }
    Headers headers = exchange.getRequestHeaders();
        if(headers.containsKey("X-Test") && headers.get("X-test").get(0).equalsIgnoreCase("true")){
            
        }
    }
    private void handleStatusCheckRequest(HttpExchange exchange){
    if(!exchange.getRequestMethod().equalsIgnoreCase("get")){
        exchange.close();
        return;
    }
    String responseMessage ="Server is ALIVE";
    sendResponse(responseMessage.getBytes(), exchange);
    }
    private void sendResponse(byte [] responseBytes,HttpExchange exchange){
        exchange.sendResponseHeaders(200,responseBytes.length);
        OutputStream outputStream =exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();

    }
}
