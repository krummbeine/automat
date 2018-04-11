package Automat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author 846995
 */
public class Queue {
    private final BlockingQueue queue = new ArrayBlockingQueue(1024);
    private static final int KAPAZITAET = 1024;
    private int index;

    public Queue() {
        index = 0;
    }

    public synchronized void hineinlegen(String x) {
        try {
            if(index < KAPAZITAET && x != null) {
                index++;
                System.out.println("in: " + x);
                queue.put(x);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized String nehmen() {
        if(index > 0) {
            try {
                String message = (String) queue.take();
                index--;
                System.out.println("out: " + message);
                return message;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
