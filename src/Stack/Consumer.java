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
public class Consumer extends Thread {

    private final Stack<Integer> s;
    private Random r = new Random();
    private int maxSleepTime;

    public Consumer(Stack s, int maxSleepTime) {
        this.s=s;
        this.maxSleepTime = maxSleepTime;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (s) {

                while(s.isEmpty()){
                    try {
                        System.err.println("Consumer wartet!");
                        s.wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    System.err.println("Consumer startet wieder!");
                }
                System.out.println(s.pop() + " wurde gepoppt!");
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
