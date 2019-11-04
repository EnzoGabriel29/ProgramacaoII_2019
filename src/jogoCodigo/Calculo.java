package jogoCodigo;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class Calculo {
    
    // https://stackoverflow.com/questions/6409652
    public static class ColecaoAleatoria<E> {
        private final NavigableMap<Double, E> map = new TreeMap<>();
        private final Random random;
        private double total = 0;

        public ColecaoAleatoria(){
            this(new Random());
        }

        public ColecaoAleatoria(Random random){
            this.random = random;
        }

        public ColecaoAleatoria<E> add(double peso, E valor){
            if (peso <= 0) return this;
            total += peso;
            map.put(total, valor);
            return this;
        }

        public E retornaValor() {
            double valor = random.nextDouble() * total;
            return map.higherEntry(valor).getValue();
        }
    }
    
}

