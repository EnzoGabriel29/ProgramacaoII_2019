package jogoInterface.Comerciante;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import jogoCodigo.Comida.Comida;
import jogoCodigo.Personagem.Personagem;
import jogoCodigo.BancoDados.EnumComida;

class GuiaComida extends Guia<EnumComida> {

    public GuiaComida(Personagem p, EnumComida[] itens, String titulo, JLabel lblPreco,
            JTextArea lblDesc, JButton btnComprar,JList lstItens, JSpinner spnQtd){
        
        super(p, itens, titulo, lblPreco, lblDesc, btnComprar, lstItens, spnQtd);
    }

    @Override
    protected void setDescricao(EnumComida item){        
        int fome = item.getFome();
        String descricao = String.format("Restaura %d pontos de fome.", fome);
        labelDescricao.setText(descricao);
    }
    
    @Override
    protected void setItem(EnumComida item){
        this.setPreco(item.getPreco());
        this.setDescricao(item);
    }
    
    @Override
    protected int getPreco(EnumComida item){
        return item.getPreco();
    }

    @Override
    protected void onItemComprado(EnumComida item, int qtd){
        Comida c = EnumComida.getComida(item);
        
        int valorTotal = qtd * item.getPreco();
        personagem.mochila.removeCarteira(valorTotal);
                
        for (int i = 0; i < qtd; i++)
            personagem.mochila.adicionaComida(c);
        
        this.atualizaBotaoComprar(item, qtd);
    }

    @Override
    protected void atualizaBotaoComprar(EnumComida item, int qtd){
        int valorTotal = item.getPreco() * qtd;
        if (valorTotal > personagem.mochila.getCarteira())
            botaoComprar.setEnabled(false);
                
        else botaoComprar.setEnabled(true);
    }
}
