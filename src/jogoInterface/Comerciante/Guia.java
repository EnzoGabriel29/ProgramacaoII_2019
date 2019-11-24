package jogoInterface.Comerciante;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import jogoCodigo.Personagem.Personagem;

abstract public class Guia<T> {
    protected final T[] itens;
    protected final String tituloGuia;
    protected final Personagem personagem;
    protected final JLabel labelPreco;
    protected final JTextArea labelDescricao;
    protected final JButton botaoComprar;
    protected final JList listaItens;
    protected final JSpinner spinnerQtd;
    
    public Guia(Personagem p, T[] itens, String titulo, JLabel lblPreco,
            JTextArea lblDesc, JButton btnComprar, JList lstItens, JSpinner spnQtd){
        
        this.personagem = p;
        this.itens = itens;
        this.tituloGuia = titulo;
        this.botaoComprar = btnComprar;
        this.labelDescricao = lblDesc;
        this.labelPreco = lblPreco;
        this.listaItens = lstItens;
        this.spinnerQtd = spnQtd;
    }
    
    abstract protected void setItem(T item);
    abstract protected int getPreco(T item);
    abstract protected void setDescricao(T item);
    abstract protected void onItemComprado(T item, int qtd);
    abstract protected void atualizaBotaoComprar(T item, int qtd);
    
    public void configuraElementos(){
        this.setPreco(0);
        labelDescricao.setText("Selecione um item.");
        
        botaoComprar.setEnabled(false);
        
        listaItens.setListData(itens);
        listaItens.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                T item = (T) listaItens.getSelectedValue();
                setItem(item);
                
                int qtd = (int) spinnerQtd.getValue();
                int preco = getPreco(item);
                int valorTotal = qtd * preco;
                
                setPreco(valorTotal);
                atualizaBotaoComprar(item, qtd);
            }
        });
        
        spinnerQtd.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e){
                T item = (T) listaItens.getSelectedValue();
                int qtd = (int) spinnerQtd.getValue();
                int preco = getPreco(item);
                int valorTotal = qtd * preco;
                
                setPreco(valorTotal);
                atualizaBotaoComprar(item, qtd);
            }
        });
        
        botaoComprar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){                
                T item = (T) listaItens.getSelectedValue();
                int qtd = (int) spinnerQtd.getValue();
                
                onItemComprado(item, qtd);
            }
        });
    }
    
    protected void setPreco(int moedas){
        String msg = String.format("%d moedas", moedas);
        this.labelPreco.setText(msg);
    }
}