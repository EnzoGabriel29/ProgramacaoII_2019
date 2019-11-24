package jogoInterface.Comerciante;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import jogoCodigo.Pocao.Pocao;
import jogoCodigo.Atributos;
import jogoCodigo.Personagem.Personagem;
import jogoCodigo.BancoDados.EnumPocao;

public class GuiaPocao extends Guia<EnumPocao> {

    public GuiaPocao(Personagem p, EnumPocao[] itens, String titulo, JLabel lblPreco,
            JTextArea lblDesc, JButton btnComprar,JList lstItens, JSpinner spnQtd){
        
        super(p, itens, titulo, lblPreco, lblDesc, btnComprar, lstItens, spnQtd);
    }

    @Override
    protected void setDescricao(EnumPocao item) {        
        Atributos a = item.getAtributos();
        String strRet = "";
        
        if (a.getHP() != 0)
            strRet += "Vida adquirida: " + a.getHP() + "\n";
        
        if (a.getForca() != 0)
            strRet += "Força adquirida: " + a.getForca() + "\n";
        
        if (a.getInteligencia() != 0)
            strRet += "Inteligência adquirida: " + a.getInteligencia() + "\n";
        
        if (a.getMaxHP() != 0)
            strRet += "Máximo de vida adquirida: " + a.getMaxHP() + "\n";
        
        if (strRet.equals(""))
            strRet = "Esse item não possui nenhum atributo." + "\n";
        
        labelDescricao.setText(strRet);
    }

    @Override
    protected int getPreco(EnumPocao item) {
        return item.getPreco();
    }

    @Override
    protected void setItem(EnumPocao item) {
        this.setPreco(item.getPreco());
        this.setDescricao(item);
    }

    @Override
    protected void onItemComprado(EnumPocao item, int qtd) {
        int valorTotal = qtd * item.getPreco();
        Pocao p = EnumPocao.getPocao(item);
        
        personagem.mochila.removeCarteira(valorTotal);
                
        for (int i = 0; i < qtd; i++)
            personagem.mochila.adicionaPocao(p);
        
        this.atualizaBotaoComprar(item, qtd);
    }
    
    @Override
    protected void atualizaBotaoComprar(EnumPocao item, int qtd) {
        int valorTotal = item.getPreco() * qtd;
        if (valorTotal > personagem.mochila.getCarteira())
            botaoComprar.setEnabled(false);
                
        else botaoComprar.setEnabled(true);
    }
}
