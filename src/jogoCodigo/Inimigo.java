package jogoCodigo;

public class Inimigo extends Personagem {

    public Inimigo(String nome, int nivel){
        super(nome);
        this.nivel = nivel;
        this.forca = this.nivel*2;
        this.hp = this.nivel*20 + 100;
    }
    
    @Override
    public void treinar(){}

    @Override
    public void ataque(Personagem p) {
        p.diminuiHP(this.forca);
    }
}
