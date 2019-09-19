package JogoCodigo;

public class Mago extends Personagem {

    public Mago(String apelido) {
        super(apelido);
        this.inteligencia = 20;
    }

    @Override
    public void treinar() {
        inteligencia += level*3;
        forca++;
        this.maxHp += level*19;
    }

    @Override
    public void ataque(Personagem p) {
        p.diminuiHP(this.inteligencia);
    }
    
}
