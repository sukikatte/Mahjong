package Game;
import Player.Player;
public class DetermineHunErWin {
    public static boolean determineWin(int huner,int[] cards) {
        for(int i=0;i<cards.length;i++){
            if(cards[i]==huner){
                System.out.println("There is a Huner："+huner);
                //Wan
                for(int j=11;j<=19;j++){
                    //Replace each with a different Wan
                    cards[i]=j;
                    if(DetermineHu.isHu(cards)){
                        return true;
                    }
                }

                //Tong
                for(int j=21;j<=29;j++){
                    //Replace each with a different Tong
                    cards[i]=j;
                    if(DetermineHu.isHu(cards)){
                        return true;
                    }
                }

                //Tiao
                for(int j=31;j<=39;j++){
                    cards[i]=j;
                    if(DetermineHu.isHu(cards)){
                        return true;
                    }
                }


                //east
                cards[i]=50;
                if(DetermineHu.isHu(cards)){
                    return true;
                }

                //West
                cards[i]=60;
                if(DetermineHu.isHu(cards)){
                    return true;
                }

                //South
                cards[i]=70;
                if(DetermineHu.isHu(cards)){
                    return true;
                }



                //North
                cards[i]=80;
                if(DetermineHu.isHu(cards)){
                    return true;
                }

                //Zhong
                cards[i]=90;
                if(DetermineHu.isHu(cards)){
                    return true;
                }

                //Facai
                cards[i]=100;
                if(DetermineHu.isHu(cards)){
                    return true;
                }

                //Baiban
                cards[i]=110;
                if(DetermineHu.isHu(cards)){
                    return true;
                }



            }

        }

        return false;
    }
}
