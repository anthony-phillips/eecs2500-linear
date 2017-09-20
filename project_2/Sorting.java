import java.io.File;
import java.nio.file.Files;
import java.util.Timer;

public class Sorting{
   
   public static void main(String[] args) {
      if (args.length < 1) {
         printUsage();
         System.exit(1);
      }

      File inputFile   = new File(args[0]);
      String delimiter = "\n";

      if (args.length > 2) {
         delimiter = args[2];
      }

      String inputFileContent = "";
      try {
         inputFileContent = new String(Files.readAllBytes(
                                              inputFile.toPath()));
      } catch (Exception e) {
         System.out.println("Unable to read file.");
         System.exit(2);
      }

      String[] inputStrings = inputFileContent.split(delimiter);
      int[] intArray = new int[inputStrings.length];

      try {
         for (int i = 0; i < inputStrings.length; i++)
            intArray[i] = Integer.parseInt(inputStrings[i].trim());
      } catch (Exception e) {
         System.out.println(e.getMessage());
         System.exit(3);
      }
      
      int comparisons = 0;
      long startTime = System.nanoTime();
      for (int i = 0; i < intArray.length - 1 ; i++) {
         for (int j = i+1 ; j > 0; j--) {
            comparisons++;
            if (intArray[j] < intArray[j-1]) {
               int intermediate = intArray[j];
               intArray[j] = intArray[j-1];
               intArray[j-1] = intermediate;
            } else 
               break;
         }
      }
      long endTime = System.nanoTime();

      long duration = endTime - startTime;

      System.out.println("Duration: " + duration + " ns");
      System.out.println("Comparisons: " + comparisons);

      for (int i = 0; i < 30; i++) {
            System.out.println((i+1) + ": " + intArray[i]);
      }
   }

   private static void printUsage() {
      System.out.print("Usage: ");
      System.out.println("java Sorting input_file [delimiter]");
   }
}