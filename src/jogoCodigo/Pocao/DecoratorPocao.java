package jogoCodigo.Pocao;

abstract public class DecoratorPocao extends Pocao {
    protected Pocao pocao;
    
    public DecoratorPocao(Pocao p){
        super(p.getNome(), p.getAtributos());
        this.pocao = p;
    }
}
