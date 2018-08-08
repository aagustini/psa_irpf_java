package Negocio;

/**
 *
 * @author bcopstein
 */
public class Contribuinte {
    private String nome;
    private String cpf;
    private int idade;
    private int nroDep;
    private double contrPrev;
    private double totRend;
    private CalculoIrpf impostoDevido;
    
    public Contribuinte(String nome,String cpf,int idade,
                        int nroDep,double contrPrev,double totRend)
                        throws IllegalArgumentException{
        
        if(!ValidadorContribuinte.getInstance().validaNome(nome))
            throw new IllegalArgumentException("Nome inválido");
        if(!ValidadorContribuinte.getInstance().validaCpf(cpf))
            throw new IllegalArgumentException("Cpf inválido");
        if(!ValidadorContribuinte.getInstance().validaIdade(idade))
            throw new IllegalArgumentException("Idade inválida");
        if(!ValidadorContribuinte.getInstance().validaNroDep(nroDep))
            throw new IllegalArgumentException("NroDep inválido");
        if(!ValidadorContribuinte.getInstance().validaContrPrev(contrPrev))
            throw new IllegalArgumentException("ContrPrev inválido");
        if(!ValidadorContribuinte.getInstance().validaTotRend(totRend))
            throw new IllegalArgumentException("TotRend inválido");
        
        this.contrPrev = contrPrev;
        this.cpf = cpf;
        this.idade = idade;
        this.nome = nome;
        this.nroDep = nroDep;
        this.totRend = totRend;
        this.impostoDevido = new CalculoIrpfSimplificado(); // Assume como default
    }

    public void defineCalculo(CalculoIrpf calc){
        impostoDevido = calc;
    }    
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(nome);
        sb.append(",");
        sb.append(cpf);
        sb.append(",");
        sb.append(idade);
        sb.append(",");
        sb.append(nroDep);
        sb.append(",");
        sb.append(contrPrev);
        sb.append(",");
        sb.append(totRend);
        return(sb.toString());
    }

    public double getContrPrev() {
        return contrPrev;
    }

    public String getCpf() {
        return cpf;
    }

    public int getIdade() {
        return idade;
    }

    public String getNome() {
        return nome;
    }

    public int getNroDep() {
        return nroDep;
    }

    public double getTotRend() {
        return totRend;
    }
    
    public double getImpostoDevido(){
        return(impostoDevido.calculaImposto(this));
    }
}
