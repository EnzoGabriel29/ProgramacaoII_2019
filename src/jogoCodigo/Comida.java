package jogoCodigo;

import jogoCodigo.Calculo.RandomCollection;

public class Comida extends ItemConsumivel {
    public static final Comida UVA      = new Comida("Uva", 1);
    public static final Comida MACA     = new Comida("Maçã", 2);
    public static final Comida BANANA   = new Comida("Banana", 5);
    public static final Comida CENOURA  = new Comida("Cenoura", 10);
    public static final Comida ENSOPADO = new Comida("Ensopado", 20);
    public static final Comida FRANGO   = new Comida("Frango", 50);
    public int fomeRestaurada;
    
    public static Comida retornaComida(){
        RandomCollection<Comida> rcc = new RandomCollection<>();
        
        rcc.add(6, UVA).add(5, MACA)
            .add(4, BANANA).add(3, CENOURA)
            .add(2, ENSOPADO).add(1, FRANGO);                        
        
        return rcc.next();
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
