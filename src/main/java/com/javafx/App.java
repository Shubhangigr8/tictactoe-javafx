package com.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private GridPane gridPane=new GridPane();
    private BorderPane borderPane=new BorderPane();
    private Label title=new Label("Tic Tac ToE");
    private Button restartButton=new Button("RESTART");
    Font font =Font.font("Calibri",FontWeight.BOLD,30);
    boolean gameOver=false;
    int activePlayer=0;
    int gameState[]={3,3,3,3,3,3,3,3,3};
    int winingPosition[][]={
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6},
    };


    private Button [] btn=new Button[9];
    @Override
    public void start(Stage stage) throws IOException {
        this.createGUI();
        this.handleEvents();
        Scene scene=new Scene(borderPane,550,650);
        stage.setScene(scene);
        stage.show();
    }

    private void createGUI() {
        title.setFont(font);
        restartButton.setFont(font);
        restartButton.setDisable(true);
        borderPane.setTop(title);
        borderPane.setBottom(restartButton);
        BorderPane.setAlignment(title,Pos.CENTER);
        BorderPane.setAlignment(restartButton, Pos.CENTER);
        borderPane.setPadding(new Insets(20,20,20,20));
        int label=0;
        for(int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                Button button=new Button();
                button.setId(label+"");
                button.setFont(font);

                button.setPrefWidth(150);
                button.setPrefHeight(150);
                gridPane.add(button,j,i);
                gridPane.setAlignment(Pos.CENTER);
                btn[label]=button;
                label++;
            }
        }
        borderPane.setCenter(gridPane);
    }
    private void handleEvents(){
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(int i=0;i<9;i++)
                {
                    gameState[i]=3;
                    btn[i].setText("");
                    btn[i].setGraphic(null);
                    btn[i].setBackground(null);
                    btn[i].setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID,CornerRadii.EMPTY, new BorderWidths(1))));
                    gameOver=false;
                    restartButton.setDisable(true);
                }
            }
        });
        for(Button b:btn)
        {
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Button curr = (Button) actionEvent.getSource();
                    String str = curr.getId();
                    int st = Integer.parseInt(str);
                    if (gameOver) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR MESSAGE");
                        alert.setContentText("Game Over");

                        alert.show();
                    } else {
                        if (gameState[st] == 3) {
                            if (activePlayer == 1) {
                                curr.setGraphic(new ImageView(new Image("file:src/main/resources/image/cross2.png")));
                                gameState[st] = activePlayer;
                                checkforwinner();
                                activePlayer = 0;
                            } else {
                                curr.setGraphic(new ImageView(new Image("file:src/main/resources/image/index.png")));
                                gameState[st] = activePlayer;
                                checkforwinner();
                                activePlayer = 1;

                            }


                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("ERROR MESSAGE");
                            alert.setContentText("Place is already occupied");
                            alert.show();
                        }
                    };
                }



            });
        }
    }

    private void checkforwinner() {
        if(!gameOver) {
            for(int wp[]:winingPosition)
            {
                if(gameState[wp[0]]==gameState[wp[1]]&&gameState[wp[2]]==gameState[wp[0]]&&gameState[wp[0]]!=3) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SUCCESS MESSAGE");
                    alert.setContentText((activePlayer == 1 ? "X " : "0 ")+" has won the game");
                    btn[wp[0]].setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY,Insets.EMPTY)));
                    btn[wp[1]].setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY,Insets.EMPTY)));
                    btn[wp[2]].setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY,Insets.EMPTY)));
                    alert.show();
                    gameOver=true;
                    restartButton.setDisable(false);
                    break;
                }
            }
            int c=0;
            for(int x:gameState)
            {
                if(x==3)
                {
                    c++;
                    break;
                }
            }
            if(c==0&&gameOver==false)
            {Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NO WINNER ... Draw");
            alert.setContentText("It is draw");
            alert.show();
            }

        }
    }


    public static void main(String[] args) {
        launch();
    }

}