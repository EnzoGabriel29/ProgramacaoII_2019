package jogoCodigo;

import java.util.ArrayList;
import java.util.Random;

public class Pocao extends ItemConsumivel {
    public static final Pocao VIDA = new Pocao("Poção de vida", new Atributos(){
        @Override public int getHP(){ return 30; }
        @Override public int getForca(){ return 0; }
        @Override public int getInteligencia(){ return 0; }
        @Override public int getMaxHP(){ return 0; }
    });
    
    public static final Pocao FORCA = new Pocao("Poção de força", new Atributos(){
        @Override public int getHP(){ return 0; }
        @Override public int getForca(){ return 20; }
        @Override public int getInteligencia(){ return 0; }
        @Override public int getMaxHP(){ return 0; }
    });
    
    public static final Pocao SAGAC = new Pocao("Poção de sagacidade", new Atributos(){
        @Override public int getHP(){ return 0; }
        @Override public int getForca(){ return 0; }
        @Override public int getInteligencia(){ return 30; }
        @Override public int getMaxHP(){ return 0; }
    });
    
    public static Pocao retornaPocao(){
        Calculo.RandomCollection<Pocao> rca = new Calculo.RandomCollection<>();
        rca.add(75, VIDA).add(25, FORCA).add(25, SAGAC);

        return rca.next();
    }
    
    private final Atributos atributos;
    
    public Pocao(String nome, Atributos atr){
        this.nome = nome;
        this.atributos = atr;
    }
    
    public Atributos retornaAtributos(){
        return atributos;
    }
    
    @Override
    public String toString(){
        return "ITEM: " + this.nome + "\n" +
               "TIPO: Poção" + "\n" +
               "VIDA ADQUIRIDA: " + atributos.getHP() + "\n" +
               "FORÇA ADQUIRIDA: " + atributos.getForca() + "\n" +
               "INTELIGÊNCIA ADQUIRIDA: " + atributos.getInteligencia() + "\n" +
               "VIDA AUMENTADA: " + atributos.getMaxHP() + "\n";
    }
}
