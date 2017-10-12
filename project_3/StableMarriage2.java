import java.io.File;
import java.nio.file.Files;


class StableMarriage2 {
   static Human[] men   = new Human[0];
   static Human[] women = new Human[0];

   public static void main(String[] args) {
      if (args.length < 1) {
         System.out.println("Please specify an input file.");
         System.exit(1);
      }

      String[] fileLines = getFileLines(new File(args[0]));

      parseFileLines(fileLines);

      System.out.println("Men:");
      for (Human man : men)
         System.out.println(man.name);
      
      System.out.println("Women:");
      for (Human woman : women)
         System.out.println(woman.name);

      
   }

   private static String[] getFileLines(File inputFile) {
      String inputFileContent = "";

      try {
         inputFileContent = new String(Files.readAllBytes(
                                       inputFile.toPath()));
      } catch (Exception e) {
         System.out.println("Unable to read file.");
         System.exit(2);
      }

      return inputFileContent.split("\n");
   }

   private static void parseFileLines(String[] fileLines) {
      int numCouples = 0;
      String[] menNames   = new String[0];
      String[] womenNames = new String[0];

      try {
         numCouples = Integer.parseInt(fileLines[0].trim());
         menNames   = fileLines[1].split(",");
         womenNames = fileLines[2].split(",");
      } catch (Exception ex) {
         System.out.println("Invalid input file format.");
         System.exit(1);
      }

      men   = new Human[numCouples];
      women = new Human[numCouples];

      for (int i = 0, j = 3; i < numCouples; i++, j++) {
         men[i] = new Human(menNames[i]);
         men[i].preferenceList = new PreferenceList();

         String[] preferences = fileLines[i].split(",");

         for (String preference : preferences)
            men[i].preferenceList.add(preference);
      }

      for (int i = 0, j = 3 + numCouples; j < fileLines.length; i++, j++) {
         women[i] = new Human(womenNames[i]);
         women[i].preferenceList = new PreferenceList();

         String[] preferences = fileLines[j].split(",");

         for (String preference : preferences)
            women[i].preferenceList.add(preference);
      }

   }

   public static class Human {

      public Human (String name) { this.name = name; }

      public String name;

      public PreferenceList preferenceList;

      public Human mate;
   }

   public static class PreferenceList {

      public PreferenceListNode head;

      public void add(String name) {
         PreferenceListNode newNode = new PreferenceListNode(name);

         newNode.link = head;

         this.head = newNode;
      }
   }

   public static class PreferenceListNode {
      public String name;

      public PreferenceListNode(String name) { this.name = name; }

      public PreferenceListNode link;
   }   
}