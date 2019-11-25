package jogoCodigo.Personagem;

import jogoCodigo.Ataque;
import jogoCodigo.Atributos;

/**
 * Fornece a classe de jogo Curandeiro.
 * A cada ataque, é adicionado 10% do valor da vida do personagem.
 * A cada vitória de batalha, é adicionado 3 pontos de HP e 1 ponto de força.
 * A cada avanço de nível, é adicionado o triplo do valor do novo nível ao HP,
 * além de 10 pontos de força e inteligência e 19 vezes o valor do novo nível
 * ao HP máximo. Ao começar o jogo, o curandeiro começa com 20 pontos a mais
 * de HP máximo em comparação às outras classes de personagens.
 * @author Enzo
 */
public class Curandeiro extends Personagem {
    private static Ataque retornaAtaque(int nivel){
        switch (nivel){
            case 2: return new Ataque("Clarão de luz", 20);
            case 4: return new Ataque("Névoa lacrimejante", 30);
            case 8: return new Ataque("Rajada de fogo", 40);
            case 16: return new Ataque("Penitência", 50);
            case 32: return new Ataque("Choque sagrado", 60);
            case 64: return new Ataque("Cura reversa", 70);
        }
        
        return null;
    }
    
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
        
        Ataque a = Curandeiro.retornaAtaque(this.nivel);
        if (a != null) this.mochila.adicionaAtaque(a);
        
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
        danoAtaque += this.getForca();
        danoAtaque += this.getHP() / 10;
        
        String nomeAtaque = a == null ? "Sem ataque" : a.getNome();
        
        p.diminuiHP(danoAtaque);
        listener.ataca(p, nomeAtaque, danoAtaque);
    }
    
}
