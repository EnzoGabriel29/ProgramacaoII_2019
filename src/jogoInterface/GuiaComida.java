package jogoInterface;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import jogoCodigo.Comida;
import jogoCodigo.Personagem;
import jogoCodigo.TipoComida;

class GuiaComida extends Guia<Comida> {

    public GuiaComida(Personagem p, Comida[] itens, String titulo, JLabel lblPreco,
            JLabel lblDesc, JButton btnComprar,JList lstItens, JSpinner spnQtd){
        
        super(p, itens, titulo, lblPreco, lblDesc, btnComprar, lstItens, spnQtd);
    }

    @Override
    protected void setDescricao(String nome) {        
        int fome = this.getItem(nome).getFomeRest();
        
        String descricao = String.format("Restaura %d pontos de fome.", fome);
        labelDescricao.setText(descricao);
    }
    
    @Override
    protected void setItem(String nome){
        switch (nome){
            case "Uva": setPreco(3); break;
            case "Maçã": setPreco(5); break;
            case "Banana": setPreco(15); break;
            case "Cenoura": setPreco(25); break;
            case "Ensopado": setPreco(45); break;
            case "Frango": setPreco(95); break;
        }
        
        this.setDescricao(nome);
    }
    
    @Override
    protected Comida getItem(String nome){
        switch (nome){
            case "Uva": return Comida.retornaComida(TipoComida.UVA);
            case "Maçã": return Comida.retornaComida(TipoComida.MACA);
            case "Banana": return Comida.retornaComida(TipoComida.BANANA);
            case "Cenoura": return Comida.retornaComida(TipoComida.CENOURA);
            case "Ensopado": return Comida.retornaComida(TipoComida.ENSOPADO);
            case "Frango": return Comida.retornaComida(TipoComida.FRANGO);
            default: return null;
        }
    }
    
    @Override
    protected int getPrecoAtual(){
        if (listaItens.getSelectedValue() == null) return 0;
        
        String nome = listaItens.getSelectedValue().toString();
        switch (nome){
            case "Uva": return 3;
            case "Maçã": return 5;
            case "Banana": return 15;
            case "Cenoura": return 25;
            case "Ensopado": return 45;
            case "Frango": return 95;
            default: return 0;
        }
    }

    @Override
    protected void onItemComprado(Comida item) {
        int qtd = (int) spinnerQtd.getValue();
        int valorTotal = qtd * getPrecoAtual();
        personagem.mochila.removeCarteira(valorTotal);
                
        for (int i = 0; i < qtd; i++)
            personagem.mochila.adicionaComida(item);
        
        if (valorTotal > personagem.mochila.getCarteira())
            botaoComprar.setEnabled(false);
                
        else botaoComprar.setEnabled(true);
    }
}
