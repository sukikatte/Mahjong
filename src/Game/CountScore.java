package Game;
import Item.Item;
import Player.Player;

public class CountScore {
    //Hu：+1000
    public static void addHuScore(Player player){
        player.addScore(1000);
    }


    //Gang：+100
    public static void addGangScore(Player player){
        player.addScore(100);
    }

    //Hand patterns

    //1.ZiMo
    public static void addZiMoScore(Player player){
        player.addScore(1000);
    }

    //2.DuiDuiHu：Seven Dui
    public static void addDuiDuiHuScore(Player player){
        player.addScore(1000);
    }

    //3.HaoHua seven Dui：When there is a dark bar in the pair, it is called the HaoHua seven Dui
    public static void addHaoHuaScore(Player player){
        player.addScore(1000);
    }


    //4.QingYiSe：When all are one color
    public static void addQingYiSeScore(Player player){
        player.addScore(1000);
    }

    //5.YiTiaoLong：When Hu, The "1-9" of a suit in the hand is a dragon
    public static void addYiTiaoLongScore(Player player){
        player.addScore(1000);
    }
}

