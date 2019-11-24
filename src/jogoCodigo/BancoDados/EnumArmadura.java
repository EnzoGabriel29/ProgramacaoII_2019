package jogoCodigo.BancoDados;

import jogoCodigo.Armadura;

/**
 * Providencia um banco de dados para as armaduras do jogo.
 * @see Armadura
 * @author Enzo
 */
public enum EnumArmadura {
    ELMO("Elmo", 40, .15),
    PEITORAL("Peitoral", 100, .35),
    CALCAS("Calças", 40, .15),
    BOTAS("Botas", 20, .5);
    
    private final String nome;
    private final int durabilidade;
    private final double protecao;
    private final int preco;
    
    private EnumArmadura(String n, int d, double p){
        this.nome = n;
        this.durabilidade = d;
        this.preco = d*10;
        this.protecao = p;
    }

    public String getNome() {
        return this.nome;
    }

    public int getDurabilidade() {
        return this.durabilidade;
    }

    public int getPreco(){
        return this.preco;
    }

    public double getProtecao() {
        return this.protecao;
    }
        
    public static Armadura getArmadura(EnumArmadura e){
        return new Armadura(e.nome, e.durabilidade, e);
    }

    @Override
    public String toString(){
        return this.nome;
    }
}
