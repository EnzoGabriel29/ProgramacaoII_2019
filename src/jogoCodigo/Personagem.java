package jogoCodigo;

public abstract class Personagem extends Atributos {
    protected AtributosListener listener;
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
            @Override public void adicionaTabela(Ataque a, int pos){}
            @Override public void adicionaTabela(Comida c, int pos){}
        };
        
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
        this("fulano");
    }
    
    public abstract void treinar();
    public abstract void ataque(Personagem p, Ataque a);
    
    public void defineAtaque(Ataque a, int pos){
        this.ataques[pos] = a;
    }
    
    public void setListener(AtributosListener l){
        this.listener = l;
    }
    
    public void aumentaXP(int valor){
        this.xp += valor;
        if (this.xp >= 100){
            this.nivel += this.xp / 100;
            this.xp %= 100;
            listener.alteraNivel();
        }
        
        listener.alteraXP();
    }

    public void diminuiXP(int valor){
        this.xp -= valor;
        if (this.xp < 0)
            this.xp = 0;
        
        listener.alteraXP();
    }
    
    public void aumentaHP(int valor){
        this.hp += valor;
        if (this.hp > this.maxHP)
            this.hp = this.maxHP;
        
        listener.alteraHP();
    }
    
    public void diminuiHP(int valor){
        this.hp -= valor;
        if (this.hp < 0)
            this.hp = 0;
        
        listener.alteraHP();
    }
    
    public void aumentaFome(int valor){
        this.fome += valor;
        if (this.fome > 100) this.fome = 100;
        listener.alteraFome();
    }
    
    public void diminuiFome(int valor){
        this.fome -= valor;
        if (this.fome < 0) this.fome = 0;
        listener.alteraFome();
    }
    
    public void restauraHP(){
        aumentaHP(this.maxHP - this.hp);
    }
        
    public int getXp(){
        return xp;
    }
    
    public int getNivel(){
        return nivel;
    }
    
    public String getApelido(){
        return apelido;
    }

    public int getFome(){
        return fome;
    }
    
    public Ataque getAtaque(int pos){
        return ataques[pos];
    }

    public void defineMaxHP(int valor){
        this.maxHP = valor;
        listener.alteraMaxHP();
    }
    
    public static interface AtributosListener {
        public void alteraHP();
        public void alteraXP();
        public void alteraMaxHP();
        public void alteraNivel();
        public void alteraFome();
        public void ataca(Ataque a, Personagem in);
//      public void adicionaTabela(Item i, int pos);
        public void adicionaTabela(Comida c, int pos);
//      public void adicionaTabela(Pocao p, int pos);
        public void adicionaTabela(Ataque a, int pos);
    }
    
}
