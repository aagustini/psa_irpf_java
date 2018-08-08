/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Negocio.Contribuinte;
import Negocio.IrpfException;
import Negocio.IrpfFachada;
import Negocio.TipoCalculo;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author Bernardo Copstein
 */
public class CtrlGUI {
    private IrpfFachada fachadaIrpf;
    private String contribuinteAtual;
    
    public CtrlGUI() throws IOException, IrpfException{
        fachadaIrpf = new IrpfFachada();
    }

    public void salva(String sNome,String sCpf,String sIdade,String sNroDep,String sContrPrev,String sTotRend) throws IrpfException, NumberFormatException {
        int idade = Integer.parseInt(sIdade);
        int nroDep = Integer.parseInt(sNroDep);
        double contrPrev = Double.parseDouble(sContrPrev);
        double totRend = Double.parseDouble(sTotRend);
        fachadaIrpf.salva(sNome, sCpf, idade, nroDep, contrPrev, totRend);
        setCorrente(sCpf);
    }

    public ListModel getContribuintes() throws IrpfException{
        DefaultListModel lm = new DefaultListModel();
        for(Contribuinte c:fachadaIrpf.getContribuintes()){
            lm.addElement(c);
        }
        return(lm);
    }

    public ListModel getContribuintesIdosos() throws IrpfException{
        DefaultListModel lm = new DefaultListModel();
        for(Contribuinte c:fachadaIrpf.getContribuintesIdosos()){
            lm.addElement(c);
        }
        return(lm);
    }

    public void setCorrente(String cpfContribuinte){
        contribuinteAtual = cpfContribuinte;
    }
    
    public String getCorrente(){
        return contribuinteAtual;
    }

    public double calcula(TipoCalculo tc) throws ContribuinteInexistenteException, IrpfException {
        if(contribuinteAtual == null) {
            throw new ContribuinteInexistenteException();
        }
        return fachadaIrpf.calcula(tc, getCorrente());
    }
}
