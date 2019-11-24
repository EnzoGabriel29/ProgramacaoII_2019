package jogoCodigo.BancoDados;

import jogoCodigo.Comida.Comida;

/**
 * Providencia um banco de dados para as comidas do jogo.
 * @see Comida
 * @author Enzo
 */
public enum EnumComida {
    UVA("Uva", 1, 3),
    MACA("Maçã", 2, 5),
    BANANA("Banana", 5, 15),
    CENOURA("Cenoura", 10, 25),
    ENSOPADO("Ensopado", 20, 45),
    FRANGO("Frango", 50, 95);
    
    private final String nome;
    private final int preco;
    private final int fome;
    
    private EnumComida(String nome, int fome, int preco){
        this.nome = nome;
        this.fome = fome;
        this.preco = preco;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public int getFome(){
        return this.fome;
    }
    
    public int getPreco(){
        return this.preco;
    }
    
    public static Comida getComida(EnumComida t){        
        return new Comida(t.nome, t.fome);
    }

    @Override
    public String toString() {
        return this.nome;
    }
}