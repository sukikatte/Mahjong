package Game;

import java.util.Arrays;

public class DetermineHu {
    //Determine if is Hu
    public static boolean isHu(int[] cards) {
        if (null == cards) {
            return false;
        }
        // The number of cards must be 2 or 5 or 8 or 11 or 14
        if (cards.length != 2 && cards.length != 5 && cards.length != 8 && cards.length != 11 && cards.length != 14) {
            return false;
        }
        // Take the Jiang out of the cards
        int[] js = getJiangs(cards);
        // If no Jiang（Dui Zi），can not Hu
        if (null == js || js.length <= 0) {
            return false;
        }

        for (int j : js) {
            int[] tempCards = Arrays.copyOf(cards, cards.length);

            //Move Jiang（Dui）
            tempCards = removeOne(tempCards, j);
            tempCards = removeOne(tempCards, j);

            if (tempCards.length <= 0) {
                System.out.println("You have been Hu");
                return true;
            }
            //Reorder the remaining cards
            Arrays.sort(tempCards);


            // Remove
            tempCards = removeAllKe(tempCards);
            if (tempCards.length <= 0) {
                System.out.println("You have been Hu");
                return true;
            }

            // Remove Shun
            tempCards = removeAllShun(tempCards);
            if (tempCards.length <= 0) {
                System.out.println("You have been Hu");
                return true;
            }




        }
        return false;
    }

    //Get all Jiang
    private static int[] getJiangs(int[] cards) {
        int[] res = new int[0];
        if (null != cards && cards.length > 1) {
            for (int i = 0; i < cards.length - 1; i++) {
                if (cards[i] == cards[i + 1]) {
                    res = add(res, cards[i]);
                    i++;
                }
            }
        }
        return res;
    }

    //Remove Ke
    private static int[] removeAllKe(int[] cards) {
        for (int i = 0; i < cards.length - 2; i++) {
            if (cards[i] == cards[i + 1] && cards[i] == cards[i + 2]) {
                cards = removeOne(cards, cards[i]);
                cards = removeOne(cards, cards[i]);
                cards = removeOne(cards, cards[i]);
            }
        }
        return cards;
    }

    //Remove all Shun
    private static int[] removeAllShun(int[] cards) {
        int[] res = Arrays.copyOf(cards, cards.length);
        for (int i = 0; i < cards.length - 2; i++) {
            if (cards[i] + 1 == cards[i + 1] && cards[i + 1] + 1 == cards[i + 2]) {
                res = removeOne(res, cards[i]);
                res = removeOne(res, cards[i + 1]);
                res = removeOne(res, cards[i + 2]);
                i += 2;
            }
        }
        return res;
    }

    // Remove a card
    private static int[] removeOne(int[] cards, int card) {
        if (null == cards || cards.length <= 0) {
            return cards;
        }
        Arrays.sort(cards);
        int index = Arrays.binarySearch(cards, card);
        if (index >= 0) {
            int[] res = new int[cards.length - 1];
            int j = 0;
            for (int i = 0; i < cards.length; i++) {
                if (i != index) {
                    res[j++] = cards[i];
                }
            }
            return res;
        }
        return cards;
    }

    // Add card to cards
    private static int[] add(int[] cards, int card) {
        int[] res = new int[cards.length + 1];
        System.arraycopy(cards, 0, res, 0, cards.length);
        res[res.length - 1] = card;
        return res;
    }
}

