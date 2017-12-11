// Anthony Phillips, Kaodili Okwuaka
// 12/11/2017
// This program is a simple spell checker, 
// implemented via a custom hashtable class.

import java.io.File;
import java.nio.file.Files;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;

public class SpellChecker extends Application {
   // Dictionary
   private static File dictFile;
   private static HashTable dictionary;

   // File to check
   private static File sampleFile;
   private static String[] sampleWords;
   
   private static Stage window;

   @Override
   public void start(final Stage stage) {
      window = stage;
      window.setTitle("Spell Checker");
      
      // Text Area
      ScrollPane scroll = new ScrollPane();
      TextFlow textArea = new TextFlow();
      scroll.setContent(textArea);
      scroll.setPrefSize(400, 350);
      scroll.setFitToWidth(true);
      scroll.setPadding(new Insets(0, 5, 0, 5));

      // Button for loading and parsing an input file
      Button btnLoadSample  = new Button("Load Sample");
      btnLoadSample.setOnAction(new EventHandler<ActionEvent>(){
         @Override
         public void handle(ActionEvent event) {
            textArea.getChildren().clear();
            FileChooser fileChooser = new FileChooser();

            sampleFile  = fileChooser.showOpenDialog(new Stage());
            sampleWords = getWords(sampleFile).split("[\n\\s]+");

            textArea.getChildren().add(new Text(String.join(" ", sampleWords)));
         }
      });
      
      // Button for spell checking sample file
      Button btnCheckSample = new Button("Check Sample");
      btnCheckSample.setOnAction(new EventHandler<ActionEvent>(){
         @Override
         public void handle(ActionEvent event) {
            textArea.getChildren().clear();
            if (sampleWords == null)
               return;

            for (String word : sampleWords) {
               Text text = new Text(word+" ");
               // Exclude numbers and punctuation
               String modifiedWord = word.replaceAll("[^a-zA-Z]", "");

               // If the word is mispelled, make it red
               if (!modifiedWord.equals("") && !dictionary.contains(modifiedWord))
                  text.setFill(Color.RED);

               textArea.getChildren().add(text);
            }
         }
      });

      // Boxes
      HBox boxButtons = new HBox();
      boxButtons.getChildren().addAll(textArea, btnLoadSample, btnCheckSample);
      boxButtons.setAlignment(Pos.CENTER);

      VBox boxEverything = new VBox();
      boxEverything.getChildren().addAll(scroll, boxButtons);

      window.setScene(new Scene(boxEverything, 400, 400));
      window.show();
   }

   public static void main(String[] args) {
      // Initialize the dictionary hashtable
      dictFile = new File(args[0]);
      String[] dictWords = getWords(dictFile).split("\r\n");
      dictionary = new HashTable(getTableSize(dictWords.length));
      int numCollisions = 0;
      for (String dictWord : dictWords) {
         try {
            if (dictionary.insert(dictWord))
               numCollisions++;
         } catch (Exception ex) { 
            System.out.println("***Unable to insert "+dictWord);
         }
      }

      System.out.println("Number of Collisions: "+numCollisions);

      launch(args);
   }

   // Reads words from a file
   private static String getWords(File file) {
      String fileContents = "";
      try {
         fileContents = new String(Files.readAllBytes(file.toPath()));
      } catch (Exception ex){ System.exit(0);}
      // Remove punctuation and split on whitespaces
      return fileContents;
   }

   // Calculates the table size using the method proposed in the assignment
   private static int getTableSize(int numWords) {
      // Find the first prime number that occurs after the number
      // equal to three times the number of words; that will be the size
      for (int i = numWords * 3; true; i++) {
         for (int j = 2; j <= i; j++) {
            if (j == i)
               return i;

            if (i % j == 0)
               break;
         }
      }
   }
}