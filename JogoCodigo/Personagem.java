package JogoCodigo;

public abstract class Personagem {
    private AtributosListener listener;
    private String apelido;
    protected int hp;
    protected int forca;
    protected int inteligencia;
    protected int maxHp;
    protected int level;
    protected int xp;

    public Personagem(String apelido) {
        this.apelido = apelido;
        this.hp = 100;
        this.maxHp = 100;
        this.forca = 10;
        this.level = 1;
    }
    
    public Personagem() {
        this("Sem nome");
    }
    
    public abstract void treinar();
    public abstract void ataque(Personagem p);
    
    public void setListener(AtributosListener listener){
        this.listener = listener;
    }
    
    public void aumentaXP(int valor){
        this.xp += valor;
        if (this.xp >= 100){
            this.level += this.xp / 100;
            this.xp %= 100;
        }
        
        listener.alteraXP(this.xp);
    }
    
    public void aumentaHP(int valor){
        this.hp += valor;
        if (this.hp > this.maxHp)
            this.hp = this.maxHp;
        
        listener.alteraHP(this.xp);
    }
    
    public void diminuiHP(int valor){
        this.hp -= valor;
        if (this.hp < 0)
            this.hp = 0;
        
        listener.alteraHP(this.xp);
    }
    
    public void restauraHP(){
        this.hp = this.maxHp;
    }
        
    public int getXp(){
        return xp;
    }
    
    public int getLevel(){
        return level;
    }
    
    public String getApelido() {
        return apelido;
    }

    public int getHp() {
        return hp;
    }

    public int getForca() {
        return forca;
    }

    public int getInteligencia() {
        return inteligencia;
    }
    
    public static interface AtributosListener {
        public void alteraHP(int valor);
        public void alteraXP(int valor);
    }
    
}
