package Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import Item.Item;
import Table.Table;

//Player's relative regulations
public class Player {
    //Player name
    private String name;
    //Mahjong tiles in the player's hand
    private List<Item> playerMahjong;
    //Tabletop class in the game
    private Table table;
    //Score
    private int score;

    private String headImg;


    //Constructor
    public Player(String name) {
        this.name = name;
        this.playerMahjong = new ArrayList<>();
        this.table = table;
        this.score = 0;
    }

    public Player(int number) {
        this.playerMahjong = new ArrayList<>();
    }

    //The player draws the mahjong
    public void takeMahjong(Item mahjong) {
        playerMahjong.add(mahjong);
    }

    //The player plays the mahjong
    public void removeMahjong(Item mahjong) {
        playerMahjong.remove(mahjong);
    }

    //get players' name
    public String getName() {
        return name;
    }

    //get players' mahjong 
    public List<Item> getPlayerMahjong() {
        return playerMahjong;
    }

    //set players' score
    public int getScore() {
        return score;
    }

    //set players' the score
    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int number) {
        this.score += number;
    }



    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
