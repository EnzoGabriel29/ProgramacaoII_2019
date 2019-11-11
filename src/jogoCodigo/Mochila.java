package jogoCodigo;

import java.util.List;
import java.util.ArrayList;

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
    
    public List<Pocao> getPocoes(){
        return new ArrayList<>(this.pocoes);
    }
    
    public List<Comida> getComidas(){
        return new ArrayList<>(this.comidas);
    }
    
    public List<Ataque> getAtaques(){
        return new ArrayList<>(this.ataques);
    }
    
    public Pocao getPocao(int pos){
        return this.pocoes.get(pos);
    }
    
    public Ataque getAtaque(int pos){
        return this.ataques.get(pos);
    }
    
    public Comida getComida(int pos){
        return this.comidas.get(pos);
    }
    
    public int getCarteira(){
        return this.carteira;
    }
    
    public void adicionaAtaque(Ataque a){
        this.ataques.add(a);
        this.personagem.listener.atualizaAtaques();
    }
    
    public void adicionaComida(Comida c){
        String nome = c.getNome();
        boolean isDuplicado = false;
        
        for (Comida cmd : this.comidas){
            if (cmd.getNome().equals(nome)){
                cmd.aumentaContador(1);
                isDuplicado = true;
                break;
            }
        }
        if (!isDuplicado) this.comidas.add(c);
        this.personagem.listener.atualizaComidas();
    }
    
    public void adicionaPocao(Pocao p){
        String nome = p.getNome();
        boolean isDuplicado = false;
        
        for (Pocao pco : this.pocoes){
            if (pco.getNome().equals(nome)){
                pco.aumentaContador(1);
                isDuplicado = true;
                break;
            }
        }
        
        if (!isDuplicado) this.pocoes.add(p);
        this.personagem.listener.atualizaPocoes();
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
        Comida c = this.comidas.get(pos);
        if (c.getContador() == 1)
            this.comidas.remove(pos);
        else
            c.diminuiContador(1);
        
        this.personagem.listener.atualizaComidas();
    }
    
    public void removePocao(int pos){
        Pocao p = this.pocoes.get(pos);
        
        if (p.getContador() == 1)
            this.pocoes.remove(pos);
        else
            p.diminuiContador(1);
        
        this.personagem.listener.atualizaPocoes();
    }
}
