package Table;

import Item.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import Player.Player;



public class Table {
    // Store eat, touch, and bar decks
    private List<List<Item>> shownTiles;
    // Store cards played but not processed
    private Map<String,List<Item>> discardedTiles;
    // The card played by the last player
    private Item lastTile;

    public void setShownTiles(List<List<Item>> shownTiles) {
        this.shownTiles = shownTiles;
    }

    public void setDiscardedTiles(Map<String,List<Item>>  discardedTiles) {
        this.discardedTiles = discardedTiles;
    }

    public List<Item> getWaitinguseTiles() {
        return waitinguseTiles;
    }

    public void setWaitinguseTiles(List<Item> waitinguseTiles) {
        this.waitinguseTiles = waitinguseTiles;
    }

    // Cards to be touched
    private  List<Item> waitinguseTiles;


    public Table() {
        this.shownTiles = new ArrayList<>();
        this.discardedTiles = new HashMap<>();
        this.lastTile = null;
    }

    // Add a new deck to the desktop
    public void addShownTiles(List<Item> tiles) {
        this.shownTiles.add(tiles);
    }

    // Set the previous card played
    public void setLastTile(Item tile) {
        this.lastTile = tile;
    }

    // Get the last card played
    public Item getLastTile() {
        return lastTile;
    }

    // Record the cards played but not dealt
    public void addDiscardedTile(String string , Item tile) {
        this.discardedTiles.get(string).add(tile);
    }

    // Get all cards played but not dealt
    public Map<String,List<Item>> getDiscardedTiles() {
        return discardedTiles;
    }

    // Gets all the decks displayed on the desktop
    public List<List<Item>> getShownTiles() {
        return shownTiles;
    }


    // Check whether the specified card can be marked
    public boolean canKong(Item tile, Player player) {
        for (List<Item> tiles : shownTiles) {
            if (tiles.contains(tile) && countTiles(tiles, tile) == 3) {
                // Make sure the deck is shown by the player and contains three of the same cards
                return true;
            }
        }
        return false;
    }

    // Auxiliary method: Count the number of cards in a particular deck
    private int countTiles(List<Item> tiles, Item targetTile) {
        int count = 0;
        for (Item tile : tiles) {
            if (tile.equals(targetTile)) {
                count++;
            }
        }
        return count;
    }
}
