package Game;

import Item.Item;
import Player.Player;
import Dice.Dice;
import GameInterface.*;
import Initialization.InitializeMahJong;
import Initialization.InitializePlayer;
import Table.Table;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game extends Application {
    private Stage primaryStage;

    private GameInterface gameinterface;

    private InitializeMahJong initializeMahjong;
    private InitializePlayer initializePlayer;
    public static List<Player> players;
    private static Table table;
    private static List<Item> peng;
    private int currentPlayerIndex = 1;
    private static BorderPane borderPane;

    private GameInterface gameInterface = new GameInterface();

    public Item HunEr;

    int huner;

    public String hunerNameT="";
    public  String hunErCardText = "HunEr:";



    private Dice dice;
    public Game() {
        dice = new Dice();
    }
    public String determineDealer() {
        int sum = dice.getSum();
        Player dealer = null;
        switch(sum) {
            case 1: case 5: case 9:
                dealer = players.get(0);
                break;
            case 2: case 6: case 10:
                dealer = players.get(1);
                break;
            case 3: case 7: case 11:
                dealer = players.get(2);
                break;
            case 4: case 8: case 12:
                dealer = players.get(3);
                break;
        }
        return dealer.getName() + " is the dealer.";
    }
    public void startNewRound() {
        dice.rollDice();
        System.out.println("Dice 1: " + dice.getDice1());
        System.out.println("Dice 2: " + dice.getDice2());
        System.out.println("Sum: " + dice.getSum());
        String dealer = determineDealer();
        System.out.println(dealer);
    }




    //Grab a card from the deck
    private Item drawCard(Player player) {
        List<Item> remainingTiles = table.getWaitinguseTiles();
        //CountScore.addGangScore(player);


        //remained 14 cards
        if (remainingTiles.size()!=14) {
            Item drawnCard = remainingTiles.remove(0);
            drawnCard.setType("new");
            player.takeMahjong(drawnCard);
            return drawnCard;
        }

        boolean isLiuJu=true;
        //leaderboard is displayed
        int size = players.get(0).getPlayerMahjong().size() + players.get(1).getPlayerMahjong().size() + players.get(2).getPlayerMahjong().size() + players.get(3).getPlayerMahjong().size();
        for (List<Item> list : table.getDiscardedTiles().values()) {
            size = list.size() + size;
        }
        size = size + peng.size();
        System.out.println("List size: " + size);

        if(gameSettlementPage(isLiuJu)){
            start(primaryStage);
            return null;
        }
        return null;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //Call login interface
        LoginInterface.login(primaryStage, this::onLoginSuccess);
    }

    public void onLoginSuccess() {
        initializeMahjong = new InitializeMahJong();
        initializePlayer = new InitializePlayer(initializeMahjong);
        gameinterface = new GameInterface();

      //  gameinterface.displayHunErCardText(hunErCardText);
        peng = new ArrayList<>();
        table = new Table();
        try {
            gameinterface.init(initializeMahjong, initializePlayer, table);
        } catch (Exception e) {

        }
        //One card as Huner
        List<Item> tiles = initializeMahjong.getMahjongTiles();
        HunEr=tiles.remove(0);
        huner=HunEr.getShu();
        hunerNameT=hunErCardText+HunEr.getName();
        System.out.println(hunErCardText+HunEr.getName());


        players = initializePlayer.getPlayers();
        borderPane = gameinterface.drawingInterface(new BorderPane(), primaryStage,HunEr);
        nextTurn(true);
        gameInterface.addTextToCenter(borderPane,hunerNameT);
    }



    //Handle the next turn
    private void nextTurn(Boolean code) {
        Player currentPlayer = players.get(currentPlayerIndex);
        if (currentPlayer.getName().equals("SOUTH")) {
            Collections.sort(currentPlayer.getPlayerMahjong());
            handleHumanPlayer(currentPlayer, code);
        } else {
            handleAIPlayer(currentPlayer);
            System.out.println(currentPlayer.getName());
            Collections.sort(currentPlayer.getPlayerMahjong());
        }
    }

    //Player turn processing
    private void handleHumanPlayer(Player player, Boolean code) {
        Platform.runLater(() -> {
            System.out.println(player.getScore());
            Item drawnCard = null;
            List<Item> list = new ArrayList<>(players.get(1).getPlayerMahjong());
            //Do not touch the card if it is Peng
            if (code) {
                drawnCard = drawCard(player);
                if (drawnCard == null) {
                    return;
                }
                list.add(drawnCard);
                if (isWin(list,player)) {
                    CountScore.addZiMoScore(player);
                    System.out.println("I win, I have" + drawnCard.getName());

                    System.out.println("You score："+players.get(1).getScore());
                    //If continue
                    if(gameSettlementPage(false)){
                        start(primaryStage);
                        return;
                    }
                }
                //After Peng, It can Gang
                if (Gang.canGangpen(player, drawnCard,peng)) {
                    if (Gang.handleGengpen(player, drawnCard,peng,players)) {
                        //Gang, Add score
                        CountScore.addGangScore(player);
                        System.out.println("Player score："+player.getScore());

                        refreshUI(player, null, drawnCard);

                        nextTurn(true);
                    }
                }
                //Angang
                if (Gang.ancanGang(player, drawnCard)) {
                    if (Gang.anhandleGang(player, drawnCard,peng,players)) {
                        //Gang, Add score
                        CountScore.addGangScore(player);
                        System.out.println("Player score："+player.getScore());

                        refreshUI(player, null, drawnCard);


                        nextTurn(true);
                    }
                }
            }

            if (isWin(list,player)) {
                CountScore.addZiMoScore(player);
                System.out.println("I win, I have" + drawnCard.getName());
                System.out.println("Your score："+players.get(1).getScore());

                //If continue
                if(gameSettlementPage(false)){
                    start(primaryStage);
                    return;
                }
            }


            //Refresh UI,play and Peng Gang Chi
            refreshUI(player, drawnCard, null);
        });
    }

    //Game billing page
    //If it is true, it means to continue the game, otherwise exit directly
    private boolean gameSettlementPage(boolean isLiuJu){
        Stage stage = (Stage) borderPane.getScene().getWindow();
        //Create a warning window
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        //ranking
        Leaderboard.result(alert,players.get(1),isLiuJu);

        alert.getDialogPane().setPrefSize(400, 700);
        ButtonType buttonTypeOne = new ButtonType("Continue a game");
        ButtonType buttonTypeTwo = new ButtonType("Exit");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        // Create a warning dialog
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeOne) {
            initializeMahjong = null;
            initializePlayer = null;
            players = null;
            table = null;
            peng = null;
            currentPlayerIndex = 1;

            BorderPane borderPane = null;
            return true;
        } else {

            stage.close();
            System.exit(0);
            return false;
        }
    }


    //ai turn processing
    private void handleAIPlayer(Player player) {
        Platform.runLater(() -> {
            //ai get cards
            Item drawnCard = drawCard(player);

            refreshUI(players.get(1), drawnCard, null);

            //Get a random card to play
            Random random = new Random();
            Item cardToPlay = player.getPlayerMahjong().get(random.nextInt(player.getPlayerMahjong().size()));
            List<Item> list = new ArrayList<>(players.get(1).getPlayerMahjong());
            list.add(cardToPlay);

            //Determine if the player can win
            if (isWin(list,players.get(1))) {
                System.out.println("You win" + cardToPlay.getName());

                System.out.println("Your score："+players.get(1).getScore());

                //If continue
                if(gameSettlementPage(false)){
                    start(primaryStage);
                    return;
                }
            }

            Map<String, List<Item>> map = Chi.canChi(players.get(1), cardToPlay);
            if (player.getName().equals("WEST") && map != null && map.size() > 0) {
                if (Chi.handleChi(player, cardToPlay, map,peng,players)) {
                    currentPlayerIndex = 1;
                    nextTurn(false);
                    return;
                }
            }
            if (Gang.canGang(players.get(1), cardToPlay)) {
                if (Gang.handleGang( cardToPlay,peng,players)) {

                    //Gang, Add acore
                    CountScore.addGangScore(players.get(1));
                    System.out.println(players.get(1).getScore());


                    refreshUI(player, null, cardToPlay);

                    currentPlayerIndex = 1;
                    nextTurn(true);
                    return;
                }
            }
            if (Peng.canPeng(players.get(1), cardToPlay)) {
                if (Peng.handlePeng(player, cardToPlay,peng,players)) {
                    refreshUI(player, null, cardToPlay);

                    currentPlayerIndex = 1;
                    nextTurn(false);
                    return;
                }
            }

            handleTileClick(player, cardToPlay);
            refreshUI(players.get(1), drawnCard, null);
        });
    }



    //If the ai wins, no player's points are added
    public boolean isWin(List<Item> hand,Player player) {

        int[] a = {};
        a = convertToIntegerArray(hand);
        System.out.println(Arrays.toString(a));

        for(int i = 0; i < a.length; i++){

            if(a[i]==huner){
                if (DetermineHunErWin.determineWin(huner,a)){
                    DetermineWinType.determineWinType(a,player);

                    if(player.getName().equals("South")){
                        //Hu：+1000
                        CountScore.addHuScore(player);
                        System.out.println(players.get(1).getScore());
                    }



                    return true;
                }

            }
        }

        //If Hu，determine
        if (DetermineHu.isHu(a)) {
            DetermineWinType.determineWinType(a,player);

            if(player.getName().equals("South")){
                //Hu：+1000
                CountScore.addHuScore(player);
                System.out.println(players.get(1).getScore());
            }


            return true;
        }
        return false;

    }

    //Turn each card into a corresponding number
    public static int[] convertToIntegerArray(List<Item> tiles) {

        int[] cardArray = new int[tiles.size()];
        for (int i = 0; i < tiles.size(); i++) {
            cardArray[i] = tiles.get(i).getShu();
            //System.out.println(cardArray[i]);
        }
        Arrays.sort(cardArray);
        return cardArray;
    }

    


    /**
     * UI Methods
     */

    private void refreshUI(Player player, Item newitem, Item penitem) {
        VBox SOUTHDiscardedTiles = refreshDiscarded(player, newitem); // Get discarded cards of the Southern player

        // a container that displays the Peng card
        HBox hbox = new HBox(4);
        if (peng != null) {
            for (Item item : peng) {
                ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/static/" + item.getImgurl()))));
                imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgb(51,255,248), 30, 0, 0, 0);");
                imageView.setFitHeight(45);
                imageView.setFitWidth(30);
                hbox.getChildren().add(imageView);
            }
        }
        hbox.setAlignment(Pos.CENTER);

        // Fixes the southern player's cards and played cards at the bottom
        HBox hbox2 = new HBox(80);
        hbox2.getChildren().add(hbox);
        hbox2.getChildren().add(SOUTHDiscardedTiles);
        hbox2.setAlignment(Pos.CENTER);

        if (player.getName().equals("SOUTH")) {
            borderPane.setBottom(hbox2);
        }
    }

    // Update the played card area
    private VBox refreshDiscarded(Player player, Item newitem) {
        VBox SOUTHDiscardedTiles = new VBox();
        System.out.println("Refresh" + player.getName());
        if (player.getName().equals("NORTH")) {
            borderPane.setTop(null);
            HBox north = createHorizontalPlayerArea(player, 180, newitem);
            VBox NORTHDiscardedTiles = createDiscardedTilesHorizontalnorth(north, table.getDiscardedTiles().get(player.getName()));
            borderPane.setTop(NORTHDiscardedTiles);
        } else if (player.getName().equals("SOUTH")) {
            HBox south = createHorizontalPlayerArea(player, 0, newitem);
            SOUTHDiscardedTiles = createDiscardedTilesHorizontalsouth(south, table.getDiscardedTiles().get(player.getName()));
            borderPane.setBottom(SOUTHDiscardedTiles);
        } else if (player.getName().equals("WEST")) {
            borderPane.setLeft(null);
            VBox east = createVerticalPlayerArea(player, 90, newitem);
            HBox WESThDiscardedTiles = createDiscardedTilesHorizontalwast(east, table.getDiscardedTiles().get(player.getName()));
            borderPane.setLeft(WESThDiscardedTiles);
        } else if (player.getName().equals("EAST")) {
            borderPane.setRight(null);
            VBox west = createVerticalPlayerArea(player, -90, newitem);
            HBox EASThDiscardedTiles = createDiscardedTilesHorizontaleast(west, table.getDiscardedTiles().get(player.getName()));
            borderPane.setRight(EASThDiscardedTiles);
        }
        return SOUTHDiscardedTiles;
    }




    public void handleTileClick(Player player, Item item) {
        System.out.println(player.getName() + "play" + item.getName());
//        gameInterface.addTextToCenter(borderPane,"混儿:"+item.getName());
        player.removeMahjong(item);
        table.getDiscardedTiles().get(player.getName()).add(item);
        refreshUI(player, item, null);
        proceedToNextTurn(player, item);
    }


    /**
     * Create a horizontal deck
     * Create ContainerHBox
     * Add avatars and views, set click events
     */

    private HBox createHorizontalPlayerArea(Player player, double rotationAngle, Item newitem) {
        HBox hbox = new HBox(3);
        String file = "";

        ImageView playerAvatar = new ImageView(new Image(getClass().getResource(player.getHeadImg()).toString()));
        playerAvatar.setFitHeight(35);
        playerAvatar.setFitWidth(35);
        hbox.getChildren().add(playerAvatar);

        if (rotationAngle == 0) {
            file = "/static/";
        } else if (rotationAngle == 180) {
            file = "/static/north/";
        }
        for (Item item : player.getPlayerMahjong()) {
            String url;
            if (!player.getName().equals("SOUTH")) {
                url = "bj.PNG";
            } else {
                url = item.getImgurl();
            }

            ImageView imageView = new ImageView(new Image(getClass().getResource(file + url).toString()));
            if (item.getType().equals("new")) {
                imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgb(51,255,248), 30, 0, 0, 0);");
                imageView.setOnMouseClicked(event -> handleTileClick(player, newitem));
                item.setType("old");
            } else {
                imageView.setOnMouseClicked(event -> handleTileClick(player, item));
            }

            imageView.setFitHeight(45);
            imageView.setFitWidth(30);
            hbox.getChildren().add(imageView);
        }
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    /**
     *Set up a vertical player deck
     */
    // The helper method creates an east-west, left-right player deck and applies rotation
    private VBox createVerticalPlayerArea(Player player, double rotationAngle, Item newitem) {
        VBox vbox = new VBox(3);
        String file = "";

        ImageView playerAvatar = new ImageView(new Image(getClass().getResource(player.getHeadImg()).toString()));
        playerAvatar.setFitHeight(35);
        playerAvatar.setFitWidth(35);
        vbox.getChildren().add(playerAvatar);

        if (rotationAngle == -90) {
            file = "/static/east/";
        } else if (rotationAngle == 90) {
            file = "/static/west/";
        }

        for (Item item : player.getPlayerMahjong()) {
            String url;
            if (!player.getName().equals("SOUTH")) {
                url = "bj.PNG";
            } else {
                url = item.getImgurl();
            }

            ImageView imageView = new ImageView(new Image(getClass().getResource(file + url).toString()));
            if (item.getType().equals("new")) {
                imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgb(51,255,248), 30, 0, 0, 0);");
                imageView.setOnMouseClicked(event -> handleTileClick(player, newitem));
                item.setType("old");
            } else {
                imageView.setOnMouseClicked(event -> handleTileClick(player, item));
            }

            imageView.setFitHeight(30);
            imageView.setFitWidth(45);
            vbox.getChildren().add(imageView);
        }
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    // Create discard area
    private VBox createDiscardedTilesHorizontalsouth(HBox south, List<Item> discardedTiles) {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.TOP_CENTER);

        HBox hbox = new HBox(1);
        int count = 0;
        for (Item item : discardedTiles) {
            hbox.setAlignment(Pos.CENTER);
            ImageView imageView = new ImageView(new Image(getClass().getResource("/static/" + item.getImgurl()).toString()));
            imageView.setFitHeight(45);
            imageView.setFitWidth(30);
            hbox.getChildren().add(imageView);
            count++;
            if (count % 20 == 0) {
                vBox.getChildren().add(0, hbox);
                hbox = new HBox(1); // Change Line every 20 cards
            }
        }
        vBox.getChildren().add(0, hbox);
        vBox.getChildren().add(south); //Adds the Southern player's hand area to the bottom of the VBox
        return vBox;
    }


    private VBox createDiscardedTilesHorizontalnorth(HBox north, List<Item> discardedTiles) {
        VBox vBox = new VBox(20);
        vBox.setMaxHeight(200);
        vBox.setAlignment(Pos.TOP_CENTER);

        HBox hbox = new HBox(1); // Set the interval between cards to 10
        vBox.getChildren().add(north);
        int count = 0;
        for (Item item : discardedTiles) {
            hbox.setAlignment(Pos.CENTER);
            ImageView imageView = new ImageView(new Image(getClass().getResource("/static/north/" + item.getImgurl()).toString()));
            imageView.setFitHeight(45);
            imageView.setFitWidth(30);
            hbox.getChildren().add(imageView);
            count++;
            if (count % 20 == 0) {
                vBox.getChildren().add(hbox);
                hbox = new HBox(1);
            }
        }
        vBox.getChildren().add(hbox);
        return vBox;
    }

    private HBox createDiscardedTilesHorizontaleast(VBox east, List<Item> discardedTiles) {
        HBox hBox = new HBox(20);
        VBox vBox = new VBox(1);
        int count = 0;
        for (Item item : discardedTiles) {
            vBox.setAlignment(Pos.CENTER);
            ImageView imageView = new ImageView(new Image(getClass().getResource("/static/east/" + item.getImgurl()).toString()));
            imageView.setFitHeight(30);
            imageView.setFitWidth(45);
            vBox.getChildren().add(imageView);
            count++;
            if (count % 15 == 0) {
                hBox.getChildren().add(vBox);
                vBox = new VBox(1);// Change Line every 13 cards
            }
        }
        hBox.getChildren().add(vBox);
        hBox.getChildren().add(east);
        return hBox;
    }

    // Create a east west play area
    private HBox createDiscardedTilesHorizontalwast(VBox wast, List<Item> discardedTiles) {
        HBox hBox = new HBox(20);
        VBox vBox = new VBox(1);
        hBox.getChildren().add(wast);
        int count = 0;
        for (Item item : discardedTiles) {
            vBox.setAlignment(Pos.CENTER);
            ImageView imageView = new ImageView(new Image(getClass().getResource("/static/west/" + item.getImgurl()).toString()));
            imageView.setFitHeight(30);
            imageView.setFitWidth(45);
            vBox.getChildren().add(imageView);
            count++;
            if (count % 15 == 0) {
                hBox.getChildren().add(vBox);
                vBox = new VBox(1);
            }
        }
        hBox.getChildren().add(vBox);
        return hBox;
    }

    //Next turn method
    private void proceedToNextTurn(Player player, Item card) {
        refreshUI(player, card, null);
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        nextTurn(true);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
