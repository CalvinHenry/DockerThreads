
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
        
        
        
            
        try {
            int n = 1024;
            int threadN = 4;
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
                    threads[i/threadSize][j/threadSize] = new MatMultiplyThread(aCols, bRows, threadN);
                    
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
            System.out.println(e);
        }
    }

}
