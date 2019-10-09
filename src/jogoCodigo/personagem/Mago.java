package jogoCodigo.personagem;

import jogoCodigo.Ataque;

public class Mago extends Personagem {

    public Mago(String apelido) {
        super(apelido);
        this.inteligencia = 20;
        Ataque congelar = new Ataque("Congelar", 30);
        this.mochila.adicionaAtaque(congelar);
        this.defineAtaque(congelar, 0);
    }

    @Override
    public void treinar() {
        inteligencia += nivel*3;
        this.aumentaXP(nivel*10);
        forca++;
        this.maxHP += nivel*19;
    }

    @Override
    public void ataque(Personagem p, Ataque a) {
        p.diminuiHP(a.getDano());
        listener.ataca(a, p);
    }
    
}
