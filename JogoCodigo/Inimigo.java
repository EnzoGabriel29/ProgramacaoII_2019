package JogoCodigo;

public class Inimigo extends Personagem{

    public Inimigo(String nome, int level) {
        super(nome);
        this.level = level;
        this.forca = this.level*2;
        this.hp = this.level*20 + 100;
    }
    
    @Override
    public void treinar(){}

    @Override
    public void ataque(Personagem p) {
        p.diminuiHP(this.forca);
    }
}
