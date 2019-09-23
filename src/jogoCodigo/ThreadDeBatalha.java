package jogoCodigo;


public class ThreadDeBatalha extends Thread {

    private int cont = 0;
    private Personagem personagem;
    private BattleActionListener listener;
    private boolean isBatalha = false;
    private boolean aguardandoAtaque = false;
    private Ataque ataqueAtual;

    public ThreadDeBatalha(Personagem personagem){
        this.personagem = personagem;
        this.listener = new BattleActionListener(){
            @Override public void terminaBatalha(){}
            @Override public void terminaRodada(Personagem p, Personagem i){}
            @Override public void inimigoEncontrado(Personagem in){}
            @Override public void jogadorFugiu(){}
            @Override public void aguardaAtaque(){}
        };
    }
    
    public void ativar(){
        this.isBatalha = true;
    }
    
    public void desativar(){
        this.isBatalha = false;
    }
    
    public void defineAtaque(Ataque a){
        this.ataqueAtual = a;
        this.aguardandoAtaque = false;
    }
        
    public void aguarda(int valor){
        try { Thread.sleep(valor*1000);
        } catch (InterruptedException e){}
    }

    public Inimigo retornaInimigo(){
        String[] inimigos = {"Dragão", "Trasgo", "Ogro", "Gigante", "Bruxa"};

        int al = (int)(Math.random()*5);
        return new Inimigo(inimigos[al], this.personagem.getNivel());
    }

    @Override
    public void run(){
        while (true){
            System.out.print("");
            if (isBatalha){                     
                Inimigo in = retornaInimigo();
                aguarda(5);
                if (!isBatalha) continue;
                listener.inimigoEncontrado(in);
                aguarda(3);
                if (!isBatalha){
                    listener.jogadorFugiu();
                    continue;
                }

                while (in.getHp() > 0 && personagem.getHp() > 0){
                    System.out.print("");
                    in.ataque(personagem, new Ataque("Padrão", 20));
                    
                    listener.aguardaAtaque();
                    aguardandoAtaque = true;
                    while (aguardandoAtaque){
                        System.out.print("");
                        if (!isBatalha){
                            listener.jogadorFugiu();
                            break;
                        }
                    }
                    
                    if (!isBatalha) break;
                    
                    personagem.ataque(in, ataqueAtual);
                    listener.terminaRodada(personagem, in);
                    
                    aguarda(1);
                }
                
                if (!isBatalha) continue;
                
                if (personagem.getHp() > 0)
                    this.personagem.treinar();

                personagem.restauraHP();

                listener.terminaBatalha();
            }
        }
    }

    public void setListener(BattleActionListener listener) {
        this.listener = listener;
    }
    
    public static interface BattleActionListener {
        public void terminaBatalha();
        public void terminaRodada(Personagem p, Personagem in);
        public void inimigoEncontrado(Personagem in);
        public void jogadorFugiu();
        public void aguardaAtaque();
    }
    
}
