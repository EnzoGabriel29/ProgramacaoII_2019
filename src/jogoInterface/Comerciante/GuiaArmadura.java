package jogoInterface.Comerciante;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JSpinner;
import jogoCodigo.Armadura;
import jogoCodigo.BancoDados.EnumArmadura;
import jogoCodigo.Personagem.Personagem;

public class GuiaArmadura extends Guia<EnumArmadura> {

    public GuiaArmadura(Personagem p, EnumArmadura[] itens, String titulo,
            JLabel lblPreco, JTextArea lblDesc, JButton btnComprar, JList lstItens, JSpinner spnQtd) {
        super(p, itens, titulo, lblPreco, lblDesc, btnComprar, lstItens, spnQtd);
        this.spinnerQtd.setEnabled(false);
    }

    @Override
    protected void setItem(EnumArmadura item){
        this.setPreco(item.getPreco());
        this.setDescricao(item);
    }

    @Override
    protected int getPreco(EnumArmadura item){
        return item.getPreco();
    }

    @Override
    protected void onItemComprado(EnumArmadura item, int qtd){
        Armadura a = EnumArmadura.getArmadura(item);
        
        int valorTotal = qtd * item.getPreco();
        personagem.mochila.removeCarteira(valorTotal);
                
        for (int i = 0; i < qtd; i++)
            personagem.monta(a);
        
        this.atualizaBotaoComprar(item, qtd);
    }

    @Override
    protected void setDescricao(EnumArmadura item){
        String s1 = String.format("Aumenta a proteção em %d%%.", (int)(item.getProtecao()*100));
        String s2 = String.format("Resiste %d pontos de dano.", item.getDurabilidade());
        labelDescricao.setText(s1 + "\n\r" + s2);
    }

    @Override
    protected void atualizaBotaoComprar(EnumArmadura item, int qtd){
        botaoComprar.setEnabled(!personagem.possuiArmadura(item.getNome()));
    }
    
}
