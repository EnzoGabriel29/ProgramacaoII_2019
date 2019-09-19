package JogoCodigo;

import JogoCodigo.Inimigo;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadDeBatalha extends Thread{

    private int cont = 0;
    private Personagem personagem;
    private BattleActionListener listener;
    private boolean isPausar = true;

    public ThreadDeBatalha(Personagem personagem) {
        this.personagem = personagem;
        this.listener = new BattleActionListener() {
            @Override public void battleEnd(){}
            @Override public void roundEnd(Personagem p, Personagem i){}
            @Override public void lostHP(int v){}
        };
    }
    
    public void ativar(){
        isPausar = false;
    }
    
    public void desativar(){
        isPausar = true;
    }
        
    @Override
    public void run() {
        while (true) {
            System.out.print("");
            if (!isPausar){
                System.out.print("");
                
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex){}
                
                Inimigo in = new Inimigo("Dragão", 1);
                while (in.getHp() > 0 && personagem.getHp() > 0){
                    if (isPausar){
                        
                        
                        break;
                    }
                    
                    in.ataque(personagem);
                    listener.lostHP(in.getForca());
                    personagem.ataque(in);
                    
                    listener.roundEnd(personagem, in);
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex){}
                }
                
                if (isPausar) continue;
                
                if(personagem.getHp() > 0){
                    this.personagem.treinar();
                }
                personagem.restauraHP();

                listener.battleEnd();
            }
        }
    }

    public void setListener(BattleActionListener listener) {
        this.listener = listener;
    }
    
    public static interface BattleActionListener {
        public void battleEnd();
        public void roundEnd(Personagem p, Personagem in);
        public void lostHP(int valor);
    }
    
}
