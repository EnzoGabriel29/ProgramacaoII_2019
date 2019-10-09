package jogoCodigo;

import jogoCodigo.personagem.Personagem;
import jogoCodigo.item.Comida;
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
    
    public ArrayList<Comida> retornaComidas(){
        return new ArrayList<>(this.comidas);
    }
    
    public ArrayList<Ataque> retornaAtaques(){
        return new ArrayList<>(this.ataques);
    }
    
    public Comida retornaComida(int pos){
        return this.comidas.get(pos);
    }
    
    public void adicionaAtaque(Ataque a){
        this.ataques.add(a);
        this.personagem.listener.atualizaAtaques();
    }
    
    public void adicionaComida(Comida c){
        this.comidas.add(c);
        this.personagem.listener.atualizaComidas();
    }
    
    public void removeComida(int pos){
        this.comidas.remove(pos);
        this.personagem.listener.atualizaComidas();
    }
}
