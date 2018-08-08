package Persistencia;

import Negocio.ContribuinteDAO;
import Negocio.Contribuinte;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Bernardo Copstein
 */
public class ContribuinteTxtFile implements ContribuinteDAO {

    private static final String narq = "Contribuintes.dat";
    private List<Contribuinte> lst;

    private void saveData() throws IOException {
        FileWriter fw = new FileWriter(narq);
        for (Contribuinte c : lst) {
            fw.write(c.toString() + "\n");
        }
        fw.close();
    }

    private void loadData() throws IOException {
        FileReader fr = new FileReader(narq);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (line != null) {
            StringTokenizer st = new StringTokenizer(line, ",");
            String nome = st.nextToken();
            String cpf = st.nextToken();
            int idade = 0;
            int nroDep = 0;
            double contrPrev = 0.0;
            double totRend = 0.0;
            try {
                idade = Integer.parseInt(st.nextToken());
                nroDep = Integer.parseInt(st.nextToken());
                contrPrev = Double.parseDouble(st.nextToken());
                totRend = Double.parseDouble(st.nextToken());
            }
            catch(NumberFormatException e) {
                throw new IOException("Formato de dados inválido", e);
            }
            try {
                Contribuinte c = new Contribuinte(nome, cpf, idade, nroDep, contrPrev, totRend);
                lst.add(c);
            }
            catch(IllegalArgumentException e) {
                throw new IOException("Valor de dados inválido", e);
            }
            line = br.readLine();
        }
        br.close();
    }

    public ContribuinteTxtFile() throws IOException {
        lst = new ArrayList<Contribuinte>();
        File f = new File(narq);
        if (f.exists()) {
            loadData();
        }
    }

    public void inserir(Contribuinte contr) throws CpfDuplicadoException, DAOException {
        if(buscar(contr.getCpf())!=null)
            throw new CpfDuplicadoException("CPF: " + contr.getCpf());
        lst.add(contr);
        try {
            saveData();
        } catch(Exception e) {
            throw new DAOException("Falha na insercao. " + e.getMessage());
        }
    }

    public void alterar(Contribuinte contr) throws CpfInexistenteException, DAOException {
        int i = 0;
        for (Contribuinte c : lst) {
            if (c.getCpf().equals(contr.getCpf())) {
                lst.set(i, contr);
                try {
                    saveData();
                } catch (Exception e) {
                    throw new DAOException("Falha na busca. " + e.getMessage());
                }
                return;
            }
            i++;
        }
        throw new CpfInexistenteException("CPF: " + contr.getCpf() + ", inexistente!");
    }

    public Contribuinte buscar(String cpf) throws DAOException{
        for (Contribuinte c : lst) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    public List<Contribuinte> buscarIdosos() throws DAOException {
        List<Contribuinte> idosos = new ArrayList<Contribuinte>();
        for(Contribuinte c : lst) {
            if(c.getIdade() > 65) {
                idosos.add(c);
            }
        }
        return idosos;
    }

    public List<Contribuinte> buscarTodos() throws DAOException {
        List<Contribuinte> todos = new ArrayList<Contribuinte>(lst.size());
        todos.addAll(lst);
        return todos;
    }

}
