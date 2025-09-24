package Game;


import Player.Player;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

import java.util.Objects;

//Before the game, player needs to LoginInterface
public class LoginInterface {
    public static void login(Stage primaryStage, Runnable onSuccess) {
        primaryStage.setTitle("login");
        Image image = new Image(Objects.requireNonNull(LoginInterface.class.getResourceAsStream("/static/other/tcmjsn.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(1000);
        imageView.setFitHeight(800);
        imageView.setPreserveRatio(false);
        Label titleLabel = new Label("Welcome to jellycat Mahjong!");
        titleLabel.setFont(Font.font("Arial", FontPosture.ITALIC, 40));
        titleLabel.setTextFill(Color.GREEN);
        Button loginButton = new Button("START GAME!");
        Button mingrentangButton = new Button("Game stars");
        Button ruleButton = new Button("Rule Introduction");

        VBox titleAndInput = new VBox(5, titleLabel);
        titleAndInput.setAlignment(Pos.CENTER);
        titleAndInput.setPadding(new Insets(20));

        HBox buttonBox = new HBox(10, loginButton, mingrentangButton,ruleButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainVBox = new VBox(40, titleAndInput, buttonBox);
        mainVBox.setAlignment(Pos.CENTER);

        StackPane rootPane = new StackPane(imageView, mainVBox);

        loginButton.setOnAction(e -> {
            primaryStage.close();
            onSuccess.run();
        });

        mingrentangButton.setOnAction(e -> {
            Leaderboard.showScoreWindow();
        });

        ruleButton.setOnAction(e -> {
            ruleWindow();
        });
        Scene scene = new Scene(rootPane, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void ruleWindow() {
        Stage ruleStage = new Stage();
        ruleStage.setTitle("MahJong Rules");

        // Create a Label to display the rule
        Label ruleLabel = new Label(
                "Win conditions: \n Your cards have this form: m*ABC+n*DDD+o*EE        (m,n can be zero)\n"
                +"\nScore rules:\n 1. You win: +1000\n 2. You gang:+100\n 3.  Win for Self touch(Zimo): +1000\n"
                +"\nSpecial rules: +1000\n" +
                        "1. One Dragon: When playing cards, the 1-9 of the same suit in the hand is called a dragon\n" +
                        "2. Paired Hu: Seven pairs\n" +
                        "3. Luxury Seven Pairs: When one of the seven pairs has a hidden bar, it is called a Luxury Seven Pairs\n" +
                        "4. Uniform color: When all colors are the same, it is called uniform color\n"+"."


        );

        // Set the style of the Label
        ruleLabel.setFont(Font.font("Arial", 20));
        ruleLabel.setStyle("-fx-text-fill: white;");
        ruleLabel.setWrapText(true);

        // Create a VBox to place the Label
        VBox ruleVBox = new VBox(ruleLabel);
        ruleVBox.setAlignment(Pos.CENTER);
        ruleVBox.setPadding(new Insets(20));
        ruleVBox.setStyle("-fx-background-color:#64c4ed;");
        Scene ruleScene = new Scene(ruleVBox, 800, 400);
        ruleStage.setScene(ruleScene);

        ruleStage.show();
    }



}
