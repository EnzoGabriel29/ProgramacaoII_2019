package jogoCodigo;

import jogoCodigo.Calculo.ColecaoAleatoria;

public class Comida extends ItemConsumivel {
    enum TipoComida {
        UVA, MACA, BANANA, CENOURA, ENSOPADO, FRANGO
    }
    
    public int fomeRestaurada;
    
    public static Comida retornaComida(){
        ColecaoAleatoria<TipoComida> ca = new ColecaoAleatoria<>();
        
        ca.add(6, TipoComida.UVA)
           .add(5, TipoComida.MACA)
           .add(4, TipoComida.BANANA)
           .add(3, TipoComida.CENOURA)
           .add(2, TipoComida.ENSOPADO)
           .add(1, TipoComida.FRANGO);                        
        
        switch(ca.retornaValor()){
            case UVA: return new Comida("Uva", 1);
            case MACA: return new Comida("Maçã", 2);
            case BANANA: return new Comida("Banana", 5);
            case CENOURA: return new Comida("Cenoura", 10);
            case ENSOPADO: return new Comida("Ensopado", 20);
            case FRANGO: return new Comida("Frango", 50);
        }
        
        return null;
    }
    
    public Comida(String nome, int fomeRest){
        this.nome = nome;
        this.fomeRestaurada = fomeRest;
    }
    
    public int getFomeRest(){
        return this.fomeRestaurada;
    }
    
    @Override
    public String toString(){
        return "ITEM: " + this.nome + "\n" +
               "TIPO: Comida" + "\n" +
               "FOME RESTAURADA: " + this.fomeRestaurada + "\n";
    }
}
