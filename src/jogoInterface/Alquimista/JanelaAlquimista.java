package jogoInterface.Alquimista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;
import jogoCodigo.Atributos;
import jogoCodigo.BancoDados.EnumPocao;
import jogoCodigo.ItemConstruivel;
import jogoCodigo.Personagem.Personagem;

/**
 * Interface gráfica para a tenda do alquimista. O alquimista
 * permite que itens, geralmente adquiridos quando se mata um
 * inimigo, sejam transformados em poções. São necessários um
 * item classe A, dois itens classe B e quatro itens classe C
 * para o alquimista produzir uma poção qualquer.
 * @author Enzo
 */
public class JanelaAlquimista extends javax.swing.JFrame {
    private final AlquimistaManager manager;
    private final Personagem personagem;
    private EnumPocao pocaoDisponivel = null;
    private final int ITENS_TOTAIS = 7;
    
    /**
     * Inicializa uma JanelaAlquimista.
     * 
     * @param p o personagem associado. 
     */
    public JanelaAlquimista(Personagem p) {
        initComponents();
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                onClose();
            }
        });
        
        this.setPocao(null);
        this.personagem = p;
        this.barraItens.setMaximum(ITENS_TOTAIS);
        
        List<ItemConstruivel> itens = personagem.mochila.getItens();

        this.manager = new AlquimistaManager(this);
        this.manager.add(new ComboList(comboA), btnAddA, new TableColumn(tabelaItens, 0));
        this.manager.add(new ComboList(comboB), btnAddB, new TableColumn(tabelaItens, 1));
        this.manager.add(new ComboList(comboC), btnAddC, new TableColumn(tabelaItens, 2));
        
        ComboList comboList;
        for (ItemConstruivel item : itens){
            comboList = this.manager.getComboList(item.getClasse());
            comboList.add(item);
        }   
        
        this.btnDevolverItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int lin = tabelaItens.getSelectedRow();
                int col = tabelaItens.getSelectedColumn();
                if (lin == -1 || col == -1) return;
                if (tabelaItens.getValueAt(lin, col) == null) return;
                manager.moveToUsuario(col, lin, 1);
            }
        });
        
        this.btnDevolverItens.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                manager.moveAllToUsuario();
            }
        });      
        
        this.btnGeraPocao.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                personagem.mochila.adicionaPocao(EnumPocao.getPocao(pocaoDisponivel));
                
                for (int i = 0; i < 3; i++) manager.clearColumn(i);
                setPocao(null);
                ((DefaultTableModel) tabelaItens.getModel()).fireTableDataChanged();
                barraItens.setValue(0);
            }
        }); 
        
        this.btnVoltar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                onClose();
            }
        }); 
    }
    
    /**
     * Define uma poção cujo nome e atributos aparecerão na interface.
     * 
     * @param pocao a poção a ser definida.
     */
    public void setPocao(EnumPocao pocao){
        this.pocaoDisponivel = pocao;
        this.btnGeraPocao.setEnabled(pocao != null);
        
        if (pocao == null){
            this.labelNomePocao.setText("");
            this.labelVida.setText("");
            this.labelInteligencia.setText("");
            this.labelForca.setText("");
            this.labelVidaMax.setText("");
            
        } else {
            Atributos a = pocao.getAtributos();
        
            this.labelNomePocao.setText(pocao.getNome());
            this.labelVida.setText(String.valueOf(a.getHP()));
            this.labelInteligencia.setText(String.valueOf(a.getInteligencia()));
            this.labelForca.setText(String.valueOf(a.getForca()));
            this.labelVidaMax.setText(String.valueOf(a.getMaxHP()));
        }
    }
    
    /**
     * Gera uma poção de acordo com os itens que foram adicionados.
     * O hashcode de cada item é somado e o resultado é mapeado para 
     * seis posições, onde dessas três equivalem à poção de vida, duas
     * equivalem à poção de sagacidade e uma equivale à poção de força.
     * 
     * @return a poção gerada.
     */
    private EnumPocao geraPocao(){
        List<ItemConstruivel> i0 = manager.getTableColumn(0).asList();
        List<ItemConstruivel> i1 = manager.getTableColumn(1).asList();
        List<ItemConstruivel> i2 = manager.getTableColumn(2).asList();

        int hashCodeTotal = 0;
        for (ItemConstruivel item: i0) hashCodeTotal += item.hashCode();
        for (ItemConstruivel item: i1) hashCodeTotal += item.hashCode();
        for (ItemConstruivel item: i2) hashCodeTotal += item.hashCode();

        switch (hashCodeTotal % 6){
            case 0: return EnumPocao.VIDA;
            case 1: return EnumPocao.VIDA;
            case 2: return EnumPocao.VIDA;
            case 3: return EnumPocao.SAGACIDADE;
            case 4: return EnumPocao.SAGACIDADE;
            default: return EnumPocao.FORCA;
        }
    }
    
    /**
     * Gera uma poção e a define na interface.
     */
    public void definePocao(){
        this.setPocao(this.geraPocao());
    }
    
    /**
     * Chamado quando a janela é fechada, tanto pelo botão
     * de fechar a janela quanto pelo botão "Voltar" da interface.
     */
    private void onClose(){
        manager.moveAllToUsuario();
        
        int numItens = personagem.mochila.getItens().size();
        for (int i = 0; i < numItens; i++)
            personagem.mochila.removeItem(0);
        
        for (int i = 0; i < 3; i++)
            for (ItemConstruivel item: manager.getComboList(i).asList())
                personagem.mochila.adicionaItem(item);
    }
    
    /**
     * Getter para a barra de progresso.
     * @return a barra de progresso.
     */
    public JProgressBar getBarraProgresso(){
        return this.barraItens;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        comboA = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboB = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        comboC = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        barraItens = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaItens = new javax.swing.JTable();
        labelVida = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        labelInteligencia = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        labelForca = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        labelVidaMax = new javax.swing.JLabel();
        labelNomePocao = new javax.swing.JLabel();
        btnGeraPocao = new javax.swing.JButton();
        btnAddA = new javax.swing.JButton();
        btnDevolverItens = new javax.swing.JButton();
        btnAddB = new javax.swing.JButton();
        btnAddC = new javax.swing.JButton();
        btnDevolverItem = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Itens Classe A");

        jLabel2.setText("Itens Classe B");

        jLabel3.setText("Itens Classe C");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("POÇÃO GERADA:");

        jLabel5.setText("VIDA ADQUIRIDA:");

        tabelaItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Itens Classe A", "Itens Classe B", "Itens Classe C"
            }
        ));
        jScrollPane2.setViewportView(tabelaItens);

        labelVida.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        labelVida.setText("[VIDA]");

        jLabel7.setText("INTELIGÊNCIA ADQUIRIDA:");

        labelInteligencia.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        labelInteligencia.setText("[INTELIGENCIA]");

        jLabel9.setText("FORÇA ADQUIRIDA:");

        labelForca.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        labelForca.setText("[FORCA]");

        jLabel11.setText("VIDA MÁXIMA ADQUIRIDA:");

        labelVidaMax.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        labelVidaMax.setText("[VIDA MAX]");

        labelNomePocao.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        labelNomePocao.setText("[NOME POCAO]");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(barraItens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(labelVida)
                                    .addComponent(jLabel7)
                                    .addComponent(labelInteligencia))
                                .addGap(74, 74, 74)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelVidaMax)
                                    .addComponent(jLabel11)
                                    .addComponent(labelForca)
                                    .addComponent(jLabel9)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelNomePocao)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(barraItens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(labelNomePocao))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelVida)
                    .addComponent(labelForca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelInteligencia)
                    .addComponent(labelVidaMax))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGeraPocao.setText("Gerar poção");

        btnAddA.setText("+");

        btnDevolverItens.setText("Devolver todos os itens");
        btnDevolverItens.setToolTipText("Devolve aos itens adicionados para o inventário.");

        btnAddB.setText("+");

        btnAddC.setText("+");

        btnDevolverItem.setText("Devolver item selecionado");

        btnVoltar.setText("Voltar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVoltar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGeraPocao))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDevolverItens, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addGap(75, 75, 75)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnAddB, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnAddA)
                                        .addComponent(btnAddC, javax.swing.GroupLayout.Alignment.TRAILING))))
                            .addComponent(comboA, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDevolverItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(btnAddA))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(btnAddB))
                        .addGap(7, 7, 7)
                        .addComponent(comboB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(btnAddC))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDevolverItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDevolverItens))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGeraPocao)
                    .addComponent(btnVoltar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraItens;
    private javax.swing.JButton btnAddA;
    private javax.swing.JButton btnAddB;
    private javax.swing.JButton btnAddC;
    private javax.swing.JButton btnDevolverItem;
    private javax.swing.JButton btnDevolverItens;
    private javax.swing.JButton btnGeraPocao;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox comboA;
    private javax.swing.JComboBox comboB;
    private javax.swing.JComboBox comboC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel labelForca;
    private javax.swing.JLabel labelInteligencia;
    private javax.swing.JLabel labelNomePocao;
    private javax.swing.JLabel labelVida;
    private javax.swing.JLabel labelVidaMax;
    private javax.swing.JTable tabelaItens;
    // End of variables declaration//GEN-END:variables
}