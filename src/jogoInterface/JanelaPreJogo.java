package jogoInterface;

import java.awt.event.ActionEvent;
import jogoCodigo.Ataque;
import jogoCodigo.Personagem.Curandeiro;
import jogoCodigo.Personagem.Gladiador;
import jogoCodigo.Personagem.Mago;
import jogoCodigo.Personagem.Personagem;

public class JanelaPreJogo extends Janela {

    public static String capitalize(String string){
        String strRet = "";
        
        if (string.contains(" ")){
            String[] strings = string.split(" ");
            for (String s: strings){
                strRet += capitalize(s);
                strRet += " ";
            }
            
            return strRet;
        }
            
        strRet += Character.toUpperCase(string.charAt(0));
        char[] chars = string.toCharArray();
        for (int i = 1; i < chars.length; i++)
            strRet += Character.toLowerCase(chars[i]);

        return strRet;
    }
    
    public JanelaPreJogo(){
        super();
        initComponents();
        this.setTitle("Configurações");
        
        btnJogar.addActionListener((ActionEvent e) -> {
            String nome = capitalize(campoNome.getText());
            String classe = (String) comboClasse.getSelectedItem();
            String dific = (String) comboDificuldade.getSelectedItem();
            
            Personagem p;
            switch (classe){
                case "Mago": p = new Mago(nome); break;
                case "Gladiador": p = new Gladiador(nome); break;
                case "Curandeiro": p = new Curandeiro(nome); break;
                default: throw new RuntimeException("não deveria entrar aqui");
            }
            
            int numDific;
            switch (dific){
                case "Fácil": numDific = 0; break;
                case "Médio": numDific = 1; break;
                case "Difícil": numDific = 2; break;
                default: throw new RuntimeException("não deveria entrar aqui");
            }
            
            p.defineAtaque(new Ataque("Sem ataque", 0));
            
            new JanelaPrincipal(p, numDific).setVisible(true);
            fechaJanela();
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboClasse = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        comboDificuldade = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        campoNome = new javax.swing.JTextField();
        btnJogar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("CLASSE:");

        comboClasse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mago", "Gladiador", "Curandeiro" }));

        jLabel3.setText("DIFICULDADE:");

        comboDificuldade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fácil", "Médio", "Difícil" }));

        jLabel4.setText("NOME DO PERSONAGEM:");

        btnJogar.setText("Iniciar partida");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel5.setText("Não será possível alterar o nível de dificuldade após a partida ter sido criada.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addGap(18, 18, 18)
                                            .addComponent(campoNome))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addGap(18, 18, 18)
                                            .addComponent(comboDificuldade, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(18, 18, 18)
                                            .addComponent(comboClasse, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnJogar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboDificuldade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnJogar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnJogar;
    private javax.swing.JTextField campoNome;
    private javax.swing.JComboBox comboClasse;
    private javax.swing.JComboBox comboDificuldade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
