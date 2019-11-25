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
    private static Ataque retornaAtaque(int nivel){
        switch (nivel){
            case 2: return new Ataque("Soco paralisante", 20);
            case 4: return new Ataque("Picada de abelha", 30);
            case 8: return new Ataque("Avalanche manual", 40);
            case 16: return new Ataque("Golpe cauterizador", 50);
            case 32: return new Ataque("Murro da aflição", 60);
            case 64: return new Ataque("Apunhalada mortal", 70);
        }
        
        return null;
    }
    
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
        
        Ataque a = Gladiador.retornaAtaque(this.nivel);
        if (a != null) this.mochila.adicionaAtaque(a);
        
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
        danoAtaque += this.getForca();
        danoAtaque += this.getForca() / 10;
        
        String nomeAtaque = a == null ? "Sem ataque" : a.getNome();
        
        p.diminuiHP(danoAtaque);
        listener.ataca(p, nomeAtaque, danoAtaque);
    }
    
}
