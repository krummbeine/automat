package Sender;
import Automat.Automat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

/**
 * Socket-Sender (Verbindungsorientiert, TCP/IP)
 * Multi-Threaded Server
 * @author 846995
 */
public class SocketSender {
    private Automat automat;
    private int port;

    public SocketSender(Automat automat, int port){
        this.automat = automat;
        this.port = port;
    }

    public void schleife() {
        try
        {
            ServerSocket s = new ServerSocket(port);
            while (true)
            {
                Socket socket = s.accept();
                SocketServerThread socketServerThread = new SocketServerThread(socket, automat);
                new Thread(socketServerThread).start();
            }
            //s.close();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
