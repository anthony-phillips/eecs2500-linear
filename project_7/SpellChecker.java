import java.io.File;
import java.nio.file.Files;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.*;

public class SpellChecker extends Application {
   private static Stage window;

   @Override
   public void start(final Stage stage) {
      window = stage;
      window.setTitle("Spell Checker");

      // Buttons
      Button btnLoadDict    = new Button("Load Dictionary");
      Button btnLoadSample  = new Button("Load Sample");
      Button btnCheckSample = new Button("Check Sample");

      
      window.getChildren().addAll(btnLoadDict, btnLoadSample, btnCheckSample);
      window.show();
   }

   public static void main(String[] args) {
      launch(args);

      File dictFile = new File(args[0]);
      String[] dictWords = getWords(dictFile);

      File checkFile = new File(args[1]);
      String[] checkWords = getWords(checkFile);

      HashTable dictionary = new HashTable(getTableSize(dictWords.length));

      for (String dictWord : dictWords) {
         try {
            dictionary.insert(dictWord);
         } catch (Exception ex) { 
            System.out.println("***Unable to insert "+dictWord);
         }
      }

      for (String checkWord : checkWords) {
         if (!dictionary.contains(checkWord))
            System.out.print("\u001B[31m"+checkWord+"\u001B[0m ");
         else
            System.out.print(checkWord+" ");
      }

      System.out.println("done.");
   }

   private static String[] getWords(File file) {
      String fileContents = "";
      try {
         fileContents = new String(Files.readAllBytes(file.toPath()));
      } catch (Exception ex){ System.exit(0);}
      // Remove punctuation and split on whitespaces
      return fileContents.split("[\\n\\s]+");
   }

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