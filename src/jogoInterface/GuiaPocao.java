package jogoInterface;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import jogoCodigo.Pocao;
import jogoCodigo.Atributos;
import jogoCodigo.Personagem;
import jogoCodigo.TipoPocao;

public class GuiaPocao extends Guia<Pocao> {

    public GuiaPocao(Personagem p, Pocao[] itens, String titulo, JLabel lblPreco,
            JLabel lblDesc, JButton btnComprar,JList lstItens, JSpinner spnQtd){
        
        super(p, itens, titulo, lblPreco, lblDesc, btnComprar, lstItens, spnQtd);
    }

    @Override
    protected void setDescricao(String nome) {        
        Atributos a = this.getItem(nome).getAtributos();
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
    protected void setItem(String nome){
        switch (nome){
            case "Poção de vida": this.setPreco(20); break;
            case "Poção de sagacidade": this.setPreco(30); break;
            case "Poção de força": this.setPreco(50); break;
        }
        
        this.setDescricao(nome);
    }
    
    @Override
    protected Pocao getItem(String nome){
        switch (nome){
            case "Poção de vida": return Pocao.retornaPocao(TipoPocao.VIDA);
            case "Poção de sagacidade": return Pocao.retornaPocao(TipoPocao.SAGACIDADE);
            case "Poção de força": return Pocao.retornaPocao(TipoPocao.FORCA);
            default: return null;
        }
    }
    
    @Override
    protected int getPrecoAtual(){
        if (listaItens.getSelectedValue() == null) return 0;
        
        String nome = listaItens.getSelectedValue().toString();
        switch (nome){
            case "Poção de vida": return 20;
            case "Poção de sagacidade": return 30;
            case "Poção de força": return 50;
            default: return 0;
        }
    }

    @Override
    protected void onItemComprado(Pocao item) {
        int qtd = (int) spinnerQtd.getValue();
        int valorTotal = qtd * getPrecoAtual();
        personagem.mochila.removeCarteira(valorTotal);
                
        for (int i = 0; i < qtd; i++)
            personagem.mochila.adicionaPocao(item);
        
        if (valorTotal > personagem.mochila.getCarteira())
            botaoComprar.setEnabled(false);
                
        else botaoComprar.setEnabled(true);
    }
}
