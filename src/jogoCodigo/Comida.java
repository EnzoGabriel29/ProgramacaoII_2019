package jogoCodigo;

import jogoCodigo.Calculo.ColecaoAleatoria;

public class Comida extends ItemConsumivel {
    public static final Comida UVA      = new Comida("Uva", 1);
    public static final Comida MACA     = new Comida("Maçã", 2);
    public static final Comida BANANA   = new Comida("Banana", 5);
    public static final Comida CENOURA  = new Comida("Cenoura", 10);
    public static final Comida ENSOPADO = new Comida("Ensopado", 20);
    public static final Comida FRANGO   = new Comida("Frango", 50);
    public int fomeRestaurada;
    
    public static Comida retornaComida(){
        ColecaoAleatoria<Comida> rcc = new ColecaoAleatoria<>();
        
        // total = 1 + 2 + 3 + 4 + 5 + 6 = 21
        // define uma probabilidade de cada item aparecer
        //     6/21 = 28% de aparecer uma uva
        //     5/21 = 23% de aparecer uma maçã
        //     4/21 = 19% de aparecer uma banana
        //     3/21 = 14% de aparecer uma cenoura
        //     2/21 =  9% de aparecer um ensopado
        //     1/21 =  4% de aparecer um frango
        
        rcc.add(6, UVA)
           .add(5, MACA)
           .add(4, BANANA)
           .add(3, CENOURA)
           .add(2, ENSOPADO)
           .add(1, FRANGO);                        
        
        return rcc.retornaValor();
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
