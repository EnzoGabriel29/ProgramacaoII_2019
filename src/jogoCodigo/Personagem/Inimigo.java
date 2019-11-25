package jogoCodigo.Personagem;

import jogoCodigo.Ataque;
import jogoCodigo.BancoDados.EnumInimigo;
import jogoCodigo.Calculo.ColecaoAleatoria;

/**
 * Fornece uma classe para inimigos do jogo.
 * Durante o passeio de um personagem, é possível que o mesmo encontre
 * inimigos durante o caminho, que, ao serem derrotados, podem gerar
 * experiência e itens. Eles podem tirar pontos de vida do personagem,
 * em que o usuário deverá tomar cuidado para não chegar a zero.
 * @author Enzo
 */
public class Inimigo extends Personagem {
    /**
     * Factory method que retorna um inimigo de acordo com o nível
     * do personagem e com o nível de dificuldade do jogo.
     * @param nivel nível do personagem que irá combater.
     * @param dific nível de dificuldade do jogo.
     * @return o inimigo mais apropriado para esse personagem.
     */
    public static Inimigo retornaInimigo(int nivel, int dific){
        ColecaoAleatoria<EnumInimigo> ca = new ColecaoAleatoria<>();
        
        ca.adicionaItem(1, EnumInimigo.DRAGAO)
                .adicionaItem(1, EnumInimigo.TRASGO)
                .adicionaItem(1, EnumInimigo.OGRO)
                .adicionaItem(1, EnumInimigo.GIGANTE)
                .adicionaItem(1, EnumInimigo.BRUXA)
                .adicionaItem(1, EnumInimigo.VAMPIRO);
        
        int inimigoNivel = nivel == 1 ? 1 : 2 * nivel + 1;
        inimigoNivel += dific;
        
        switch (ca.retornaItem()){
            case DRAGAO: return new Inimigo("Dragão", inimigoNivel);
            case TRASGO: return new Inimigo("Trasgo", inimigoNivel);
            case OGRO: return new Inimigo("Ogro", inimigoNivel);
            case GIGANTE: return new Inimigo("Gigante", inimigoNivel);
            case BRUXA: return new Inimigo("Bruxa", inimigoNivel);
            case VAMPIRO: return new Inimigo("Vampiro", inimigoNivel);        
        }
        
        return null;
    }
    
    /**
     * Inicializa um inimigo.
     * @param nome nome do inimigo.
     * @param nivel nível do inimigo.
     */
    public Inimigo(String nome, int nivel){
        super(nome);
        this.setNivel(nivel);
    }
    
    /**
     * Define um nível para o inimigo e ajusta os
     * seus atributos de acordo com o nível definido.
     * @param nivel novo nível do inimigo.
     */
    private void setNivel(int nivel){
        this.nivel = nivel;
        this.hp = this.nivel*20 + 50;
        this.forca = this.nivel + 2;
    }
    
    /**
     * O inimigo não possui métodos de evoluir e de melhorar.
     * Além disso, o seu ataque para um personagem é padrão.
     */
    @Override public void evolui(){}
    @Override public void melhora(){}
    @Override
    public void ataque(Personagem p, Ataque a){
        p.diminuiHP(this.forca);
    }
}
