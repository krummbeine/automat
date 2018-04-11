package Empfaenger;

import Automat.Automat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

/**
 * Startet in einem Thread einen WatchService zur Überwachung des input-Ordners
 * @author 846995
 */
public class VerzeichnisWatchService {
    private Automat automat;

    public VerzeichnisWatchService(Automat automat){
        this.automat = automat;
    }

    /**
     * WatchServiceThread
     */
    public void schleife() {
        Path verzeichnis = Paths.get("src/input");
        WatchService watchService = null;
        try {
            watchService = verzeichnis.getFileSystem().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VerzeichnisEmpfaenger inputOrdnerUeberwachung = new VerzeichnisEmpfaenger(watchService, automat);
        Thread th = new Thread(inputOrdnerUeberwachung);
        th.start();

        try {
            verzeichnis.register(watchService, ENTRY_CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
