package Persistencia;

import Negocio.*;
import java.sql.*;
import java.util.*;

public class ContribuinteDAODerby implements ContribuinteDAO {

    public ContribuinteDAODerby() throws DAOException{
        try {
             Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException ex) {
            throw new DAOException("JdbcOdbDriver not found!!");
        }
        // Cria o banco de dados vazio
        // Retirar do comentário se necessário
        /*
        tRy {
            createDB();
        } catch (DAOException ex) {
            System.out.println("Problemas para criar o banco: "+ex.getMessage());
            System.exit(0);
        }
        */
        
    }

    private static Connection getConnection() throws SQLException {
        //derbyDB sera o nome do diretorio criado localmente
        return DriverManager.getConnection("jdbc:derby:derbyDB");
    }

    public static void createDB() throws DAOException {
        try {
            Connection con = DriverManager.getConnection("jdbc:derby:derbyDB;create=true");
            Statement sta = con.createStatement();
            String sql = "CREATE TABLE Contribuintes ("
                    + "NOME VARCHAR(100) NOT NULL,"
                    + "CPF VARCHAR(20) PRIMARY KEY,"
                    + "IDADE NUMERIC(3) NOT NULL,"
                    + "NRODEP NUMERIC(2) NOT NULL,"
                    + "CONTRPREV DECIMAL(10,2) NOT NULL,"
                    + "TOTREND DECIMAL(10,2) NOT NULL"
                    + ")";
            sta.executeUpdate(sql);
            sta.close();
            con.close();
        } catch (SQLException ex) {
            throw new DAOException("Falha de criacao. " + ex.getMessage());
        }
    }

    public void inserir(Contribuinte c) throws CpfDuplicadoException,DAOException {
        if (buscar(c.getCpf())!=null) {
            throw new CpfDuplicadoException("CPF ja existe no banco de dados : " + c.getCpf());
        }
        try {
            Connection con = getConnection();
            String sql = "INSERT INTO Contribuintes (NOME, CPF, IDADE, NRODEP, CONTRPREV, TOTREND) VALUES (?,?,?,?,?,?)";
            PreparedStatement sta = con.prepareStatement(sql);
            sta.setString(1,c.getNome());
            sta.setString(2,c.getCpf());
            sta.setInt(3,c.getIdade());
            sta.setInt(4,c.getNroDep());
            sta.setDouble(5,c.getContrPrev());
            sta.setDouble(6,c.getTotRend());
            sta.executeUpdate();
            sta.close();
            con.close();
        } catch (SQLException ex) {
            throw new DAOException("Falha na inserção. " + ex.getMessage());
        }
    }

    public void alterar(Contribuinte c) throws CpfInexistenteException, DAOException {
        if (buscar(c.getCpf())==null) {
            throw new CpfInexistenteException("CPF Inexistente no banco de dados : " + c.getCpf());
        }
        try {
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE Contribuintes SET NOME=?, IDADE=?, NRODEP=?, CONTRPREV=?, TOTREND=? WHERE CPF = ?" //                             1        2         3            4          5             6
                    );
            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getIdade());
            stmt.setInt(3, c.getNroDep());
            stmt.setDouble(4, c.getContrPrev());
            stmt.setDouble(5, c.getTotRend());
            stmt.setString(6, c.getCpf());
            stmt.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            throw new DAOException("Falha na alteração. " + ex.getMessage());
        }
    }

    public Contribuinte buscar(String cpf) throws DAOException {
        Contribuinte contribuinte = null;
        try {
            Connection con = getConnection();
            String sql = "SELECT NOME, CPF, IDADE, NRODEP, CONTRPREV, TOTREND FROM Contribuintes WHERE CPF = ?";
            PreparedStatement sta = con.prepareStatement(sql);
            sta.setString(1, cpf);
            ResultSet res = sta.executeQuery();
            if (res.next()) {
                contribuinte = new Contribuinte(
                        res.getString("NOME"),
                        res.getString("CPF"),
                        res.getInt("IDADE"),
                        res.getInt("NRODEP"),
                        res.getDouble("CONTRPREV"),
                        res.getDouble("TOTREND"));
            }
            res.close();
            con.close();
            return contribuinte;
        } catch (Exception ex) {
            throw new DAOException("Falha na busca. " + ex.getMessage());
        }
    }

    public List<Contribuinte> buscarTodos() throws DAOException{
        List<Contribuinte> lst = new LinkedList<Contribuinte>();
        String sql = "SELECT NOME, CPF, IDADE, NRODEP, CONTRPREV, TOTREND FROM Contribuintes";
        try {
            Connection con = getConnection();
            Statement sta = con.createStatement();
            ResultSet res = sta.executeQuery(sql);
            while (res.next()) {
                lst.add(new Contribuinte(
                        res.getString("NOME"),
                        res.getString("CPF"),
                        res.getInt("IDADE"),
                        res.getInt("NRODEP"),
                        res.getDouble("CONTRPREV"),
                        res.getDouble("TOTREND")));
            }
            res.close();
            sta.close();
            con.close();
            return lst;
        } catch (SQLException ex) {
            throw new DAOException("Falha na busca. " + ex.getMessage());
        }
    }

    public List<Contribuinte> buscarIdosos() throws DAOException {
        List<Contribuinte> lst = new LinkedList<Contribuinte>();
        String sql = "SELECT NOME, CPF, IDADE, NRODEP, CONTRPREV, TOTREND FROM Contribuintes WHERE IDADE > 65";
        try {
            Connection con = getConnection();
            Statement sta = con.createStatement();
            ResultSet res = sta.executeQuery(sql);
            while (res.next()) {
                lst.add(new Contribuinte(
                        res.getString("NOME"),
                        res.getString("CPF"),
                        res.getInt("IDADE"),
                        res.getInt("NRODEP"),
                        res.getDouble("CONTRPREV"),
                        res.getDouble("TOTREND")));
            }
            res.close();
            sta.close();
            con.close();
            return lst;
        } catch (Exception ex) {
            throw new DAOException("Falha na busca. " + ex.getMessage());
        }
    }
}
