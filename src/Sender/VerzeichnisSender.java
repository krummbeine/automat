package Sender;

import Automat.Automat;
import Automat.Symbol;


/**
 * @author 846995
 */
public class VerzeichnisSender {
    private Automat automat;

    public VerzeichnisSender(Automat automat){
        this.automat = automat;
    }

    public void schleife() {
        while(true) {
            String jsonString = automat.sender.ausQueueNehmen();

            if (jsonString != null){
                Symbol ausgabeSymbol = automat.jsonService.jsonToSymbol(jsonString);

                if(ausgabeSymbol != null) {
                    //System.out.println(".msg - " + jsonString);
                    automat.dateiVerwaltung.erstelleMessageDatei(ausgabeSymbol);
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
