package jogoCodigo;

abstract public class Item {
    protected String nome;
    
    public String getNome(){
        return this.nome;
    }
    
    public String getDescricao(){
        return "Sem descrição disponível";
    }
    
    public String getDescricaoHTML(){
        return ("<html>" + this.getDescricao() + "</html>").replace("\n", "<br>");
    }

    @Override
    public String toString(){
        return this.nome;
    }
}
