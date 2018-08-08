package Persistencia;

/**
 *
 * @author Bernardo Copstein
 */
public class CpfInexistenteException extends Exception{
    public CpfInexistenteException(String msg){
        super(msg);
    }
}
