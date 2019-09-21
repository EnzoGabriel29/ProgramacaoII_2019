package jogoInterface;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jogoCodigo.*;

public class JanelaPrincipal extends JFrame {
    public static String getStringAtaque(Ataque a){
        return "<html><center><p style=\"font-size:12px\">"
                + a.getNome() + "</p><br><p>" +
                a.getDano() + " HP</p></center></html>";
    }
    
    public static String getStringDupla(String p1, String p2){
        return "<html><center>" + p1 + "<br>" + p2 + "</center></html>";
    }
    
    public ThreadDeBatalha tb;
    private int contador = 0;
    private Personagem p;
    
    public void atualizaLog(String texto){
        tAreaLog.setText(texto + "\n" + tAreaLog.getText());
    }
    
    public JanelaPrincipal(Personagem p) {   
        initComponents();
        this.p = p;
        
        this.p.setListener(new Personagem.AtributosListener(){
            @Override
            public void alteraHP(){
                labelHP.setText(String.format("%d", JanelaPrincipal.this.p.getHp()));
                int novoHP = (int) (((float) JanelaPrincipal.this.p.getHp()/
                        JanelaPrincipal.this.p.getMaxHP())*100);
                barraHP.setValue(novoHP);
            }

            @Override
            public void alteraXP(){
                labelXP.setText(String.format("%d", JanelaPrincipal.this.p.getXp()));
                barraXP.setValue(JanelaPrincipal.this.p.getXp());
            }

            @Override
            public void alteraNivel(){
                labelNivel.setText(String.format("%d", JanelaPrincipal.this.p.getNivel()));
                atualizaLog(JanelaPrincipal.this.p.getApelido() + " subiu de nível!");
            }

            @Override
            public void alteraMaxHP(){
                barraXP.setMaximum(JanelaPrincipal.this.p.getMaxHP());
            }
            
            @Override
            public void ataca(Ataque a, Personagem in){
                atualizaLog(JanelaPrincipal.this.p.getApelido() + " utilizou "
                        + a.getNome() + " e causou um dano de " + a.getDano() +
                        " HP em " + in.getApelido() + "!");
            }
        });
        
        this.labelNome.setText(this.p.getApelido());
        this.labelClasse.setText(this.p.getClass().getSimpleName());
        this.labelNivel.setText(String.format("%d", this.p.getNivel()));  
        this.p.aumentaHP(0);
        this.p.aumentaXP(0);
        this.btnAtaque1.setEnabled(false);
        this.btnAtaque2.setEnabled(false);
        this.btnAtaque3.setEnabled(false);
        
        Ataque a1 = this.p.getAtaque(0);
        if (a1 != null) this.btnAtaque1.setText(getStringDupla(a1.getNome(),
                String.format("%d HP", a1.getDano())));
        else this.btnAtaque1.setText("VAZIO");
        
        Ataque a2 = this.p.getAtaque(1);
        if (a2 != null) this.btnAtaque2.setText(getStringAtaque(a2));
        else this.btnAtaque2.setText("VAZIO");
        
        Ataque a3 = this.p.getAtaque(2);
        if (a3 != null) this.btnAtaque3.setText(getStringAtaque(a3));
        else this.btnAtaque3.setText("VAZIO");
        
        this.tb = new ThreadDeBatalha(p);
        this.tb.start();
        this.tb.setListener(new ThreadDeBatalha.BattleActionListener(){
            @Override
            public void terminaBatalha() {
                atualizaLog("A batalha terminou!");
                atualizaLog("A vida de " + JanelaPrincipal.this.p.getApelido() +
                        " foi " + "restaurada e foram adquiridos " + 
                        JanelaPrincipal.this.p.getNivel()*10 + " pontos de XP.");
            }

            @Override
            public void terminaRodada(Personagem pe, Personagem in) {
                atualizaLog(pe.getApelido() + " tem " + pe.getHp() +
                        " de HP e o inimigo " + in.getApelido() +
                        " tem " + in.getHp() + " de HP.");
            }

            @Override
            public void inimigoEncontrado(Personagem in){
                atualizaLog("HP: " + in.getHp());
                atualizaLog("NOME: " + in.getApelido());
                atualizaLog("Um inimigo foi localizado! Prepare-se!");
            }

            @Override
            public void jogadorFugiu(){
                btnAtaque1.setEnabled(false);
                btnAtaque2.setEnabled(false);
                btnAtaque3.setEnabled(false);
                atualizaLog("Fugir no meio de uma batalha não é legal!");
                atualizaLog("Você perdeu 10 pontos de XP pela sua covardia.");
                JanelaPrincipal.this.p.diminuiXP(10);
            }
            
            @Override
            public void aguardaAtaque(){
                atualizaLog("Escolha um ataque para seu inimigo.");
                if(JanelaPrincipal.this.p.getAtaque(0) != null) btnAtaque1.setEnabled(true);
                if(JanelaPrincipal.this.p.getAtaque(1) != null) btnAtaque1.setEnabled(true);
                if(JanelaPrincipal.this.p.getAtaque(2) != null) btnAtaque1.setEnabled(true);
            }
        });
        
      
        this.btnBatalhar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if ("Entrar em batalha".equals(btnBatalhar.getText())){
                    JanelaPrincipal.this.tb.ativar();
                    atualizaLog("Você está em campo de batalha!");
                    btnBatalhar.setText("Sair de batalha");

                } else {
                    JanelaPrincipal.this.tb.desativar();
                    atualizaLog("Você saiu do campo de batalha.");
                    btnBatalhar.setText("Entrar em batalha");
                }
            }
        });
        
        this.btnAtributos.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                atualizaLog(String.format("FORÇA: %d\nINTELIGÊNCIA: %d",
                        JanelaPrincipal.this.p.getForca(),
                        JanelaPrincipal.this.p.getInteligencia()));
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
                tb.defineAtaque(JanelaPrincipal.this.p.getAtaque(0));
                btnAtaque1.setEnabled(false);
                btnAtaque2.setEnabled(false);
                btnAtaque3.setEnabled(false);
            }
        });
        
        this.btnAtaque2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                tb.defineAtaque(JanelaPrincipal.this.p.getAtaque(1));
                btnAtaque1.setEnabled(false);
                btnAtaque2.setEnabled(false);
                btnAtaque3.setEnabled(false);
            }
        });
        
        this.btnAtaque3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                tb.defineAtaque(JanelaPrincipal.this.p.getAtaque(2));
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
        btnBatalhar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnAtaque1 = new javax.swing.JButton();
        btnAtaque3 = new javax.swing.JButton();
        btnAtaque2 = new javax.swing.JButton();

        jButton2.setText("jButton2");

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

        btnBatalhar.setText("Entrar em batalha");

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
                        .addGap(130, 130, 130)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTreinar, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(btnBatalhar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAtributos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpaLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
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
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(labelHP)
                                .addComponent(barraHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fxLabel5)
                            .addComponent(barraXP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelXP)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTreinar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatalhar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAtributos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpaLog)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraHP;
    private javax.swing.JProgressBar barraXP;
    private javax.swing.JButton btnAtaque1;
    private javax.swing.JButton btnAtaque2;
    private javax.swing.JButton btnAtaque3;
    private javax.swing.JButton btnAtributos;
    private javax.swing.JButton btnBatalhar;
    private javax.swing.JButton btnLimpaLog;
    private javax.swing.JButton btnTreinar;
    private javax.swing.JLabel fxLabel1;
    private javax.swing.JLabel fxLabel2;
    private javax.swing.JLabel fxLabel3;
    private javax.swing.JLabel fxLabel4;
    private javax.swing.JLabel fxLabel5;
    private javax.swing.JButton jButton2;
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
