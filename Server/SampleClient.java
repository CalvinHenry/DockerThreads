
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

public class SampleClient {

    public static void main(String[] args) {

        

        
        try {
            long overheadSum = 0;
            for (int j = 0; j < 10; j ++) {
                
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = 0; i < 100; i ++) {
                    list.add(i);
                }

                SumThread sumInstance = new SumThread(list);
                sumInstance.start();

                sumInstance.join();
                System.out.println(sumInstance.getReturn());

                
                long startTime = System.nanoTime();
                
                
                long endTime = System.nanoTime();
                long duration = (endTime - startTime)/1000000;
                
                overheadSum += duration;
            }
            System.out.println("milliseconds:" + overheadSum/10);
            //System.out.println(test.getReturn().getValue());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
