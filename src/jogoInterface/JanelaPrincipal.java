package jogoInterface;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jogoCodigo.*;

public class JanelaPrincipal extends JFrame {
    
    public static String getStringDupla(String p1, String p2){
        return "<html><center>" + p1 + "<br>" + p2 + "</center></html>";
    }
    
    public ThreadPasseio tp;
    private Personagem personagem;
    
    public void atualizaLog(String texto){
        tAreaLog.setText(texto + "\n" + tAreaLog.getText());
    }
    
    public JanelaPrincipal(Personagem p) {   
        initComponents();
        this.personagem = p;
        
        this.personagem.setListener(new Personagem.AtributosListener(){
            @Override
            public void alteraHP(){
                labelHP.setText(String.format("%d", personagem.getHP()));
                int novoHP = (int) (((float) personagem.getHP()/
                        personagem.getMaxHP())*100);
                barraHP.setValue(novoHP);
            }

            @Override
            public void alteraXP(){
                labelXP.setText(String.format("%d", personagem.getXp()));
                barraXP.setValue(personagem.getXp());
            }

            @Override
            public void alteraNivel(){
                labelNivel.setText(String.format("%d", personagem.getNivel()));
                atualizaLog(personagem.getApelido() + " subiu de nível!");
            }

            @Override
            public void alteraMaxHP(){
                barraXP.setMaximum(personagem.getMaxHP());
            }
            
            @Override
            public void alteraFome(){
                barraFome.setValue(personagem.getFome());
                labelFome.setText(String.format("%d", personagem.getFome()));
            }
            
            @Override
            public void ataca(Ataque a, Personagem in){
                atualizaLog(personagem.getApelido() + " utilizou "
                        + a.getNome() + " e causou um dano de " + a.getDano() +
                        " HP em " + in.getApelido() + "!");
            }
            
            @Override
            public void atualizaComidas(){
                ArrayList<Comida> comidas = personagem.mochila.retornaComidas();
                DefaultTableModel model = (DefaultTableModel)tabelaMochila.getModel();
                
                for (int i = 0; i < comidas.size(); i++)
                    model.setValueAt("", i, 1);
                
                for (int i = 0; i < comidas.size(); i++)
                    model.setValueAt(comidas.get(i).getNome(), i, 1);
                
            }
            
            @Override
            public void atualizaAtaques(){
                ArrayList<Ataque> ataques = personagem.mochila.retornaAtaques();
                DefaultTableModel model = (DefaultTableModel)tabelaMochila.getModel();
                
                for (int i = 0; i < ataques.size(); i++)
                    model.setValueAt("", i, 3);
                
                for (int i = 0; i < ataques.size(); i++)
                    model.setValueAt(ataques.get(i).getNome(), i, 3);
                
            }
        });
        
        this.personagem.listener.atualizaAtaques();
        this.personagem.listener.atualizaComidas();
        
        this.labelNome.setText(this.personagem.getApelido());
        this.labelClasse.setText(this.personagem.getClass().getSimpleName());
        this.labelNivel.setText(String.format("%d", this.personagem.getNivel()));
        this.labelFome.setText(String.format("%d", this.personagem.getFome()));
        this.personagem.aumentaHP(0);
        this.personagem.aumentaXP(0);
        this.personagem.aumentaFome(0);
        this.btnAtaque1.setEnabled(false);
        this.btnAtaque2.setEnabled(false);
        this.btnAtaque3.setEnabled(false);
        
        Ataque a1 = this.personagem.getAtaque(0);
        if (a1 != null) this.btnAtaque1.setText(getStringDupla(
                a1.getNome(), String.format("%d HP", a1.getDano())));
        else this.btnAtaque1.setText("VAZIO");
        
        Ataque a2 = this.personagem.getAtaque(1);
        if (a2 != null) this.btnAtaque2.setText(getStringDupla(
                a2.getNome(), String.format("%d HP", a2.getDano())));
        else this.btnAtaque2.setText("VAZIO");
        
        Ataque a3 = this.personagem.getAtaque(2);
        if (a3 != null) this.btnAtaque3.setText(getStringDupla(
                a3.getNome(), String.format("%d HP", a3.getDano())));
        else this.btnAtaque3.setText("VAZIO");
        
        this.tp = new ThreadPasseio(p);
        this.tp.start();
        this.tp.setListenerBatalha(new ThreadPasseio.BattleActionListener(){
            @Override
            public void terminaBatalha() {
                atualizaLog("A batalha terminou!");
                atualizaLog("A vida de " + personagem.getApelido() +
                        " foi " + "restaurada e foram adquiridos " + 
                        personagem.getNivel()*10 + " pontos de XP.");
            }

            @Override
            public void terminaRodada(Personagem pe, Personagem in) {
                atualizaLog(pe.getApelido() + " tem " + pe.getHP() +
                        " de HP e o inimigo " + in.getApelido() +
                        " tem " + in.getHP() + " de HP.");
            }

            @Override
            public void inimigoEncontrado(Personagem in){
                atualizaLog("HP: " + in.getHP());
                atualizaLog("NOME: " + in.getApelido());
                atualizaLog("Um inimigo foi localizado! Prepare-se!");
            }

            @Override
            public void jogadorFugiu(){
                btnAtaque1.setEnabled(false);
                btnAtaque2.setEnabled(false);
                btnAtaque3.setEnabled(false);
                atualizaLog("Fugir no meio de uma batalha não é legal!");
                atualizaLog("Você perdeu 10 pontos de XP.");
                personagem.diminuiXP(10);
            }
            
            @Override
            public void aguardaAtaque(){
                atualizaLog("Escolha um ataque para seu inimigo.");
                if (personagem.getAtaque(0) != null) btnAtaque1.setEnabled(true);
                if (personagem.getAtaque(1) != null) btnAtaque1.setEnabled(true);
                if (personagem.getAtaque(2) != null) btnAtaque1.setEnabled(true);
            }
            
            @Override
            public void encontraBau(){
                int n = JOptionPane.showConfirmDialog(null,
                    "Você gostaria de adicionar esse item à mochila?",
                    "Você encontrou um baú!",
                    JOptionPane.YES_NO_OPTION);
                
                if (n == JOptionPane.YES_OPTION) System.out.println("adicionado!");
                else System.out.println("nao adicionado :(");
                
            }
            
            @Override
            public void encontraBau(Comida c){
                int n = JOptionPane.showConfirmDialog(null, "Você gostaria " +
                        "de adicionar " + c.getNome() + ", que restaura " +
                        c.getFomeRest() + " pontos de fome, ao seu inventário?",
                        "Você encontrou um baú!", JOptionPane.YES_NO_OPTION);
                
                if (n == JOptionPane.YES_OPTION)
                    personagem.mochila.adicionaComida(c);
            }
        });
      
        this.btnPassear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (btnPassear.getText().equals("Dar um passeio")){
                    tp.ativa();
                    atualizaLog("Você está dando uma volta...");
                    btnPassear.setText("Voltar para casa");

                } else {
                    tp.desativa();
                    atualizaLog("Você voltou para casa.");
                    btnPassear.setText("Dar um passeio");
                }
            }
        });
        
        this.btnUsarItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int lin = tabelaMochila.getSelectedRow();
                int col = tabelaMochila.getSelectedColumn();
                
                switch (col){
                    case 0: {
                        break;
                    }
                    
                    case 1: {
                        atualizaLog("");
                        Comida c = personagem.mochila.retornaComida(lin);
                        personagem.mochila.removeComida(lin);
                        personagem.diminuiFome(c.getFomeRest());
                        break;
                    }
                    
                    case 2: {
                        break;
                    }
                    
                    case 3: {
                        atualizaLog("Ataques não podem ser consumidos!");
                        break;
                    }
                }
            }
        });
        
        this.btnAtributos.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                atualizaLog(String.format("FORÇA: %d\nINTELIGÊNCIA: %d",
                        personagem.getForca(), personagem.getInteligencia()));
            }
        });
        
        this.btnLimpaLog.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                tAreaLog.setText("");
            }
        });
        
        this.btnAtaque1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                tp.defineAtaque(personagem.getAtaque(0));
                btnAtaque1.setEnabled(false);
                btnAtaque2.setEnabled(false);
                btnAtaque3.setEnabled(false);
            }
        });
        
        this.btnAtaque2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                tp.defineAtaque(personagem.getAtaque(1));
                btnAtaque1.setEnabled(false);
                btnAtaque2.setEnabled(false);
                btnAtaque3.setEnabled(false);
            }
        });
        
        this.btnAtaque3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                tp.defineAtaque(personagem.getAtaque(2));
                btnAtaque1.setEnabled(false);
                btnAtaque2.setEnabled(false);
                btnAtaque3.setEnabled(false);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tAreaLog = new javax.swing.JTextArea();
        fxLabel1 = new javax.swing.JLabel();
        labelNome = new javax.swing.JLabel();
        fxLabel2 = new javax.swing.JLabel();
        labelClasse = new javax.swing.JLabel();
        fxLabel3 = new javax.swing.JLabel();
        labelNivel = new javax.swing.JLabel();
        fxLabel4 = new javax.swing.JLabel();
        barraHP = new javax.swing.JProgressBar();
        fxLabel5 = new javax.swing.JLabel();
        barraXP = new javax.swing.JProgressBar();
        labelHP = new javax.swing.JLabel();
        labelXP = new javax.swing.JLabel();
        btnLimpaLog = new javax.swing.JButton();
        btnAtributos = new javax.swing.JButton();
        btnTreinar = new javax.swing.JButton();
        btnPassear = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnAtaque1 = new javax.swing.JButton();
        btnAtaque3 = new javax.swing.JButton();
        btnAtaque2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaMochila = new javax.swing.JTable();
        fxLabel6 = new javax.swing.JLabel();
        barraFome = new javax.swing.JProgressBar();
        labelFome = new javax.swing.JLabel();
        btnUsarItem = new javax.swing.JButton();

        jButton2.setText("jButton2");

        jScrollPane2.setViewportView(jTextPane1);

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
        jScrollPane3.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tAreaLog.setColumns(20);
        tAreaLog.setRows(5);
        tAreaLog.setEnabled(false);
        jScrollPane1.setViewportView(tAreaLog);

        fxLabel1.setText("Nome do personagem:");

        labelNome.setText("[NOME]");

        fxLabel2.setText("Classe do personagem:");

        labelClasse.setText("[CLASSE]");

        fxLabel3.setText("Nível do personagem:");

        labelNivel.setText("[NÍVEL]");

        fxLabel4.setText("HP:");

        fxLabel5.setText("XP:");

        labelHP.setText("[HP]");

        labelXP.setText("[XP]");

        btnLimpaLog.setText("Limpar registro");

        btnAtributos.setText("Ver atributos");

        btnTreinar.setText("Iniciar treinamento");

        btnPassear.setText("Dar um passeio");

        btnAtaque1.setText("[ATAQUE 1]");

        btnAtaque3.setText("[ATAQUE 3]");

        btnAtaque2.setText("[ATAQUE 2]");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnAtaque1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtaque2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtaque3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAtaque1, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
            .addComponent(btnAtaque2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAtaque3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabelaMochila.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Item", "Comida", "Poção", "Ataque"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tabelaMochila);
        if (tabelaMochila.getColumnModel().getColumnCount() > 0) {
            tabelaMochila.getColumnModel().getColumn(0).setResizable(false);
            tabelaMochila.getColumnModel().getColumn(1).setResizable(false);
            tabelaMochila.getColumnModel().getColumn(2).setResizable(false);
            tabelaMochila.getColumnModel().getColumn(3).setResizable(false);
        }

        fxLabel6.setText("FOME:");

        labelFome.setText("[FOME]");

        btnUsarItem.setText("Utilizar item");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fxLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelNome))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fxLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelClasse))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fxLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelNivel))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fxLabel6)
                                    .addComponent(fxLabel5)
                                    .addComponent(fxLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(barraHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(barraXP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(barraFome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelHP)
                            .addComponent(labelXP)
                            .addComponent(labelFome))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLimpaLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPassear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTreinar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAtributos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUsarItem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(fxLabel1)
                                    .addComponent(labelNome))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(fxLabel2)
                                    .addComponent(labelClasse))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(fxLabel3)
                                    .addComponent(labelNivel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fxLabel4)
                                    .addComponent(barraHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelHP))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fxLabel5)
                                    .addComponent(barraXP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelXP))
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fxLabel6)
                                    .addComponent(barraFome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelFome)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTreinar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPassear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAtributos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLimpaLog)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUsarItem)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraFome;
    private javax.swing.JProgressBar barraHP;
    private javax.swing.JProgressBar barraXP;
    private javax.swing.JButton btnAtaque1;
    private javax.swing.JButton btnAtaque2;
    private javax.swing.JButton btnAtaque3;
    private javax.swing.JButton btnAtributos;
    private javax.swing.JButton btnLimpaLog;
    private javax.swing.JButton btnPassear;
    private javax.swing.JButton btnTreinar;
    private javax.swing.JButton btnUsarItem;
    private javax.swing.JLabel fxLabel1;
    private javax.swing.JLabel fxLabel2;
    private javax.swing.JLabel fxLabel3;
    private javax.swing.JLabel fxLabel4;
    private javax.swing.JLabel fxLabel5;
    private javax.swing.JLabel fxLabel6;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel labelClasse;
    private javax.swing.JLabel labelFome;
    private javax.swing.JLabel labelHP;
    private javax.swing.JLabel labelNivel;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelXP;
    private javax.swing.JTextArea tAreaLog;
    private javax.swing.JTable tabelaMochila;
    // End of variables declaration//GEN-END:variables
}
