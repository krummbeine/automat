package Automat;

import Automat.Automat;
import Automat.Symbol;
import com.google.gson.Gson;

/**
 * Mealy-Maschine
 * Erhält alle Eingaben von EingabeQueue
 * Im eigenem Thread ausgeführt
 * Ließt Json-String aus EingabeQueue, Umwandlung in EingabeSymbol
 * Führt Zustandsübergang mit diesem EingabeSymbol aus
 * @author 846995
 */
public class MealyMaschine {
    private Automat automat;

    public MealyMaschine(Automat automat){
        this.automat = automat;

        Thread mealyMaschineThread = new Thread( new Runnable() {
            public void run() {
                try {
                    schleife();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // Mealy-Maschine starten
        mealyMaschineThread.start();
    }

    /**
     * Mealy-Maschine Thread
     */
    public void schleife() throws InterruptedException {
        while(true) {
            // Json-String aus EingabeQueue
            String jsonString = automat.eingabeQueue.nehmen();

            if (jsonString != null){
                // In EingabeSymbol umwandeln
                Symbol eingabeSymbol = automat.jsonService.jsonToSymbol(jsonString);

                if(eingabeSymbol != null) {
                    // ZustandsUebergang mit EingabeSymbol
                    automat.aktuellerZustand = automat.uebergangsFunktion.uebergang(automat.aktuellerZustand, eingabeSymbol);

                    // AusgabeSymbol(e) ermitteln
                    Symbol[] ausgabeSymbole = automat.ausgabeFunktion.ausgabe(automat.aktuellerZustand, eingabeSymbol);

                    // AusgabeSymbol(e) als Json-String(s) in AusgabeQueue legen
                    for (int i = 0; i < ausgabeSymbole.length; i++)
                        automat.ausgabeQueue.hineinlegen(automat.jsonService.symbolToJsonString(ausgabeSymbole[i]));
                }
            }
            Thread.sleep(100);
        }
    }


}