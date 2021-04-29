
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

public class DockerInteger implements DockerVar, Serializable {

    Integer value;
    public DockerInteger(Integer i) {
        this.value = i;
    }


    public Integer getValue() {
        return value;
    }

    public String serialize() throws IOException  {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( this );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
