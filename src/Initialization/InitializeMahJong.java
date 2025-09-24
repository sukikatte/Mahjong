package Initialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import Item.Item;
import Item.WindPlate;
import Item.WordPlate;

//Before the game, Initialization of Mahjong plate
public class InitializeMahJong {
    // Card collection
    private List<Item> mahjongTiles;

    // Initialize the pai
    public List<Item> InitializeMahjong() {
        this.mahjongTiles = new ArrayList<>();
        // Add pai
        initializeMahjongTiles();
        // Shuffle back
        return shuffleMahjongTiles();
    }

    // Initializes the mahjong set
    void initializeMahjongTiles() {
        // Add wind card
        for (int i = 0; i < 4; i++) { // Each card has four cards
            mahjongTiles.add(new WindPlate("东风","dong.PNG",50));
            mahjongTiles.add(new WindPlate("南风","nan.PNG",70));
            mahjongTiles.add(new WindPlate("西风","xi.PNG",60));
            mahjongTiles.add(new WindPlate("北风","bei.PNG",80));
            mahjongTiles.add(new WindPlate("红中","hong.PNG",90));
            mahjongTiles.add(new WindPlate("发财","fa.PNG",100));
            mahjongTiles.add(new WindPlate("白板","bai.PNG",110));
        }

        // Add Wan tags
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j < 4; j++) {
                mahjongTiles.add(new WordPlate(i + "万",i+"w.PNG",10+i));
            }
        }

        //// Add tiao cards, one of which is a special deal for Yiji
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j < 4; j++) {
                mahjongTiles.add(new WordPlate(i + "条",i+"t.PNG",30+i));
            }
        }

        // Add tong card
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j < 4; j++) {
                mahjongTiles.add(new WordPlate(i + "筒",i+"o.PNG",20+i));
            }
        }

        shuffleMahjongTiles();
    }

    // Shuffle method
    public  List<Item> shuffleMahjongTiles() {
        Collections.shuffle(this.mahjongTiles);
        return this.mahjongTiles;
    }

    // Get the mahjong set method
    public List<Item> getMahjongTiles() {
        return mahjongTiles;
    }
    public void setMahjongTiles(List<Item> mahjongTiles) {
        this.mahjongTiles = mahjongTiles;
    }
}
