package jogoCodigo;

/**
 * Providencia atributos.
 * É uma classe base tanto para personagens quanto para itens.
 * @author Enzo
 */
abstract public class Atributos {
    protected int hp;
    protected int forca;
    protected int inteligencia;
    protected int maxHP;
    
    abstract public int getHP();
    abstract public int getForca();
    abstract public int getInteligencia();
    abstract public int getMaxHP();
}
