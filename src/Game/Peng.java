package Game;

import Item.Item;
import Player.Player;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.List;
import java.util.Optional;

public class Peng {
    // Check if peng is OK
    public static boolean canPeng(Player player, Item card) {
        List<Item> hand = player.getPlayerMahjong();
        int count = 0;
        for (Item item : hand) {
            if (item.getName().equals(card.getName())) {
                count++;
            }
        }
        return count >= 2;
    }

    // Players deal with peng
    public static boolean handlePeng(Player player, Item card, List<Item> peng,List<Player> players) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Tips");
        dialog.setHeaderText(null);
        dialog.setContentText("Whether you need to touch the card:" + card.getName());
        ButtonType buttonTypePeng = new ButtonType("Peng");
        dialog.getDialogPane().getButtonTypes().setAll(buttonTypePeng);
        dialog.setOnCloseRequest(event -> {
            System.out.println("peng was cancelled.");
        });
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == buttonTypePeng) {
            System.out.println("The user clicks the 'peng' button");
            player.removeMahjong(card);
            List<Item> itemList = players.get(1).getPlayerMahjong();
            int count = 0;
            for (int i = itemList.size() - 1; i >= 0; i--) {
                if (itemList.get(i).getName().equals(card.getName())) {
                    itemList.remove(i);
                    count++;
                    if (count == 2) {
                        break;
                    }
                }
            }
            peng.add(card);
            peng.add(card);
            peng.add(card);

            return true;
        } else {
            return false;
        }
    }

    //ai handles peng
    public static boolean aiHandlePeng(Player player, Item card, List<Item> peng,List<Player> players,int index) {
            player.removeMahjong(card);
            List<Item> itemList = players.get(index).getPlayerMahjong();
            int count = 0;
            for (int i = itemList.size() - 1; i >= 0; i--) {
                if (itemList.get(i).getName().equals(card.getName())) {
                    itemList.remove(i);
                    count++;
                    if (count == 2) {
                        break;
                    }
                }
            }
            peng.add(card);
            peng.add(card);
            peng.add(card);
            return true;
    }



}
