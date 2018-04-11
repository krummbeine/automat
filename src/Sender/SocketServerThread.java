package Sender;

import Automat.Automat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

/**
 * @author 846995
 */
public class SocketServerThread implements Runnable{
    private Socket socket = null;
    private Automat automat;

    public SocketServerThread(Socket socket, Automat automat) {
        this.socket = socket;
        this.automat = automat;
    }

    public void run() {
        OutputStream os = null;
        InputStream is = null;
        try
        {
            os = socket.getOutputStream();
            is = socket.getInputStream();

            // Json-String zum Senden aus Queue holen:
            String zuSenden = automat.sender.ausQueueNehmen();
            byte[] message = zuSenden.getBytes();

            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

            // An dem unter diesen Thread angemeldeten Client senden:
            dOut.writeInt(message.length);
            dOut.write(message);

            //os.write(test.getBytes());
        }
        catch (IOException e) {}
        finally {
            try {
                is.close();
                os.close();
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
