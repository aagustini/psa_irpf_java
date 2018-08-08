package Negocio;

import Persistencia.ContribuinteDAODerby;
import Persistencia.ContribuinteTxtFile;
import Persistencia.CpfDuplicadoException;
import Persistencia.DAOException;
import java.util.List;

/**
 *
 * @author Bernardo Copstein
 */
public class IrpfFachada {

    private ContribuinteDAO cadContribuinte;

    public IrpfFachada() throws IrpfException {
        try {
            cadContribuinte = new ContribuinteTxtFile();
            //cadContribuinte = new ContribuinteDAODerby();
        }  catch (Exception ex) {
            throw new IrpfException(ex);
        }
    }

    public void salva(String nome, String cpf, int idade, int nroDep, double contrPrev, double totRend) throws IrpfException {
        if (!ValidadorContribuinte.getInstance().validaNome(nome)) {
            throw new IrpfException("Nome inválido");
        }
        if (!ValidadorContribuinte.getInstance().validaCpf(cpf)) {
            throw new IrpfException("Cpf inválido");
        }
        if (!ValidadorContribuinte.getInstance().validaIdade(idade)) {
            throw new IrpfException("Idade inválida");
        }
        if (!ValidadorContribuinte.getInstance().validaNroDep(nroDep)) {
            throw new IrpfException("NroDep inválido");
        }
        if (!ValidadorContribuinte.getInstance().validaContrPrev(contrPrev)) {
            throw new IrpfException("ContrPrev inválido");
        }
        if (!ValidadorContribuinte.getInstance().validaTotRend(totRend)) {
            throw new IrpfException("TotRend inválido");
        }
        Contribuinte contr = new Contribuinte(nome, cpf, idade, nroDep, contrPrev, totRend);
        try {
            cadContribuinte.inserir(contr);
        } catch (CpfDuplicadoException e) {
            throw new IrpfException(e);
        }catch (DAOException e) {
            throw new IrpfException(e);
        }
    }

    public List<Contribuinte> getContribuintes() throws IrpfException {
        try {
            return cadContribuinte.buscarTodos();
        } catch (Exception e) {
            throw new IrpfException(e);
        }
    }

    public List<Contribuinte> getContribuintesIdosos() throws IrpfException {
        try {
            return cadContribuinte.buscarIdosos();
        } catch (Exception e) {
            throw new IrpfException(e);
        }
    }

    public double calcula(TipoCalculo t, String cpf) throws IrpfException {
        Contribuinte contribuinte;
        try {
            contribuinte = cadContribuinte.buscar(cpf);
        } catch (DAOException ex) {
            throw new IrpfException("Erro: " + ex.getMessage());
        }
        if (contribuinte == null) {
            throw new IrpfException("CPF inexistente: " + cpf);
        }
        CalculoIrpf ir = CalculoIrpfFactory.createInstance(t);
        contribuinte.defineCalculo(ir);
        return contribuinte.getImpostoDevido();
    }
}
