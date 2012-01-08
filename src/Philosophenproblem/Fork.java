
package Philosophenproblem;

/**
 *
 * @author Martin Kamleithner
 * @class 3BHDV
 * @Katalog 14
 */
public class Fork {
    //Gibt an, ob die Gabel derzeit verwendet wird
    private boolean isUsed;

    public Fork(){};

    public Fork(boolean isUsed){
        this.isUsed=isUsed;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }



}
