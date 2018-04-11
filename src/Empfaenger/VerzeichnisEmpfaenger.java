package Empfaenger;

import Automat.Automat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

/**
 * Überwacht den input-Ordner
 * Wird eine neue Datei erstellt, wird sie gelesen und der in ihr enthaltende
 * Json-String der EingabeQueue übergeben. Nach dem Lesen der Datei, wird diese gelöscht.
 * @author 846995
 */
public class VerzeichnisEmpfaenger implements Runnable {
    private WatchService watcher;
    private Automat automat;

    public VerzeichnisEmpfaenger(WatchService watcher, Automat automat) {
        this.watcher = watcher;
        this.automat = automat;
    }

    @Override
    public void run() {
        try {
            WatchKey key = watcher.take();
            while(key != null) {
                for (WatchEvent event : key.pollEvents()) {

                    if(event.kind() == ENTRY_CREATE) {
                        // Datei wurde im VerzeichnisEmpfaenger input erstellt !
                        String dateiName = event.context().toString();
                        // Json-String aus Datei laden und Queue hinzufügen
                        jsonStringAusDateiQueueHinzufuegen(dateiName);
                    }

                }
                key.reset();
                key = watcher.take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Json-String aus Datei laden und Queue hinzufügen
     */
    private void jsonStringAusDateiQueueHinzufuegen(String dateiName){
        String jsonString = automat.dateiVerwaltung.leseInputMessage(dateiName);
        automat.empfaenger.inQueueHineinlegen(jsonString);
    }
}
