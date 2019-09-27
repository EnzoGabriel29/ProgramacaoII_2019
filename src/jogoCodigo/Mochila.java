package jogoCodigo;

import java.util.ArrayList;

public class Mochila {
    private Personagem personagem;
    private ArrayList<Ataque> ataques;
    private ArrayList<Comida> comidas;
    
    public Mochila(Personagem p){
        this.personagem = p;
        this.comidas = new ArrayList<>();
        this.ataques = new ArrayList<>();
    }
    
    public ArrayList<Ataque> retornaAtaques(){
        return new ArrayList<>(ataques);
    }
    
    public void adicionaAtaque(Ataque a){
        this.ataques.add(a);
        this.personagem.listener.adicionaTabela(a, ataques.size()-1);
    }
    
    public void adicionaComida(Comida c){
        this.comidas.add(c);
        this.personagem.listener.adicionaTabela(c, comidas.size()-1);
    }
}
