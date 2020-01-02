package jogoCodigo.Pocao;

import java.util.Random;
import jogoCodigo.Atributos;
import jogoCodigo.BancoDados.EnumPocao;
import jogoCodigo.Calculo.ColecaoAleatoria;
import jogoCodigo.Item.ItemEmpilhavel;

/**
 * Providencia funções de poção no jogo. Cada personagem possui um atributo,
 * como vida, inteligência, força e vida máxima. Ao utilizar uma poção, esse
 * atributo pode atuar tanto instantaneamente (como a poção de vida) quanto
 * ainda durar por um minuto após da poção ser consumida (como a poção de
 * inteligência e de força).
 * @author Enzo
 */
public class Pocao extends ItemEmpilhavel {
    /**
     * Factory method que retorna uma poção de acordo com a classe do persongem.
     * @param classe a classe do personagem que irá utilizá-la.
     * @return a poção.
     */
    public static Pocao retornaPocao(String classe){
        ColecaoAleatoria<EnumPocao> ca = new ColecaoAleatoria<>();
        ca.adicionaItem(75, EnumPocao.VIDA)
                .adicionaItem(25, EnumPocao.FORCA)
                .adicionaItem(25, EnumPocao.SAGACIDADE);
        
        Pocao pocaoRet = EnumPocao.getPocao(ca.retornaItem());
        
        /**
         * 33% de chance de um elixir ser adicionado à poção.
         */
        switch (new Random().nextInt(3)){
            case 0: return pocaoRet;
            case 1: return pocaoRet;
            case 2: {
                switch (classe){
                    case "Mago": return new ElixirInteligencia(pocaoRet);
                    case "Gladiador": return new ElixirForca(pocaoRet);
                    case "Curandeiro": return new ElixirVida(pocaoRet);
                }
            }
        }
        
        return null;
    }
            
    private final EnumPocao tipo;
    private final Atributos atributos;
    
    /**
     * Inicializa uma Pocao.
     * @param nome nome da poção.
     * @param atr atributos que essa poção irá restaurar.
     */
    public Pocao(String nome, Atributos atr){
        this.nome = nome;
        this.atributos = atr;
        this.tipo = EnumPocao.porNome(nome);
    }
    
    /**
     * Getter para atributos.
     * @return os atributos restaurados.
     */
    public Atributos getAtributos(){
        return this.atributos;
    }
    
    /**
     * Getter para o tipo.
     * @return o tipo dessa poção.
     */
    public EnumPocao getTipo(){
        return this.tipo;
    }
    
    @Override
    public String getDescricao(){
        return "ITEM: " + this.nome + "\n" +
               "TIPO: Poção" + "\n" +
               "VIDA ADQUIRIDA: " + atributos.getHP() + "\n" +
               "FORÇA ADQUIRIDA: " + atributos.getForca() + "\n" +
               "INTELIGÊNCIA ADQUIRIDA: " + atributos.getInteligencia() + "\n" +
               "VIDA AUMENTADA: " + atributos.getMaxHP() + "\n";
    }
}
