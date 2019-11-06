package jogoCodigo;

import jogoCodigo.Calculo.ColecaoAleatoria;

public class Inimigo extends Personagem {
    enum TipoInimigo {
        DRAGAO, TRASGO, OGRO, GIGANTE, BRUXA, VAMPIRO
    }
    
    public static Inimigo retornaInimigo(int nivel){
        ColecaoAleatoria<TipoInimigo> ca = new ColecaoAleatoria<>();
        
        ca.add(1, TipoInimigo.DRAGAO)
                .add(1, TipoInimigo.TRASGO)
                .add(1, TipoInimigo.OGRO)
                .add(1, TipoInimigo.GIGANTE)
                .add(1, TipoInimigo.BRUXA)
                .add(1, TipoInimigo.VAMPIRO);
        
        int inimigoNivel = nivel == 1 ? 1 : nivel - 1;
        
        switch (ca.retornaValor()){
            case DRAGAO: return new Inimigo("Dragão", inimigoNivel);
            case TRASGO: return new Inimigo("Trasgo", inimigoNivel);
            case OGRO: return new Inimigo("Ogro", inimigoNivel);
            case GIGANTE: return new Inimigo("Gigante", inimigoNivel);
            case BRUXA: return new Inimigo("Bruxa", inimigoNivel);
            case VAMPIRO: return new Inimigo("Vampiro", inimigoNivel);        
        }
        
        return null;
    }
    
    public Inimigo(String nome, int nivel){
        super(nome);
        this.setNivel(nivel);
    }
    
    private void setNivel(int nivel){
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
