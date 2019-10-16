package jogoCodigo;

import jogoInterface.JanelaPrincipal;

public class ClassePrincipal {
    
    public static void main(String[] args) {
        Personagem p1;
        p1 = new Mago("João");        
        
        new JanelaPrincipal(p1).setVisible(true);
    }  
    
}
