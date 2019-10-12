package jogoCodigo;

import java.util.concurrent.ThreadLocalRandom;
import jogoCodigo.Calculo.RandomCollection;

public class ThreadPasseio extends Thread {
    private static final int ACAO_BAU = 0;
    private static final int ACAO_INIMIGO = 1;
    private static final int ITEM_POCAO = 2;
    private static final int ITEM_COMIDA = 3;
    private static final int ITEM = 4;
    
    private final Personagem personagem;
    private BattleActionListener listenerBatalha;
    private boolean isPasseio = false;
    private boolean aguardandoAtaque = false;
    private Ataque ataqueAtual;
    
    public ThreadPasseio(Personagem p){
        this.personagem = p;
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
    
    public void defineAtaque(Ataque a){
        this.ataqueAtual = a;
        this.aguardandoAtaque = false;
    }
        
    @Override
    public void run(){
        while (true){
            System.out.print("");
            if (isPasseio){
                //int tempoEspera = retornaAleatorio(1, 15);
                //System.out.println(tempoEspera);
                //aguarda(tempoEspera);

                RandomCollection<Integer> rca = new RandomCollection<>();
                rca.add(75, ACAO_BAU).add(25, ACAO_INIMIGO);
                int acao = rca.next();
                
                switch (acao){
                    case ACAO_BAU: {
                        RandomCollection<Integer> rci = new RandomCollection<>();
                        rci.add(10, ITEM_COMIDA).add(7, ITEM_POCAO).add(3, ITEM);                        
                        int objeto = rci.next();
                        
                        switch (objeto){
                            case ITEM: { // encontra Item
                                System.out.println("item");
                                listenerBatalha.encontraBau();
                                break;
                            }
                            
                            case ITEM_COMIDA: { // encontra Comida                                
                                Comida c = Comida.retornaComida();
                                listenerBatalha.encontraBau(c);
                                break;
                            }
                            
                            case ITEM_POCAO: { // encontra Pocao
                                Pocao p = Pocao.retornaPocao();
                                listenerBatalha.encontraBau(p);
                                break;
                            }
                        }
                        
                        break;
                    }

                    case ACAO_INIMIGO: { 
                        Inimigo in = Inimigo.retornaInimigo(personagem);
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
        listenerBatalha.aguardaAtaque();
        aguardandoAtaque = true;

        while (aguardandoAtaque){
            aguarda(0);
            if (!isPasseio) return false;
        }
        
        aguarda(3);
        
        if (!isPasseio) return false;

        while (in.getHP() > 0 && personagem.getHP() > 0){
            in.ataque(personagem, new Ataque("Padrão", 20));
            
            personagem.ataque(in, ataqueAtual);
            listenerBatalha.terminaRodada(personagem, in);
                    
            aguarda(1);
        }
                
        if (!isPasseio) return false;
                
        if (personagem.getHP() > 0)
            this.personagem.evolui();
        
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
        public void encontraBau(Comida c);
        public void encontraBau(Pocao p);
    }
}
