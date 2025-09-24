package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Game.Game;
import Player.Player;
import Item.Item;
import Item.WordPlate;
import Item.WindPlate;

import java.util.ArrayList;
import java.util.List;

import static Game.DetermineHunErWin.determineWin;
import static Game.DetermineWinType.determineWinType;
import static org.junit.jupiter.api.Assertions.*;


public class HuTest {

    // Declare parameters for later testing
    private Game game;
    private Player player;
    private List<Item> hand;

    // Initialize
    @BeforeEach
    public void setUp() {
        game = new Game();
        player = new Player("1");
        hand = new ArrayList<>();
    }

    // Test if you can play cards without huner
    @Test
    public void testIsWinWithNoHun1() {
        hand.add(new WordPlate(15));
        hand.add(new WordPlate(16));
        hand.add(new WordPlate(17));
        hand.add(new WordPlate(25));
        hand.add(new WordPlate(26));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(28));
        hand.add(new WordPlate(29));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(11));
        hand.add(new WordPlate(11));
        assertTrue(game.isWin(hand, player), "Player should win with mixed tiles including hunEr");
    }

    @Test
    public void testIsWinWithNoHun2() {
        hand.add(new WordPlate(15));
        hand.add(new WordPlate(16));
        hand.add(new WordPlate(17));
        hand.add(new WordPlate(25));
        hand.add(new WordPlate(26));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(28));
        hand.add(new WordPlate(29));
        hand.add(new WordPlate(32));
        hand.add(new WordPlate(32));
        hand.add(new WordPlate(32));
        hand.add(new WindPlate(50));
        hand.add(new WindPlate(60));
        assertFalse(game.isWin(hand, player), "Player should not win with mixed tiles including hunEr");
    }

    @Test
    public void testIsWinWithNoHun3() { // Test if you can play cards without Huner
        hand.add(new WordPlate(15));
        hand.add(new WordPlate(16));
        hand.add(new WordPlate(18));
        hand.add(new WordPlate(25));
        hand.add(new WordPlate(26));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(28));
        hand.add(new WordPlate(29));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(11));
        hand.add(new WordPlate(11));
        assertFalse(game.isWin(hand, player), "Player should not win with mixed tiles including hunEr");
    }

    @Test
    public void testIsWinWithNoHun4() {
        hand.add(new WordPlate(15));
        hand.add(new WordPlate(16));
        hand.add(new WordPlate(17));
        hand.add(new WordPlate(25));
        hand.add(new WordPlate(26));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(28));
        hand.add(new WordPlate(29));
        hand.add(new WordPlate(32));
        hand.add(new WordPlate(32));
        hand.add(new WordPlate(32));
        hand.add(new WindPlate(50));
        hand.add(new WindPlate(50));
        assertTrue(game.isWin(hand, player), "Player should win with mixed tiles including hunEr");
    }

    // Judge whether there is a huner to Hu card
    @Test
    public void testIsWinWithHun1() { //hun for jiang
        hand.add(new WordPlate(15));
        hand.add(new WordPlate(16));
        hand.add(new WordPlate(17));
        hand.add(new WordPlate(25));
        hand.add(new WordPlate(26));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(28));
        hand.add(new WordPlate(29));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(11));
        hand.add(new WordPlate(12));
        int[] test=new int[14];
        for(int i=0;i<14;i++){
            test[i]=hand.get(i).getShu();
        }
        assertTrue(determineWin(12,test), "Player should win with valid tiles without hunEr");
    }

    @Test
    public void testIsWinWithHun2() { // //hun is used in pairs
        hand.add(new WordPlate(15));
        hand.add(new WordPlate(16));
        hand.add(new WordPlate(17));
        hand.add(new WordPlate(25));
        hand.add(new WordPlate(26));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(28));
        hand.add(new WordPlate(29));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(11));
        hand.add(new WordPlate(11));
        hand.add(new WordPlate(12));
        int[] test=new int[14];
        for(int i=0;i<14;i++){
            test[i]=hand.get(i).getShu();
        }
        assertTrue(determineWin(12,test), "Player should win with valid tiles without hunEr");
    }

    // Judge whether there is a Huner to Hu card
    @Test
    public void testIsWinWithHun3() {  //Huner is used for smoothing
        hand.add(new WordPlate(15));
        hand.add(new WordPlate(16));
        hand.add(new WordPlate(12));
        hand.add(new WordPlate(25));
        hand.add(new WordPlate(26));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(28));
        hand.add(new WordPlate(29));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(50));
        hand.add(new WordPlate(50));
        int[] test=new int[14];
        for(int i=0;i<14;i++){
            test[i]=hand.get(i).getShu();
        }
        assertTrue(determineWin(12,test), "Player should win with valid tiles without hunEr");
    }

    @Test
    public void testIsWinDui7() { //Check whether the seven pairs can be correctly detected
        hand.add(new WordPlate(15));
        hand.add(new WordPlate(15));
        hand.add(new WordPlate(25));
        hand.add(new WordPlate(25));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(28));
        hand.add(new WordPlate(28));
        hand.add(new WordPlate(29));
        hand.add(new WordPlate(29));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(11));
        hand.add(new WordPlate(11));
        int[] test=new int[14];
        for(int i=0;i<14;i++){
            test[i]=hand.get(i).getShu();
        }
        determineWinType(test, player);
        assertEquals(player.getScore(), 2000);
        System.out.println(player.getScore());
    }

    @Test
    public void testIsWinHao7() { // Check whether the HO7 pair can be correctly judged
        hand.add(new WordPlate(26));
        hand.add(new WordPlate(26));
        hand.add(new WordPlate(26));
        hand.add(new WordPlate(26));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(27));
        hand.add(new WordPlate(28));
        hand.add(new WordPlate(28));
        hand.add(new WordPlate(29));
        hand.add(new WordPlate(29));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(11));
        hand.add(new WordPlate(11));
        int[] test=new int[14];
        for(int i=0;i<14;i++){
            test[i]=hand.get(i).getShu();
        }
        determineWinType(test, player);
        assertEquals(player.getScore(), 2000);
        System.out.println(player.getScore());
    }

    @Test
    public void testIsWinQing() { // Check if you can judge all the colors correctly
        hand.add(new WordPlate(11));
        hand.add(new WordPlate(12));
        hand.add(new WordPlate(13));
        hand.add(new WordPlate(14));
        hand.add(new WordPlate(15));
        hand.add(new WordPlate(16));
        hand.add(new WordPlate(17));
        hand.add(new WordPlate(17));
        hand.add(new WordPlate(17));
        hand.add(new WordPlate(18));
        hand.add(new WordPlate(18));
        hand.add(new WordPlate(18));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(19));
        int[] test=new int[14];
        for(int i=0;i<14;i++){
            test[i]=hand.get(i).getShu();
        }
        determineWinType(test, player);
        assertEquals(player.getScore(), 2000);
        System.out.println(player.getScore());
    }

    @Test
    public void testIsWinLong() { // Check if you can correctly judge a dragon
        hand.add(new WordPlate(11));
        hand.add(new WordPlate(12));
        hand.add(new WordPlate(13));
        hand.add(new WordPlate(14));
        hand.add(new WordPlate(15));
        hand.add(new WordPlate(16));
        hand.add(new WordPlate(17));
        hand.add(new WordPlate(18));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(17));
        hand.add(new WordPlate(18));
        hand.add(new WordPlate(19));
        hand.add(new WordPlate(14));
        hand.add(new WordPlate(14));
        int[] test=new int[14];
        for(int i=0;i<14;i++){
            test[i]=hand.get(i).getShu();
        }
        determineWinType(test, player);
        assertEquals(player.getScore(), 2000);
        System.out.println(player.getScore());
    }

}
