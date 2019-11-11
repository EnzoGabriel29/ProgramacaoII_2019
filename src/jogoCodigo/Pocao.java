package jogoCodigo;

import jogoCodigo.Calculo.ColecaoAleatoria;

public class Pocao extends ItemEmpilhavel {
    
    public static Pocao retornaPocao(){
        ColecaoAleatoria<TipoPocao> ca = new ColecaoAleatoria<>();
        ca.add(75, TipoPocao.VIDA)
                .add(25, TipoPocao.FORCA)
                .add(25, TipoPocao.SAGACIDADE);

        return Pocao.retornaPocao(ca.retornaValor());
    }
    
    public static Pocao retornaPocao(TipoPocao t){
        switch (t){
            case VIDA: return new Pocao("Poção de vida", new Atributos(){
                            @Override public int getHP(){ return 30; }
                            @Override public int getForca(){ return 0; }
                            @Override public int getInteligencia(){ return 0; }
                            @Override public int getMaxHP(){ return 0; }
                        });
                
            case SAGACIDADE: return new Pocao("Poção de sagacidade", new Atributos(){
                            @Override public int getHP(){ return 0; }
                            @Override public int getForca(){ return 0; }
                            @Override public int getInteligencia(){ return 30; }
                            @Override public int getMaxHP(){ return 0; }
                        });
                
            case FORCA: return new Pocao("Poção de força", new Atributos(){
                            @Override public int getHP(){ return 0; }
                            @Override public int getForca(){ return 20; }
                            @Override public int getInteligencia(){ return 0; }
                            @Override public int getMaxHP(){ return 0; }
                        });
                
            default: return null;
        }
    }
            
    private final TipoPocao tipo;
    private final Atributos atributos;
    
    public Pocao(String nome, Atributos atr){
        this.nome = nome;
        this.atributos = atr;
        
        switch (nome){
            case "Poção de vida": this.tipo = TipoPocao.VIDA; break;
            case "Poção de sagacidade": this.tipo = TipoPocao.SAGACIDADE; break;
            case "Poção de força": this.tipo = TipoPocao.FORCA; break;
            default: this.tipo = null;
        }
    }
    
    public Atributos getAtributos(){
        return this.atributos;
    }
    
    public TipoPocao getTipo(){
        return this.tipo;
    }
    
    public String retornaInfo(){
        return "ITEM: " + this.nome + "\n" +
               "TIPO: Poção" + "\n" +
               "VIDA ADQUIRIDA: " + atributos.getHP() + "\n" +
               "FORÇA ADQUIRIDA: " + atributos.getForca() + "\n" +
               "INTELIGÊNCIA ADQUIRIDA: " + atributos.getInteligencia() + "\n" +
               "VIDA AUMENTADA: " + atributos.getMaxHP() + "\n";
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
