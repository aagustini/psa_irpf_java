/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

/**
 *
 * @author Júlio
 */
public class IrpfException extends Exception{

    public IrpfException(Throwable cause) {
        super(cause);
    }

    public IrpfException(String message, Throwable cause) {
        super(message, cause);
    }

    public IrpfException(String message) {
        super(message);
    }

    public IrpfException() {
    }
    
}
