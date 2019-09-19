package JogoCodigo;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;

public class JanelaPrincipal extends JFrame {

    int contador = 0;
    private Personagem p;
    
    public void atualizaLog(String texto){
        tAreaLog.setText(texto + "\n" + tAreaLog.getText());
    }
    
    public void alteraHP(int valor){
        if (valor > 0) this.p.aumentaHP(valor);
        else this.p.diminuiHP(-valor);
        
        this.labelHP.setText(String.format("%d", this.p.getHp()));
        this.barraHP.setValue(p.getHp());
    }
    
    public void alteraXP(int valor){
        this.p.aumentaXP(valor);
        this.labelXP.setText(String.format("%d", this.p.getXp()));
        this.barraXP.setValue(p.getXp());
    }
    
    public JanelaPrincipal(Personagem p) {   
        initComponents();
        this.p = p;
        
        this.p.setListener(new Personagem.AtributosListener(){
            
        });
        
        this.labelNome.setText(this.p.getApelido());
        this.labelClasse.setText(this.p.getClass().getSimpleName());
        this.labelNivel.setText(String.format("%d", this.p.getLevel()));  
        this.alteraHP(0);
        this.alteraXP(0);
        
        ThreadDeBatalha tb = new ThreadDeBatalha(p);
        tb.start();
        tb.setListener(new ThreadDeBatalha.BattleActionListener(){
            @Override
            public void battleEnd() {
                atualizaLog("A batalha terminou!");
            }

            @Override
            public void roundEnd(Personagem pe, Personagem in) {
                atualizaLog(pe.getApelido() + " tem " + pe.getHp() +
                        " de HP e o inimigo " + in.getApelido() +
                        " tem " + in.getHp() + " de HP.");
            }
            
            @Override
            public void lostHP(int valor){
                alteraHP(-valor);
            }
        });
        
      
        this.btnBatalhar.addActionListener((ActionEvent e) -> {
            if (btnBatalhar.getText() == "Entrar em batalha"){
                tb.ativar();
                atualizaLog("Você está em campo de batalha!");
                btnBatalhar.setText("Sair de batalha");
                
            } else {
                tb.desativar();
                atualizaLog("Você saiu do campo de batalha.");
                btnBatalhar.setText("Entrar em batalha");
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jPanel1 = new javax.swing.JPanel();
        btnTreinar = new javax.swing.JButton();
        btnBatalhar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

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

        btnTreinar.setText("Iniciar treinamento");

        btnBatalhar.setText("Entrar em batalha");

        jButton1.setText("Ver atributos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTreinar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnBatalhar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnTreinar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBatalhar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(0, 28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fxLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(barraXP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelXP))
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
                                .addComponent(fxLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(barraHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelHP)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE))
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
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(labelHP)
                                .addComponent(barraHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fxLabel5)
                            .addComponent(barraXP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelXP))
                        .addGap(23, 23, 23)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraHP;
    private javax.swing.JProgressBar barraXP;
    private javax.swing.JButton btnBatalhar;
    private javax.swing.JButton btnTreinar;
    private javax.swing.JLabel fxLabel1;
    private javax.swing.JLabel fxLabel2;
    private javax.swing.JLabel fxLabel3;
    private javax.swing.JLabel fxLabel4;
    private javax.swing.JLabel fxLabel5;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelClasse;
    private javax.swing.JLabel labelHP;
    private javax.swing.JLabel labelNivel;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelXP;
    private javax.swing.JTextArea tAreaLog;
    // End of variables declaration//GEN-END:variables
}
