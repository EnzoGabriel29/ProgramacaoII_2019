package jogoCodigo;

import jogoCodigo.Item.Item;
import jogoCodigo.BancoDados.EnumArmadura;

/**
 * Providencia uma função de armadura para o jogo. Um personagem possui
 * quatro slots para armadura, onde podem ser adicionados elmos, peitorais,
 * calças e botas, um de cada vez. Não é um item empilhável, ou seja, é
 * possível apenas ter uma armadura de cada tipo de uma vez. Após a armadura
 * ultrapassar o máximo de dano que ela pode receber, ela é retirada do
 * personagem automaticamente. Cada armadura possui uma porcentagem de
 * proteção do personagem dependendo do seu tipo.
 * @author Enzo
 */
public class Armadura extends Item {
    private int hp;
    private final EnumArmadura tipo;
    public boolean isMontada = false;
    
    /**
     * Inicializa uma armadura.
     * @param nome nome da armadura.
     * @param hp máximo de dano que a armadura pode levar.
     * @param e tipo da armadura.
     */
    public Armadura(String nome, int hp, EnumArmadura e){
        this.nome = nome;
        this.hp = hp;
        this.tipo = e;
    }
    
    /**
     * Verifica se a armadura ultrapassou o seu dano máximo.
     * @return se a armadura está inutilizável.
     */
    public boolean isDanificada(){
        return this.hp <= 0;
    }
    
    /**
     * Diminui pontos de dano da armadura.
     * @param valor valor do dano recebido.
     */
    public void diminuiHP(int valor){
        this.hp -= valor;
    }
    
    /**
     * Getter para o HP da armadura.
     * @return o HP da armadura.
     */
    public int getHP() {
        return this.hp;
    }

    /**
     * Getter para o tipo da armadura.
     * @return o tipo da armadura.
     */
    public EnumArmadura getTipo() {
        return this.tipo;
    }
    
    
}
