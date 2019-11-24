
package jogoCodigo.BancoDados;

import jogoCodigo.Personagem.Inimigo;

/**
 * Providencia um banco de dados para os inimigos do jogo.
 * @see Inimigo
 * @author Enzo
 */
public enum EnumInimigo {
    DRAGAO, TRASGO, OGRO, GIGANTE, BRUXA, VAMPIRO;
    
    public static EnumInimigo porInimigo(Inimigo i){
        switch (i.getApelido()){
            case "Dragão": return DRAGAO;
            case "Trasgo": return TRASGO;
            case "Ogro": return OGRO;
            case "Gigante": return GIGANTE;
            case "Bruxa": return BRUXA;
            case "Vampiro": return VAMPIRO;
        }
        
        return null;
    }
}
