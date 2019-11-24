package jogoCodigo.BancoDados;

import java.util.LinkedList;
import java.util.List;
import jogoCodigo.ItemConstruivel;

/**
 * Providencia um banco de dados para os itens classe C do jogo.
 * @see ItemConstruivel
 * @author Enzo
 */
public enum EnumClasseC {
    ASA_DE_DRAGAO(EnumInimigo.DRAGAO, "Asa de dragão"),
    CHIFRE_DE_DRAGAO(EnumInimigo.DRAGAO, "Chifre de dragão"),
    PATA_DE_DRAGAO(EnumInimigo.DRAGAO, "Pata de dragão"),
    NARINA_DE_DRAGAO(EnumInimigo.DRAGAO, "Narina de dragão"),
    TANGA_DE_TRASGO(EnumInimigo.TRASGO, "Tanga de trasgo"),
    UNHA_DE_TRASGO(EnumInimigo.TRASGO, "Unha de trasgo"),
    BASTAO_DE_TRASGO(EnumInimigo.TRASGO, "Bastão de trasgo"),
    TOUCA_DE_TRASGO(EnumInimigo.TRASGO, "Touca de trasgo"),
    PELE_DE_OGRO(EnumInimigo.OGRO, "Pele de ogro"),
    GORDURA_DE_OGRO(EnumInimigo.OGRO, "Gordura de ogro"),
    ORELHA_DE_OGRO(EnumInimigo.OGRO, "Orelha de ogro"),
    SOBRANCELHA_DE_OGRO(EnumInimigo.OGRO, "Sobrancelha de ogro"),
    SANGUE_DE_GIGANTE(EnumInimigo.GIGANTE, "Sangue de gigante"),
    PELE_DE_GIGANTE(EnumInimigo.GIGANTE, "Pele de gigante"),
    TANGA_DE_GIGANTE(EnumInimigo.GIGANTE, "Tanga de gigante"),
    BARBA_DE_GIGANTE(EnumInimigo.GIGANTE, "Barba de gigante"),
    NARIZ_DE_BRUXA(EnumInimigo.BRUXA, "Nariz de bruxa"),
    VASSOURA_DE_BRUXA(EnumInimigo.BRUXA, "Vassoura de bruxa"),
    VERRUGA_DE_BRUXA(EnumInimigo.BRUXA, "Verruga de bruxa"),
    COLAR_DE_BRUXA(EnumInimigo.BRUXA, "Colar de bruxa"),
    PINGENTE_DE_VAMPIRO(EnumInimigo.VAMPIRO, "Pingente de vampiro"),
    LINGUA_DE_VAMPIRO(EnumInimigo.VAMPIRO, "Língua de vampiro"),
    SANGUE_DE_VAMPIRO(EnumInimigo.VAMPIRO, "Sangue de vampiro"),
    PELE_DE_VAMPIRO(EnumInimigo.VAMPIRO, "Pele de vampiro");
    
    private final EnumInimigo inimigo;
    private final String nome;
    
    private EnumClasseC(EnumInimigo i, String nome){
        this.inimigo = i;
        this.nome = nome;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public static List<EnumClasseC> porInimigo(EnumInimigo i){
        List<EnumClasseC> itens = new LinkedList<>();
        
        for (EnumClasseC item : EnumClasseC.values()){
            if (item.inimigo == i)
                itens.add(item);
        }
        
        return itens;
    } 
    
    public static ItemConstruivel porEnum(EnumClasseC c){
        return new ItemConstruivel(c.nome, ItemConstruivel.CLASSE_C);
    }
}
