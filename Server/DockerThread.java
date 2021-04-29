

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

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


public class DockerThread extends Thread implements Serializable {

    static String [] services = {"https://matrix-1-fuuw52yj3a-uc.a.run.app", "https://matrix-2-fuuw52yj3a-uc.a.run.app", "https://matrix-3-fuuw52yj3a-uc.a.run.app", "https://matrix-5-fuuw52yj3a-uc.a.run.app", "https://matrix-4-fuuw52yj3a-uc.a.run.app", "https://matrix-6-fuuw52yj3a-uc.a.run.app", "https://matrix-7-fuuw52yj3a-uc.a.run.app", "https://matrix-8-fuuw52yj3a-uc.a.run.app", "https://matrix-9-fuuw52yj3a-uc.a.run.app", "https://matrix-10-fuuw52yj3a-uc.a.run.app", "https://matrix-11-fuuw52yj3a-uc.a.run.app", "https://matrix-12-fuuw52yj3a-uc.a.run.app", "https://matrix-13-fuuw52yj3a-uc.a.run.app", "https://matrix-14-fuuw52yj3a-uc.a.run.app", "https://matrix-15-fuuw52yj3a-uc.a.run.app", "https://matrix-16-fuuw52yj3a-uc.a.run.app"};
    static Integer serviceId = 0;
    DockerVar result;
    String myService;
    @Override
    public void run() {

        synchronized (serviceId) {
            myService = services[serviceId];
            serviceId ++;
        }
        System.out.println("My URL: " + myService);
        //Start Google cloud platform container
        //Connect to it
        //wait for response
        Map<String,String> arguments = new HashMap<>();

        HttpClient client = HttpClient.newHttpClient();
        String lresult = "";
        try {
            this.sleep(100);
            HttpRequest request = HttpRequest.newBuilder()
                    //.uri(URI.create("http://localhost:8080"))
                    //.uri(URI.create(myService))
                    .uri(URI.create("https://instance-version-fuuw52yj3a-uc.a.run.app"))
                    .POST(HttpRequest.BodyPublishers.ofString(this.serialize()))
                    .build();

            HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            lresult = (String)response.body();
            
            this.result = (DockerVar)buildThread(lresult);
        } catch(Exception e){
            System.out.println(e);
        }
        
        System.out.println("Got response");
        //this.execute();
        
    }
 
    public String serialize() throws IOException  {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( this );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public void execute() {

    }

    public DockerVar getReturn() {
        return this.result;    
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
