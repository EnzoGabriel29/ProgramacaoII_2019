package jogoCodigo;

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
    private BattleActionListener listenerBatalha;
    private boolean isPasseio = false;
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
                rca.add(75, ACAO_BAU).add(25, ACAO_INIMIGO);
                int acao = rca.retornaValor();
                
                switch (acao){
                    case ACAO_BAU: {
                        ColecaoAleatoria<Integer> rci = new ColecaoAleatoria<>();
                        rci.add(10, ITEM_COMIDA).add(7, ITEM_POCAO)
                                .add(7, ITEM_MOEDA).add(3, ITEM);                        
                        int objeto = rci.retornaValor();
                        
                        switch (objeto){
                            case ITEM: {
                                System.out.println("item");
                                listenerBatalha.encontraBau();
                                break;
                            }
                            
                            case ITEM_COMIDA: {                             
                                Comida c = Comida.retornaComida();
                                listenerBatalha.encontraBau(c);
                                break;
                            }
                            
                            case ITEM_POCAO: {
                                Pocao p = Pocao.retornaPocao();
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
        ataqueAtual = personagem.getAtaque();
        aguarda(3);
        
        if (!isPasseio) return false;

        while (in.getHP() > 0 && personagem.getHP() > 0){
            in.ataque(personagem, new Ataque("Padrão", 20));
            
            personagem.ataque(in, ataqueAtual);
            listenerBatalha.terminaRodada(personagem, in);
                    
            aguarda(1);
            
            if (!isPasseio) return false;
        }
                
        if (personagem.getHP() > 0)
            this.personagem.aumentaXP(this.personagem.nivel*10);
        
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
        public void encontraBau();
        public void encontraBau(Comida c);
        public void encontraBau(Pocao p);
        public void encontraBau(int moedas);
    }
}
