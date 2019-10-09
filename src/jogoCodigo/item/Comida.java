package jogoCodigo.item;

import jogoCodigo.personagem.Personagem;

public class Comida extends ItemUtilizavel {
    private final int fomeRest;
    
    public Comida(String nome, int fomeRest){
        super(nome);
        this.fomeRest = fomeRest;
    }

    @Override
    public void utilizarItem(Personagem p){
        p.diminuiFome(fomeRest);
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public int getFomeRest(){
        return this.fomeRest;
    }
}
