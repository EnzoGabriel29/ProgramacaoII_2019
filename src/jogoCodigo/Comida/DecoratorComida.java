package jogoCodigo.Comida;

/**
 * Fornece um decorator para os itens do tipo Comida.
 * @author Enzo
 */
abstract public class DecoratorComida extends Comida {
    protected Comida comida;
    
    public DecoratorComida(Comida c){
        super(c.getNome(), c.getFomeRest());
        this.comida = c;
    }
}
