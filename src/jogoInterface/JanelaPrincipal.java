package jogoInterface;

import jogoInterface.Comerciante.JanelaComerciante;
import jogoInterface.Alquimista.JanelaAlquimista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;
import jogoCodigo.Ataque;
import jogoCodigo.Comida.Comida;
import jogoCodigo.BancoDados.EnumPocao;
import jogoCodigo.Personagem.Inimigo;
import jogoCodigo.Item;
import jogoCodigo.ItemConstruivel;
import jogoCodigo.Listener.ListenerAtributos;
import jogoCodigo.Listener.ListenerPasseio;
import jogoCodigo.Personagem.Personagem;
import jogoCodigo.Pocao.Pocao;
import jogoCodigo.ThreadPasseio;

public class JanelaPrincipal extends Janela {
    public ThreadPasseio tp;
    private int nivelDificuldade;
    private ArmaduraManager aManager;
    private Personagem personagem;
    private int[] barrasPocao = {0, 0};
    private ListenerPasseio listenerPasseio;
    private DefaultTableModel tableModel;
    
    /**
     * Atualiza a área de texto com o registro
     * das ações realizadas pelo personagem.
     * @param texto texto a ser adicionado.
     */
    public void atualizaLog(String texto){
        tAreaLog.setText(texto + "\n" + tAreaLog.getText());
    }
     
    /**
     * Define informações de HP na interface gráfica.
     * @param valor nível de HP.
     * @param valorMax nível de HP máximo.
     */
    private void setHP(int valor, int valorMax){
        labelHP.setText(String.valueOf(valor));
        
        int novoValor = (int) (((float) valor/valorMax)*100);
        barraHP.setValue(novoValor);
    }
    
    /**
     * Define informações de XP na interface gráfica.
     * @param valor nível de XP.
     */
    private void setXP(int valor){
        labelXP.setText(String.valueOf(valor));
        barraXP.setValue(valor);
    }
    
    /**
     * Define informações de fome na interface gráfica.
     * @param valor nível de fome.
     */
    private void setFome(int valor){
        labelFome.setText(String.valueOf(valor));
        barraFome.setValue(valor);
    }
    
    /**
     * Define informações de itens na interface gráfica.
     * @param itens lista de itens disponíveis.
     */
    private void setItens(List<ItemConstruivel> itens){
        ItemConstruivel i;
        
        for (int j = 0; j <= itens.size(); j++)
            this.tableModel.setValueAt("", j, 0);
        
        for (int j = 0; j < itens.size(); j++){
            i = itens.get(j);
            this.tableModel.setValueAt(i.getNome() + " (" + i.getContador() + ")", j, 0);
        }
    }
    
    /**
     * Define informações de comidas na interface grafica.
     * @param comidas lista de comidas disponíveis.
     */
    private void setComidas(List<Comida> comidas){
        Comida c;
                
        for (int i = 0; i <= comidas.size(); i++)
            this.tableModel.setValueAt("", i, 1);
                
        for (int i = 0; i < comidas.size(); i++){
            c = comidas.get(i);
            this.tableModel.setValueAt(c.getNome() + " (" + c.getContador() + ")", i, 1);
        }
    }
    
    /**
     * Define informações de poções na interface gráfica.
     * @param pocoes lista de poções disponíveis.
     */
    private void setPocoes(List<Pocao> pocoes){
        Pocao p;
        
        for (int i = 0; i <= pocoes.size(); i++)
            this.tableModel.setValueAt("", i, 2);
        
        for (int i = 0; i < pocoes.size(); i++){
            p = pocoes.get(i);
            this.tableModel.setValueAt(p.getNome() + " (" + p.getContador() + ")", i, 2);
        }
    }
    
    /**
     * Define informações de ataques na interface gráfica.
     * @param ataques lista de ataques disponíveis.
     */
    private void setAtaques(List<Ataque> ataques){
        int ultimoSel = comboAtaques.getSelectedIndex();
        comboAtaques.removeAllItems();
        
        for (Ataque a: ataques)
            comboAtaques.addItem(a.getNome());
        
        comboAtaques.setSelectedIndex(ultimoSel == -1 ? 0 : ultimoSel);
    }
    
    /**
     * Define informações de atributos na interface gráfica.
     * @param p personagem cujos atributos serão definidos.
     */
    private void setAtributos(Personagem p){
        labelForca.setText(String.valueOf(p.getForca()));
        labelInteligencia.setText(String.valueOf(p.getInteligencia()));
        labelCarteira.setText(String.valueOf(p.mochila.getCarteira()));
    }
    
    /**
     * Define informações sobre efeito de poções na interface gráfica.
     * @param p poção a ser utilizada.
     * @param barra barra a ser alterada na interface.
     * @param pos posição da poção a ser utilizada. Definir informações
     *            para poção de proteção, força e sagacidade equivalem
     *            às posições 0, 1 e 2, respectivamente.
     */
    private void setBarraPocao(final Pocao p, final JProgressBar barra, final int pos){  
        barrasPocao[pos] = 60;
        
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        
        final ScheduledFuture<?> barraService = ses
                .scheduleWithFixedDelay(new Runnable(){
            @Override
            public void run(){
                int valorBarra;
                
                barrasPocao[pos] -= 1;
                valorBarra = (int) (((float) barrasPocao[pos]/60)*100);
                barra.setValue(valorBarra);
            }
            
        }, 0L, 1L, TimeUnit.SECONDS);
        
        ses.schedule(new Runnable(){
            @Override
            public void run(){
                barraService.cancel(true);
                personagem.retiraAtributos(p.getAtributos());
                barrasPocao[pos] = 0;
            }
        }, 60L, TimeUnit.SECONDS);
        
    }
    
    /**
     * Inicializa uma JanelaPrincipal.
     * @param p personagem associado às informações nessa janela.
     * @param d nível de dificuldade relacionado cm os inimigos.
     */
    public JanelaPrincipal(Personagem p, int d){
        super();
        initComponents();
        this.setTitle("Painel de controle");
        this.tableModel = (DefaultTableModel) this.tabelaMochila.getModel();
        
        this.personagem = p;
        this.aManager = new ArmaduraManager(this, personagem);
        this.nivelDificuldade = d;
        comboAtaques.addItem("Sem ataque");
        comboAtaques.setSelectedIndex(0);
        
        this.personagem.setListener(new ListenerAtributos(){
            @Override
            public void morre(){
                mostraCaixaDialogo(personagem.getApelido() + " morreu!");
                tp.interrupt();
                fechaJanela();
                new JanelaInicial().setVisible(true);
            }
            
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
            public void atualizaItens() {
                setItens(personagem.mochila.getItens());
            }
            
            @Override
            public void atualizaComidas(){
                setComidas(personagem.mochila.getComidas());
            }
            
            @Override
            public void atualizaPocoes(){
                setPocoes(personagem.mochila.getPocoes());
            }
            
            @Override
            public void atualizaAtaques(){
                setAtaques(personagem.mochila.getAtaques());
            }
            
            @Override
            public void atualizaAtributos(){
                setAtributos(personagem);
            }
            
            @Override
            public void utilizaPocao(Pocao p){
                int pos;
                JProgressBar barra;
                
                switch (p.getTipo()){
                    case FORCA: pos = 0; barra = barraPocaoForca; break;
                    case SAGACIDADE: pos = 1; barra = barraPocaoSagacidade; break;
                    default: return;
                }
                
                if (barrasPocao[pos] == 0) setBarraPocao(p, barra, pos);
                else atualizaLog("Uma poção desse tipo já esta em uso!");
            }

            @Override
            public void atualizaArmaduras(){
                for (int i = 0; i < 4; i++){
                    if (!aManager.getCheckBox(i).isEnabled())
                        aManager.set(i);
                    
                    else aManager.updateBar(i);
                }
            }
        });
        
        this.labelNivel.setText(String.valueOf(this.personagem.getNivel()));
        
        this.personagem.mochila.adicionaCarteira(10000);
        
        this.setAtaques(personagem.mochila.getAtaques());
        this.setComidas(personagem.mochila.getComidas());
        this.setAtributos(personagem);
        this.setHP(personagem.getHP(), personagem.getMaxHP());
        this.setXP(personagem.getXP());
        this.setFome(personagem.getFome());    
                
        this.aManager.add(checkElmo, barraElmo);
        this.aManager.add(checkPeitoral, barraPeitoral);
        this.aManager.add(checkCalcas, barraCalcas);
        this.aManager.add(checkBotas, barraBotas);
        for (int i = 0; i < 4; i++) aManager.set(i);
        
        this.listenerPasseio = new ListenerPasseio(){
            /**
             * A cada termino de batalha, em caso de vitória do personagem,
             * será adicionado 10 vezes a quantidade do seu nível atual à 
             * sua barra de XP e, caso o inimigo gere algum item, será
             * adicionado à mochila do personagem. Em caso de derrota do
             * personagem, o jogo irá ser encerrado.
             * @param i oponente de batalha.
             */
            @Override
            public void terminaBatalha(Personagem i) {
                if (personagem.getHP() > 0){
                    atualizaLog("A batalha terminou!");
                    atualizaLog(personagem.getApelido() + " adquiriu "
                            + personagem.getNivel()*10 + " pontos de XP.");
                    
                    personagem.melhora();

                    ItemConstruivel item = ItemConstruivel.retornaItem((Inimigo) i);
                    if (item != null)
                        personagem.mochila.adicionaItem(item);
                    
                    barraHPInimigo.setString("");
                }
            }

            /**
             * A cada termino de rodada, a barra de HP do
             * oponente irá diminuindo de acordo com a sua vida.
             * @param i oponente de batalha.
             */
            @Override
            public void terminaRodada(Personagem i){
                barraHPInimigo.setValue(i.getHP());
            }

            /**
             * Define informações sobre o inimigo na interface gráfica.
             * @param i oponente de batalha.
             */
            @Override
            public void inimigoEncontrado(Personagem i){
                atualizaLog("HP: " + i.getHP());
                atualizaLog("NOME: " + i.getApelido());
                atualizaLog("Um inimigo foi localizado! Prepare-se!");
                
                barraHPInimigo.setMaximum(i.getHP());
                barraHPInimigo.setValue(i.getHP());
                barraHPInimigo.setString(String.valueOf(i.getApelido()));
            }

            /**
             * Chamado toda vez que o usuário pressiona o botão
             * "Voltar para casa" quando o mesmo está no meio de
             * uma batalha. O jogador perde 10 pontos de XP.
             */
            @Override
            public void jogadorFugiu(){
                atualizaLog("Fugir no meio de uma batalha não é legal!");
                atualizaLog(personagem.getApelido() + " perdeu 10 pontos de XP.");
                personagem.diminuiXP(10);
                
                barraHPInimigo.setValue(0);
                barraHPInimigo.setString("");
            }
            
            /**
             * Caso o jogador encontre uma comida,
             * a mesma é adicionada a sua mochila.
             * @param c comida encontrada.
             */
            @Override
            public void encontraBau(Comida c){
                atualizaLog(personagem.getApelido() + " encontrou " + c.getNome() + "!");
                personagem.mochila.adicionaComida(c);
            }
            
            /**
             * Caso o jogador encontre uma poção,
             * a mesma é adicionada a sua mochila.
             * @param p poção encontrada.
             */
            @Override
            public void encontraBau(Pocao p){
                atualizaLog(personagem.getApelido() + " encontrou " + p.getNome() + "!");
                personagem.mochila.adicionaPocao(p);
            }
            
            /**
             * Caso o jogador encontra uma quantidade de
             * moedas, a mesma é adicionada a sua carteira.
             * @param moedas quantidade de moedas encontradas.
             */
            @Override
            public void encontraBau(int moedas){
                if (moedas > 0){
                    atualizaLog(personagem.getApelido() + " encontrou " + moedas + " moedas em um baú!");
                    personagem.mochila.adicionaCarteira(moedas);
                    labelCarteira.setText(String.valueOf(personagem.mochila.getCarteira()));
                
                } else atualizaLog(personagem.getApelido() + " encontrou um baú, porém não havia nada.");
            }
        };
        
        this.tp = ThreadPasseio.getInstance(personagem, nivelDificuldade, listenerPasseio);
      
        /**
         * Alterna entre os modos de passeio (externo) e casa (interno).
         * O modo externo permite ao jogador batalhar contra monstros,
         * adquirir itens, tanto obtidos por inimigos mortos quanto 
         * encontrados em baús, além de aumentar sua experiència e nível.
         * O modo interno permite ao jogador realizar compras com comerciantes,
         * utilizando as moedas que foi adquirido no modo externo para comprar
         * comidas ou poções, e também fazer trocas de itens com o alquimista,
         * que dependendo de cada item irá gerá uma poção correspondente.
         */
        this.btnPassear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (btnPassear.getText().equals("Dar um passeio")){
                    btnComerciante.setEnabled(false);
                    btnAlquimista.setEnabled(false);
                    
                    if (!ThreadPasseio.isInstance())
                        tp = ThreadPasseio.getInstance(personagem, nivelDificuldade, listenerPasseio);
                    
                    tp.start();
                    
                    atualizaLog(personagem.getApelido() + " está dando uma volta...");
                    btnPassear.setText("Voltar para casa");

                } else {
                    tp.interrupt();
                    atualizaLog(personagem.getApelido() + " voltou para casa.");
                    btnPassear.setText("Dar um passeio");
                    btnComerciante.setEnabled(true);
                    btnAlquimista.setEnabled(true);
                }
            }
        });
        
        /**
         * Gerencia o uso de itens do jogo, ou seja, poções e comidas.
         */
        this.btnUsarItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int lin = tabelaMochila.getSelectedRow();
                int col = tabelaMochila.getSelectedColumn();
                if (col == 0) return;
                
                switch (col){
                    case 1: {
                        if (personagem.getFome() == 0){
                            atualizaLog(personagem.getApelido() + " não está com fome!");
                            break;
                        }
                        
                        Comida c = personagem.mochila.getComida(lin);
                        
                        if (c != null){
                            atualizaLog(personagem.getApelido() + " comeu " +
                                    c.getNome() + " e diminuiu " +
                                    c.getFomeRest() + " pontos de fome.");
                            
                            personagem.mochila.removeComida(lin);
                            personagem.come(c);
                        }
                            
                        break;
                    }
                    
                    case 2: {
                        Pocao p = personagem.mochila.getPocao(lin);
                        if (p == null) break;
                        JProgressBar barra = null;
                        
                        if (p.getTipo() != EnumPocao.VIDA){
                            int pos;
                            switch (p.getTipo()){
                                case FORCA: pos = 0; barra = barraPocaoForca; break;
                                case SAGACIDADE: pos = 1; barra = barraPocaoSagacidade; break;
                                default: throw new RuntimeException("não deveria entrar aqui");
                            }
                            
                            if (barrasPocao[pos] > 0){
                                atualizaLog("Uma poção desse tipo já esta em uso!");
                                break;
                                
                            } else setBarraPocao(p, barra, pos);
                        }
                        
                        atualizaLog(personagem.getApelido() +
                                " consumiu a poção " + p.getNome() + "!");     
                        personagem.mochila.removePocao(lin);
                        personagem.bebe(p);
                        
                        break;
                    }
                }
            }
        });
        
        /**
         * Limpa o campo de registros de ações do usuário.
         */
        this.btnLimpaLog.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                tAreaLog.setText("");
            }
        });
        
        /**
         * Define um ataque atual para ser utilizado em batalhas.
         */
        this.comboAtaques.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int indAtaque = comboAtaques.getSelectedIndex();
                if (indAtaque != -1){
                    Ataque a = personagem.mochila.getAtaque(indAtaque);
                    personagem.defineAtaque(a);
                }
            }
        });
        
        /**
         * Inicia o modo alquimista.
         */
        this.btnAlquimista.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                new JanelaAlquimista(personagem).setVisible(true);
            }
        });
        
        /**
         * Inicia o modo comerciante.
         */
        this.btnComerciante.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                new JanelaComerciante(personagem).setVisible(true);
            }
        });
        
        this.barraHPInimigo.setStringPainted(true);
        this.barraHPInimigo.setString("");
        
        this.barraPocaoForca.setStringPainted(true);
        this.barraPocaoForca.setString("Poção de força");
        
        this.barraPocaoSagacidade.setStringPainted(true);
        this.barraPocaoSagacidade.setString("Poção de sagacidade");
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
        tabelaMochila = new javax.swing.JTable(){
            public String getToolTipText(MouseEvent e){
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                int realColumnIndex = convertColumnIndexToModel(colIndex);
                String text = (String) getValueAt(rowIndex, colIndex);

                try {
                    Item item = null;
                    switch (realColumnIndex){
                        case 0: item = personagem.mochila.getItem(rowIndex); break;
                        case 1: item = personagem.mochila.getComida(rowIndex); break;
                        case 2: item = personagem.mochila.getPocao(rowIndex); break;
                    }

                    return item == null ? null : item.getDescricaoHTML();

                } catch (IndexOutOfBoundsException err){
                    return null;
                }
            }
        };
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
        jLabel3 = new javax.swing.JLabel();
        labelForca = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        labelInteligencia = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelCarteira = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        labelNivel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnComerciante = new javax.swing.JButton();
        btnPassear = new javax.swing.JButton();
        btnLimpaLog = new javax.swing.JButton();
        btnAlquimista = new javax.swing.JButton();
        btnUsarItem = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        comboAtaques = new javax.swing.JComboBox();
        barraPocaoForca = new javax.swing.JProgressBar();
        barraPocaoSagacidade = new javax.swing.JProgressBar();
        barraHPInimigo = new javax.swing.JProgressBar();
        barraElmo = new javax.swing.JProgressBar();
        barraPeitoral = new javax.swing.JProgressBar();
        barraCalcas = new javax.swing.JProgressBar();
        barraBotas = new javax.swing.JProgressBar();
        checkElmo = new javax.swing.JCheckBox();
        checkCalcas = new javax.swing.JCheckBox();
        checkPeitoral = new javax.swing.JCheckBox();
        checkBotas = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tAreaLog = new javax.swing.JTextArea();

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

        fxLabel4.setText("HP:");

        fxLabel5.setText("XP:");

        fxLabel6.setText("FOME:");

        labelHP.setText("[HP]");

        labelXP.setText("[XP]");

        labelFome.setText("[FOME]");

        jLabel3.setText("FORÇA:");

        labelForca.setText("[FORCA]");

        jLabel4.setText("INTELIGÊNCIA:");

        labelInteligencia.setText("[INTELIGENCIA]");

        jLabel5.setText("CARTEIRA:");

        labelCarteira.setText("[MOEDAS]");

        jLabel1.setText("NÍVEL:");

        labelNivel.setText("[NIVEL]");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
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
                            .addComponent(labelFome)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelForca))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelInteligencia))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelCarteira))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(labelNivel)))
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
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelForca))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(labelInteligencia))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelCarteira))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(labelNivel))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btnComerciante.setText("Visitar comerciante");

        btnPassear.setText("Dar um passeio");

        btnLimpaLog.setText("Limpar registro");

        btnAlquimista.setText("Visitar alquimista");

        btnUsarItem.setText("Utilizar item");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnComerciante, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
            .addComponent(btnUsarItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPassear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAlquimista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLimpaLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnComerciante)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAlquimista)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPassear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUsarItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpaLog)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel2.setText("ATAQUE SELECIONADO:");

        barraPocaoForca.setToolTipText("Poção de força");
        barraPocaoForca.setName(""); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(barraPocaoSagacidade, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(barraPocaoForca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(barraHPInimigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(comboAtaques, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkCalcas)
                    .addComponent(checkPeitoral)
                    .addComponent(checkBotas)
                    .addComponent(checkElmo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(barraBotas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(barraCalcas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barraPeitoral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barraElmo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboAtaques, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(barraHPInimigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkElmo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barraElmo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkPeitoral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barraPeitoral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkCalcas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barraCalcas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkBotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barraBotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(barraPocaoSagacidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barraPocaoForca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        barraPocaoForca.getAccessibleContext().setAccessibleName("");

        tAreaLog.setColumns(20);
        tAreaLog.setRows(5);
        tAreaLog.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tAreaLog.setEnabled(false);
        jScrollPane1.setViewportView(tAreaLog);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 67, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraBotas;
    private javax.swing.JProgressBar barraCalcas;
    private javax.swing.JProgressBar barraElmo;
    private javax.swing.JProgressBar barraFome;
    private javax.swing.JProgressBar barraHP;
    private javax.swing.JProgressBar barraHPInimigo;
    private javax.swing.JProgressBar barraPeitoral;
    private javax.swing.JProgressBar barraPocaoForca;
    private javax.swing.JProgressBar barraPocaoSagacidade;
    private javax.swing.JProgressBar barraXP;
    private javax.swing.JButton btnAlquimista;
    private javax.swing.JButton btnComerciante;
    private javax.swing.JButton btnLimpaLog;
    private javax.swing.JButton btnPassear;
    private javax.swing.JButton btnUsarItem;
    private javax.swing.JCheckBox checkBotas;
    private javax.swing.JCheckBox checkCalcas;
    private javax.swing.JCheckBox checkElmo;
    private javax.swing.JCheckBox checkPeitoral;
    private javax.swing.JComboBox comboAtaques;
    private javax.swing.JLabel fxLabel4;
    private javax.swing.JLabel fxLabel5;
    private javax.swing.JLabel fxLabel6;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel labelCarteira;
    private javax.swing.JLabel labelFome;
    private javax.swing.JLabel labelForca;
    private javax.swing.JLabel labelHP;
    private javax.swing.JLabel labelInteligencia;
    private javax.swing.JLabel labelNivel;
    private javax.swing.JLabel labelXP;
    private javax.swing.JTextArea tAreaLog;
    private javax.swing.JTable tabelaMochila;
    // End of variables declaration//GEN-END:variables
}
