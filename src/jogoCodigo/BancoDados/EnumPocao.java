package jogoCodigo.BancoDados;

import jogoCodigo.Atributos;
import jogoCodigo.Pocao.Pocao;

/**
 * Providencia um banco de dados para as poções do jogo.
 * @see Pocao
 * @author Enzo
 */
public enum EnumPocao {
    VIDA("Poção de vida", 20, new Atributos(){
        @Override public int getHP(){ return 30; }
        @Override public int getForca(){ return 0; }
        @Override public int getInteligencia(){ return 0; }
        @Override public int getMaxHP(){ return 0; }
    }),
    
    SAGACIDADE("Poção de sagacidade", 30, new Atributos(){
        @Override public int getHP(){ return 0; }
        @Override public int getForca(){ return 0; }
        @Override public int getInteligencia(){ return 30; }
        @Override public int getMaxHP(){ return 0; }
    }),
    
    FORCA("Poção de força", 50, new Atributos(){
        @Override public int getHP(){ return 0; }
        @Override public int getForca(){ return 20; }
        @Override public int getInteligencia(){ return 0; }
        @Override public int getMaxHP(){ return 0; }
    });
    
    private final String nome;
    private final int preco;
    private final Atributos atributos;
    
    public static EnumPocao porNome(String nome){
        switch (nome){
            case "Poção de vida": return VIDA;
            case "Poção de sagacidade": return SAGACIDADE;
            case "Poção de força": return FORCA;
            default: return null;
        }
    }
        
    private EnumPocao(String nome, int preco, Atributos atributos){
        this.nome = nome;
        this.preco = preco;
        this.atributos = atributos;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public int getPreco(){
        return this.preco;
    }
    
    public Atributos getAtributos(){
        return this.atributos;
    }
    
    public static Pocao getPocao(EnumPocao t){        
        return new Pocao(t.nome, t.atributos);
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
