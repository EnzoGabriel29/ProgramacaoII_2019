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
    
    public static final Pocao SAGAC = new Pocao("Poção de força", new Atributos(){
        @Override public int getHP(){ return 0; }
        @Override public int getForca(){ return 0; }
        @Override public int getInteligencia(){ return 30; }
        @Override public int getMaxHP(){ return 0; }
    });
    
    public static Pocao retornaPocao(){
        ArrayList<Pocao> pocoes = new ArrayList<>();
        for (int i = 0; i < 3; i++) pocoes.add(VIDA);
        for (int i = 0; i < 2; i++) pocoes.add(SAGAC);
        for (int i = 0; i < 1; i++) pocoes.add(FORCA);
        
        Random r = new Random();
        return pocoes.get(r.nextInt(6));
    }
    
    private final Atributos atributosModif;
    
    public Pocao(String nome, Atributos atr){
        this.nome = nome;
        this.atributosModif = atr;
    }
    
    public Atributos retornaAtributos(){
        return atributosModif;
    }
}
