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
public class Stack<E> {

    private E[] s; // Datenfeld
    private int tos = -1; // Index des obersten Elementes, -1=leer

    public Stack(int n) {
        if (n > 0) {
            s = (E[]) new Object[n] ;
        } else {
            throw new EmptyStackException("Größe <= 0! ");
        }
    }
/**
 * public void push()
 * Push das Element auf den TOS.
 * Wenn der Stack voll ist, wird eine StackOverFlowException geworfen.
 */
    public  void push(E o) {
        try {
            tos++;
            s[tos] = o;
        } catch (IndexOutOfBoundsException e) {
            throw new StackOverFlow(e.getMessage());
        }
    }
/**
 * public E pop()
 * Liefert das oberste Element am Stack zurück.
 * Wirft eine EmptyStackException, wenn der Stack leer ist.
 * @return Top of Stack
 */
    public E pop() { // entfernt und liefert das oberste Objekt
        try {
            tos--;
            return s[tos + 1];
        } catch (IndexOutOfBoundsException e) {
            throw new EmptyStackException(e.getMessage());
        }
    }
/**
 * Liefert true, wenn der Stack voll ist.
 *
 */
    public boolean isFull() { 
        if (this.tos + 1 >= s.length) {
            return true;
        }
        return false;
    }
/**
 * Liefert true, wenn der Stack leer ist.
 *
 */
    public boolean isEmpty() { // liefert true, wenn der Stack voll ist
        return tos == -1;
    }
}
