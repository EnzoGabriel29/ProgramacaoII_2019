package jogoInterface.Alquimista;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jogoCodigo.ItemConstruivel;

/**
 * Fornece métodos para manipular a coluna de uma JTable.
 * 
 * @author Enzo
 */
public class TableColumn {
    protected int column;
    protected JTable table;
    protected int numItens = 0;
    protected DefaultTableModel model;
    
    /**
     * Inicializa uma TableColumn.
     * 
     * @param t a tabela a qual a coluna pertence.
     * @param col o índice da coluna a ser manipulada.
     */
    public TableColumn(JTable t, int col){
        this.table = t;
        this.column = col;
        this.model = (DefaultTableModel) table.getModel();
    }
    
    /**
     * Adiciona um item à coluna da JTable.
     * 
     * @param item o item a ser adicionado
     */
    public void add(ItemConstruivel item){
        this.set(item, this.numItens);
    }
    
    /**
     * Define um item em uma posição da coluna da JTable.
     * 
     * @param item o item a ser definido.
     * @param pos a posição do item na coluna.
     */
    public void set(ItemConstruivel item, int pos){
        ItemConstruivel itemAnt = this.get(pos);
        if (itemAnt == null && item != null) this.numItens += 1;
        else if (itemAnt != null && item == null) this.numItens -= 1;
        
        this.model.setValueAt(item, pos, this.column);
    }
    
    /**
     * Retorna um item da coluna da JTable.
     * 
     * @param pos a posição do item.
     * @return o item na posição especificada.
     */
    public ItemConstruivel get(int pos){
        return (ItemConstruivel) this.model.getValueAt(pos, this.column);
    }
    
    /**
     * Remove um item da coluna da JTable, deslocando
     * os itens à sua direita para a esquerda.
     * 
     * @param pos a posição do item.
     * @return o item na posição especificada.
     */
    public ItemConstruivel remove(int pos){
        ItemConstruivel item = this.get(pos);
        int offset = this.numItens == 4 ? 0 : 1;
        
        this.set(null, pos);
        
        for (int i = pos; i < this.numItens+offset; i++){
            this.set(this.get(i+1), i);
            this.set(null, i+1);
        }
        
        return item;
    }
    
    /**
     * Verifica se um item está presente na coluna da JTable.
     * 
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
     * Retorna um item presente na coluna da JTable.
     * 
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
     * Coloca os itens da coluna da JTable em uma List.
     * 
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
}
