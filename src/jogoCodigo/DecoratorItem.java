package jogoCodigo;

public class DecoratorItem extends Item {
    protected Item item;
    
    protected DecoratorItem(Item item){
        this.item = item;
    }
}
