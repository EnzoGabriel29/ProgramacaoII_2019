package jogoCodigo;

public class Comida {
    private final String nome;
    private final int fomeRest;
    
    public Comida(String nome, int fomeRest){
        this.nome = nome;
        this.fomeRest = fomeRest;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public int getFomeRest(){
        return this.fomeRest;
    }
}
