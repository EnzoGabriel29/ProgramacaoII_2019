package jogoCodigo.Pocao;

/**
 * Fornece um decorator para os itens do tipo Pocao.
 * @author Enzo
 */
abstract public class DecoratorPocao extends Pocao {
    protected Pocao pocao;
    
    public DecoratorPocao(Pocao p){
        super(p.getNome(), p.getAtributos());
        this.pocao = p;
    }
}
