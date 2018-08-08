/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

/**
 *
 * @author Bernardo Copstein
 */
public class CalculoIrpfFactory {
    public static CalculoIrpf createInstance(TipoCalculo t) {
        switch(t) {
            case SIMPLIFICADO:
                return new CalculoIrpfSimplificado();
            case COMPLETO:
                return new CalculoIrpfCompleto();
        }
        return null;
    }
}
