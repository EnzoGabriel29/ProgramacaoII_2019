package jogoCodigo;


public class Mago extends Personagem {
    private static final Ataque ATK01 = new Ataque("Bola de fogo", 10);
    private static final Ataque ATK02 = new Ataque("Cajado elétrico", 15);
    private static final Ataque ATK04 = new Ataque("Coluna de fumaça", 20);
    private static final Ataque ATK08 = new Ataque("Raio explosivo", 30);
    private boolean[] ataquesAdq = {false, false, false, false};

    public Mago(String apelido) {
        super(apelido);
        this.inteligencia = 20;
        Ataque none = new Ataque("Sem ataque", 0);
        this.ataqueAtual = none;
        this.mochila.adicionaAtaque(none);
    }
    
    @Override
    public void atualizaNivel(){
        this.nivel += this.xp / 100;
        this.xp %= 100;
                
        if (this.nivel >= 1 && !ataquesAdq[0]){
            this.mochila.adicionaAtaque(ATK01);
            ataquesAdq[0] = true;
            
        } if (this.nivel >= 2 && !ataquesAdq[1]){
            this.mochila.adicionaAtaque(ATK02);
            ataquesAdq[1] = true;
            
        } if (this.nivel >= 4 && !ataquesAdq[2]){
            this.mochila.adicionaAtaque(ATK04);
            ataquesAdq[2] = true;
            
        } if (this.nivel >= 8 && !ataquesAdq[3]){
            this.mochila.adicionaAtaque(ATK08);
            ataquesAdq[3] = true;
        }
        
        listener.alteraNivel();
    }

    @Override
    public void evolui(){
        Atributos aux = new Atributos(){
            @Override public int getHP(){ return 0; }
            @Override public int getInteligencia(){ return nivel*3; }
            @Override public int getForca(){ return forca+1; }
            @Override public int getMaxHP(){ return nivel*19; }
        };
        this.atualizaAtributos(aux);
        
        this.aumentaXP(nivel*10);
    }

    @Override
    public void ataque(Personagem p, Ataque a) {
        p.diminuiHP(this.forca + a.getDano());
        listener.ataca(a, p);
    }
    
}
