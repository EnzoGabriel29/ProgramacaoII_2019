package jogoCodigo.Personagem;

import jogoCodigo.Ataque;
import jogoCodigo.Atributos;

/**
 * Fornece a classe de jogo Gladiador.
 * A cada ataque, é adicionado 10% do valor da força do personagem.
 * A cada vitória de batalha, é adicionado 3 pontos de força.
 * A cada avanço de nível, é adicionado o triplo do valor do novo nível à
 * força, além de 10 pontos de inteligência e 19 vezes o valor do novo nível
 * ao HP máximo. Ao começar o jogo, o gladiador começa com 20 pontos a mais
 * de força em comparação às outras classes de personagens.
 * @author Enzo
 */
public class Gladiador extends Personagem {
    private static final Ataque ATK001 = new Ataque("Soco paralisante", 20);
    private static final Ataque ATK002 = new Ataque("Picada de abelha", 30);
    private static final Ataque ATK004 = new Ataque("Avalanche manual", 40);
    private static final Ataque ATK008 = new Ataque("Golpe cauterizador", 50);
    private static final Ataque ATK016 = new Ataque("Murro da aflição", 60);
    private static final Ataque ATK032 = new Ataque("Apunhalada mortal", 70);
    
    public Gladiador(String apelido){
        super(apelido);
        this.forca += 20;
    }
    
    @Override
    public void evolui(){
        this.nivel++;
        
        this.atualizaAtributos(new Atributos(){
            @Override public int getHP(){ return 0; }
            @Override public int getMaxHP(){ return nivel*19; }
            @Override public int getForca(){ return nivel*3; }
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
            @Override public int getHP(){ return 0; }
            @Override public int getMaxHP(){ return 0; }
            @Override public int getForca(){ return this.forca + 3; }
            @Override public int getInteligencia(){ return 0; }
        });
    }

    @Override
    public void ataque(Personagem p, Ataque a) {
        int danoAtaque = a == null ? 0 : a.getDano();
        danoAtaque += this.getForca() / 10;
        p.diminuiHP(this.getForca() + danoAtaque);
        listener.ataca(a, p);
    }
    
}
