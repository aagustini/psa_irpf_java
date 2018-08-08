package Negocio;

/**
 *
 * @author Bernardo Copstein
 */
public class ValidadorContribuinte {

    private static ValidadorContribuinte vc = null;

    private ValidadorContribuinte() {
    }

    public static ValidadorContribuinte getInstance() {
        if (vc == null) {
            vc = new ValidadorContribuinte();
        }
        return (vc);
    }

    public boolean validaNome(String nome) {
        if (nome == null
                || nome.length() == 0
                || !nome.contains(" ")) {
            return false;
        }
        return true;
    }

    public boolean validaCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }
        return true;
    }

    public boolean validaIdade(int idade) {
        if (idade < 0 || idade > 150) {
            return false;
        }
        return true;
    }

    public boolean validaNroDep(int nroDep) {
        if (nroDep < 0 || nroDep > 50) {
            return false;
        }
        return true;
    }

    public boolean validaContrPrev(double contrPrev) {
        if (contrPrev < 0) {
            return false;
        }
        return true;
    }

    public boolean validaTotRend(double totRend) {
        if (totRend < 0) {
            return false;
        }
        return true;
    }
}
