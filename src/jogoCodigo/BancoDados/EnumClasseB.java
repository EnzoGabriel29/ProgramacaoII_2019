package jogoCodigo.BancoDados;

import java.util.LinkedList;
import java.util.List;
import jogoCodigo.ItemConstruivel;

/**
 * Providencia um banco de dados para os itens classe B do jogo.
 * @see ItemConstruivel
 * @author Enzo
 */
public enum EnumClasseB {
    SALIVA_DE_DRAGAO(EnumInimigo.DRAGAO, "Saliva de dragão"),
    CAUDA_DE_DRAGAO(EnumInimigo.DRAGAO, "Cauda de dragão"),
    PELE_DE_TRASGO(EnumInimigo.TRASGO, "Pele de trasgo"),
    ORELHA_DE_TRASGO(EnumInimigo.TRASGO, "Orelha de trasgo"),
    PELO_DE_OGRO(EnumInimigo.OGRO, "Pêlo de ogro"),
    CARCACA_DE_OGRO(EnumInimigo.OGRO, "Carcaça de ogro"),
    MELECA_DE_GIGANTE(EnumInimigo.GIGANTE, "Meleca de gigante"),
    COURO_DE_GIGANTE(EnumInimigo.GIGANTE, "Couro de gigante"),
    CHAPEU_DE_BRUXA(EnumInimigo.BRUXA, "Chapéu de bruxa"),
    CABELO_DE_BRUXA(EnumInimigo.BRUXA, "Cabelo de bruxa"),
    DENTE_DE_VAMPIRO(EnumInimigo.VAMPIRO, "Dente de vampiro"),
    CAPA_DE_VAMPIRO(EnumInimigo.VAMPIRO, "Capa de vampiro");
    
    private final EnumInimigo inimigo;
    private final String nome;
    
    private EnumClasseB(EnumInimigo i, String nome){
        this.inimigo = i;
        this.nome = nome;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public static List<EnumClasseB> porInimigo(EnumInimigo i){
        List<EnumClasseB> itens = new LinkedList<>();
        
        for (EnumClasseB item : EnumClasseB.values()){
            if (item.inimigo == i)
                itens.add(item);
        }
        
        return itens;
    } 
    
    public static ItemConstruivel porEnum(EnumClasseB b){
        return new ItemConstruivel(b.nome, ItemConstruivel.CLASSE_B);
    }
}
