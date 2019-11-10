package pl.bolo.memo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    private int picturesNumber = 8, maxPicture = 36; // count of pictures in resources/images/ without icon.png
    private Button lastButton = new Button();
    private Button prevButton = new Button();
    private static boolean preferVertical = false;
    private int inscrutable, movesNumber;
    private Scene sceneMenu, sceneOption;
    private byte counter;

    public static void main(String[] args) {
        launch(args);
    }

    /*
     * - start included two Scene: menu and option
     * - menu scene started together with program
     */
    @Override
    public void start(Stage stage) {

        int sizeButton[] = {300, 80};
        int sizeText[] = {10,40};
        int sizeSpacing = 20;
        int sizeFont = 20;
        int sizeFrame = 50;

        // Main menu
        Text textWelcome = new Text(sizeText[0], sizeText[1], "Welcome to Memo game");
        textWelcome.setFont(new Font(sizeFont));

        Button buttonPlay = new Button("PLAY");
        buttonPlay.setFont(new Font(sizeFont));
        buttonPlay.setPrefSize(sizeButton[0], sizeButton[1]);
        buttonPlay.setOnAction(e -> {game(); stage.close();});

        Button buttonOptions = new Button("Options");
        buttonOptions.setFont(new Font(sizeFont));
        buttonOptions.setPrefSize(sizeButton[0], sizeButton[1]);
        buttonOptions.setOnAction(e ->stage.setScene(sceneOption));

        Button buttonExit = new Button("Exit Game");
        buttonExit.setFont(new Font(sizeFont));
        buttonExit.setPrefSize(sizeButton[0], sizeButton[1]);
        buttonExit.setOnAction(e ->stage.close());

        VBox layoutMenu = new VBox(sizeSpacing);
        layoutMenu.setAlignment(Pos.CENTER);
        layoutMenu.getChildren().addAll(textWelcome, buttonPlay, buttonOptions, buttonExit);
        sceneMenu = new Scene(layoutMenu,sizeButton[0]+sizeFrame,sizeText[1]+sizeButton[1]*3+sizeSpacing*3+sizeFrame);

        // Options menu
        Text textOptions = new Text(sizeText[0], sizeText[1], "Options");
        textOptions.setFont(new Font(sizeFont));

        Text textPictureNumber = new Text(sizeText[0], sizeText[1], "Choice count of picture in the game.\nEvery picture will be display two times");
        ChoiceBox<String> choicePictureNumber = new ChoiceBox<>();
        choicePictureNumber.setPrefSize(35,35);
        choicePictureNumber.setStyle("-fx-font: 15px \"Serif\"; -fx-alignment: -center");
        for(int i=2;i<=maxPicture;i++){
            choicePictureNumber.getItems().add(String.valueOf(i));
        }
        choicePictureNumber.setValue(String.valueOf(picturesNumber));
        choicePictureNumber.setOnAction(e -> picturesNumber = Integer.valueOf(choicePictureNumber.getValue()));
        HBox layoutPictureNumber = new HBox(8);
        layoutPictureNumber.setAlignment(Pos.CENTER);
        layoutPictureNumber.getChildren().addAll(choicePictureNumber, textPictureNumber);

        CheckBox boxPreferVertical = new CheckBox("Prefer vertical placement of pictures");
        boxPreferVertical.setSelected(false);
        boxPreferVertical.setAlignment(Pos.CENTER);
        boxPreferVertical.setOnAction(e -> preferVertical=boxPreferVertical.isSelected());

        Button buttonBack = new Button("Back to menu");
        buttonBack.setFont(new Font(sizeFont));
        buttonBack.setPrefSize(sizeButton[0], sizeButton[1]);
        buttonBack.setOnAction(e ->stage.setScene(sceneMenu));

        VBox layoutOptions = new VBox(sizeSpacing);
        layoutOptions.setAlignment(Pos.CENTER);
        layoutOptions.getChildren().addAll(textOptions, layoutPictureNumber, boxPreferVertical, buttonBack);
        sceneOption = new Scene(layoutOptions, sizeButton[0]+sizeFrame, sizeText[1]+sizeButton[1]*3+sizeSpacing*3+sizeFrame);

        // Stage
        stage.setScene(sceneMenu);
        stage.setTitle("Memo game");
        stage.getIcons().add(new Image(getClass().getResourceAsStream( "/images/icon.png")));
        stage.setResizable(false);
        stage.show();
    }

    /*
     * - This function is responsible for the main entertainment
     */
    private void game(){
        lastButton.setId(null);
        prevButton.setId(null);
        Stage stage = new Stage();
        Scene sceneGame;
        List<Button> buttonList = new ArrayList<>();
        inscrutable = picturesNumber;
        counter = 0;
        movesNumber = 0;
        int[] pictureSize = {120, 120};
        int sizeSpacing[] = {8, 4};
        int sizeText[] = {100,25};
        int row = rectangleRowSize(picturesNumber*2);
        int column = (picturesNumber*2)/(row);
        Text textMovesNumber = new Text(sizeText[0], sizeText[1], String.valueOf(0));
        textMovesNumber.setFont(new Font(15));
        ArrayList indexList = getRandomNumberOfPictureList(picturesNumber,maxPicture);
        VBox layoutVertical = new VBox(sizeSpacing[1]);
        for (int i=0; i<column; i++) {
            HBox layoutHorizontal = new HBox(sizeSpacing[0]);
            for(int j=0; j<row; j++) {
                Button button = new Button();
                button.setMinSize(pictureSize[0], pictureSize[1]);
                button.setId(String.valueOf(indexList.get(i * (row) + j)));
                buttonList.add(button);
                button.setOnAction(event -> {
                    Button clickedButton = (Button) event.getSource();
                    Image image = new Image(getClass().getResourceAsStream("/images/" + clickedButton.getId() + ".png"));
                    clickedButton.setGraphic(new ImageView(image));
                    if (counter == 0) {
                        counter = 1;
                        if ((clickedButton != prevButton) && (prevButton.getId() != null) && !buttonList.get(buttonList.indexOf(prevButton)).isDisable())
                            buttonList.get(buttonList.indexOf(prevButton)).setGraphic(null);
                        if ((clickedButton != lastButton) && (lastButton.getId() != null) && !buttonList.get(buttonList.indexOf(lastButton)).isDisable())
                            buttonList.get(buttonList.indexOf(lastButton)).setGraphic(null);
                        lastButton = clickedButton;
                    } else if (counter == 1) {
                        counter = 0;
                        movesNumber++;
                        textMovesNumber.setText(String.valueOf(movesNumber));
                        if ((clickedButton.getId().equals(lastButton.getId())) && (lastButton != clickedButton)) {
                            buttonList.get(buttonList.indexOf(clickedButton)).setDisable(true);
                            buttonList.get(buttonList.indexOf(lastButton)).setDisable(true);
                            inscrutable--;
                            buttonList.get(buttonList.indexOf(lastButton)).isDisable();
                        } else {
                            prevButton = lastButton;
                            lastButton = clickedButton;
                        }
                    }
                    if (inscrutable == 0) {
                        stage.close();
                        String[] buttons = {"Play again", "Back to menu", "Exit game"};
                        int returnValue = JOptionPane.showOptionDialog(
                                null,
                                "\"Congratulations!!! \n Your number of moves is: " + movesNumber,
                                "You win", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, buttons, buttons[2]);
                        if (returnValue == 0)
                            game();
                        if (returnValue==1)
                            start(new Stage());
                    }
                });
                layoutHorizontal.getChildren().add(button);
            }
            layoutVertical.getChildren().add(layoutHorizontal);
        }
        layoutVertical.getChildren().add(textMovesNumber);
        sceneGame = new Scene(layoutVertical);
        stage.setScene(sceneGame);
        stage.setTitle("Memo");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
        stage.setMinWidth((pictureSize[0]+16)*row+(sizeSpacing[1]-12)*(row-1));
        stage.setMinHeight((pictureSize[1])*column+(sizeSpacing[1])*(column)+sizeText[1]+34);
        stage.setResizable(false);
        stage.show();
    }

    /*
     * - returned list is double bigger like count of pictures because everyone picture have to appear twice
     * - pictures is randomly choice form 1 to  maxNumberOfPictures
     */
    private static ArrayList getRandomNumberOfPictureList(int numberOfPictures, int maxNumberOfPictures) {

        ArrayList<Integer> allIndexList = new ArrayList<>();
        for (int i = 0; i < maxNumberOfPictures+1; i++) {
            allIndexList.add(i);
        }
        Random generator = new Random();
        int index;
        ArrayList<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < numberOfPictures; i++) {
            index = generator.nextInt(allIndexList.size());
            indexList.add(generator.nextInt(indexList.size()+1), allIndexList.get(index));
            indexList.add(generator.nextInt(indexList.size()+1), allIndexList.get(index));
            allIndexList.remove(index);
        }
        return indexList;
    }

    /*
     * - Algorithm return count of line to even distribution of lines
     */
    private static int rectangleRowSize(int n) {
        if(n<2)
            throw new IllegalArgumentException("Can't get row becouse number of pictures is < 2");

        ArrayList<ArrayList<Integer>> column = new ArrayList<>();
        for (int i=2; i<=(int)Math.sqrt(n); i++) {
            ArrayList<Integer> line = new ArrayList<>();
            for (int j=i; j<=(n/i); j++) {
                if ((((i*j)%2)) == 0)
                    line.add(i*j);
            }
            column.add(line);
        }
        for (int i = column.size()-1; i >=0 ; i--) {
            if (-1 < column.get(i).indexOf(n)){
                if(preferVertical)
                    return i+2;
                else
                    return n/(i+2);
            }

        }
        return -1;
    }
}