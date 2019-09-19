package jogoCodigo;

public class Mago extends Personagem {

    public Mago(String apelido) {
        super(apelido);
        this.inteligencia = 20;
    }

    @Override
    public void treinar() {
        inteligencia += nivel*3;
        this.aumentaXP(nivel*10);
        forca++;
        this.maxHP += nivel*19;
    }

    @Override
    public void ataque(Personagem p) {
        p.diminuiHP(this.inteligencia);
    }
    
}
