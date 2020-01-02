package jogoInterface.Alquimista;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import jogoCodigo.Item.ItemConstruivel;

/**
 * Gerencia as trocas realizadas na JFrame JanelaAlquimista.
 * 
 * @author Enzo
 */
public class AlquimistaManager {
    private final JanelaAlquimista window;
    private final List<JButton> buttons;
    private final List<ComboList> comboLists;
    private final List<TableColumn> tableCols;
    private final JProgressBar progressBar; 
    public final int[] numItensAdded = {0, 0, 0};
    
    /**
     * Inicializa um AlquimistaManager.
     * 
     * @param j a JanelaAlquimista a ser gerenciada.
     */
    public AlquimistaManager(JanelaAlquimista j){
        this.window = j;
        this.buttons = new ArrayList<>();
        this.comboLists = new ArrayList<>();
        this.tableCols = new ArrayList<>();
        this.progressBar = j.getBarraProgresso();
    }
    
    /**
     * Adiciona uma classe de ItemConstruivel para ser gerenciada.
     * 
     * @param c a ComboList relacionada com essa classe, onde
     *          serão armazenados os itens que o usuário possui.
     * @param b o JButton relacionada com essa classe, onde será
     *          utilizado para enviar os itens do usuário ao alquimista.
     * @param t a TableColumn relacionada com essa classe, onde
     *          serão armazenados os itens que o alquimista possui.
     */
    public void add(final ComboList c, final JButton b, final TableColumn t){
        this.comboLists.add(c);
        this.buttons.add(b);
        this.tableCols.add(t);
        
        b.addActionListener((ActionEvent e) -> {
            if (t.column == 0 && numItensAdded[0] == 1) return;
            if (t.column == 1 && numItensAdded[1] == 2) return;
            if (t.column == 2 && numItensAdded[2] == 4) return;
            
            int pos = c.comboBox.getSelectedIndex();
            if (pos < 0) return;
            
            moveToAlquimista(t.column, pos, 1);
        });
    }
    
    /**
     * Limpa o conteúdo de uma coluna da JTable.
     * 
     * @param i o índice da coluna.
     */
    public void clearColumn(int i){
        this.numItensAdded[i] = 0;
        TableColumn tc = this.getTableColumn(i);
        int numItens = tc.numItens;
        for (int j = 0; j < numItens; j++)
            tc.remove(0);
    }
    
    /**
     * Retorna uma ComboList relacionada com uma classe. 
     * 
     * @param i a classe ou posição da ComboList de acordo
     *          com a ordem que ela foi inserida. Como nesse
     *          projeto, os valores constantes das classes A,
     *          B e C são 0, 1 e 2, então é possível utilizar
     *          esses valores como parâmetros no método.
     * @return a ComboList relacionada.
     */
    public ComboList getComboList(int i){
        return this.comboLists.get(i);
    }
    
    /**
     * Retorna um JButton relacionada com uma classe. 
     * 
     * @param i a classe ou posição do JButton de acordo
     *          com a ordem que ele foi inserido. Como nesse
     *          projeto, os valores constantes das classes A,
     *          B e C são 0, 1 e 2, então é possível utilizar
     *          esses valores como parâmetros no método.
     * @return o JButton relacionado.
     */
    public JButton getJButton(int i){
        return this.buttons.get(i);
    }
    
    /**
     * Retorna uma TableColumn relacionada com uma classe. 
     * 
     * @param i a classe ou posição da TableColumn de acordo
     *          com a ordem que ela foi inserida. Como nesse
     *          projeto, os valores constantes das classes A,
     *          B e C são 0, 1 e 2, então é possível utilizar
     *          esses valores como parâmetros no método.
     * @return a TableColumn relacionada.
     */
    public TableColumn getTableColumn(int i){
        return this.tableCols.get(i);
    }
    
    /**
     * Envia um item do usuário para o alquimista.
     * 
     * @param classItem a classe desse item.
     * @param indexItem a posição desse item na ComboList.
     * @param qtdItem a quantidade a ser enviada.
     */
    public void moveToAlquimista(int classItem, int indexItem, int qtdItem){
        ComboList cl = this.getComboList(classItem);
        TableColumn tc = this.getTableColumn(classItem);
        
        ItemConstruivel icu = cl.get(indexItem);
        ItemConstruivel ica;
        
        if (icu.getContador() < qtdItem) throw new IllegalArgumentException();
        if (icu.getContador() == qtdItem) cl.remove(indexItem);
        else icu.diminuiContador(qtdItem);
            
        if (tc.search(icu.getNome())){
            ica = tc.getItem(icu.getNome());
            ica.aumentaContador(qtdItem);
            
        } else {
            ica = new ItemConstruivel(icu.getNome(), icu.getClasse());
            ica.setContador(qtdItem);
            tc.add(ica);
        }
        
        numItensAdded[classItem] += qtdItem;
        if (this.getNumItens() == 7) window.definePocao();
        this.progressBar.setValue(this.progressBar.getValue() + qtdItem);
        tc.model.fireTableDataChanged();
    }
    
    /**
     * Devolve um item enviado para o alquimista ao usuário.
     * 
     * @param classItem a classe desse item.
     * @param indexItem a posição desse item na TableColumn.
     * @param qtdItem a quantidade a ser enviada.
     */
    public void moveToUsuario(int classItem, int indexItem, int qtdItem){
        ComboList cl = this.getComboList(classItem);
        TableColumn tc = this.getTableColumn(classItem);
        
        ItemConstruivel ica = tc.get(indexItem);
        ItemConstruivel icu;
        
        if (ica.getContador() < qtdItem) throw new IllegalArgumentException();
        if (ica.getContador() == qtdItem) tc.remove(indexItem);
        else ica.diminuiContador(qtdItem);
        
        if (cl.search(ica.getNome())){
            icu = cl.getItem(ica.getNome());
            icu.aumentaContador(qtdItem);
            
        } else {
            icu = new ItemConstruivel(ica.getNome(), ica.getClasse());
            icu.setContador(qtdItem);
            cl.add(icu);
        }
        
        cl.sort();
        numItensAdded[classItem] -= qtdItem;
        if (this.getNumItens() < 7) window.setPocao(null);
        tc.model.fireTableDataChanged();
        this.progressBar.setValue(this.progressBar.getValue() - qtdItem);
    }
    
    /**
     * Devolve todos os itens enviados para o alquimista ao usuário.
     */
    public void moveAllToUsuario(){
        TableColumn tc;
        
        int numItens;
        for (int i = 0; i < 3; i++){
            tc = this.getTableColumn(i);
            
            numItens = tc.numItens;
            for (int j = 0; j < numItens; j++){
                ItemConstruivel item = tc.get(0);
                this.moveToUsuario(i, 0, item.getContador());
            }
        }
    }
    
    /**
     * Calcula o número de itens que foram enviados ao alquimista.
     * 
     * @return o número de itens. 
     */
    public int getNumItens(){
        return numItensAdded[0] + numItensAdded[1] + numItensAdded[2];
    }
}
