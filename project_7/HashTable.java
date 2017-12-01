public class HashTable {
    private int size = 0;

    public HashTable() {

    }

    public HashTable(int n) {
        this.size = n;
    }

    public void insert(String s) {

    }

    public boolean contains(String s) {
        return false;
    }

    public int count() {
        return 0;
    }

    // Why am I doing this
    public int hashCode(String s) {
        s = s.toUpperCase();
        char[] chars = s.toCharArray();

        int numOfChunks = s.length() / 4 + s.length() % 4 == 0 ? 0 : 1;
        String[] chunks = new String[numOfChunks];

        for (int i = 0; i < numOfChunks; i++) {
            String chunk = "";

            for (int j = i*4; j < Math.min(s.length(), j+4); j++)
                chunk += chars[j];
            
            chunks[i] = chunk;
        }

        int chunkSum = 0;

        for (int i = 0, j = numOfChunks-1; i < numOfChunks; i++, j--) {
            int chunkVal = 0;
            char[] chunk = chunks[i].toCharArray();

            for (int k = 0; k < chunks[0].length(); k++)
                chunkVal += chunk[k]-64;

            chunkSum += chunkVal * Math.pow(31, j);
        }

        return chunkSum % this.size;
    }
}