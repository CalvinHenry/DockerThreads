
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

import java.io.Serializable;

public class DockerMatrix implements DockerVar, Serializable {

    Integer n;
    Integer [][] mat;
    public DockerMatrix(Integer n, Integer[][] mat) {
        this.mat = mat;
        this.n = n;
    }


    public Integer[][] getValue() {
        return mat;
    }
    public Integer size(){
        return n;
    }

    public String toString() {
        String ret = "";
        for (int i = 0; i < n; i ++) {
            for (int j = 0; j < n; j ++) {
                ret += mat[i][j] + "\t";
            }
            ret += "\n";
        }
        return ret;
    }
    public String serialize() throws IOException  {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( this );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
