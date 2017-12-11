// Class that implements a hashtable, resolving 
// collisions through linear probing.
public class HashTable {
   private static final int DEFAULT_SIZE = 1000;
   private static final int CHUNK_SIZE = 4;

   private int size = 0;
   private String[] dict;

   private int count = 0;
   public int count() { return this.count; }

   public HashTable() { this(DEFAULT_SIZE); }

   public HashTable(int n) {
      this.size = n;
      dict = new String[size];
   }

   // Inserts a string, returning a bool indicating if a collision occurred
   public boolean insert(String s) throws Exception{
      boolean wasCollision = false;
      s = s.toUpperCase();

      // If the hashtable is full, throw an exception
      if (count == size)
         throw new Exception();

      int index = hashCode(s);

      // If something exists at the index, a collision occurred
      if (dict[index] != null)
            wasCollision = true;

      // If there's a collision, use the next available index
      while (dict[index] != null) {
         // Disallow duplicate entries
         if (dict[index].equals(s))
            return true;

         index++;
         if (index >= size-1)
            index = 0;
      }

      dict[index] = s;         
      count++;
      return wasCollision;
   }

   public boolean contains(String s) {
      // Get the index of the string
      s = s.toUpperCase();
      int index = hashCode(s);

      // If something exists at that index, check if it is the string we
      // are looking for. If it is not, start linearly probing for it.
      while (dict[index] != null) {
         if (dict[index].equals(s))
            return true;

         index++;
         if (index == size)
            index = 0;
      }

      return false; 
   }

   public int hashCode(String s) {
      // This algorithm is case insensitive
      s = s.toUpperCase();

      // Divide s into chunks of 4 chars
      int cs = CHUNK_SIZE;
      char[] chars = s.toCharArray();
      int numOfChunks = (s.length() / CHUNK_SIZE) + (s.length() % cs == 0 ? 0 : 1);
      String[] chunks = new String[numOfChunks];

      for (int i = 0; i < numOfChunks; i++) {
         String chunk = "";
         for (int j = i*cs; j < Math.min(s.length(), (i*cs)+cs); j++)
            chunk += chars[j];

         chunks[i] = chunk;
      }

      // Calculate the hash value for s
      int hashValue = 0;

      for (int i = 0; i < numOfChunks; i++) {
         char[] chunk = chunks[i].toCharArray();

         for (int k = 0, j = 3; k < chunk.length; k++, j--)
            hashValue += (chunk[k] - 64) * Math.pow(31, j);
      }

      // The index will be the hash value mod table size
      return hashValue % this.size;
   }
}