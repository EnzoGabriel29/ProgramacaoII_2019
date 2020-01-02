package jogoInterface.Alquimista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import jogoCodigo.Item.ItemConstruivel;

/**
 * Fornece métodos para manipular uma JComboBox.
 * @author Enzo
 */
public class ComboList {
    protected int numItens = 0;
    protected JComboBox comboBox;
    protected DefaultComboBoxModel model;
    
    /**
     * Inicializa uma ComboList.
     * @param c a JComboBox relacionada.
     */
    public ComboList(JComboBox c){
        this.comboBox = c;
        this.model = (DefaultComboBoxModel) c.getModel();
    }
    
    /**
     * Adiciona um item à JComboBox.
     * @param item o item a ser adicionado
     */
    public void add(ItemConstruivel item){
        this.comboBox.addItem(item);
        this.numItens++;
    }
    
    /**
     * Retorna um item da JComboBox.
     * @param pos a posição do item.
     * @return o item na posição especificada.
     */
    public ItemConstruivel get(int pos){
        return (ItemConstruivel) this.comboBox.getItemAt(pos);
    }
    
    /**
     * Remove um item da JComboBox, deslocando
     * os itens à sua direita para a esquerda.
     * @param pos a posição do item.
     * @return o item na posição especificada.
     */
    public ItemConstruivel remove(int pos){
        ItemConstruivel item = this.get(pos);
        this.comboBox.removeItemAt(pos);
        this.numItens--;
        
        return item;
    }
    
    /**
     * Verifica se um item está presente na JComboBox.
     * @param nome o nome do item.
     * @return se o item está presente.
     */
    public boolean search(String nome){
        for (int i = 0; i < this.numItens; i++){
            if (nome.equals(this.get(i).getNome()))
                return true;
        }
        return false;
    }
    
    /**
     * Retorna um item presente na JComboBox.
     * @param nome o nome do item.
     * @return o item com o nome especificado.
     * @throws IllegalArgumentException 
     *         se o item não está presente.
     */
    public ItemConstruivel getItem(String nome){        
        ItemConstruivel item;
        for (int i = 0; i < this.numItens; i++){
            item = this.get(i);
            if (nome.equals(item.getNome()))
                return item;
        }
        
        throw new IllegalArgumentException();
    }
    
    /**
     * Coloca os itens da JComboBox em uma List.
     * @return a lista com os itens.
     */
    public List<ItemConstruivel> asList(){
        List<ItemConstruivel> list = new ArrayList<>();
        
        ItemConstruivel item;
        for (int i = 0; i < this.numItens; i++){
            item = this.get(i);
            list.add(item);
        }
        
        return list;
    }
    
    /**
     * Ordena os itens da JComboBox por nome.
     */
    public void sort(){
        List<ItemConstruivel> actualItens = this.asList();
                
        Collections.sort(actualItens, (ItemConstruivel i1,
                ItemConstruivel i2) -> i1.getNome().compareTo(i2.getNome()));
        
        DefaultComboBoxModel sortedModel = new DefaultComboBoxModel(actualItens.toArray());
        this.comboBox.removeAllItems();
        this.comboBox.setModel(sortedModel);
        this.model = sortedModel;
    }
}