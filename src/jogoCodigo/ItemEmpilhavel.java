package jogoCodigo;

/**
 * Fornece uma classe base para itens empilháveis no jogo.
 * Itens empilháveis podem ser agrupados e assim economizar
 * espaço na interface gráfica. Exemplos de itens empilháveis
 * são comidas, poções e itens construíveis. 
 * @author Enzo
 */
abstract public class ItemEmpilhavel extends Item {    
    protected int contador = 1;
    
    /**
     * Aumenta o contador do item.
     * @param n valor a ser aumentado.
     */
    public void aumentaContador(int n){
        if (n < 1) return;
        this.contador += n;
    }
    
    /**
     * Diminui o contador do item.
     * @param n valor a ser diminuido.
     */
    public void diminuiContador(int n){
        if (n < 1) return;
        if (this.contador == 0) return;
        this.contador -= n;
    }
    
    /**
     * Getter para o contador.
     * @return o valor do contador.
     */
    public int getContador(){
        return this.contador;
    }
    
    /**
     * Setter para o contador.
     * @param valor o novo valor do contador.
     */
    public void setContador(int valor){
        this.contador = valor;
    }
    
    @Override
    public String toString() {
        return super.toString() + " (" + this.contador + ")";
    }
}
