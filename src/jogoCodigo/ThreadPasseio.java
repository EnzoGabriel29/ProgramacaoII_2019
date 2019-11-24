package jogoCodigo;

import jogoCodigo.Comida.Comida;
import jogoCodigo.Pocao.Pocao;
import jogoCodigo.Personagem.Personagem;
import jogoCodigo.Personagem.Inimigo;
import jogoCodigo.Listener.ListenerPasseio;
import java.util.Random;
import jogoCodigo.Calculo.ColecaoAleatoria;

public class ThreadPasseio extends Thread {
    private static final int ACAO_BAU = 0;
    private static final int ACAO_INIMIGO = 1;
    private static final int ITEM_POCAO = 2;
    private static final int ITEM_COMIDA = 3;
    private static final int ITEM_MOEDA = 4;
    private static final int ITEM = 5;
    
    private final Personagem personagem;
    private final int nivelDific;
    private ListenerPasseio listenerBatalha;
    private boolean isPasseio = false;
    private Ataque ataqueAtual;
    
    public ThreadPasseio(Personagem p, int dificuldade){
        this.personagem = p;
        this.nivelDific = dificuldade;
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
        
    @Override
    public void run(){
        while (true){
            System.out.print("");
            if (isPasseio){
                Random r1 = new Random();
                int tempoEspera = r1.nextInt(5);
                aguarda(tempoEspera);

                ColecaoAleatoria<Integer> rca = new ColecaoAleatoria<>();
                rca.adicionaItem(75, ACAO_BAU).adicionaItem(25, ACAO_INIMIGO);
                int acao = rca.retornaItem();
                
                switch (acao){
                    case ACAO_BAU: {
                        ColecaoAleatoria<Integer> rci = new ColecaoAleatoria<>();
                        rci.adicionaItem(10, ITEM_COMIDA).adicionaItem(7, ITEM_POCAO)
                                .adicionaItem(7, ITEM_MOEDA).adicionaItem(3, ITEM);                        
                        int objeto = rci.retornaItem();
                        
                        switch (objeto){  
                            case ITEM_COMIDA: {                             
                                Comida c = Comida.retornaComida();
                                listenerBatalha.encontraBau(c);
                                break;
                            }
                            
                            case ITEM_POCAO: {
                                String classe = personagem.getClass().getSimpleName();
                                Pocao p = Pocao.retornaPocao(classe);
                                listenerBatalha.encontraBau(p);
                                break;
                            }
                            
                            case ITEM_MOEDA: {
                                Random r2 = new Random();
                                int moedas = r2.nextInt(30);
                                listenerBatalha.encontraBau(moedas);
                            }
                        }
                        
                        break;
                    }

                    case ACAO_INIMIGO: { 
                        Inimigo in = Inimigo
                                .retornaInimigo(personagem.getNivel(), nivelDific);
                        boolean batalhaConcluida = this.iniciaBatalha(in);
                        
                        if (batalhaConcluida)
                            listenerBatalha.terminaBatalha(in);
                        
                        else listenerBatalha.jogadorFugiu();
                                                
                        isPasseio = batalhaConcluida;
                    }
                }
            }
        }
    }
    
    public boolean iniciaBatalha(Inimigo in){
        listenerBatalha.inimigoEncontrado(in);
        ataqueAtual = personagem.getAtaque();
        aguarda(3);
        
        if (!isPasseio) return false;

        while (in.getHP() > 0 && personagem.getHP() > 0){
            in.ataque(personagem, new Ataque("Padrão", 20));
            
            personagem.ataque(in, ataqueAtual);
            listenerBatalha.terminaRodada(in);
                    
            aguarda(1);
            
            if (!isPasseio) return false;
        }
                
        if (personagem.getHP() > 0)
            this.personagem.aumentaXP(this.personagem.getNivel()*10);
        
        return true;
    }
    
    public void setListenerBatalha(ListenerPasseio l) {
        this.listenerBatalha = l;
    }
}
