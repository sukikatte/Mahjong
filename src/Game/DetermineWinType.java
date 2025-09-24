package Game;

import Player.Player;

import java.util.Arrays;
import java.util.HashMap;

public class DetermineWinType {
    public static void determineWinType(int[] cards, Player player) {
        int jiangNumber = getJiangNumber(cards);

        if(jiangNumber==7){
            //DuiDuiHu or Haohua Seven Dui

            //If there is AnGang，HaoHau Seven Dui
            if(anGang(cards)){
                CountScore.addHaoHuaScore(player);
            }
            //if DuiDuiHu
            else{
                CountScore.addDuiDuiHuScore(player);
            }

        }

        //QingYiSe
        if(sameType(cards)){
            CountScore.addQingYiSeScore(player);
        }


        //YiTioaLong
        if(yiTiaoLong(cards)){
            CountScore.addYiTiaoLongScore(player);
        }

    }

    public static int getJiangNumber(int[]cards){
        Arrays.sort(cards);
        int jiangNumber = 0;
        for (int i = 0; i < cards.length-1; i+=2) {
            if (cards[i] == cards[i + 1]) {
                jiangNumber++;
            }
        }
        return jiangNumber;
    }

    public static boolean anGang(int[]cards){
        Arrays.sort(cards);

        for (int i = 0; i < cards.length - 3; i++) {
            if (cards[i] == cards[i + 1] && cards[i] == cards[i + 2] && cards[i] == cards[i + 3]) {
                return true;
            }
        }
        return false;
    }

    public static boolean sameType(int[]cards){
        //First
        //11-19 Wan
        //21-29 Tong
        //31-39 Tiao
        // Zhong Fa Bai can not be QingYiSe
        int huase=(cards[0]/10)*10;

        for (int i = 0; i <=cards.length-1; i++) {
            if (huase+1>cards[i]&&cards[i]>huase+9) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean yiTiaoLong(int[]cards){
        int count=0;
        
        int temp=1;

        for(int j=0;j<=cards.length;j++) {
            for (int i = 0; i <=cards.length - 1; i++) {
                int x = cards[i] % 10;
                if (x == temp) {
                    count++;
                    temp++;
                }
            }
        }

        if(count==9){
            return true;
        }
        return false;
    }
}

