package jogoCodigo.item;

import jogoCodigo.personagem.Personagem;

public class Pocao extends ItemUtilizavel {
    public int hpRecup;
    public boolean isVeneno;
    
    public Pocao(String nome, int hpRecup, boolean isVeneno){
        super(nome);
        this.hpRecup = hpRecup;
        this.isVeneno = isVeneno;
    }
    
    @Override
    public void utilizarItem(Personagem p) {
        if (this.isVeneno) p.diminuiHP(hpRecup);
        else p.aumentaHP(hpRecup);
    }    
}
