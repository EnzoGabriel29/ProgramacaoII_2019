package jogoCodigo;

import jogoCodigo.Comida.Comida;
import jogoCodigo.Pocao.Pocao;
import jogoCodigo.Personagem.Personagem;
import java.util.List;
import java.util.ArrayList;

public class Mochila {
    public static final int ITEM = 0;
    public static final int POCAO = 1;
    public static final int COMIDA = 2;
    
    private final Personagem personagem;
    private final ArrayList<Ataque> ataques;
    private final ContainerMochila<ItemConstruivel> itens;    
    private final ContainerMochila<Pocao> pocoes;
    private final ContainerMochila<Comida> comidas;
    
    private int carteira;
    
    public Mochila(Personagem p){
        this.personagem = p;
        
        this.itens = new ContainerMochila<>();
        this.pocoes = new ContainerMochila<>();
        this.comidas = new ContainerMochila<>();
        this.ataques = new ArrayList<>();
        
        Ataque atkNulo = new Ataque("Sem ataque", 0);
        this.ataques.add(atkNulo);
        this.personagem.listener.atualizaAtaques();
    }
    
    private void adicionaItem(Object o, int tipo){
        switch (tipo){
            case POCAO: this.pocoes.adicionaItem((Pocao) o); break;
            case ITEM: this.itens.adicionaItem((ItemConstruivel) o); break;
            case COMIDA: this.comidas.adicionaItem((Comida) o); break;
        }
    }
    
    private void removeItem(int pos, int tipo){
        switch (tipo){
            case POCAO: this.pocoes.removeItem(pos); break;
            case ITEM: this.itens.removeItem(pos); break;
            case COMIDA: this.comidas.removeItem(pos); break;
        }
    }
    
    private Object retornaItens(int tipo){
        switch (tipo){
            case POCAO: return this.pocoes.retornaItens();
            case ITEM: return this.itens.retornaItens();
            case COMIDA: return this.comidas.retornaItens();
        }
        return null;
    }
    
    private Object retornaItem(int pos, int tipo){
        switch (tipo){
            case POCAO: return this.pocoes.retornaItem(pos);
            case ITEM: return this.itens.retornaItem(pos);
            case COMIDA: return this.comidas.retornaItem(pos);
        }
        
        return null;
    }
    
    public List<ItemConstruivel> getItens(){
        return (ArrayList<ItemConstruivel>) this.retornaItens(ITEM);
    }
    
    public List<Pocao> getPocoes(){
        return (ArrayList<Pocao>) this.retornaItens(POCAO);
    }
    
    public List<Comida> getComidas(){
        return (ArrayList<Comida>) this.retornaItens(COMIDA);
    }
    
    public ItemConstruivel getItem(int pos){
        return (ItemConstruivel) this.retornaItem(pos, ITEM);
    }
    
    public Pocao getPocao(int pos){
        return (Pocao) this.retornaItem(pos, POCAO);
    }
    
    public Comida getComida(int pos){
        return (Comida) this.retornaItem(pos, COMIDA);
    }
    
    public void adicionaItem(ItemConstruivel i){
        this.adicionaItem(i, ITEM);
        this.personagem.listener.atualizaItens();
    }
    
    public void adicionaPocao(Pocao p){
        this.adicionaItem(p, POCAO);
        this.personagem.listener.atualizaPocoes();
    }
    
    public void adicionaComida(Comida c){
        this.adicionaItem(c, COMIDA);
        this.personagem.listener.atualizaComidas();
    }
    
    public void removeItem(int pos){
        this.removeItem(pos, ITEM);
        this.personagem.listener.atualizaItens();
    }
    
    public void removePocao(int pos){
        this.removeItem(pos, POCAO);
        this.personagem.listener.atualizaPocoes();
    }
    
    public void removeComida(int pos){
        this.removeItem(pos, COMIDA);
        this.personagem.listener.atualizaComidas();
    }
    
    public int getCarteira(){
        return this.carteira;
    }
            
    public void adicionaCarteira(int moedas){
        this.carteira += moedas;
        this.personagem.listener.atualizaAtributos();
    }
    
    public boolean removeCarteira(int moedas){
        if (moedas > this.carteira) return false;
        this.carteira -= moedas;
        this.personagem.listener.atualizaAtributos();
        return true;
    }
    
    public List<Ataque> getAtaques(){
        return new ArrayList<>(this.ataques);
    }
    
    public Ataque getAtaque(int pos){
        return this.ataques.get(pos);
    }
    
    public void adicionaAtaque(Ataque a){
        this.ataques.add(a);
        this.personagem.listener.atualizaAtaques();
    }
}
