/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Stack;


/**
 *
 * @author Martin Kamleithner
 * @class 3BHDV
 * @Katalog 14
 */
public class Main {

    public static int STACK_SIZE=20;
    public static int MAX_SLEEP_TIME_CONSUMER=10000;
    public static int MAX_SLEEP_TIME_PRODUCER=1000, MAX_PUSH_SIZE=100;


    public static void main(String[] args) {
        Stack s= new Stack(Main.STACK_SIZE);
        new Producer(s,Main.MAX_SLEEP_TIME_PRODUCER,Main.MAX_PUSH_SIZE).start();
        new Consumer(s,Main.MAX_SLEEP_TIME_CONSUMER).start();
    }

}
