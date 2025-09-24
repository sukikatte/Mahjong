package Initialization;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import Item.Item;
import Player.Player;

//Before the game, Initialization of the player
public class InitializePlayer {
    // Mahjong group object
    private final InitializeMahJong initializeMahjong;
    // List of players
    private List<Player> players;

    public InitializePlayer(InitializeMahJong initializeMahjong) {
        this.initializeMahjong = initializeMahjong;
        this.players = new ArrayList<>();
    }

    // Get all players
    public List<Player> getPlayers() {

        // Suppose there are 4 players and each player is randomly assigned an avatar
        List<String> avatarPaths = new ArrayList<>(Arrays.asList(
                "/static/profile/1.png",
                "/static/profile/2.png",
                "/static/profile/3.png",
                "/static/profile/4.png",
                "/static/profile/5.png",
                "/static/profile/6.png",
                "/static/profile/7.png",
                "/static/profile/8.png",
                "/static/profile/9.png"
        ));

        Collections.shuffle(avatarPaths);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setHeadImg(avatarPaths.get(i));
        }
        return players;
    }

    // Initialize the player and issue the initial cards

    public  List<Item> initializePlayers(List<String> playerNames) {
        List<Item> tiles = initializeMahjong.getMahjongTiles();

        // Each player starts with 13 cards
        int numTilesPerPlayer = 13;

        for (String name : playerNames) {
            Player player = new Player(name);

            for (int i = 0; i < numTilesPerPlayer; i++) {
                if (!tiles.isEmpty()) {
                    player.takeMahjong(tiles.remove(0));
                }
            }
            Collections.sort(player.getPlayerMahjong());



            players.add(player);
        }

        return tiles;
    }

}

