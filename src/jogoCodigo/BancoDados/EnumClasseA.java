package jogoCodigo.BancoDados;

import java.util.List;
import java.util.LinkedList;
import jogoCodigo.Item.ItemConstruivel;

/**
 * Providencia um banco de dados para os itens classe A do jogo.
 * @see ItemConstruivel
 * @author Enzo
 */
public enum EnumClasseA {
    ESCAMA_DE_DRAGAO(EnumInimigo.DRAGAO, "Escama de dragão"),
    CORACAO_DE_TRASGO(EnumInimigo.TRASGO, "Coração de trasgo"),
    DEDO_DE_OGRO(EnumInimigo.OGRO, "Dedo de ogro"),
    OLHO_DE_GIGANTE(EnumInimigo.GIGANTE, "Olho de gigante"),
    VARINHA_DE_BRUXA(EnumInimigo.BRUXA, "Varinha de bruxa"),
    CABECA_DE_VAMPIRO(EnumInimigo.VAMPIRO, "Cabeça de vampiro");
    
    private final EnumInimigo inimigo;
    private final String nome;
    
    private EnumClasseA(EnumInimigo i, String nome){
        this.inimigo = i;
        this.nome = nome;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public static List<EnumClasseA> porInimigo(EnumInimigo i){
        List<EnumClasseA> itens = new LinkedList<>();
        
        for (EnumClasseA item : EnumClasseA.values()){
            if (item.inimigo == i)
                itens.add(item);
        }
        
        return itens;
    }
    
    public static ItemConstruivel porEnum(EnumClasseA a){
        return new ItemConstruivel(a.nome, ItemConstruivel.CLASSE_A);
    }
}
