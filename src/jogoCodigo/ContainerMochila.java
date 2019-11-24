package jogoCodigo;

import java.util.ArrayList;
import java.util.List;

public class ContainerMochila<T extends ItemEmpilhavel>{
    public final List<T> itens;
    
    public ContainerMochila(){
        this.itens = new ArrayList<>();
    }
        
    public List<T> retornaItens(){
        return new ArrayList<>(this.itens);
    }
    
    public T retornaItem(int pos){
        return this.itens.get(pos);
    }
    
    public void adicionaItem(T item){
        String nome = item.getNome();
        boolean isDuplicado = false;

        for (ItemEmpilhavel ic : this.itens){
            if (ic.getNome().equals(nome)){
                ic.aumentaContador(1);
                isDuplicado = true;
                break;
            }
        }

        if (!isDuplicado) this.itens.add(item);
    }
    
    public void removeItem(int pos){
        ItemEmpilhavel item = this.itens.get(pos);
        
        if (item.getContador() == 1)
            this.itens.remove(pos);
                
        else item.diminuiContador(1);   
    }
}