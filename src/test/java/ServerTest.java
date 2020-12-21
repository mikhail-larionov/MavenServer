import com.company.Server.Connection;
import com.company.Server.Server;
import com.company.Server.Users;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

public class ServerTest {
    Server server = new Server();
    static Connection connection;
    @Before
    public void initConnection() throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                server.start();
                server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();



    }
    @Test
    public void serverTest() throws IOException, InterruptedException {
        connection = new Connection(new Socket("localhost", 3345));
        Thread.sleep(100);
        Assert.assertTrue(server.getUsers().getUsers().size() > 0);
    }
}
