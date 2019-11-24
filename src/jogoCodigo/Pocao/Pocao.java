package jogoCodigo.Pocao;

import java.util.Random;
import jogoCodigo.Atributos;
import jogoCodigo.BancoDados.EnumPocao;
import jogoCodigo.Calculo.ColecaoAleatoria;
import jogoCodigo.ItemEmpilhavel;

public class Pocao extends ItemEmpilhavel {
    
    public static Pocao retornaPocao(String classe){
        ColecaoAleatoria<EnumPocao> ca = new ColecaoAleatoria<>();
        ca.adicionaItem(75, EnumPocao.VIDA)
                .adicionaItem(25, EnumPocao.FORCA)
                .adicionaItem(25, EnumPocao.SAGACIDADE);
        
        Pocao pocaoRet = EnumPocao.getPocao(ca.retornaItem());
        
        switch (new Random().nextInt(3)){
            case 0: return pocaoRet;
            case 1: return pocaoRet;
            case 2: {
                switch (classe){
                    case "Mago": return new ElixirInteligencia(pocaoRet);
                    case "Gladiador": return new ElixirForca(pocaoRet);
                    case "Curandeiro": return new ElixirVida(pocaoRet);
                }
            }
        }
        
        return null;
    }
            
    private final EnumPocao tipo;
    private final Atributos atributos;
    
    public Pocao(String nome, Atributos atr){
        this.nome = nome;
        this.atributos = atr;
        this.tipo = EnumPocao.porNome(nome);
    }
    
    public Atributos getAtributos(){
        return this.atributos;
    }
    
    public EnumPocao getTipo(){
        return this.tipo;
    }
    
    @Override
    public String getDescricao(){
        return "ITEM: " + this.nome + "\n" +
               "TIPO: Poção" + "\n" +
               "VIDA ADQUIRIDA: " + atributos.getHP() + "\n" +
               "FORÇA ADQUIRIDA: " + atributos.getForca() + "\n" +
               "INTELIGÊNCIA ADQUIRIDA: " + atributos.getInteligencia() + "\n" +
               "VIDA AUMENTADA: " + atributos.getMaxHP() + "\n";
    }
}
