package jogoInterface;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
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
    
    private void setHP(int valor, int valorMax){
        labelHP.setText(String.valueOf(valor));
        
        int novoValor = (int) (((float) valor/valorMax)*100);
        barraHP.setValue(novoValor);
    }
    
    private void setXP(int valor){
        labelXP.setText(String.valueOf(valor));
        barraXP.setValue(valor);
    }
    
    private void setFome(int valor){
        labelFome.setText(String.valueOf(valor));
        barraFome.setValue(valor);
    }
    
    private void setComidas(List<Comida> comidas){
        DefaultTableModel model = (DefaultTableModel)tabelaMochila.getModel();
                
        for (int i = 0; i <= comidas.size(); i++)
            model.setValueAt("", i, 1);
                
        for (int i = 0; i < comidas.size(); i++)
            model.setValueAt(comidas.get(i).getNome(), i, 1);
    }
    
    private void setPocoes(List<Pocao> pocoes){
        DefaultTableModel model = (DefaultTableModel)tabelaMochila.getModel();
                
        for (int i = 0; i <= pocoes.size(); i++)
            model.setValueAt("", i, 2);
        
        for (int i = 0; i < pocoes.size(); i++)
            model.setValueAt(pocoes.get(i).getNome(), i, 2);
    }
    
    private void setAtaques(List<Ataque> ataques){
        int ultimoSel = cbAtaques.getSelectedIndex();
        cbAtaques.removeAllItems();
        
        for (Ataque a : ataques)
            cbAtaques.addItem(a.getNome());
        
        cbAtaques.setSelectedIndex(ultimoSel == -1 ? 0 : ultimoSel);
    }
    
    private void setAtributos(Personagem p){
        labelForca.setText(String.valueOf(p.getForca()));
        labelInteligencia.setText(String.valueOf(p.getInteligencia()));
        labelCarteira.setText(String.valueOf(p.mochila.getCarteira()));
    }
        
    public JanelaPrincipal(Personagem p) {   
        initComponents();
        this.personagem = p;
        cbAtaques.addItem("Sem ataque");
        cbAtaques.setSelectedIndex(0);
        
        this.personagem.setListener(new Personagem.AtributosListener(){
            @Override
            public void alteraHP(){
                setHP(personagem.getHP(), personagem.getMaxHP());
            }

            @Override
            public void alteraXP(){
                setXP(personagem.getXP());
            }

            @Override
            public void alteraNivel(){
                labelNivel.setText(String.valueOf(personagem.getNivel()));
                atualizaLog(personagem.getApelido() + " subiu de nível!");
            }

            @Override
            public void alteraMaxHP(){
                barraXP.setMaximum(personagem.getMaxHP());
            }
            
            @Override
            public void alteraFome(){
                setFome(personagem.getFome());
            }
            
            @Override
            public void ataca(Ataque a, Personagem in){
                atualizaLog(personagem.getApelido() + " utilizou "
                        + a.getNome() + " e causou um dano de " +
                        (personagem.getForca() + a.getDano()) +
                        " HP em " + in.getApelido() + "!");
            }
            
            @Override
            public void atualizaComidas(){
                setComidas(personagem.mochila.retornaComidas());
            }
            
            @Override
            public void atualizaPocoes(){
                setPocoes(personagem.mochila.retornaPocoes());
            }
            
            @Override
            public void atualizaAtaques(){
                setAtaques(personagem.mochila.retornaAtaques());
            }
            
            @Override
            public void atualizaAtributos(){
                setAtributos(personagem);
            }
        });
        
        this.labelNome.setText(this.personagem.getApelido());
        this.labelClasse.setText(this.personagem.getClass().getSimpleName());
        this.labelNivel.setText(String.valueOf(this.personagem.getNivel()));
        
        this.setAtaques(personagem.mochila.retornaAtaques());
        this.setComidas(personagem.mochila.retornaComidas());
        this.setAtributos(personagem);
        this.setHP(personagem.getHP(), personagem.getMaxHP());
        this.setXP(personagem.getXP());
        this.setFome(personagem.getFome());        
        
        this.personagem.defineAtaque(new Ataque("Sem ataque", 0));
        
        this.tp = new ThreadPasseio(p);
        this.tp.start();
        this.tp.setListenerBatalha(new ThreadPasseio.BattleActionListener(){
            @Override
            public void terminaBatalha() {
                atualizaLog("A batalha terminou!");
                atualizaLog("Você adquiriu " + personagem.getNivel()*10 + " pontos de XP.");
                personagem.melhora();
            }

            @Override
            public void terminaRodada(Personagem in) {
                atualizaLog("Você tem " + personagem.getHP() +
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
                atualizaLog("Fugir no meio de uma batalha não é legal!");
                atualizaLog("Você perdeu 10 pontos de XP.");
                personagem.diminuiXP(10);
            }
            
            @Override
            public void encontraBau(){
                System.out.println("adicionado!");
            }
            
            @Override
            public void encontraBau(Comida c){
                atualizaLog("Você encontrou " + c.getNome() + "!");
                personagem.mochila.adicionaComida(c);
            }
            
            @Override
            public void encontraBau(Pocao p){
                atualizaLog("Você encontrou " + p.getNome() + "!");
                personagem.mochila.adicionaPocao(p);
            }
            
            @Override
            public void encontraBau(int moedas){
                atualizaLog("Você encontrou " + moedas + " moedas em um baú!");
                personagem.mochila.adicionaCarteira(moedas);
                labelCarteira.setText(String.valueOf(personagem.mochila.getCarteira()));
            }
        });
      
        this.btnPassear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (btnPassear.getText().equals("Dar um passeio")){
                    btnLoja.setEnabled(false);
                    tp.ativa();
                    atualizaLog("Você está dando uma volta...");
                    btnPassear.setText("Voltar para casa");

                } else {
                    tp.desativa();
                    atualizaLog("Você voltou para casa.");
                    btnPassear.setText("Dar um passeio");
                    btnLoja.setEnabled(true);
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
                            atualizaLog("Você consumiu a poção " + p.getNome() + "!");                            
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
        
        this.btnLimpaLog.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                tAreaLog.setText("");
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
                            case 2: {
                                Pocao p = personagem.mochila.retornaPocao(lin);
                                txtDescricaoItem.setText(p.toString());
                                break;
                            }
                        }
                    }
                
                } catch (NullPointerException n){
                    txtDescricaoItem.setText("");
                }
            }
        });
        
        cbAtaques.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int indAtaque = cbAtaques.getSelectedIndex();
                if (indAtaque != -1){
                    Ataque a = personagem.mochila.retornaAtaque(indAtaque);
                    personagem.defineAtaque(a);
                }
            }
        });
        
        btnXP.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                personagem.aumentaXP(30);
            }
        });
        
        btnLoja.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                new JanelaLoja(personagem).setVisible(true);
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
        btnLoja = new javax.swing.JButton();
        btnPassear = new javax.swing.JButton();
        btnLimpaLog = new javax.swing.JButton();
        btnXP = new javax.swing.JButton();
        btnUsarItem = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tAreaLog = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDescricaoItem = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbAtaques = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        labelInteligencia = new javax.swing.JLabel();
        labelForca = new javax.swing.JLabel();
        labelCarteira = new javax.swing.JLabel();

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

        tabelaMochila.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Item", "Comida", "Poção"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
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

        btnLoja.setText("Ir para a loja");

        btnPassear.setText("Dar um passeio");

        btnLimpaLog.setText("Limpar registro");

        btnXP.setText("Aumenta XP em 30");

        btnUsarItem.setText("Utilizar item");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLoja, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
            .addComponent(btnPassear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLimpaLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnXP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUsarItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLoja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPassear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpaLog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUsarItem)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tAreaLog.setColumns(20);
        tAreaLog.setRows(5);
        tAreaLog.setDisabledTextColor(new java.awt.Color(0, 0, 0));
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
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        txtDescricaoItem.setColumns(20);
        txtDescricaoItem.setRows(5);
        txtDescricaoItem.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescricaoItem.setEnabled(false);
        jScrollPane5.setViewportView(txtDescricaoItem);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ESTATÍSTICAS");

        jLabel2.setText("ATAQUE SELECIONADO:");

        jLabel3.setText("FORÇA:");

        jLabel4.setText("INTELIGÊNCIA:");

        jLabel5.setText("CARTEIRA:");

        labelInteligencia.setText("[INTELIGENCIA]");

        labelForca.setText("[FORCA]");

        labelCarteira.setText("[MOEDAS]");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(cbAtaques, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelCarteira)
                                    .addComponent(labelForca)
                                    .addComponent(labelInteligencia))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbAtaques, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelForca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(labelInteligencia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelCarteira))
                .addContainerGap(29, Short.MAX_VALUE))
        );

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
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraFome;
    private javax.swing.JProgressBar barraHP;
    private javax.swing.JProgressBar barraXP;
    private javax.swing.JButton btnLimpaLog;
    private javax.swing.JButton btnLoja;
    private javax.swing.JButton btnPassear;
    private javax.swing.JButton btnUsarItem;
    private javax.swing.JButton btnXP;
    private javax.swing.JComboBox cbAtaques;
    private javax.swing.JLabel fxLabel1;
    private javax.swing.JLabel fxLabel2;
    private javax.swing.JLabel fxLabel3;
    private javax.swing.JLabel fxLabel4;
    private javax.swing.JLabel fxLabel5;
    private javax.swing.JLabel fxLabel6;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel labelCarteira;
    private javax.swing.JLabel labelClasse;
    private javax.swing.JLabel labelFome;
    private javax.swing.JLabel labelForca;
    private javax.swing.JLabel labelHP;
    private javax.swing.JLabel labelInteligencia;
    private javax.swing.JLabel labelNivel;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelXP;
    private javax.swing.JTextArea tAreaLog;
    private javax.swing.JTable tabelaMochila;
    private javax.swing.JTextArea txtDescricaoItem;
    // End of variables declaration//GEN-END:variables
}
