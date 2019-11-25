package jogoCodigo.Personagem;

import jogoCodigo.Listener.ListenerAtributos;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import jogoCodigo.Armadura;
import jogoCodigo.Ataque;
import jogoCodigo.Atributos;
import jogoCodigo.Comida.Comida;
import jogoCodigo.Comida.DecoratorComida;
import jogoCodigo.Mochila;
import jogoCodigo.Pocao.Pocao;

/**
 * Fornece uma classe base para todos os personagens do jogo.
 * Um personagem pode ser utilizado pelo usuário para controlar o jogo,
 * onde se escolhe entre mago, gladiador e curandeiro, ou pode ser usado
 * para a modelagem de inimigos.
 * @author Enzo
 */
abstract public class Personagem extends Atributos {
    protected int xp;
    protected int fome;
    protected int nivel;
    public Mochila mochila;
    protected Ataque ataque;
    protected String apelido;
    protected Ataque[] ataques;
    public ListenerAtributos listener;
    protected Armadura[] armaduras = {null, null, null, null};

    /**
     * Inicializa um personagem.
     * @param apelido o apelido do personagem.
     */
    public Personagem(String apelido){
        this.apelido = apelido;
        this.hp = 100;
        this.xp = 0;
        this.maxHP = 100;
        this.forca = 10;
        this.nivel = 1;
        this.fome = 0;

        this.listener = new ListenerAtributos(){
            @Override public void morre(){}
            @Override public void alteraHP(){}
            @Override public void alteraXP(){}
            @Override public void alteraFome(){}
            @Override public void alteraNivel(){}
            @Override public void alteraMaxHP(){}
            @Override public void atualizaItens(){}
            @Override public void atualizaPocoes(){}
            @Override public void atualizaComidas(){}
            @Override public void atualizaAtaques(){}
            @Override public void atualizaAtributos(){}
            @Override public void atualizaArmaduras(){}
            @Override public void encontraDecorator(Comida c){}
            @Override public void ataca(Personagem i, String a, int d){}
        };
        
        this.mochila = new Mochila(this);
        
        /**
         * Thread que monitora a fome do personagem, aumentando 5 pontos de
         * fome a cada minuto.
         */
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(
            new Runnable(){
                @Override
                public void run(){
                    aumentaFome(5);
                }
            },  
        60L, 60L, TimeUnit.SECONDS);
    }
    
    public Personagem() {
        this("Fulano");
    }
    
    /**
     * Define uma ação a ser realizada quando o nível de XP ultrapassa 100.
     */
    public abstract void evolui();
    
    /**
     * Define uma ação a ser realizada quando o personagem ganha uma batalha.
     */
    public abstract void melhora();
    
    /**
     * Define uma ação a ser realizada quando o personagem ataca um oponente.
     * @param p personagem atacado.
     * @param a ataque utilizado.
     */
    public abstract void ataque(Personagem p, Ataque a);
    
    /**
     * Define um ataque atual para o personagem utilizar em batalhas.
     * @param a ataque a ser definido.
     */
    public void defineAtaque(Ataque a){
        this.ataque = a;
    }
    
    /**
     * Define um listener de atributos para o personagem.
     * @param l listener a ser definido.
     */
    public void setListener(ListenerAtributos l){
        this.listener = l;
    }
    
    /**
     * Aumenta valores de atributos do personagem.
     * @param a atributos a serem aumentados.
     */
    public void atualizaAtributos(Atributos a){
        if (a.getHP() < 0) this.diminuiHP(a.getHP());
        else this.aumentaHP(a.getHP());
        
        this.forca += a.getForca();
        this.maxHP += a.getMaxHP();
        this.inteligencia += a.getInteligencia();
        listener.atualizaAtributos();
    }
    
    /**
     * Remove valores de atributos do personagem.
     * @param a atributos a serem removidos.
     */
    public void retiraAtributos(Atributos a){
        if (a.getHP() > 0) this.diminuiHP(a.getHP());
        else this.aumentaHP(a.getHP());
        
        this.forca -= a.getForca();
        this.maxHP -= a.getMaxHP();
        this.inteligencia -= a.getInteligencia();
        listener.atualizaAtributos();
    }
    
    /**
     * Aumenta o XP e o nível, caso o XP ultrapasse 100.
     * @param valor valor de XP a ser aumentado. 
     */
    public void aumentaXP(int valor){
        this.xp += valor;
        if (this.xp >= 100){
            this.xp %= 100;
            this.evolui();
            listener.alteraNivel();
        }
        
        listener.alteraXP();
    }

    /**
     * Diminui o XP do personagem, não ultrapassando 0.
     * @param valor valor da XP a ser diminuído.
     */
    public void diminuiXP(int valor){
        this.xp -= valor;
        if (this.xp < 0)
            this.xp = 0;
        
        listener.alteraXP();
    }
    
    /**
     * Aumenta o HP do personagem, não ultrapassando o HP máximo.
     * @param valor valor de HP a ser aumentado.
     */
    public void aumentaHP(int valor){
        this.hp += valor;
        if (this.hp > this.maxHP)
            this.hp = this.maxHP;
        
        listener.alteraHP();
    }
        
    /**
     * Diminui o HP do personagem, não ultrapassando 0. Caso o personagem
     * esteja utilizando armadura, em relação ao dano recebido, o elmo retém
     * 15%, o peitoral retém 35%, as calças retém 15% e as botas retém 5% do
     * dano. Portanto, utilizando uma armadura completa, o personagem apenas
     * recebe 30% do dano do que receberia originalmente.
     * @param valor valor de HP a ser diminuído.
     */
    public void diminuiHP(int valor){
        Armadura a;
        double porc;
        int valorRed;
        
        for (int i = 0; i < armaduras.length; i++){
            a = armaduras[i];
            if (a == null || !a.isMontada) continue;
            
            switch (a.getTipo()){
                case ELMO: porc = .15; break;
                case PEITORAL: porc = .35; break;
                case CALCAS: porc = .15; break;
                case BOTAS: porc = .5; break;
                default: porc = 1;
            }
            
            valorRed = (int) (porc * valor);
            a.diminuiHP(valorRed);
            if (a.isDanificada()) armaduras[i] = null;
            
            valor -= valorRed;
        }
        
        this.hp -= valor;
        if (this.hp < 0) this.morre();
        
        listener.alteraHP();
        listener.atualizaArmaduras();
    }
    
    /**
     * Aumenta a fome do personagem, não ultrapassando 100.
     * @param valor valor da fome a ser aumentada.
     */
    public void aumentaFome(int valor){
        this.fome += valor;
        if (this.fome > 100) this.morre();
        listener.alteraFome();
    }
    
    /**
     * Diminui a fome do personagem, não ultrapassando 0.
     * @param valor valor da fome a ser aumentada.
     */
    public void diminuiFome(int valor){
        this.fome -= valor;
        if (this.fome < 0) this.fome = 0;
        listener.alteraFome();
    }
        
    /**
     * Restaura o HP do personagem.
     */
    public void restauraHP(){
        this.aumentaHP(this.maxHP - this.hp);
    }
    
    /**
     * Define o HP máximo do personagem.
     * @param valor valor do novo HP máximo.
     */
    public void defineMaxHP(int valor){
        this.maxHP = valor;
        listener.alteraMaxHP();
    }
    
    /**
     * Notifica a interface gráfica sobre a morte do personagem.
     */
    public void morre(){
        listener.morre();
    }
    
    /**
     * Faz o personagem comer uma comida, diminuindo a fome respectiva.
     * @param c comida a ser utilizada.
     */
    public void come(Comida c){
        int fomeRest = c.getFomeRest();
        
        if (fomeRest > 0)
            this.diminuiFome(fomeRest);
        else
            this.aumentaFome(-fomeRest);
        
        if (c instanceof DecoratorComida)
            listener.encontraDecorator(c);
    }
    
    /**
     * Faz o personagem beber uma poção, alterando os atributos respectivos.
     * @param p poção a ser utilizada.
     */
    public void bebe(Pocao p){
        this.atualizaAtributos(p.getAtributos());
    }
    
    /**
     * Faz o personagem montar uma armadura.
     * @param a armadura a ser montada.
     */
    public void monta(Armadura a){
        int pos = 0;
        switch (a.getTipo()){
            case ELMO: pos = 0; break;
            case PEITORAL: pos = 1; break;
            case CALCAS: pos = 2; break;
            case BOTAS: pos = 3; break;
        }
        
        a.isMontada = true;
        this.armaduras[pos] = a;
        this.listener.atualizaArmaduras();
    }
    
    /**
     * Faz o personagem desmontar uma armadura.
     * @param a armadura a ser desmontada.
     */
    public void desmonta(Armadura a){
        int pos = 0;
        switch (a.getTipo()){
            case ELMO: pos = 0; break;
            case PEITORAL: pos = 1; break;
            case CALCAS: pos = 2; break;
            case BOTAS: pos = 3; break;
        }
        
        this.armaduras[pos] = null;
        this.listener.atualizaArmaduras();
    }
    
    /**
     * Verifica se o personagem possui uma armadura.
     * @param nome nome da armadura.
     * @return se a armadura existe no inventário.
     */
    public boolean possuiArmadura(String nome){
        for (Armadura a: this.armaduras){
            if (a == null) continue;
            if (a.getNome().equals(nome)) return true;
        }
        
        return false;
    }
    
    /**
     * Getter para HP.
     * @return o nível de HP.
     */
    @Override
    public int getHP(){
        return this.hp;
    }
    
    /**
     * Getter para força.
     * @return o nível de força.
     */
    @Override
    public int getForca(){
        return this.forca;
    }
    
    /**
     * Getter para HP máximo.
     * @return o nível de HP máximo.
     */
    @Override
    public int getMaxHP(){
        return this.maxHP;
    }
    
    /**
     * Getter para inteligência.
     * @return o nível de inteligência.
     */
    @Override
    public int getInteligencia(){
        return this.inteligencia;
    }
    
    /**
     * Getter para ataque.
     * @return o ataque atual do personagem.
     */
    public Ataque getAtaque(){
        return ataque;
    }
    
    /**
     * Getter para experiência.
     * @return o nível de experiência.
     */
    public int getXP(){
        return xp;
    }
    
    /**
     * Getter para nível.
     * @return o nível do personagem.
     */
    public int getNivel(){
        return nivel;
    }
    
    /**
     * Getter para o apelido.
     * @return o apelido do personagem.
     */
    public String getApelido(){
        return apelido;
    }
    
    /**
     * Getter para a fome.
     * @return o nível de fome.
     */
    public int getFome(){
        return fome;
    }
    
    /**
     * Getter para as armaduras.
     * @param pos posição da armadura.
     * @return a armadura.
     */
    public Armadura getArmadura(int pos){
        return this.armaduras[pos];
    }
}
