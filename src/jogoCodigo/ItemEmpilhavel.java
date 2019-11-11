package jogoCodigo;

public class ItemEmpilhavel extends ItemConsumivel {
    protected int contador = 1;
    
    public int getContador(){
        return this.contador;
    }
    
    public void aumentaContador(int n){
        if (n < 1) return;
        this.contador += n;
    }
    
    public void diminuiContador(int n){
        if (n < 1) return;
        if (this.contador == 0) return;
        this.contador -= n;
    }
}
