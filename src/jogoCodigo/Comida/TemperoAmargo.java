package jogoCodigo.Comida;

/**
 * Adiciona um tempero amargo na comida, que ao invés da fome do
 * personagem diminuir quando se come, a fome do personagem é aumentada.
 * @author Enzo
 */
public class TemperoAmargo extends DecoratorComida {
    public TemperoAmargo(Comida c){
        super(c);
    }

    @Override
    public int getFomeRest(){
        return -2 * this.comida.getFomeRest();
    }
}
