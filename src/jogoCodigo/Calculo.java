package jogoCodigo;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class Calculo {
    
    /**
     * Define seleção de itens aleatória, onde cada
     * item possui uma probabilidade de aparecer.
     * Fonte: https://stackoverflow.com/questions/6409652
     * @param <E> tipo do item a ser escolhido.
     */
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

        /**
         * Adiciona um item com seu respectivo peso.
         * @param peso peso do item a ser adicionado.
         * @param item item a ser adicionado.
         * @return instância de ColecaoAleatoria.
         */
        public ColecaoAleatoria<E> adicionaItem(double peso, E item){
            if (peso <= 0) return this;
            total += peso;
            map.put(total, item);
            return this;
        }

        /**
         * Retorna um item com a probabilidade do seu peso.
         * @return o item escolhido.
         */
        public E retornaItem(){
            double valor = random.nextDouble() * total;
            return map.higherEntry(valor).getValue();
        }
    }
    
}

