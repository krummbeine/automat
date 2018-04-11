package Empfaenger;
import Automat.*;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Socket-Empfänger (Verbindungsorientiert, TCP/IP)
 * @author 846995
 */
public class SocketEmpfaenger {
    private Automat automat;
    private int port;

    public SocketEmpfaenger(Automat automat, int port){
        this.automat = automat;
        this.port = port;
    }

    public void schleife() {
        BufferedReader in;
        Socket socket;
        try
        {
            socket =
                    new Socket(InetAddress.getLocalHost(), port);

            DataInputStream dIn = new DataInputStream(socket.getInputStream());

            int length = dIn.readInt();
            if(length>0) {
                byte[] message = new byte[length];
                dIn.readFully(message, 0, message.length);

                // Empfangenen json-String in EingabeQueue legen
                String empfangen = new String(message);

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
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}
