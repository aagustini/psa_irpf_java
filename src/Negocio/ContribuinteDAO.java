package Negocio;

import Persistencia.CpfDuplicadoException;
import Persistencia.CpfInexistenteException;
import Persistencia.DAOException;
import java.util.List;

/**
 *
 * @author Bernardo Copstein
 */
public interface ContribuinteDAO {
    void inserir(Contribuinte c) throws CpfDuplicadoException, DAOException;
    void alterar(Contribuinte c) throws CpfInexistenteException, DAOException;
    Contribuinte buscar(String cpf) throws DAOException;
    List<Contribuinte> buscarIdosos() throws DAOException;
    List<Contribuinte> buscarTodos() throws DAOException;
}
