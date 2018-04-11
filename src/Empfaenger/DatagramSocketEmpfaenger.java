package Empfaenger;
import Automat.*;

import java.io.IOException;
import java.net.*;
import java.text.DateFormat;
import java.util.Date;

/**
 * DatagramSocket-Emfpänger (Paketorientiert, UDP/IP)
 * @author 846995
 */
public class DatagramSocketEmpfaenger {
    private Automat automat;
    private int port;

    public DatagramSocketEmpfaenger(Automat automat, int port){
        this.automat = automat;
        this.port = port;
    }

    public void schleife() {
        try {
            DatagramSocket ds = new DatagramSocket();
            // ds.setSoTimeout(5500);

            DatagramPacket packet =
                    new DatagramPacket(new byte[255], 255,
                            InetAddress.getLocalHost(), port);

            while(true) {
                // Verbinden
                ds.send(packet);

                // Packet empfangen
                ds.receive(packet);

                // Empfangenen json-String in EingabeQueue legen
                String empfangen = new String(packet.getData());

                System.out.println("Empfangen: >" + empfangen + "<");

                if(empfangen != null) {

                    Symbol checkSymbol = automat.jsonService.jsonToSymbol(empfangen);

                    if(checkSymbol.getTyp().equals("request")) {
                        automat.empfaenger.inQueueHineinlegen(empfangen);
                    }
                    else{
                        System.out.println("Response aussortiert: " + empfangen);
                    }
                }
            }
            //ds.close();
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
