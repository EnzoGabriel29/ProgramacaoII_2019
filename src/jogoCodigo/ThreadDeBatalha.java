package jogoCodigo;


public class ThreadDeBatalha extends Thread {

    private int cont = 0;
    private Personagem personagem;
    private BattleActionListener listener;
    private boolean isPausar = true;

    public ThreadDeBatalha(Personagem personagem){
        this.personagem = personagem;
        this.listener = new BattleActionListener(){
            @Override public void battleEnd(){}
            @Override public void roundEnd(Personagem p, Personagem i){}
            @Override public void enemyFound(Personagem in){}
            @Override public void playerRunAway(){}
        };
    }
    
    public void ativar(){
        isPausar = false;
    }
    
    public void desativar(){
        isPausar = true;
    }
        
    public void aguarda(int valor){
        try { Thread.sleep(valor*1000);
        } catch (InterruptedException e){}
    }

    public Inimigo retornaInimigo(){
        String[] inimigos = {"Dragão", "Trasgo",
            "Bolsonaro", "Ogro", "Gigante"};

        int al = (int)(Math.random()*10) % 5;
        return new Inimigo(inimigos[al], this.personagem.getNivel());
    }

    @Override
    public void run(){
        while (true){
            if (!isPausar){                       
                Inimigo in = retornaInimigo();
                aguarda(5);
                if (isPausar) continue;
                listener.enemyFound(in);
                aguarda(3);
                if (isPausar){
                    listener.playerRunAway();
                    continue;
                }

                while (in.getHp() > 0 && personagem.getHp() > 0){
                    if (isPausar){
                        listener.playerRunAway();
                        break;
                    }
                    
                    in.ataque(personagem);
                    personagem.ataque(in);
                    
                    listener.roundEnd(personagem, in);
                    
                    aguarda(1);
                }
                
                if (isPausar) continue;
                
                if (personagem.getHp() > 0)
                    this.personagem.treinar();

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
        public void enemyFound(Personagem in);
        public void playerRunAway();
    }
    
}
