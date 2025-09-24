package Game;

import Player.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//After the game, player can see the leaderboard
public class Leaderboard {

    public static void showScoreWindow() {
        Stage scoreStage = new Stage();
        scoreStage.setTitle("Game star leaderboard");

        // Create a Label to display the score information
        Label scoreLabel = new Label("\nAmy:   100\nBob:   90\nCandy:   80\nDavid:   100\nJane:   90\n");

        // Set the style of the Label
        scoreLabel.setFont(Font.font("Arial", 20));
        scoreLabel.setWrapText(true);
        scoreLabel.setStyle("-fx-text-fill: white;");
        VBox scoreVBox = new VBox(scoreLabel);
        scoreVBox.setAlignment(Pos.CENTER);
        scoreVBox.setPadding(new Insets(20));

        scoreVBox.setStyle("-fx-background-color:#64c4ed;");

        Scene scoreScene = new Scene(scoreVBox, 400, 200);
        scoreStage.setScene(scoreScene);
        scoreStage.show();
    }

    // Player scores and leaderboards are displayed at checkout
    public static void result(Alert alert, Player player, boolean isLiuJu){
        // If the flow is interrupted
        if(isLiuJu){
            alert.setTitle("No One Win :(");
        }
        // If the player wins
        else {
            alert.setTitle("You Win！ :)");
        }

        alert.setHeaderText("Leaderboard:");
        Label label = new Label("\nAmy:   100\nBob:   90\nCandy:   80\nDavid:   100\nJane:   90\nYour score:   "+player.getScore());
        label.setFont(Font.font("Arial", 20));
        //label.setStyle("-fx-text-fill: white;");
        label.setWrapText(true);

        alert.getDialogPane().setContent(label);
    }





}
