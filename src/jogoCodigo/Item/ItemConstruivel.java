package jogoCodigo.Item;

import jogoCodigo.Personagem.Inimigo;
import jogoCodigo.BancoDados.EnumClasseA;
import jogoCodigo.BancoDados.EnumInimigo;
import jogoCodigo.BancoDados.EnumClasseC;
import jogoCodigo.BancoDados.EnumClasseB;
import java.util.List;
import java.util.Random;
import jogoCodigo.Calculo.ColecaoAleatoria;

/**
 * Fornece uma classe para itens contruíveis no jogo.
 * Itens construíveis são utilizados para fazer trocas com
 * alquimistas, onde o personagem lhe fornece itens para 
 * receber em troca uma poção. Existem três classes de itens
 * construíveis: itens classe A, que são raros, itens classe
 * B, que são encontrados normalmente, e itens classe C, que
 * são enconstrados facilmente. Para trocar uma poção com o
 * alquimista, é preciso ter pelo menos 1 item classe A, 2
 * itens classe B e 4 itens classe C de qualquer tipo.
 * Itens construíveis são dropados de inimigos após suas mortes.
 * @author Enzo
 */
public class ItemConstruivel extends ItemEmpilhavel {
    public static final int CLASSE_A = 0;
    public static final int CLASSE_B = 1;
    public static final int CLASSE_C = 2;
    protected int classe;
    
    /**
     * Factory method que gera um item construível associado a
     * cada inimigo que foi morto. É possível também que o inimigo
     * não gere nenhum item. Existe uma probabilidade de 50% de não
     * ser dropado nenhum item, de 30% de dropar um item de classe C,
     * 20% de dropar um item de classe C e 10% de dropar um item de classe A.
     * @param i inimigo que irá gerar o item construível.
     * @return o item pertencente a esse inimigo.
     */
    public static ItemConstruivel retornaItem(Inimigo i){
        EnumInimigo tipo = EnumInimigo.porInimigo(i);
        
        ColecaoAleatoria<Integer> ca = new ColecaoAleatoria();
        ca.adicionaItem(50, -1).adicionaItem(30, CLASSE_C).adicionaItem(20, CLASSE_B).adicionaItem(10, CLASSE_A);
        
        Random r = new Random();
        int valor = ca.retornaItem();
        
        switch (valor){
            case -1: return null;
            case CLASSE_A: {
                List<EnumClasseA> a = EnumClasseA.porInimigo(tipo);
                EnumClasseA e = a.get(r.nextInt(a.size()));
                return EnumClasseA.porEnum(e);
            }
            
            case CLASSE_B: {
                List<EnumClasseB> b = EnumClasseB.porInimigo(tipo);
                EnumClasseB e = b.get(r.nextInt(b.size()));
                return EnumClasseB.porEnum(e);
            }
            
            case CLASSE_C: {
                List<EnumClasseC> c = EnumClasseC.porInimigo(tipo);
                EnumClasseC e = c.get(r.nextInt(c.size()));
                return EnumClasseC.porEnum(e);
            }
        }
        return null;
    }
    
    /**
     * Inicializa um item construível.
     * @param nome nome do item.
     * @param classe classe do item.
     */
    public ItemConstruivel(String nome, int classe){
        this.nome = nome;
        this.classe = classe;
    }

    /**
     * Getter para a classe.
     * @return a classe do item.
     */
    public int getClasse() {
        return classe;
    }

    @Override
    public String getDescricao(){
        return "ITEM: " + this.nome + "\n" +
               "TIPO: Item" + "\n" +
               "CLASSE: " + (this.classe == CLASSE_A ? "A" :
                (this.classe == CLASSE_B ? "B" : "C")) + "\n";
    }
}
