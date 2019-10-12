package jogoCodigo;

public abstract class Personagem extends Atributos {
    public AtributosListener listener;
    public Mochila mochila;
    protected Ataque[] ataques;
    protected String apelido;
    protected int fome;
    protected int nivel;
    protected int xp;

    public Personagem(String apelido) {
        this.mochila = new Mochila(this);
        this.apelido = apelido;
        this.hp = 100;
        this.ataques = new Ataque[3];
        this.xp = 0;
        this.maxHP = 100;
        this.forca = 10;
        this.nivel = 1;
        this.fome = 0;

        this.listener = new AtributosListener(){
            @Override public void alteraHP(){}
            @Override public void alteraXP(){}
            @Override public void alteraNivel(){}
            @Override public void alteraMaxHP(){}
            @Override public void alteraFome(){}
            @Override public void ataca(Ataque a, Personagem in){}
            @Override public void atualizaComidas(){}
            @Override public void atualizaAtaques(){}
            @Override public void atualizaPocoes(){}
        };
        
        // Thread que monitora a fome do personagem.
        new Thread(new Runnable(){
            @Override
            public void run(){
                while (true){
                    try { Thread.sleep(60000);
                    } catch (InterruptedException e){}
                    
                    aumentaFome(5);
                }
            }
        }).start();
    }
    
    public Personagem() {
        this("Fulano");
    }
    
    public abstract void evolui();
    public abstract void ataque(Personagem p, Ataque a);
    
    public void defineAtaque(Ataque a, int pos){
        this.ataques[pos] = a;
    }
    
    public void setListener(AtributosListener l){
        this.listener = l;
    }
    
    public void atualizaAtributos(Atributos a){
        if (a.getHP() < 0) this.diminuiHP(a.getHP());
        else this.aumentaHP(a.getHP());
        
        this.forca += a.getForca();
        this.maxHP += a.getMaxHP();
        this.inteligencia += a.getInteligencia();
    }
    
    // Aumenta o XP e o nível, caso o XP ultrapasse 100.
    public void aumentaXP(int valor){
        this.xp += valor;
        if (this.xp >= 100){
            this.nivel += this.xp / 100;
            this.xp %= 100;
            listener.alteraNivel();
        }
        
        listener.alteraXP();
    }

    // Diminui o XP do personagem, não ultrapassando 0.
    public void diminuiXP(int valor){
        this.xp -= valor;
        if (this.xp < 0)
            this.xp = 0;
        
        listener.alteraXP();
    }
    
    // Aumenta o HP do personagem, não ultrapassando o HP máximo.
    public void aumentaHP(int valor){
        this.hp += valor;
        if (this.hp > this.maxHP)
            this.hp = this.maxHP;
        
        listener.alteraHP();
    }
    
    // Diminui o HP do personagem, não ultrapassando 0.
    public void diminuiHP(int valor){
        this.hp -= valor;
        if (this.hp < 0)
            this.hp = 0;
        
        listener.alteraHP();
    }
    
    // Aumenta a fome do personagem, não ultrapassando 100.
    public void aumentaFome(int valor){
        this.fome += valor;
        if (this.fome > 100) this.fome = 100;
        listener.alteraFome();
    }
    
    // Diminui a fome do personagem, não ultrapassando 0.
    public void diminuiFome(int valor){
        this.fome -= valor;
        if (this.fome < 0) this.fome = 0;
        listener.alteraFome();
    }
    
    // Restaura o HP do personagem.
    public void restauraHP(){
        this.aumentaHP(this.maxHP - this.hp);
    }
    
    // Altera o HP máximo do personagem.
    public void defineMaxHP(int valor){
            this.maxHP = valor;
            listener.alteraMaxHP();
    }
    
    public void come(Comida c){
        this.diminuiFome(c.getFomeRest());
    }
    
    public void bebe(Pocao p){
        this.atualizaAtributos(p.retornaAtributos());
    }
    
    // Utilizado para alterar atributos na interface gráfica.
    public static interface AtributosListener {
        public void alteraHP();
        public void alteraXP();
        public void alteraMaxHP();
        public void alteraNivel();
        public void alteraFome();
        public void ataca(Ataque a, Personagem in);
        public void atualizaComidas();
        public void atualizaAtaques();
        public void atualizaPocoes();
    }
    
    @Override public int getHP(){ return this.hp; }
    @Override public int getForca(){ return this.forca; }
    @Override public int getMaxHP(){ return this.maxHP; }
    @Override public int getInteligencia(){ return this.inteligencia; }
    public int getXp(){ return xp; }
    public int getNivel(){ return nivel; }
    public String getApelido(){ return apelido; }
    public int getFome(){ return fome; }
    public Ataque getAtaque(int pos){ return ataques[pos]; }
}
