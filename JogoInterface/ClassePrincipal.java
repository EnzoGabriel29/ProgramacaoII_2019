package JogoInterface;

import JogoCodigo.JanelaPrincipal;
import JogoCodigo.Personagem;
import JogoCodigo.Mago;

public class ClassePrincipal {
    
    public static void main(String[] args) {
        Personagem p1;
        p1 = new Mago("João");        
        
        JanelaPrincipal jp = new JanelaPrincipal(p1);
        jp.setVisible(true);
    }  
    
}
