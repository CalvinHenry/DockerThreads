

//import DockerInteger;
//import DockerList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.net.*;
import java.io.*;
import java.util.stream.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;

public class Main {

    public static void main(String[] args) {


        Path inFile = Path.of("input.txt");
        try {
            String content = Files.readString(inFile);
            //String content = "rO0ABXNyABVjb20uY29tcGFueS5TdW1UaHJlYWQAAAAAAAAAAQIAAkkAA3N1bUwACWxpc3RUb1N1bXQAGUxEb2NrZXJUaHJlYWQvRG9ja2VyTGlzdDt4cgAZRG9ja2VyVGhyZWFkLkRvY2tlclRocmVhZKwSWU5n1hAxAgAAeHAAAAAAc3IAF0RvY2tlclRocmVhZC5Eb2NrZXJMaXN0i8WS0dHVLHUCAAFMAARsaXN0dAAVTGphdmEvdXRpbC9BcnJheUxpc3Q7eHIAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAN3BAAAAANzcgAaRG9ja2VyVGhyZWFkLkRvY2tlckludGVnZXJLvxyN/jeWFAIAAUwABXZhbHVldAATTGphdmEvbGFuZy9JbnRlZ2VyO3hwc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAJzcQB+AAhzcQB+AAsAAAADc3EAfgAIc3EAfgALAAAABHhw";
            DockerThread test = (DockerThread) buildThread(content);
            test.execute();

            System.out.println(test.getReturn().serialize());
        } catch (Exception e) {
            System.out.println(e);
        }
        
            /*ServerSocket server = new ServerSocket(8080);
            Socket conn = server.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //String request = reader.lines().collect(Collectors.joining());
            String line = reader.readLine();
            //String line = reader.readLine();
            //System.out.println("output: " + line);
            while(line.compareTo("") != 0) {
                line = reader.readLine();
                System.out.println("output: " + line);

            }
            while(line.compareTo("") != 0) {
                line = reader.readLine();
                System.out.println("output: " + line);

            }
            //System.out.println("Http request:" + request);
            // don't use buffered writer because we need to write both "text" and "binary"
            OutputStream out = conn.getOutputStream();

        //Path threadFile = Path.of("thread.txt");
        
            //System.out.println(t.serialize());
            //String content = Files.readString(threadFile);
            String content = "rO0ABXNyAAlTdW1UaHJlYWQAAAAAAAAAAQIAAkkAA3N1bUwACWxpc3RUb1N1bXQADExEb2NrZXJMaXN0O3hyAAxEb2NrZXJUaHJlYWQS4u1Fzf0vcwIAAHhwAAAAAHNyAApEb2NrZXJMaXN00plKbzvDC/YCAAFMAARsaXN0dAAVTGphdmEvdXRpbC9BcnJheUxpc3Q7eHIAE2phdmEudXRpbC5BcnJheUxpc3R4gdIdmcdhnQMAAUkABHNpemV4cAAAAAN3BAAAAANzcgANRG9ja2VySW50ZWdlcvNQXrjTqS8tAgABTAAFdmFsdWV0ABNMamF2YS9sYW5nL0ludGVnZXI7eHBzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAnNxAH4ACHNxAH4ACwAAAANzcQB+AAhzcQB+AAsAAAAEeHA=";
            SumThread test = (SumThread) buildThread(content);
            test.execute();

            System.out.println(test.getReturn().getValue());
        } catch (Exception e) {
            System.out.println(e);
        }*/


    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("Got a message");
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


    public static Object buildThread(String s) throws IOException, ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

}
