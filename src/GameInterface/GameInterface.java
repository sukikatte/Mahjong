package GameInterface;
import Item.*;
import Player.*;
import Table.*;
import Game.*;
import Initialization.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.geometry.Insets;


import java.util.ArrayList;
import java.util.List;


public class GameInterface  {
    //List of players
    private static List<Player> gameplayers;
    //Initialize the mahjong
    private static InitializeMahJong initializeMahjong;
    //Initialize player
    private static InitializePlayer initializePlayer;
    //Initialize game
    private static Game game;

    public void init(InitializeMahJong Mahjong,InitializePlayer Player,Table table) throws Exception {
        game = new Game();
        //Generate the list of players
        List<String> playerList = new ArrayList<>();
        playerList.add("WEST");
        playerList.add("SOUTH");
        playerList.add("EAST");
        playerList.add("NORTH");
        initializeMahjong = Mahjong;
        initializePlayer = Player;
        //Shuffle the mahjong
        initializeMahjong.InitializeMahjong();
        //Initialize players and dealing
        initializePlayer.initializePlayers(playerList);

        table.setWaitinguseTiles(initializeMahjong.getMahjongTiles());
        //get list of players
        table.getDiscardedTiles().put("WEST", new ArrayList<>());
        table.getDiscardedTiles().put("SOUTH", new ArrayList<>());
        table.getDiscardedTiles().put("EAST", new ArrayList<>());
        table.getDiscardedTiles().put("NORTH", new ArrayList<>());
        gameplayers = initializePlayer.getPlayers();
    }

    public BorderPane drawingInterface(BorderPane borderPane, Stage primaryStage, Item HunEr) {
        // Create the layout panel
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #64c4ed;");

        // Create surrounding tiles and a played tiles
        VBox west = createVerticalPlayerArea(gameplayers.get(3), 90, null);
        HBox south = createHorizontalPlayerArea(gameplayers.get(1), 0, null);
        VBox east = createVerticalPlayerArea(gameplayers.get(2), -90, null);
        HBox north = createHorizontalPlayerArea(gameplayers.get(0), 180, null); // 北方玩家

        // Add the tiles to the positions corresponding to the border layout
        borderPane.setLeft(west);
        borderPane.setBottom(south);
        borderPane.setRight(east);
        borderPane.setTop(north);

        // Determine the dealer and print the result
        String dealer = game.determineDealer();
        System.out.println(dealer);

        // Create a label to show the dealer
        Label dealerLabel = new Label(dealer);
        // Sets the text color to white
        dealerLabel.setStyle("-fx-text-fill: white;");

        // Create a tag to show the HunEr
        Label hunErLabel = new Label("HunEr：");
        // Sets the text color to white
        hunErLabel.setStyle("-fx-text-fill: white;");

        // Create an ImageView to display the picture of HunEr
        ImageView hunErImageView = new ImageView();
        Image hunErImage = new Image(getClass().getResource("/static/" + HunEr.getImgurl()).toExternalForm());

        // Set ImageView
        hunErImageView.setImage(hunErImage);
        hunErImageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgb(51,255,248), 30, 0, 0, 0);");
        hunErImageView.setFitHeight(45);
        hunErImageView.setFitWidth(30);

        // Set center picture
        StackPane stackPane = new StackPane();
        Image centerImage = new Image(getClass().getResource("/static/center.png").toExternalForm());
        ImageView centerImageView = new ImageView(centerImage);
        centerImageView.setFitWidth(300);
        centerImageView.setFitHeight(400);
        stackPane.getChildren().add(centerImageView);

        borderPane.setCenter(stackPane);

        // Create a vertically laid out container
        VBox contentBox = new VBox(10);
        contentBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(contentBox, Pos.CENTER);

        // Set the margins of the BorderPane
        Insets centerMargin = new Insets(50);
        BorderPane.setMargin(contentBox, centerMargin);

        // Add labels and pictures to the vertical layout container
        contentBox.getChildren().addAll(dealerLabel, hunErLabel, hunErImageView);
        stackPane.getChildren().add(contentBox);

        // Set the stage
        Scene scene = new Scene(borderPane, 900, 700);
        primaryStage.setTitle("MahJong");
        primaryStage.setScene(scene);
        primaryStage.show();

        return borderPane;
    }

    //Auxiliary method: player area
    private HBox createHorizontalPlayerArea(Player player, double rotationAngle, Item newitem) {
        HBox hbox = new HBox(3);
        String file = "";
        //Set the path, according to the rotation angle
        if (rotationAngle == 180) {
            file = "/static/north/";
        } else if (rotationAngle == 0) {
            file = "/static/";
        }

        //Deal with every single tile of the player
        for (Item item : player.getPlayerMahjong()) {
            String url = "";

            if (!player.getName().equals("SOUTH")) {
                url = "bj.PNG";
            } else {
                url = item.getImgurl();
            }

            //Determine the type of mahjong tile
            ImageView imageView = new ImageView(new Image(getClass().getResource(file + url).toString()));
            if (item.getType().equals("new")) {
                imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgb(51,255,248), 30, 0, 0, 0);");
                imageView.setOnMouseClicked(event -> game.handleTileClick(player, newitem));
                item.setType("old");
            } else {
                imageView.setOnMouseClicked(event -> game.handleTileClick(player, item));
            }

            imageView.setFitHeight(45);
            imageView.setFitWidth(30);
            hbox.getChildren().add(imageView);
        }

        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    //The helper method creates an east-west player tile and applies rotation
    private VBox createVerticalPlayerArea(Player player, double rotationAngle, Item newitem) {
        VBox vbox = new VBox(3);
        String file = "";

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
                imageView.setOnMouseClicked(event -> game.handleTileClick(player, newitem));
                item.setType("old");
            } else {
                imageView.setOnMouseClicked(event -> game.handleTileClick(player, item));
            }

            imageView.setFitHeight(30);
            imageView.setFitWidth(45);
            vbox.getChildren().add(imageView);
        }

        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    public void addTextToCenter(BorderPane borderPane, String hunerNameT) {
    }
}


