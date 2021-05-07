
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

public class MatrixClient {
    static String [] services = {"https://matrix-1-fuuw52yj3a-uc.a.run.app", "https://matrix-2-fuuw52yj3a-uc.a.run.app", "https://matrix-3-fuuw52yj3a-uc.a.run.app", "https://matrix-5-fuuw52yj3a-uc.a.run.app", "https://matrix-4-fuuw52yj3a-uc.a.run.app", "https://matrix-6-fuuw52yj3a-uc.a.run.app", "https://matrix-7-fuuw52yj3a-uc.a.run.app", "https://matrix-8-fuuw52yj3a-uc.a.run.app", "https://matrix-9-fuuw52yj3a-uc.a.run.app", "https://matrix-10-fuuw52yj3a-uc.a.run.app", "https://matrix-11-fuuw52yj3a-uc.a.run.app", "https://matrix-12-fuuw52yj3a-uc.a.run.app", "https://matrix-13-fuuw52yj3a-uc.a.run.app", "https://matrix-14-fuuw52yj3a-uc.a.run.app", "https://matrix-15-fuuw52yj3a-uc.a.run.app", "https://matrix-16-fuuw52yj3a-uc.a.run.app"};

    static ArrayList<String> serviceList = new ArrayList<>();
    public static Integer[][] extractArray(Integer[][] src, int xSrc, int ySrc, int n) {
        Integer[][] ret = new Integer[n][n];
        for (int i = 0; i < n; i ++) {
            for (int j = 0; j < n ; j ++) {
                ret[i][j] = src[xSrc + i][ySrc + j];
            }
        }
        return ret;
    }
    public static void main(String[] args) {
        
        
        String baseName = "message-passing-";

        for (int i = 1; i <= 16; i ++) {
            String tmp = "https://" + baseName + i + "-fuuw52yj3a-uc.a.run.app";
            serviceList.add(tmp);
        }
            
        try {
            int n = 256;
            int threadN = 2;
            Integer[][] a = new Integer[n][n];
            Integer[][]b = new Integer[n][n];
            MatMultiplyThread[][] threads = new MatMultiplyThread[threadN][threadN];

            for (int i = 0; i < n; i ++) {
                for (int j = 0;  j < n; j ++){
                    a[i][j] = 1;
                    b[i][j] = 1;
                }
            }

            int threadSize = n/threadN;
            int threadCount = 0;        
            for (int i = 0; i < n; i += threadSize) {
                for ( int j = 0; j < n; j +=threadSize) { //loop through all blocks
                    MatMultiplyThread temp;
                    DockerList<DockerMatrix> aCols = new DockerList<>();
                    DockerList<DockerMatrix> bRows = new DockerList<>();
                    for (int k = 0; k < n; k += threadSize) {
                        Integer[][] subA = extractArray(a, k, j, threadSize);
                        Integer[][] subB = extractArray(b, i, k, threadSize);
                        aCols.add(new DockerMatrix(threadSize, subA));
                        bRows.add(new DockerMatrix(threadSize, subB));
                    }
                    System.out.println("initializing:" + i/threadSize + ", " + j/threadSize);
                    threads[i/threadSize][j/threadSize] = new MatMultiplyThread(aCols, bRows, threadN, serviceList.get(threadCount));
                    threadCount ++;
                    
                }
            }

            long startTime = System.nanoTime();
        
            for (int i = 0; i < threadN; i ++){
                for (int j = 0; j < threadN; j ++){
                    threads[i][j].start();
                    
                }
            }
            for (int i = 0; i < threadN; i ++){
                for (int j = 0; j < threadN; j ++){
                    threads[i][j].join();
                }
            }
            long endTime = System.nanoTime();
            long duration = (endTime - startTime)/1000000;
            System.out.println("Finished Parallel Execution in: " + duration);
            
            for (int i = 0; i < threadN; i ++){
                for (int j = 0; j < threadN; j ++){
                    //System.out.println(threads[i][j].getReturn());
                    
                }
            }
            

            DockerMatrix aWrapper = new DockerMatrix(n, a);
            DockerMatrix bWrapper = new DockerMatrix(n, b);
            //System.out.println(aWrapper);
            DockerList<DockerMatrix> aRows = new DockerList<>();
            DockerList<DockerMatrix> bCols = new DockerList<>();
            aRows.add(aWrapper);
            bCols.add(bWrapper);
            MatMultiplyThread test = new MatMultiplyThread(aRows, bCols, 1);

            startTime = System.nanoTime();
            test.execute();
            endTime = System.nanoTime();
            duration = (endTime - startTime)/1000000;
            
            DockerMatrix result = (DockerMatrix)test.getReturn();
            System.out.println("Finished Serial execution in: " + duration);
            
            
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println(e);
        }
    }

}
