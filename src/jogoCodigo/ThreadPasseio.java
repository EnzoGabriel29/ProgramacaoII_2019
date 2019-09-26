package jogoCodigo;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadPasseio extends Thread {
    private Personagem personagem;
    private BattleActionListener listenerBatalha;
    private boolean isPasseio = false;
    private boolean aguardandoAtaque = false;
    private Ataque ataqueAtual;
    
    public ThreadPasseio(Personagem p){
        this.personagem = p;
    }
    
    public static int retornaAleatorio(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }
    
    public static void aguarda(int valor){
        try { Thread.sleep(valor*1000);
        } catch (InterruptedException e){}
    }
    
    public void ativa(){
        this.isPasseio = true;
    }
    
    public void desativa(){
        this.isPasseio = false;
    }
    
    public Inimigo retornaInimigo(){
        String[] inimigos = {"Dragão", "Trasgo", "Ogro", "Gigante", "Bruxa"};

        int indexRandom = retornaAleatorio(0, inimigos.length-1);
        int nivelRandom = retornaAleatorio(this.personagem.getNivel()
                -1, this.personagem.getNivel()+1);
        
        return new Inimigo(inimigos[indexRandom], nivelRandom);
    }
    
    public void defineAtaque(Ataque a){
        this.ataqueAtual = a;
        this.aguardandoAtaque = false;
    }
        
    @Override
    public void run(){
        while (true){
            System.out.print("");
            if (isPasseio){
                int tempoEspera = retornaAleatorio(0, 15);
                System.out.println(tempoEspera);
                aguarda(tempoEspera);

                int acao = retornaAleatorio(1, 2);
                System.out.println(acao);
                switch (acao){
                    case 1: { // encontra Baú
                        listenerBatalha.encontraBau();
                        break;
                    }

                    case 2: { // encontra Inimigo
                        Inimigo in = retornaInimigo();
                        boolean batalhaConcluida = this.iniciaBatalha(in);
                        if (batalhaConcluida) listenerBatalha.terminaBatalha();
                        else listenerBatalha.jogadorFugiu();
                        isPasseio = batalhaConcluida;
                    }
                }
            }
        }
    }
    
    public boolean iniciaBatalha(Inimigo in){
        listenerBatalha.inimigoEncontrado(in);
        aguarda(3);
        
        if (!isPasseio) return false;

        while (in.getHp() > 0 && personagem.getHp() > 0){
            in.ataque(personagem, new Ataque("Padrão", 20));
                    
            listenerBatalha.aguardaAtaque();
            aguardandoAtaque = true;
            while (aguardandoAtaque){
                System.out.print("");
                if (!isPasseio) return false;
            }
            
            personagem.ataque(in, ataqueAtual);
            listenerBatalha.terminaRodada(personagem, in);
                    
            aguarda(1);
        }
                
        if (!isPasseio) return false;
                
        if (personagem.getHp() > 0) this.personagem.treinar();
        personagem.restauraHP();
        return true;
    }
    
    public void setListenerBatalha(BattleActionListener l) {
        this.listenerBatalha = l;
    }
    
    public static interface BattleActionListener {
        public void terminaBatalha();
        public void terminaRodada(Personagem p, Personagem in);
        public void inimigoEncontrado(Personagem in);
        public void jogadorFugiu();
        public void aguardaAtaque();
        public void encontraBau();
    }
}
