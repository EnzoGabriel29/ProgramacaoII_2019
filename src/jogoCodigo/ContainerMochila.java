package jogoCodigo;

import jogoCodigo.Item.ItemEmpilhavel;
import java.util.ArrayList;
import java.util.List;

/**
 * Fornece métodos para manipular itens na mochila.
 * @author Enzo
 * @param <T> o tipo do item que será guardado na mochila.
 */
public class ContainerMochila<T extends ItemEmpilhavel>{
    public final List<T> itens;
    
    /**
     * Inicializa um espaço na mochila.
     */
    public ContainerMochila(){
        this.itens = new ArrayList<>();
    }
    
    /**
     * Getter para os itens na mochila.
     * @return os itens.
     */
    public List<T> retornaItens(){
        return new ArrayList<>(this.itens);
    }
    
    /**
     * Getter para um item na mochila.
     * @param pos posição do item.
     * @return o item na posição.
     */
    public T retornaItem(int pos){
        return this.itens.get(pos);
    }
    
    /**
     * Adiciona um item na mochila.
     * @param item item a ser adicionado.
     */
    public void adicionaItem(T item){
        String nome = item.getNome();
        boolean isDuplicado = false;

        for (ItemEmpilhavel ic : this.itens){
            if (ic.getNome().equals(nome)){
                ic.aumentaContador(1);
                isDuplicado = true;
                break;
            }
        }

        if (!isDuplicado) this.itens.add(item);
    }
    
    /**
     * Remove um item da mochila.
     * @param pos posição do item a ser removido.
     */
    public void removeItem(int pos){
        ItemEmpilhavel item = this.itens.get(pos);
        
        if (item.getContador() == 1)
            this.itens.remove(pos);
                
        else item.diminuiContador(1);   
    }
}