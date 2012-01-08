/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Stack;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Martin Kamleithner
 * @class 3BHDV
 * @Katalog 14
 */
public class Producer extends Thread {

    private final Stack<Integer> s;
    private Random r = new Random();
    private int maxSleepTime, maxPush;

    public Producer(Stack s, int maxSleepTime, int maxPush) {
        this.s = s;
        this.maxSleepTime = maxSleepTime;
        this.maxPush=maxPush;
    }

    @Override
    public void run() {

        int pushWert;

        while (true) {
            synchronized (s) {
                while (s.isFull()) {
                    System.err.println("Producer wartet!");
                    try {
                        s.wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    System.err.println("Producer started wieder!");
                }

                s.push(pushWert=r.nextInt(maxPush));
                System.out.println(""+ pushWert+" wurde gepusht!");
                s.notifyAll();
            }
            try {
                Thread.sleep(r.nextInt(maxSleepTime));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
