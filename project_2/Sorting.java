import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ArrayList;

public class Sorting{
   
   public static void main(String[] args) {
      if (args.length < 2) {
         printUsage();
         System.exit(1);
      }

      File inputFile   = new File(args[0]);
      File outputFile  = new File(args[1]);
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
      
      for (int i = 0; i < intArray.length - 1 ; i++) {
         for (int j = i+1 ; j > 0; j--) {
            if (intArray[j] < intArray[j-1]) {
               int intermediate = intArray[j];
               intArray[j] = intArray[j-1];
               intArray[j-1] = intermediate;
            } else 
               break;
         }
      }

      ArrayList<String> outputStrings = new ArrayList<String>();
      for (Integer integer : intArray)
         outputStrings.add(Integer.toString(integer));

      try {
         Files.write(outputFile.toPath(), outputStrings, 
                     StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
      } catch (Exception e) {
         System.out.println("Failed to create file or write to file.");
         printUsage();
      }
   }

   private static void printUsage() {
      System.out.print("Usage: ");
      System.out.println("java Sorting input_file output_file [delimiter]");
   }
}