package Sender;//

import Automat.Automat;

/**
 * Startet einen Sender in einem Thread, der in der Klasse Automat definiert wurde.
 * @author 846995
 */
public class Sender {
    private Automat automat;

    public Sender(Automat automat){
        this.automat = automat;
        this.start();
    }

    private void start(){
        Thread t2 = new Thread( new Runnable() {
            public void run() {
                automat.sender.schleife();
            }
        });
        t2.start();
    }

    public String ausQueueNehmen(){
        return automat.ausgabeQueue.nehmen();
    }
}