package jogoCodigo.Listener;

import jogoCodigo.Personagem.Personagem;
import jogoCodigo.Pocao.Pocao;

/**
 * Providencia um listener utilizado para atualizar os
 * atributos do personagem na interface gráfica.
 * @author Enzo
 */
public interface ListenerAtributos {
    public void morre();
    public void alteraHP();
    public void alteraXP();
    public void alteraFome();
    public void alteraMaxHP();
    public void alteraNivel();
    public void atualizaItens();
    public void atualizaPocoes();
    public void atualizaComidas();
    public void atualizaAtaques();
    public void atualizaAtributos();
    public void atualizaArmaduras();
    public void utilizaPocao(Pocao p);
    public void ataca(Personagem i, String ataque, int dano);
}
