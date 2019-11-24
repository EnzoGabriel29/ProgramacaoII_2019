package jogoCodigo.Listener;

import jogoCodigo.Comida.Comida;
import jogoCodigo.Personagem.Personagem;
import jogoCodigo.Pocao.Pocao;

/**
 * Providencia um listener utilizado para notificar
 * as ações de batalha e de passeio na interface gráfica.
 * @author Enzo
 */
public interface ListenerPasseio {
    public void jogadorFugiu();
    public void encontraBau(Pocao p);
    public void encontraBau(Comida c);
    public void encontraBau(int moedas);
    public void terminaRodada(Personagem i);
    public void terminaBatalha(Personagem i);
    public void inimigoEncontrado(Personagem i);
}
