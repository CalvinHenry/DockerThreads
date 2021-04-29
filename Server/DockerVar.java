

import java.io.IOException;
public interface DockerVar {

    Object getValue();

    String serialize() throws IOException;

    
}
