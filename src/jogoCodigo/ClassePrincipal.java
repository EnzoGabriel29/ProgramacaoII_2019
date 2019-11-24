package jogoCodigo;

import jogoCodigo.Personagem.Personagem;
import jogoCodigo.Personagem.Mago;
import jogoInterface.JanelaInicial;
import jogoInterface.JanelaPrincipal;

/**
 * Classe principal do jogo.
 * Deverá ser executada para o jogo ser iniciado.
 * @author usuario
 */
public class ClassePrincipal {
    public static boolean DEBUG = true;
    public static void main(String[] args){
        if (DEBUG){
            Personagem p = new Mago("Enzo");
            p.defineAtaque(new Ataque("Sem ataque", 0));
            new JanelaPrincipal(p, 3).setVisible(true);
            
        } else new JanelaInicial().setVisible(true);
    }  
}
