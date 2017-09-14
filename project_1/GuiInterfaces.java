// This is a program that displays a stylized javafx window
// Anthony Phillips
// 09/14/2017

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.*;

public class GuiInterfaces extends Application{
   private static Stage window;

   public static void main(String[] args){
      launch(args);
   }

   @Override
   public void start(final Stage stage){
      window = stage;
      window.setTitle("EECS 2500");
   
      // Buttons
      Button btnJava         = new Button("Java");
      Button btnProgramming  = new Button("Programming");
      Button btnNotEasy      = new Button("Is Not So Easy.");
      Button btnLinearStruct = new Button("Linear Data Structures !");
      
      // Boxes
      VBox boxEverything = new VBox();
      HBox boxUpper      = new HBox();
      HBox boxLeft       = new HBox();
      VBox boxRight      = new VBox();
      HBox boxLower      = new HBox();
      
      // Set the styles
      boxLeft.setStyle("-fx-background-color: #FF775A");
      boxLeft.setPrefWidth(250);
      boxLeft.setPrefHeight(250);
      boxLeft.setSpacing(15);
      boxLeft.setAlignment(Pos.CENTER);

      boxRight.setStyle("-fx-background-color: #FFFC2F");
      boxRight.setPrefWidth(250);
      boxRight.setPrefHeight(250);
      boxRight.setAlignment(Pos.CENTER);

      boxLower.setStyle("-fx-background-color: #2F51FF");
      boxLower.setPrefWidth(500);
      boxLower.setPrefHeight(50);
      boxLower.setAlignment(Pos.CENTER);
      
      // Put everything in place
      boxEverything.getChildren().addAll(boxUpper, boxLower);
      boxUpper.getChildren().addAll(boxLeft, boxRight);
      boxLeft.getChildren().addAll(btnJava, btnProgramming);
      boxRight.getChildren().addAll(btnNotEasy);
      boxLower.getChildren().add(btnLinearStruct);

      // Show the window
      window.setScene(new Scene(boxEverything));
      window.show();
   }
}