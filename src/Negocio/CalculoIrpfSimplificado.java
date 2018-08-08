package Negocio;

/**
 *
 * @author Bernardo Copstein
 */
public class CalculoIrpfSimplificado implements CalculoIrpf {
    @Override
    public double calculaImposto(Contribuinte c) {
        double baseDeCalculo = c.getTotRend() - c.getContrPrev();
        double descPadrao = baseDeCalculo * 0.5;
        baseDeCalculo = baseDeCalculo - descPadrao;
        double impPagar;
        if (baseDeCalculo <= 12000.0) {
            impPagar = 0;
        } else if ((baseDeCalculo >= 12000.0) && (baseDeCalculo < 24000.0)) {
            impPagar = (baseDeCalculo - 12000.0) * 0.15;
        } else {
            double p1 = (23999.0 - 12000.0) * 0.15;
            double p2 = (baseDeCalculo - 23999.0) * 0.275;
            impPagar = p1 + p2;
        }
        return (impPagar);
    }    
}
