package jogoInterface;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JProgressBar;
import jogoCodigo.Armadura;
import jogoCodigo.Personagem.Personagem;

public class ArmaduraManager {
    private final Personagem personagem;
    private final JanelaPrincipal window;
    private final List<JCheckBox> checkBoxes;
    private final List<JProgressBar> progressBars;
    
    public ArmaduraManager(JanelaPrincipal j, Personagem p){
        this.personagem = p;
        this.window = j;
        this.checkBoxes = new ArrayList<>();
        this.progressBars = new ArrayList<>();
    }
    
    public void add(final JCheckBox c, final JProgressBar p){
        final int pos = this.checkBoxes.size();
        p.setStringPainted(true);
        p.setString("");
        p.setEnabled(false);
        c.setEnabled(false);
        
        c.addActionListener((ActionEvent e) -> {
            Armadura a = personagem.getArmadura(pos);
            
            boolean cond = c.isSelected();
            p.setEnabled(cond);
            if (a != null) a.isMontada = cond;
        });
        
        this.checkBoxes.add(c);
        this.progressBars.add(p);
    }

    public JCheckBox getCheckBox(int pos) {
        return this.checkBoxes.get(pos);
    }
    public void set(int pos){
        Armadura a = this.personagem.getArmadura(pos);
        if (a == null) return;
        JCheckBox c = this.checkBoxes.get(pos);
        JProgressBar p = this.progressBars.get(pos);
        
        c.setEnabled(true);
        c.setSelected(true);
        p.setString(a.getNome());
        p.setMaximum(a.getHP());
        p.setValue(a.getHP());
    }
    
    public void updateBar(int pos){
        Armadura a = this.personagem.getArmadura(pos);
        
        JCheckBox c = this.checkBoxes.get(pos);
        JProgressBar p = this.progressBars.get(pos);
        
        if (a == null || a.isDanificada()){
            p.setString("");
            p.setValue(0);
            c.setEnabled(false);
            c.setSelected(false);
            
        } else p.setValue(a.getHP());
    }
}
