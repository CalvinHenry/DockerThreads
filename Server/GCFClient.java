
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

public class GCFClient {

    public static void main(String[] args) {

        /*DockerList<DockerInteger> list = new DockerList<DockerInteger>();
        String content = "rO0ABXNyAA1Eb2NrZXJJbnRlZ2Vy9b55npVyvxsCAAFMAAV2YWx1ZXQAE0xqYXZhL2xhbmcvSW50ZWdlcjt4cHNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAAJ";
        
        list.add(new DockerInteger(2));
        list.add(new DockerInteger(3));
        list.add(new DockerInteger(4));*/
        

        GCFThread gThread = new GCFThread(new DockerInteger(479001600));
        try {

            
            //DockerInteger in = (DockerInteger)DockerThread.buildThread(content);
            //System.out.println(in.getValue());
            gThread.run();
            System.out.println(gThread.getReturn().getValue());
            
        } catch (Exception e) {

        }
    }

    

}
