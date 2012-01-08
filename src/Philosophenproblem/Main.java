

package Philosophenproblem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  
 * @author Martin Kamleithner
 * @class 3BHDV
 * @Katalog 14
 * Zeigt einen Ablauf des Philosophenproblems.
 */
public class Main {

    private static final int NUMBER_OF_PHILSOPHS=30;
    private static final int NUMBER_OF_ROUNDS=10;



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Fork[] f = new Fork[Main.NUMBER_OF_PHILSOPHS];
        Philosoph[] p = new Philosoph[Main.NUMBER_OF_PHILSOPHS];
        long nanos= System.nanoTime();
        //
        Philosoph.setNumberOfRounds(NUMBER_OF_ROUNDS);
        //Fork[] initialisieren
        for (int i=0 ; i<Main.NUMBER_OF_PHILSOPHS; i++) {
            f[i]=new Fork();
        }
        //Threads erzeugen und starten
        for (int i=0;i<Main.NUMBER_OF_PHILSOPHS;i++) {
            p[i]=new Philosoph(f[i], f[(i+1)%Main.NUMBER_OF_PHILSOPHS]);
            p[i].start();
        }
        //Daemon starten
        new PhilosophDaemon(f,p).start();
        //Warten bis alle Threads fertig sind
        for (Philosoph philosoph : p) {
            try {
                philosoph.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }
        nanos=System.nanoTime()-nanos;
        System.out.println("Alle Philosphen sind fertig!\n Benötigte Zeit: "+ nanos/1000000000 +
                " Sekunden bei " +Main.NUMBER_OF_PHILSOPHS + " Philosophen und " + Main.NUMBER_OF_ROUNDS + " Runde(n)");


    }

}
