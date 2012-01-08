package Philosophenproblem;

import java.util.Random;

/**
 *
 * @author Martin Kamleithner
 * @class 3BHDV
 * @Katalog 14
 */
public class Philosoph extends Thread {

    private static int numberOfPhilosophsEating,  //Anzahl der Essenden Philsophen
                       numberOfPhilosophsThinking,//Anzahl der denkenden Philosophen
                       numberOfPhilosophsWaiting, //Anzahl der wartenden Philosophen
                       numberOfPhilosophs,        //Gesamtanzahl der Philosophen
                       numberOfRounds;            //Anzahl der runden
    private static final int maxEatTime = 5000,   //Maximale Zeit in ms, die ein Philosoph isst
                             maxThinkTime = 1000; //Maximale Zeit in mx, die ein Philosoph denkt
    private static Random rd = new Random();
    private static boolean ausgabe = false;       //Debug Ausgaben
    private int number;                           //ID
    private final Fork leftFork, rightFork;       //Gabeln
    private boolean isThinking, isEating, isWaiting; //Zustand
    private static final Object 
            syncDummyEat = new Object(),
            syncDummyThink = new Object(),
            syncDummyWait = new Object(); //Dummies für Synchronisation
    /**
     * Setzt die Anzahl der Runden, wenn kein Thread läuft, sonst IllegalStateException
     * @param numberOfRounds 
     */
    public static void setNumberOfRounds(int numberOfRounds) {
        if (Philosoph.numberOfPhilosophs != 0) {
            throw new IllegalStateException("Kann Anzahl der Runden nur setzen, wenn kein Thread läuft!");
        }
        Philosoph.numberOfRounds = numberOfRounds;
    }
    /**
     * Erzeugt einen neuen Philosphen mit den übergebenen Gabeln
     * @param leftFork
     * @param rightFork 
     */
    public Philosoph(Fork leftFork, Fork rightFork) {
        this.number = ++numberOfPhilosophs;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {

        for (int i = 0; i < Philosoph.numberOfRounds; i++) {
            //Gabel nehmen
            synchronized (this.leftFork) {
                //Zuerst nur auf die linke Gabel warten
                //Erhöht laut einigen Tests die Performance.
                while (leftFork.isUsed()) {
                    synchronized (Philosoph.syncDummyWait) {
                        Philosoph.numberOfPhilosophsWaiting++;
                    }
                    isWaiting = true;
                    try {
                        leftFork.wait(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    synchronized (Philosoph.syncDummyWait) {
                        Philosoph.numberOfPhilosophsWaiting--;
                    }
                    isWaiting = false;
                }

                synchronized (this.rightFork) {

                    while (rightFork.isUsed() || leftFork.isUsed()) {
                        isWaiting = true;

                        synchronized (Philosoph.syncDummyWait) {
                            Philosoph.numberOfPhilosophsWaiting++;
                        }
                        try {
                            //Abwechselnd beide Gabeln freigeben
                            rightFork.wait(100);
                            leftFork.wait(100);

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        synchronized (Philosoph.syncDummyWait) {
                            Philosoph.numberOfPhilosophsWaiting--;
                        }
                        isWaiting = false;
                    }
                    this.takeLeftFork();
                    this.takeRightFork();
                }
            }
            this.eat();
            //Zurückgeben
            synchronized (this.leftFork) {
                this.putLeftFork();
                this.leftFork.notifyAll();
            }
            synchronized (this.rightFork) {
                this.putRightFork();
                this.rightFork.notifyAll();
            }

            this.think();
            //System.out.println("Philosoph " + this.number + " hat Runde " + (i + 1) + " beendet!");
        }
        System.out.println("Philosoph " + this.number + " ist satt!");
        Philosoph.numberOfPhilosophs--;
    }

    private void takeLeftFork() {
        if (leftFork.isUsed()) {
            System.err.println("Fehler in takeLeftFork!"); //Dürfte nie passieren
        }
        this.leftFork.setUsed(true);
    }

    private void putLeftFork() {
        if (!leftFork.isUsed()) {
            System.err.println("Fehler in putLeftFork!");  //Dürfte nie passieren
        }

        this.leftFork.setUsed(false);
    }

    private void putRightFork() {
        if (!rightFork.isUsed()) {
            System.err.println("Fehler in putRightFork!");  //Dürfte nie passieren
        }
        this.rightFork.setUsed(false);
    }

    private void takeRightFork() {
        if (rightFork.isUsed()) {
            System.err.println("Fehler in takeRightFork!"); //Dürfte nie passieren
        }
        this.rightFork.setUsed(true);
    }
    /**
     * Die "Iss" Aktion des Philosophen.
     */
    private void eat() {

        if (ausgabe) {
            System.out.println("Philosoph " + (this.number) + " isst...");
        }
        isEating = true;
        synchronized (Philosoph.syncDummyEat) {
            Philosoph.numberOfPhilosophsEating++;
        }
        pause(Philosoph.maxEatTime);
        synchronized (Philosoph.syncDummyEat) {
            Philosoph.numberOfPhilosophsEating--;
        }
        isEating = false;
    }

    public static int getNumberOfPhilosophs() {
        return numberOfPhilosophs;
    }

    public static int getNumberOfPhilosophsEating() {
        return numberOfPhilosophsEating;
    }

    public static int getNumberOfPhilosophsThinking() {
        return numberOfPhilosophsThinking;
    }
    /**
     * Die "Denk" Aktion des Philosophen
     */
    private void think() {
        if (ausgabe) {
            System.out.println("Philosoph " + (this.number) + " denkt...");
        }
        isThinking = true;
        synchronized (Philosoph.syncDummyThink) {
            Philosoph.numberOfPhilosophsThinking++;
        }
        pause(Philosoph.maxThinkTime);
        synchronized (Philosoph.syncDummyThink) {
            Philosoph.numberOfPhilosophsThinking--;
        }
        isThinking = false;
    }
    /**
     * Setzt den Thread eine zufällige zeit, maximal jedoch max ms in den
     * sleep Zustand.
     * Wird für eat(), think() verwendet.
     * @param max 
     */
    private void pause(int max) {
        try {
            Thread.sleep(rd.nextInt(max) + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isEating() {
        return isEating;
    }

    public boolean isThinking() {
        return isThinking;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public static int getNumberOfPhilosophsWaiting() {
        return numberOfPhilosophsWaiting;
    }
}


