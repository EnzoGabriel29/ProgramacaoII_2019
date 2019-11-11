package jogoInterface;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import jogoCodigo.Comida;
import jogoCodigo.Personagem;
import jogoCodigo.Pocao;
import jogoCodigo.TipoComida;
import jogoCodigo.TipoPocao;

public class JanelaLoja extends javax.swing.JFrame {
    private final Personagem personagem;
    
    private void configuraAbaComidas(){
        Comida[] comidas = {
            Comida.retornaComida(TipoComida.UVA),
            Comida.retornaComida(TipoComida.MACA),
            Comida.retornaComida(TipoComida.BANANA),
            Comida.retornaComida(TipoComida.CENOURA),
            Comida.retornaComida(TipoComida.ENSOPADO),
            Comida.retornaComida(TipoComida.FRANGO)
        };
        
        GuiaComida gc = new GuiaComida(personagem, comidas, "Comidas",
                labelPrecoComida, labelDescricaoComida, botaoComprarComida,
                listaComidas, spinnerQtdComida);
        
        gc.configuraElementos();
    }
    
    private void configuraAbaPocoes(){
        Pocao[] pocoes = {
            Pocao.retornaPocao(TipoPocao.VIDA),
            Pocao.retornaPocao(TipoPocao.SAGACIDADE),
            Pocao.retornaPocao(TipoPocao.FORCA)
        };
        
        GuiaPocao gp = new GuiaPocao(personagem, pocoes, "Poções",
                labelPrecoPocao, labelDescricaoPocao, botaoComprarPocao,
                listaPocoes, spinnerQtdPocao);
        
        gp.configuraElementos();
    }
    
    private void atualizaGuias(){
        switch (menuGuias.getSelectedIndex()){
            case 0: configuraAbaComidas(); break;
            case 1: configuraAbaPocoes(); break;
        }
    }
    
    public JanelaLoja(Personagem p){
        this.personagem = p;
        this.initComponents();
        this.atualizaGuias();
        
        menuGuias.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e){
                atualizaGuias();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuGuias = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaComidas = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        spinnerQtdComida = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        labelPrecoComida = new javax.swing.JLabel();
        botaoComprarComida = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        labelDescricaoComida = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaPocoes = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        spinnerQtdPocao = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        labelDescricaoPocao = new javax.swing.JLabel();
        botaoComprarPocao = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        labelPrecoPocao = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Loja");

        listaComidas.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaComidas);

        jLabel1.setText("Quantidade");

        spinnerQtdComida.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        jLabel2.setText("Preço");

        labelPrecoComida.setText("[MOEDAS]");

        botaoComprarComida.setText("COMPRAR");

        jLabel3.setText("Descrição");

        labelDescricaoComida.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(labelDescricaoComida)
                            .addComponent(spinnerQtdComida, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 137, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoComprarComida, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelPrecoComida, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerQtdComida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(labelDescricaoComida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPrecoComida)
                        .addGap(12, 12, 12)
                        .addComponent(botaoComprarComida))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        menuGuias.addTab("Comidas", jPanel1);

        listaPocoes.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listaPocoes);

        jLabel4.setText("Quantidade");

        spinnerQtdPocao.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        jLabel5.setText("Descrição");

        labelDescricaoPocao.setText("jLabel6");

        botaoComprarPocao.setText("COMPRAR");

        jLabel7.setText("Preço");

        labelPrecoPocao.setText("[MOEDAS]");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(spinnerQtdPocao, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(labelDescricaoPocao))
                        .addGap(0, 137, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoComprarPocao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelPrecoPocao, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerQtdPocao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(labelDescricaoPocao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPrecoPocao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botaoComprarPocao))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        menuGuias.addTab("Poções", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuGuias)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuGuias)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoComprarComida;
    private javax.swing.JButton botaoComprarPocao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelDescricaoComida;
    private javax.swing.JLabel labelDescricaoPocao;
    private javax.swing.JLabel labelPrecoComida;
    private javax.swing.JLabel labelPrecoPocao;
    private javax.swing.JList listaComidas;
    private javax.swing.JList listaPocoes;
    private javax.swing.JTabbedPane menuGuias;
    private javax.swing.JSpinner spinnerQtdComida;
    private javax.swing.JSpinner spinnerQtdPocao;
    // End of variables declaration//GEN-END:variables
}
