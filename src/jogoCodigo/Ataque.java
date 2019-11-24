package jogoCodigo;

/**
 * Providencia a função de ataque do jogo. Cada personagem possui
 * um ataque específico à sua classe. Os inimigos possuem um ataque
 * padrão, que depende do seu nível. A cada rodada, o usuário pode
 * escolher um ataque para utilizar em batalhas. A cada nível que o
 * usuário avança, ataques mais poderosos serão desbloqueados.
 * @author Enzo
 */
public class Ataque {
    private final String nome;
    private final int dano;
    
    /**
     * Inicializa um ataque.
     * @param nome nome do ataque.
     * @param dano dano causado pelo ataque.
     */
    public Ataque(String nome, int dano){
        this.nome = nome;
        this.dano = dano;
    }
    
    /**
     * Getter para o nome.
     * @return o nome do ataque.
     */
    public String getNome(){
        return nome;
    }
    
    /**
     * Getter para o dano.
     * @return o dano causado pelo ataque.
     */
    public int getDano(){
        return dano;
    }

    /**
     * Sobreposição do método toString.
     * @return o nome do ataque.
     */
    @Override
    public String toString() {
        return this.nome;
    }   
    
    
}
