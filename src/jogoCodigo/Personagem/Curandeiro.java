package jogoCodigo.Personagem;

import jogoCodigo.Ataque;
import jogoCodigo.Atributos;

public class Curandeiro extends Personagem {
    private static final Ataque ATK001 = new Ataque("Raio de energia", 20);
    private static final Ataque ATK002 = new Ataque("Espinhos mágicos", 30);
    private static final Ataque ATK004 = new Ataque("Rajada de fogo", 40);
    private static final Ataque ATK008 = new Ataque("Trovão incandescente", 50);
    private static final Ataque ATK016 = new Ataque("Explosão mística", 60);
    private static final Ataque ATK032 = new Ataque("Sopro do dragão", 70);
    
    public Curandeiro(String apelido){
        super(apelido);
        this.defineMaxHP(this.getMaxHP() + 20);
    }

    @Override
    public void evolui(){
        this.nivel++;
        
        this.atualizaAtributos(new Atributos(){
            @Override public int getHP(){ return nivel*3; }
            @Override public int getMaxHP(){ return nivel*19; }
            @Override public int getForca(){ return 10; }
            @Override public int getInteligencia(){ return 10; }
        });
        
        switch (this.nivel){
            case 1: this.mochila.adicionaAtaque(ATK001); break;
            case 2: this.mochila.adicionaAtaque(ATK002); break;
            case 4: this.mochila.adicionaAtaque(ATK004); break;
            case 8: this.mochila.adicionaAtaque(ATK008); break;
            case 16: this.mochila.adicionaAtaque(ATK016); break;
            case 32: this.mochila.adicionaAtaque(ATK032); break;
        }
        
        if (this.maxHP > this.nivel*100)
            this.maxHP = this.nivel*100;
    }
    
    @Override
    public void melhora(){
        this.atualizaAtributos(new Atributos(){
            @Override public int getHP(){ return this.hp + 3; }
            @Override public int getMaxHP(){ return 0; }
            @Override public int getForca(){ return this.forca + 1; }
            @Override public int getInteligencia(){ return 0; }
        });
    }

    @Override
    public void ataque(Personagem p, Ataque a){
        int danoAtaque = a == null ? 0 : a.getDano();
        danoAtaque += this.getHP() / 10;
        p.diminuiHP(this.getForca() + danoAtaque);
        listener.ataca(a, p);
    }
    
}
