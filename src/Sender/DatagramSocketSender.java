package Sender;
import Automat.*;

import java.io.IOException;
import java.net.*;

/**
 * DatagramSocket-Sender (Packetorientiert, UDP/IP)
 * @author 846995
 */
public class DatagramSocketSender {
    private Automat automat;
    private int port;
    private boolean startSymbolGesendet;

    public DatagramSocketSender(Automat automat, int port){
        this.automat = automat;
        this.port = port;
        this.startSymbolGesendet = false;
    }

    public void schleife() {
        try {
            DatagramSocket ds = new DatagramSocket(port);
            //ds.setSoTimeout(5000);

            DatagramPacket packet = new DatagramPacket(new byte[255], 255);

            while (true) {
                String zuSenden = automat.sender.ausQueueNehmen();

                if(zuSenden != null) {

                    ds.receive(packet);

                    packet.setData(zuSenden.getBytes());

                    System.out.println("Gesendet: " + zuSenden);

                    ds.send(packet);
                }

                Thread.sleep(500);
            }
            //ds.close();
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
