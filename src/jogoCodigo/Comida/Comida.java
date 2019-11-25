package jogoCodigo.Comida;

import java.util.Random;
import jogoCodigo.BancoDados.EnumComida;
import jogoCodigo.Calculo.ColecaoAleatoria;
import jogoCodigo.ItemEmpilhavel;

/**
 * Providencia funções de comida no jogo. Cada personagem possui um atributo
 * de fome, que vai aumentando ao passar do tempo. Para o personagem não morrer
 * de fome, ele pode tanto comprar comidas no comerciante quanto achar comidas
 * em suas caminhadas. Cada comida possui uma fome restaurada.
 * @author Enzo
 */
public class Comida extends ItemEmpilhavel {
    public int fomeRestaurada;
    
    /**
     * Factory method que retorna uma comida. Cada comida possui uma
     * probabilidade de aparição, que é inversamente proporcional à
     * quantidade de fome restaurada. Ou seja, se uma comida restaura
     * mais fome, a probabilidade de ela aparecer é menor.
     * @return uma comida aleatória.
     */
    public static Comida retornaComida(){
        ColecaoAleatoria<EnumComida> ca = new ColecaoAleatoria<>();
        
        ca.adicionaItem(6, EnumComida.UVA)
           .adicionaItem(5, EnumComida.MACA)
           .adicionaItem(4, EnumComida.BANANA)
           .adicionaItem(3, EnumComida.CENOURA)
           .adicionaItem(2, EnumComida.ENSOPADO)
           .adicionaItem(1, EnumComida.FRANGO); 
        
        Comida comidaRet = EnumComida.getComida(ca.retornaItem());
        
        /**
         * 25% de chance de ser adicionado um tempero amargo.
         * 25% de chance de ser adicionado um tempero doce.
         */
        switch (new Random().nextInt(4)){
            case 0: return comidaRet;
            case 1: return comidaRet;
            case 2: return new TemperoAmargo(comidaRet);
            case 3: return new TemperoDoce(comidaRet);
            default: throw new RuntimeException("não deveria entrar aqui");
        }
    }
    
    /**
     * Inicializa uma comida.
     * @param nome nome da comida.
     * @param fomeRest fome restaurada ao ser utilizada.
     */
    public Comida(String nome, int fomeRest){
        this.nome = nome;
        this.fomeRestaurada = fomeRest;
    }
    
    /**
     * Getter para a fome restaurada.
     * @return a fome restaurada.
     */
    public int getFomeRest(){
        return this.fomeRestaurada;
    }
    
    @Override
    public String getDescricao(){
        return "ITEM: " + this.nome + "\n" +
               "TIPO: Comida" + "\n" +
               "FOME RESTAURADA: " + this.fomeRestaurada + "\n";
    }
}
