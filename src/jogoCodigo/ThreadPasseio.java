package jogoCodigo;

import jogoCodigo.Comida.Comida;
import jogoCodigo.Pocao.Pocao;
import jogoCodigo.Personagem.Personagem;
import jogoCodigo.Personagem.Inimigo;
import jogoCodigo.Listener.ListenerPasseio;
import java.util.Random;
import jogoCodigo.Calculo.ColecaoAleatoria;

/**
 * Thread que monitora o passeio do personagem. No passeio, um personagem
 * pode encontrar inimigos, que poderão ser combatidos para ganhar itens e
 * experiência, baús, que poderão ser encontradas moedas, além de poções e
 * comidas que poderão ser armazenadas na mochila do personagem. É possível
 * entrar e sair de um passeio a qualquer hora, porém caso o usuário saia de
 * um passeio no meio de uma batalha, serão perdidos 10 pontos de experiência.
 * @author Enzo
 */
public class ThreadPasseio extends Thread {
    private static ThreadPasseio thread = null;
    
    private ThreadPasseio(Personagem p, int d, ListenerPasseio l){
        this.personagem = p;
        this.nivelDific = d;
        this.listenerBatalha = l;
    }
    
    /**
     * Singleton de uma ThreadPasseio.
     * @param p personagem associado.
     * @param d nível de dificuldade com o qual os inimigos serão gerados.
     * @param l listener utilizado para comunicar com a interface gráfica.
     * @return 
     */
    public static ThreadPasseio getInstance(Personagem p, int d, ListenerPasseio l){
        if (thread == null){
            thread = new ThreadPasseio(p, d, l);
            
        }
        return thread;
    }
    
    /**
     * Verifica se já existe uma instância de ThreadPasseio.
     * @return se o Singleton não for nulo.
     */
    public static boolean isInstance(){
        return thread != null;
    }
    
    private static final int ACAO_BAU = 0;
    private static final int ACAO_INIMIGO = 1;
    private static final int ITEM_POCAO = 2;
    private static final int ITEM_COMIDA = 3;
    private static final int ITEM_MOEDA = 4;
    
    private final Personagem personagem;
    private final int nivelDific;
    private ListenerPasseio listenerBatalha;
    private Ataque ataqueAtual;
        
    @Override
    public void run(){
        while (true){
            Random r1 = new Random();
            int tempoEspera = r1.nextInt(5);

            try {
                Thread.sleep(tempoEspera * 1000);

            } catch (InterruptedException e){
                thread = null;
                return;
            }
            
            /**
             * Decide sobre o que o personagem irá encontrar no passeio.
             * Existe uma chance de 75% do personagem encontrar um baú
             * e 25% do personagem encontrar um inimigo.
             */
            ColecaoAleatoria<Integer> rca = new ColecaoAleatoria<>();
            rca.adicionaItem(75, ACAO_BAU).adicionaItem(25, ACAO_INIMIGO);
            int acao = rca.retornaItem();

            switch (acao){
                case ACAO_BAU: {
                    /**
                     * Caso o jogador encontre um baú, decide sobre o que o
                     * personagem irá encontrar no baú. Existe uma chance de
                     * 37% do personagem encontrar comida, 25% do personagem
                     * encontrar poção e 25% do personagem encontrar moedas.
                     */
                    ColecaoAleatoria<Integer> rci = new ColecaoAleatoria<>();
                    rci.adicionaItem(10, ITEM_COMIDA)
                            .adicionaItem(7, ITEM_POCAO)
                            .adicionaItem(7, ITEM_MOEDA);  
                    
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

                    else return;
                }
            }
            
        }
    }
    
    /**
     * Gerencia uma batalha.
     * @param in inimigo com o qual o personagem irá combater.
     * @return se a batalha foi concluída com sucesso.
     */
    public boolean iniciaBatalha(Inimigo in){
        listenerBatalha.inimigoEncontrado(in);
        ataqueAtual = personagem.getAtaque();
        
        try {
            Thread.sleep(3000);
            
        } catch (InterruptedException e){
            listenerBatalha.jogadorFugiu();
            thread = null;
            return false;
        }
        
        while (in.getHP() > 0 && personagem.getHP() > 0){
            in.ataque(personagem, new Ataque("Padrão", 20));
            
            personagem.ataque(in, ataqueAtual);
            listenerBatalha.terminaRodada(in);
                    
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e){
                listenerBatalha.jogadorFugiu();
                thread = null;
                return false;
            }
        }
                
        if (personagem.getHP() > 0)
            this.personagem.aumentaXP(this.personagem.getNivel()*10);
        else
            this.personagem.morre();
        
        return true;
    }
    
    public void setListenerBatalha(ListenerPasseio l) {
        this.listenerBatalha = l;
    }
}