package jogoCodigo.Comida;

abstract public class DecoratorComida extends Comida {
    protected Comida comida;
    
    public DecoratorComida(Comida c){
        super(c.getNome(), c.getFomeRest());
        this.comida = c;
    }
}
