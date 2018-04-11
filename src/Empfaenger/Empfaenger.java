package Empfaenger;
import Automat.Automat;

/**
 * Startet einen (mehrere) Empfänger in einem Thread, der in der Klasse Automat definiert wurde(n).
 * @author 846995
 */
public class Empfaenger {
    private Automat automat;

    public Empfaenger(Automat automat){
        this.automat = automat;
        this.start();
    }

    private void start(){
        Thread empfaengerThread = new Thread( new Runnable() {
            public void run() {
                automat.empfaenger.schleife();
            }
        });
        empfaengerThread.start();
    }

    public void inQueueHineinlegen(String jsonString){
        automat.eingabeQueue.hineinlegen(jsonString);
    }
}
