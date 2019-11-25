package jogoCodigo.Personagem;

import jogoCodigo.Ataque;
import jogoCodigo.Atributos;

/**
 * Fornece a classe de jogo Mago.
 * A cada ataque, é adicionado 10% do valor da inteligência do personagem.
 * A cada vitória de batalha, é adicionado 3 pontos de inteligência e 1 ponto
 * de força. A cada avanço de nível, é adicionado o triplo do valor do novo
 * nível à inteligência, além de 10 pontos de força e 19 vezes o valor do novo
 * nível ao HP máximo. Ao começar o jogo, o mago começa com 20 pontos a mais
 * de inteligência em comparação às outras classes de personagens.
 * @author Enzo
 */
public class Mago extends Personagem {
    private static Ataque retornaAtaque(int nivel){
        switch (nivel){
            case 2: return new Ataque("Raio de energia", 20);
            case 4: return new Ataque("Espinhos mágicos", 30);
            case 8: return new Ataque("Rajada de fogo", 40);
            case 16: return new Ataque("Trovão incandescente", 50);
            case 32: return new Ataque("Explosão mística", 60);
            case 64: return new Ataque("Sopro do dragão", 70);
        }
        
        return null;
    }
    
    public Mago(String apelido) {
        super(apelido);
        this.inteligencia += 20;
    }

    @Override
    public void evolui(){
        this.nivel++;
        
        this.atualizaAtributos(new Atributos(){
            @Override public int getHP(){ return 0; }
            @Override public int getMaxHP(){ return nivel*19; }
            @Override public int getForca(){ return 10; }
            @Override public int getInteligencia(){ return nivel*3; }
        });
        
        Ataque a = Mago.retornaAtaque(this.nivel);
        if (a != null) this.mochila.adicionaAtaque(a);
                
        if (this.maxHP > this.nivel*100)
            this.maxHP = this.nivel*100;
    }
    
    @Override
    public void melhora(){
        this.atualizaAtributos(new Atributos(){
            @Override public int getHP(){ return 0; }
            @Override public int getMaxHP(){ return 0; }
            @Override public int getForca(){ return this.forca + 1; }
            @Override public int getInteligencia(){ return this.inteligencia + 3; }
        });
    }

    @Override
    public void ataque(Personagem p, Ataque a){
        int danoAtaque = a == null ? 0 : a.getDano();
        danoAtaque += this.getForca();
        danoAtaque += this.getInteligencia() / 10;
        
        String nomeAtaque = a == null ? "Sem ataque" : a.getNome();
        
        p.diminuiHP(danoAtaque);
        listener.ataca(p, nomeAtaque, danoAtaque);
    }
}
