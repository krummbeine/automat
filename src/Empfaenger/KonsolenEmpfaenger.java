package Empfaenger;

import Automat.Automat;
import Automat.Symbol;

import java.io.IOException;
import java.util.Scanner;

/**
 * Ließt einen String von der KonsolenEmpfaenger mit Scanner,
 * sucht ein zu diesem EingabeString gültiges Symbol und
 * wandelt es in einen Json-String um, der in die EingabeQueue gelegt wird
 *
 * Alternativ: ablegen des gelesenen Strings in eine Message-Datei im Ordner input
 * @author 846995
 */
public class KonsolenEmpfaenger {
    private Automat automat;
    private Scanner scanner;

    public KonsolenEmpfaenger(Automat automat){
        this.automat = automat;
        scanner = new Scanner(System.in);
    }

    public void schleife() {
        System.out.println("Automat gestartet. Dein Eingabe-String:");

        while(true){
            // Eingabe
            Symbol eingabeSymbol = tastaturEingabeAbfragen();
            String jsonEingabeString = automat.jsonService.symbolToJsonString(eingabeSymbol);

            if(eingabeSymbol != null) {
                automat.empfaenger.inQueueHineinlegen(jsonEingabeString);

                // Alternativ: Es wird eine Datei im VerzeichnisEmpfaenger input angelegt:
                // automat.dateiVerwaltung.erstelleMessageDatei(eingabeSymbol);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ließt den eingegebenen String und sucht das passende EingabeSymbol
     * aus der Datenbank der Mealy-Maschine. Wird keines gefunden, ist das
     * Eingabesymbol / der EingabeString ungueltig.
     */
    private Symbol tastaturEingabeAbfragen(){
        String tastaturEingabe = scanner.nextLine();
        for(int i = 0; i < automat.datenbank.symbolTabelle.size(); i++)
            if(automat.datenbank.symbolTabelle.get(i).getPayload().equals(tastaturEingabe))
                return automat.datenbank.symbolTabelle.get(i);

        System.out.println("Ungueltiges Eingabezeichen");
        return null;
    }
}
