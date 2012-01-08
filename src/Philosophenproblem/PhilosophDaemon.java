
package Philosophenproblem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin Kamleithner
 * @class 3BHDV
 * @Katalog 14
 */
public class PhilosophDaemon extends Thread {

    private final Fork[] f;
    private final Philosoph[] p;
    //Erzeugt einen neuen DaemonThread mit übergebenen Gabeln und Philosophen
    public PhilosophDaemon(Fork[] f, Philosoph[] p) {
        this.setDaemon(true);
      
        this.f = f;
        this.p = p;

    }
    /**
     * Gibt laufend Informationen über die Philosophen aus.
     */
    @Override
    public void run() {

        while (true) {

          /*  System.out.print("Es denken " + Philosoph.getNumberOfPhilosophsThinking() + " Philosophen: [");
            for (int i = 0; i < p.length; i++) {
                if (p[i].isThinking()) {
                    System.out.print((i+1) + " ");
                }

            }
            System.out.println("]"); */

            System.out.print("Es essen " + Philosoph.getNumberOfPhilosophsEating() + " Philosophen [");
            for (int i = 0; i < p.length; i++) {
                if (p[i].isEating()) {
                    System.out.print((i+1) + " ");
                }

            }
            System.out.println("]");

/*
            System.out.print("Es warten " + Philosoph.getNumberOfPhilosophsWaiting() + " Philosophen [");
            for (int i = 0; i < p.length; i++) {
                if (p[i].isWaiting()) {
                    System.out.print(i + " ");
                }

            }
 
 
            System.out.println("]"); */
            System.out.print("Gabeln [");
            for (int i = 0; i < f.length; i++) {
                if (f[i].isUsed()) {
                    System.out.print(i+1 + " ");
                }

            }

            System.out.println("] sind belegt!");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }


    }
}
