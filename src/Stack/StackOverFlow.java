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
public class StackOverFlow extends RuntimeException{

    public StackOverFlow(){

    }

    public StackOverFlow(String msg){
        super(msg);
    }

    @Override
    public String toString(){
        return super.getMessage();
    }

}
