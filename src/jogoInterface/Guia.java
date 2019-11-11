package jogoInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import jogoCodigo.Comida;
import jogoCodigo.Personagem;
import jogoCodigo.TipoComida;

abstract public class Guia<T> {
    protected final T[] itens;
    protected final String tituloGuia;
    protected final Personagem personagem;
    protected final JLabel labelPreco;
    protected final JLabel labelDescricao;
    protected final JButton botaoComprar;
    protected final JList listaItens;
    protected final JSpinner spinnerQtd;
    
    public Guia(Personagem p, T[] itens, String titulo, JLabel lblPreco,
            JLabel lblDesc, JButton btnComprar, JList lstItens, JSpinner spnQtd){
        
        this.personagem = p;
        this.itens = itens;
        this.tituloGuia = titulo;
        this.botaoComprar = btnComprar;
        this.labelDescricao = lblDesc;
        this.labelPreco = lblPreco;
        this.listaItens = lstItens;
        this.spinnerQtd = spnQtd;
    }
    
    abstract protected void setItem(String nome);
    abstract protected T getItem(String nome);
    abstract protected int getPrecoAtual();
    abstract protected void onItemComprado(T item);
    abstract protected void setDescricao(String nome);
    
    public void configuraElementos(){
        this.setPreco(0);
        labelDescricao.setText("Selecione um item.");
        
        botaoComprar.setEnabled(false);
        
        listaItens.setListData(itens);
        listaItens.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                String item = listaItens.getSelectedValue().toString();
                setItem(item);
                
                int qtd = (int) spinnerQtd.getValue();
                int preco = getPrecoAtual();
                int valorTotal = qtd * preco;
                
                setPreco(valorTotal);
                
                if (valorTotal > personagem.mochila.getCarteira())
                    botaoComprar.setEnabled(false);
                
                else botaoComprar.setEnabled(true);
            }
        });
        
        spinnerQtd.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e){
                int qtd = (int) spinnerQtd.getValue();
                int preco = getPrecoAtual();
                int valorTotal = qtd * preco;
                
                setPreco(valorTotal);
                
                if (valorTotal > personagem.mochila.getCarteira())
                    botaoComprar.setEnabled(false);
                
                else botaoComprar.setEnabled(true);
            }
        });
        
        botaoComprar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){                
                T item = (T)(listaItens.getSelectedValue());
                onItemComprado(item);
            }
        });
    }
    
    protected void setPreco(int moedas){
        String msg = String.format("%d moedas", moedas);
        this.labelPreco.setText(msg);
    }
}