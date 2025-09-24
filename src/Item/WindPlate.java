package Item;

//WindPlate entity class
public class WindPlate extends Item {

    public WindPlate(String name,String imgurl,int shu) {
        super("WindPlate", name, imgurl);
        this.shu = shu;
    }

    public WindPlate(int shu){
        super(shu);
    }
}
