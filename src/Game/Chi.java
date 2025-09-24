package Game;

import Item.Item;
import Player.Player;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chi {
    public static String removeChineseCharacters(String cardName) {
        return cardName.replaceAll("[\u4e00-\u9fa5]", "");
    }

    // Check eat
    public static Map<String, List<Item>> canChi(Player player, Item card) {
        List<Item> hand = player.getPlayerMahjong();

        String regex = "(\\d+)([\\u4e00-\\u9fa5]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(card.getName());

        if (matcher.find()) {
            int number = Integer.parseInt(matcher.group(1));
            String chinesePart = matcher.group(2);
            System.out.println("Number: " + number);
            System.out.println("Chinese Part: " + chinesePart);
            String previousCardName = "";
            String nextCardName = "";
            Item hasPreviousItem = null;
            Item hasNextItem = null;
            boolean hasPreviousCard = false;
            boolean hasNextCard = false;
            //Judge the logic of eating
            if (number > 1 && number < 9) {
                String preNumber = String.valueOf(number - 1);
                String nextNumber = String.valueOf(number + 1);
                previousCardName = preNumber + chinesePart;
                nextCardName = nextNumber + chinesePart;

                for (Item item : hand) {
                    if (item.getName().equals(previousCardName)) {
                        hasPreviousItem = item;
                        hasPreviousCard = true;
                    }
                    if (item.getName().equals(nextCardName)) {
                        hasNextItem = item;
                        hasNextCard = true;
                    }
                }
            }
            Item NextItem = null;
            Item NextnextItem = null;
            boolean NextCard = false;
            boolean NextnextCard = false;
            if (number < 8) {
                String preNumber = String.valueOf(number + 1);
                String nextNumber = String.valueOf(number + 2);
                previousCardName = preNumber + chinesePart;
                nextCardName = nextNumber + chinesePart;
                for (Item item : hand) {
                    if (item.getName().equals(previousCardName)) {
                        NextItem = item;
                        NextCard = true;
                    }
                    if (item.getName().equals(nextCardName)) {
                        NextnextItem = item;
                        NextnextCard = true;
                    }
                }
            }
            boolean PreCard = false;
            boolean PrePreCard = false;
            Item PreItem = null;
            Item PrePreItem = null;
            if (number > 2) {
                String preNumber = String.valueOf(number - 1);
                String nextNumber = String.valueOf(number - 2);
                previousCardName = preNumber + chinesePart;
                nextCardName = nextNumber + chinesePart;
                for (Item item : hand) {
                    if (item.getName().equals(previousCardName)) {
                        PreItem = item;
                        PreCard = true;
                    }
                    if (item.getName().equals(nextCardName)) {
                        PrePreItem = item;
                        PrePreCard = true;
                    }
                }
            }
            List<Item> hasPreviousCarditems = new ArrayList<>();
            hasPreviousCarditems.add(hasPreviousItem);
            hasPreviousCarditems.add(hasNextItem);
            List<Item> PreCarditems = new ArrayList<>();
            PreCarditems.add(PreItem);
            PreCarditems.add(PrePreItem);
            List<Item> NextCarditems = new ArrayList<>();
            NextCarditems.add(NextItem);
            NextCarditems.add(NextnextItem);
            Map<String, List<Item>> map = new HashMap<>();
            if (PreCard && PrePreCard) {
                map.put("Pre", PreCarditems);
            }
            if (NextCard && NextnextCard) {
                map.put("Next", NextCarditems);
            }
            if (hasPreviousCard && hasNextCard) {
                map.put("Has", hasPreviousCarditems);
            }
            return map;
        } else {
            return null;
        }
    }

    //Handle player eat
    public static boolean handleChi(Player player2, Item card, Map<String, List<Item>> map, List<Item> peng,List<Player> players) {
        // Create prompt window
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Tips");
        dialog.setHeaderText(null);
        dialog.setContentText("Please choose how to eat the card:" + card.getName());

        // Create buttons dynamically based on map
        DialogPane dialogPane = dialog.getDialogPane();
        for (Map.Entry<String, List<Item>> entry : map.entrySet()) {
            ButtonType buttonType = new ButtonType(entry.getValue().get(0).getName() + "  " + entry.getValue().get(1).getName());
            dialogPane.getButtonTypes().add(buttonType);

            // Set the response of the button
            dialogPane.lookupButton(buttonType).addEventFilter(
                    javafx.event.ActionEvent.ACTION,
                    event -> {
                        System.out.println("The user chose to eat:" + entry.getKey());
                        List<Item> itemsToEat = entry.getValue();
                        System.out.println("The cards to eat are:" + itemsToEat.stream().map(Item::getName));
                        // Perform specific card eating operations
                        // assume handle the eating logic
                        player2.removeMahjong(card);
                        performChiOperation(players.get(1), itemsToEat, card,peng);
                        dialog.close();  // close dialogue box
                    }
            );
        }

        // Add cancel button
        ButtonType cancelButtonType = new ButtonType("Cancel");
        dialogPane.getButtonTypes().add(cancelButtonType);

        
        dialog.setOnCloseRequest(event -> System.out.println("The user canceled the operation."));

        // Displays a dialog box and waits for the user to respond
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() != cancelButtonType) {
            return true;
        } else {
            return false;
        }
    }

    private static void performChiOperation(Player player, List<Item> itemsToEat, Item card,List<Item> peng) {
        // Removes the corresponding card from the player's hand 
        // adds the eaten card to the player's hand
        player.getPlayerMahjong().remove(itemsToEat.get(0));
        player.getPlayerMahjong().remove(itemsToEat.get(1));
        peng.add(itemsToEat.get(0));
        peng.add(itemsToEat.get(1));
        peng.add(card);
        System.out.println("The card eating operation was performed.");
    }

    //Processing ai eat operation
    public static void aiHandleChi(Player player2, Item card, Map<String, List<Item>> map, List<Item> peng,List<Player> players,int index) {

       // eating operations
       player2.removeMahjong(card);
       //performChiOperation(players.get(index), itemsToEat, card,peng);
    }

}
