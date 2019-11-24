package jogoCodigo.Pocao;

import jogoCodigo.Atributos;

public class ElixirVida extends DecoratorPocao {
    public ElixirVida(Pocao p){
        super(p);
    }

    @Override
    public Atributos getAtributos(){
        return new Atributos(){
            @Override
            public int getHP(){
                return pocao.getAtributos().getHP() + 20;
            }
            
            @Override
            public int getInteligencia(){
                return pocao.getAtributos().getInteligencia();
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
