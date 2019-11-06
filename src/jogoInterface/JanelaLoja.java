package jogoInterface;

// TODO -> atualizar atributo Carteira
// em JanelaPrincipal ao comprar item
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import jogoCodigo.*;

public class JanelaLoja extends javax.swing.JFrame {
    private Personagem personagem;
    
    private void setComida(String comida){
        switch (comida){
            case "Uva": setPreco(3); setDescricao(1); break;
            case "Maçã": setPreco(5); setDescricao(2); break;
            case "Banana": setPreco(15); setDescricao(5); break;
            case "Cenoura": setPreco(25); setDescricao(10); break;
            case "Ensopado": setPreco(45); setDescricao(20); break;
            case "Frango": setPreco(95); setDescricao(50); break;
        }
    }
    
    private void setPreco(int moedas){
        String msg = String.format("%d moedas", moedas);
        labelPreco.setText(msg);
    }
    
    private void setDescricao(String msg){
        labelDescricao.setText(msg);
    }
    
    private void setDescricao(int fomeRest){
        String msg = String.format("Restaura %d pontos de fome.", fomeRest);
        labelDescricao.setText(msg);
    }
    
    private int getPrecoAtual(){
        if (listaComidas.getSelectedValue() == null) return 0;
        
        switch ((String) listaComidas.getSelectedValue()){
            case "Uva": return 3;
            case "Maçã": return 5;
            case "Banana": return 15;
            case "Cenoura": return 25;
            case "Ensopado": return 45;
            case "Frango": return 95;
            default: return 0;
        }
    }
    
    private void configuraAbaComidas(){
        String[] comidas = {"Uva", "Maçã",
            "Banana", "Cenoura", "Ensopado", "Frango"};
        
        this.setPreco(0);
        this.setDescricao("Selecione um item.");
        
        botaoComprar.setEnabled(false);
        
        listaComidas.setListData(comidas);
        listaComidas.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                String comida = (String) listaComidas.getSelectedValue();
                setComida(comida);
                
                int qtd = (int) spinnerQtd.getValue();
                int preco = getPrecoAtual();
                int valorTotal = qtd * preco;
                
                setPreco(valorTotal);
                
                if (valorTotal > personagem.mochila.getCarteira())
                    botaoComprar.setEnabled(false);
                
                else botaoComprar.setEnabled(true);
            }
        });
        
        spinnerQtd.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e){
                int qtd = (int) spinnerQtd.getValue();
                int preco = getPrecoAtual();
                int valorTotal = qtd * preco;
                
                setPreco(valorTotal);
                
                if (valorTotal > personagem.mochila.getCarteira())
                    botaoComprar.setEnabled(false);
                
                else botaoComprar.setEnabled(true);
            }
        });
        
        botaoComprar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String nome = (String) listaComidas.getSelectedValue();
                Comida c;
                switch (nome){
                    case "Uva": c = new Comida(nome, 1); break;
                    case "Maçã": c = new Comida(nome, 2); break;
                    case "Banana": c = new Comida(nome, 5); break;
                    case "Cenoura": c = new Comida(nome, 10); break;
                    case "Ensopado": c = new Comida(nome, 20); break;
                    case "Frango": c = new Comida(nome, 50); break;
                    default: c = null;
                }
                        
                int qtd = (int) spinnerQtd.getValue();
                personagem.mochila.removeCarteira(qtd * getPrecoAtual());
                
                for (int i = 0; i < qtd; i++)
                    personagem.mochila.adicionaComida(c);
                
            }
        });
    }
    
    public JanelaLoja(Personagem p){
        this.personagem = p;
        initComponents();

        if (menuGuias.getSelectedIndex() == 0)
            configuraAbaComidas();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuGuias = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaComidas = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        spinnerQtd = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        labelPreco = new javax.swing.JLabel();
        botaoComprar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        labelDescricao = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        listaComidas.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaComidas);

        jLabel1.setText("Quantidade");

        spinnerQtd.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        jLabel2.setText("Preço");

        labelPreco.setText("[MOEDAS]");

        botaoComprar.setText("COMPRAR");

        jLabel3.setText("Descrição");

        labelDescricao.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(spinnerQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(labelDescricao))
                        .addContainerGap(156, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botaoComprar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelPreco)
                            .addComponent(jLabel2))
                        .addGap(14, 14, 14))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(labelDescricao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPreco)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botaoComprar))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        menuGuias.addTab("Comidas", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 567, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 367, Short.MAX_VALUE)
        );

        menuGuias.addTab("tab2", jPanel2);

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
    private javax.swing.JButton botaoComprar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelDescricao;
    private javax.swing.JLabel labelPreco;
    private javax.swing.JList listaComidas;
    private javax.swing.JTabbedPane menuGuias;
    private javax.swing.JSpinner spinnerQtd;
    // End of variables declaration//GEN-END:variables
}
