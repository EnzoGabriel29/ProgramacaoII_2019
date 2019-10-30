package jogoCodigo;

import jogoInterface.JanelaPrincipal;

public class ClassePrincipal {
    
    public static void main(String[] args) {
        Personagem p1;
        p1 = new Mago("Pedro");        
        
        new JanelaPrincipal(p1).setVisible(true);
    }  
    
}
