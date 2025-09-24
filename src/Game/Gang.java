package Game;

import Item.Item;
import Player.Player;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Gang {
    // Check if gang is OK
    public static boolean canGang(Player player, Item card) {
        List<Item> hand = player.getPlayerMahjong();
        // Check if there are three cards in the hand that are the same as the card
        int count = 0;
        for (Item item : hand) {
            if (item.getName().equals(card.getName())) {
                count++;
            }
        }
        return count >= 3;
    }

    // Check if you can do an gang
    public static boolean ancanGang(Player player, Item card) {
        List<Item> hand = new ArrayList<>(player.getPlayerMahjong());
        // Check if there are four cards in the hand that are the same as the card
        int count = 0;
        if (hand.size() == 0) {
            return false;
        }
        for (Item item : hand) {
            if (item.getName().equals(card.getName())) {
                count++;
            }
        }
        return count >= 4;
    }

    // Already touched, check if you can gang
    public static boolean canGangpen(Player player, Item card,List<Item> peng) {
        List<Item> hand = new ArrayList<>(peng);
        // Check if there are three cards in the hand that are the same as the card
        int count = 0;
        if (hand.size() == 0) {
            return false;
        }
        for (Item item : hand) {
            if (item.getName().equals(card.getName())) {
                count++;
            }
        }
        return count >= 3;
    }
    // Deal with minggang
    public static boolean handleGang( Item card,List<Item> peng,List<Player> players) {
        // Create a prompt window
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Tips");
        dialog.setHeaderText(null);
        dialog.setContentText("Whether you need a bar:" + card.getName());
        ButtonType buttonTypePeng = new ButtonType("Gang");
        dialog.getDialogPane().getButtonTypes().setAll(buttonTypePeng);

        // Add a response handler
        dialog.setOnCloseRequest(event -> {
            System.out.println("Cancelled Gang");
        });
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == buttonTypePeng) {
            System.out.println("The user clicks the 'Gang' button");
            List<Item> itemList = players.get(1).getPlayerMahjong();
            int count = 0;
            for (int i = itemList.size() - 1; i >= 0; i--) {
                if (itemList.get(i).getName().equals(card.getName())) {
                    itemList.remove(i);
                    count++;
                    if (count == 3) {
                        break;
                    }
                }
            }
            peng.add(card);
            peng.add(card);
            peng.add(card);
            peng.add(card);

            return true;
        } else {
            return false;
        }
    }

    // Deal with An Gang
    public static boolean anhandleGang(Player player, Item card,List<Item> peng,List<Player> players) {
        // Create a prompt window
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Tips");
        dialog.setHeaderText(null);
        dialog.setContentText("Whether you need a bar:" + card.getName());
        ButtonType buttonTypePeng = new ButtonType("Gang");
        dialog.getDialogPane().getButtonTypes().setAll(buttonTypePeng);

        dialog.setOnCloseRequest(event -> {
            System.out.println("Cancelled Gang");
        });
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == buttonTypePeng) {
            System.out.println("The user clicks the 'Gang' button");
            List<Item> itemList = players.get(1).getPlayerMahjong();
            int count = 0;
            for (int i = itemList.size() - 1; i >= 0; i--) {
                if (itemList.get(i).getName().equals(card.getName())) {
                    itemList.remove(i);
                    count++;
                    if (count == 4) {
                        break;
                    }
                }
            }
            peng.add(card);
            peng.add(card);
            peng.add(card);
            peng.add(card);

            return true;
        } else {
            return false;
        }
    }

    //Peng over the card, and touch a, at this time can Gang
    public static boolean handleGengpen(Player player, Item card,List<Item> peng,List<Player> players) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Tips");
        dialog.setHeaderText(null);
        dialog.setContentText("Whether you need a bar:" + card.getName());
        ButtonType buttonTypePeng = new ButtonType("Gang");
        dialog.getDialogPane().getButtonTypes().setAll(buttonTypePeng);

        dialog.setOnCloseRequest(event -> {
            System.out.println("Cancelled Gang");
        });
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == buttonTypePeng) {
            System.out.println("The user clicks the 'Gang' button");
            List<Item> itemList = players.get(1).getPlayerMahjong();
            for (int i = itemList.size() - 1; i >= 0; i--) {
                if (itemList.get(i).getName().equals(card.getName())) {
                    itemList.remove(i);
                    break;
                }
            }
            peng.add(card);

            return true;
        } else {
            return false;
        }
    }
}
