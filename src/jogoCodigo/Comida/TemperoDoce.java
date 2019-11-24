package jogoCodigo.Comida;

/**
 * Adiciona um tempero doce na comida que faz com que a
 * fome do personagem diminua o dobro do que deveria.
 * @author Enzo
 */
public class TemperoDoce extends DecoratorComida {
    public TemperoDoce(Comida c){
        super(c);
    }

    @Override
    public int getFomeRest(){
        return 2 * this.comida.getFomeRest();
    }
}
