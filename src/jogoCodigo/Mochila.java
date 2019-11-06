package jogoCodigo;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Mochila {
    private final Personagem personagem;
    private final List<Pocao> pocoes;
    private final List<Ataque> ataques;
    private final List<Comida> comidas;
    private int carteira;
    
    public Mochila(Personagem p){
        this.personagem = p;
        this.pocoes = new ArrayList<>();
        this.comidas = new ArrayList<>();
        this.ataques = new ArrayList<>();
        
        Ataque atkNulo = new Ataque("Sem ataque", 0);
        this.ataques.add(atkNulo);
    }
    
    public List<Pocao> retornaPocoes(){
        return new ArrayList<>(this.pocoes);
    }
    
    public List<Comida> retornaComidas(){
        return new ArrayList<>(this.comidas);
    }
    
    public List<Ataque> retornaAtaques(){
        return new ArrayList<>(this.ataques);
    }
    
    public Pocao retornaPocao(int pos){
        return this.pocoes.get(pos);
        
    }
    
    public Ataque retornaAtaque(int pos){
        return this.ataques.get(pos);
    }
    
    public Comida retornaComida(int pos){
        try {
            return this.comidas.get(pos);
        
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }
    
    public void adicionaAtaque(Ataque a){
        this.ataques.add(a);
        this.personagem.listener.atualizaAtaques();
    }
    
    public void adicionaComida(Comida c){
        this.comidas.add(c);
        this.personagem.listener.atualizaComidas();
    }
    
    public void adicionaCarteira(int moedas){
        this.carteira += moedas;
        this.personagem.listener.atualizaAtributos();
    }
    
    public boolean removeCarteira(int moedas){
        if (moedas > this.carteira) return false;
        this.carteira -= moedas;
        return true;
    }
    
    public void removeComida(int pos){
        this.comidas.remove(pos);
        this.personagem.listener.atualizaComidas();
    }
    
    public void adicionaPocao(Pocao p){
        this.pocoes.add(p);
        this.personagem.listener.atualizaPocoes();
    }
    
    public void removePocao(int pos){
        this.pocoes.remove(pos);
        this.personagem.listener.atualizaPocoes();
    }
    
    public int getCarteira(){
        return this.carteira;
    }
}
