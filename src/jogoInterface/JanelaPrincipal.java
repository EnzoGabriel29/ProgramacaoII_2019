package jogoInterface;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
                List<Comida> comidas = personagem.mochila.retornaComidas();
                DefaultTableModel model = (DefaultTableModel)tabelaMochila.getModel();
                
                for (int i = 0; i < comidas.size()+1; i++)
                    model.setValueAt("", i, 1);
                
                for (int i = 0; i < comidas.size(); i++)
                    model.setValueAt(comidas.get(i).getNome(), i, 1);
            }
            
            @Override
            public void atualizaPocoes(){
                List<Pocao> pocoes = personagem.mochila.retornaPocoes();
                DefaultTableModel model = (DefaultTableModel)tabelaMochila.getModel();
                
                for (int i = 0; i < pocoes.size()+1; i++)
                    model.setValueAt("", i, 2);
                
                for (int i = 0; i < pocoes.size(); i++)
                    model.setValueAt(pocoes.get(i).getNome(), i, 2);
            }
            
            @Override
            public void atualizaAtaques(){
                List<Ataque> ataques = personagem.mochila.retornaAtaques();
                DefaultTableModel model = (DefaultTableModel)tabelaMochila.getModel();
                
                for (int i = 0; i < ataques.size()+1; i++)
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
            
            @Override
            public void encontraBau(Pocao p){
                int n = JOptionPane.showConfirmDialog(null, "Você gostaria " +
                        "de adicionar " + p.getNome() + " ao seu inventário?",
                        "Você encontrou um baú!", JOptionPane.YES_NO_OPTION);
                
                if (n == JOptionPane.YES_OPTION)
                    personagem.mochila.adicionaPocao(p);
            }
        });
      
        this.btnPassear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (btnPassear.getText().equals("Dar um passeio")){
                    btnTreinar.setEnabled(false);
                    tp.ativa();
                    atualizaLog("Você está dando uma volta...");
                    btnPassear.setText("Voltar para casa");

                } else {
                    tp.desativa();
                    atualizaLog("Você voltou para casa.");
                    btnPassear.setText("Dar um passeio");
                    btnTreinar.setEnabled(true);
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
                        if (personagem.getFome() == 0){
                            atualizaLog("Você não está com fome!");
                            break;
                        }
                        
                        Comida c = personagem.mochila.retornaComida(lin);
                        
                        if (c != null){
                            atualizaLog("Você comeu " + c.getNome() + " e " +
                                    "diminuiu " + c.getFomeRest() + " pontos de fome.");
                            personagem.mochila.removeComida(lin);
                            personagem.come(c);
                        }
                            
                        break;
                    }
                    
                    case 2: {
                        Pocao p = personagem.mochila.retornaPocao(lin);
                        
                        if (p != null){
                            atualizaLog("Você consumiu a poção " +
                                    p.getNome() + "!");
                            
                            personagem.mochila.removePocao(lin);
                            personagem.bebe(p);
                        }
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
        
        tabelaMochila.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent event){
                try {
                    int lin = tabelaMochila.getSelectedRow();
                    int col = tabelaMochila.getSelectedColumn();
                    
                    if (!event.getValueIsAdjusting() && lin != -1){
                        switch (col){
                            case 0: break;
                            case 1: {
                                Comida c = personagem.mochila.retornaComida(lin);
                                txtDescricaoItem.setText(c.toString());
                                break;
                            }
                            case 2: break; 
                            case 3: break;
                        }
                    }
                
                } catch (NullPointerException n){
                    txtDescricaoItem.setText("");
                }
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
        jPanel1 = new javax.swing.JPanel();
        btnAtaque1 = new javax.swing.JButton();
        btnAtaque3 = new javax.swing.JButton();
        btnAtaque2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaMochila = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        fxLabel1 = new javax.swing.JLabel();
        fxLabel2 = new javax.swing.JLabel();
        fxLabel3 = new javax.swing.JLabel();
        labelNome = new javax.swing.JLabel();
        labelClasse = new javax.swing.JLabel();
        labelNivel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        fxLabel4 = new javax.swing.JLabel();
        barraHP = new javax.swing.JProgressBar();
        fxLabel5 = new javax.swing.JLabel();
        barraXP = new javax.swing.JProgressBar();
        fxLabel6 = new javax.swing.JLabel();
        barraFome = new javax.swing.JProgressBar();
        labelHP = new javax.swing.JLabel();
        labelXP = new javax.swing.JLabel();
        labelFome = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnTreinar = new javax.swing.JButton();
        btnPassear = new javax.swing.JButton();
        btnLimpaLog = new javax.swing.JButton();
        btnAtributos = new javax.swing.JButton();
        btnUsarItem = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tAreaLog = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDescricaoItem = new javax.swing.JTextArea();

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

        fxLabel1.setText("Nome do personagem:");

        fxLabel2.setText("Classe do personagem:");

        fxLabel3.setText("Nível do personagem:");

        labelNome.setText("[NOME]");

        labelClasse.setText("[CLASSE]");

        labelNivel.setText("[NÍVEL]");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(fxLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(labelNome))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(fxLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(labelClasse))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(fxLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(labelNivel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fxLabel1)
                    .addComponent(labelNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fxLabel2)
                    .addComponent(labelClasse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fxLabel3)
                    .addComponent(labelNivel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        fxLabel4.setText("HP:");

        fxLabel5.setText("XP:");

        fxLabel6.setText("FOME:");

        labelHP.setText("[HP]");

        labelXP.setText("[XP]");

        labelFome.setText("[FOME]");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(fxLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(barraHP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(fxLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(barraXP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(fxLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(barraFome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelHP)
                    .addComponent(labelXP)
                    .addComponent(labelFome))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(barraHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fxLabel4)
                            .addComponent(labelHP))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fxLabel5)
                            .addComponent(barraXP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(labelXP))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fxLabel6)
                    .addComponent(barraFome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelFome))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btnTreinar.setText("Iniciar treinamento");

        btnPassear.setText("Dar um passeio");

        btnLimpaLog.setText("Limpar registro");

        btnAtributos.setText("Ver atributos");

        btnUsarItem.setText("Utilizar item");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTreinar, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
            .addComponent(btnPassear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLimpaLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAtributos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUsarItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTreinar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPassear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpaLog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtributos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUsarItem)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tAreaLog.setColumns(20);
        tAreaLog.setRows(5);
        tAreaLog.setEnabled(false);
        jScrollPane1.setViewportView(tAreaLog);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Capacete", "Colete", "Calça", "Bota", "Equipamento"
            }
        ));
        jScrollPane6.setViewportView(jTable3);

        txtDescricaoItem.setColumns(20);
        txtDescricaoItem.setRows(5);
        txtDescricaoItem.setEnabled(false);
        jScrollPane5.setViewportView(txtDescricaoItem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                        .addComponent(jScrollPane6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel labelClasse;
    private javax.swing.JLabel labelFome;
    private javax.swing.JLabel labelHP;
    private javax.swing.JLabel labelNivel;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelXP;
    private javax.swing.JTextArea tAreaLog;
    private javax.swing.JTable tabelaMochila;
    private javax.swing.JTextArea txtDescricaoItem;
    // End of variables declaration//GEN-END:variables
}
