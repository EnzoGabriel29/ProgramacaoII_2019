package jogoCodigo.item;

import jogoCodigo.personagem.Personagem;

abstract public class Item {
    protected String nome;
    abstract public void utilizarItem(Personagem p);  
    public String getNome(){
        return nome;
    }
}
