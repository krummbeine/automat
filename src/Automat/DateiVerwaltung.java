package Automat;

import Automat.Automat;
import com.google.gson.Gson;

import java.io.*;

/**
 * Die Funktionen zum Lesen und Schreiben der Message-Dateien in den input- oder output-Ordner
 * @author 846995
 */
public class DateiVerwaltung {
    private static int output_counter;
    private static int input_counter;
    private static Automat automat;

    public DateiVerwaltung(Automat automat){
        output_counter = input_counter = 0;
        this.automat = automat;
    }

    public static void schreibeInDatei(String jsonString, String pfad){
        try {
            FileWriter fw = new FileWriter(pfad);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(jsonString);
            bw.close();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Wandelt das Symbol in einen Json-String um
     * Dieser wird in eine Datei geschrieben
     * Die Datei wird entweder im Ordner input oder output erstellt,
     * abhängig ob das Symbol den Typ Request (input) oder Response (output) hat
     */
    public static void erstelleMessageDatei(Symbol symbol){
        String jsonString = automat.jsonService.symbolToJsonString(symbol);

        if(symbol.getTyp().equals("request"))
            schreibeInDatei(jsonString, "src/input/symbol_" + input_counter++ + ".msg");

        if(symbol.getTyp().equals("response"))
            schreibeInDatei(jsonString, "src/output/symbol_" + output_counter++ + ".msg");
    }

    /**
     * Ließt die Datei im VerzeichnisEmpfaenger Input, löscht sie darauf und
     * gibt den in ihr enthaltenden Json-String zurück
     */
    public static String leseInputMessage(String dateiName){
        try {
            FileReader fileReader = new FileReader("src/input/" + dateiName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            bufferedReader.close();

            File msgDatei = new File("src/input/" + dateiName);
            msgDatei.delete();

            return line;
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}
