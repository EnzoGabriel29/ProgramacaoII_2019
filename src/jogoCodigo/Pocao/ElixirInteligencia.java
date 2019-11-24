package jogoCodigo.Pocao;

import jogoCodigo.Atributos;

/**
 * Adiciona um elixir que aumenta em 20 o nível de inteligência à poção.
 * Aparece de vez em quando para a classe de personagem Mago.
 * @author Enzo
 */
public class ElixirInteligencia extends DecoratorPocao {
    public ElixirInteligencia(Pocao p) {
        super(p);
    }
        
    @Override
    public Atributos getAtributos(){
        return new Atributos(){
            @Override
            public int getHP(){
                return pocao.getAtributos().getHP();
            }
            
            @Override
            public int getInteligencia(){
                return pocao.getAtributos().getInteligencia() + 20;
            }

            @Override
            public int getMaxHP(){
                return pocao.getAtributos().getMaxHP();
            }
            
            @Override public int getForca(){
                return pocao.getAtributos().getForca();
            }           
        };
    }
}
