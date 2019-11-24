package jogoCodigo;

/**
 * Fornece uma classe base para todos os itens do jogo.
 * @author Enzo
 */
abstract public class Item {
    protected String nome;
    
    /**
     * Getter para o nome do item.
     * @return o nome do item.
     */
    public String getNome(){
        return this.nome;
    }
    
    /**
     * Getter para a descrição do item.
     * @return a descrição do item.
     */
    public String getDescricao(){
        return "Sem descrição disponível";
    }
    
    /**
     * Getter para a descrição em HTML do item. Usado para mostrar descrições
     * com mais de uma linha na tabela de itens da interface gráfica.
     * @return a descrição em HTML do item.
     */
    public String getDescricaoHTML(){
        return ("<html>" + this.getDescricao() + "</html>").replace("\n", "<br>");
    }

    @Override
    public String toString(){
        return this.nome;
    }
}
