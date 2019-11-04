package jogoCodigo;

import java.util.Random;
import jogoCodigo.Calculo.ColecaoAleatoria;

public class Inimigo extends Personagem {   
    public static final Inimigo DRAGAO = new Inimigo("Dragão", 1);
    public static final Inimigo TRASGO = new Inimigo("Trasgo", 1);
    public static final Inimigo OGRO = new Inimigo("Ogro", 1);
    public static final Inimigo GIGANTE = new Inimigo("Gigante", 1);
    public static final Inimigo BRUXA = new Inimigo("Bruxa", 1);
    public static final Inimigo VAMPIRO = new Inimigo("Vampiro", 1);
    
    public static Inimigo retornaInimigo(Personagem p){
        ColecaoAleatoria<Inimigo> rc = new ColecaoAleatoria<>();
        rc.add(1, DRAGAO).add(1, TRASGO).add(1, OGRO)
                .add(1, GIGANTE).add(1, BRUXA).add(1, VAMPIRO);
        
        Random r = new Random();
        int inimigoNivel;
        
        if (p.getNivel() == 1) inimigoNivel = 1;
        else inimigoNivel = p.getNivel() - 1;
        
        Inimigo i = rc.retornaValor();
        i.defineNivel(inimigoNivel);
        
        return i;
    }
    
    public Inimigo(String nome, int nivel){
        super(nome);
        this.nivel = nivel;
    }
    
    private void defineNivel(int nivel){
        this.nivel = nivel;
        this.hp = this.nivel*20 + 50;
        this.forca = this.nivel + 2;
    }
    
    @Override public void evolui(){}
    @Override public void melhora(){}

    @Override
    public void ataque(Personagem p, Ataque a) {
        p.diminuiHP(this.forca);
    }
}
