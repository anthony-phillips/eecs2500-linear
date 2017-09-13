public class Classes {
    public static void main(String[] args) {
        Die[] dice = new Die[6];

        for (int i = 0; i < 6; i++) {
            dice[i] = new Die(i + 1);
        }

        for (Die die : dice) {

            die.printFace();
        }
    }

    public static class Die {
        private int value;
        public int getValue() {
            return this.value;
        }
        public void setValue(int value) {
            this.value = value;
        }

        public Die() {
            this.value = -1;
        }

        public Die(int value) {
            this.value = value;
        }

        public void roll() {
            int randomInt = (int)(Math.random() * 5) + 1;
            this.value = randomInt;
        }

        public void printFace() {
            System.out.println();
            switch (this.value) {
                case 1:
                    System.out.println("\n  *  \n");
                    break;
                case 2:
                    System.out.println("*    \n     \n    *");
                    break;
                case 3:
                    System.out.println("*    \n  *  \n    *");
                    break;
                case 4:
                    System.out.println("*   *\n     \n*   *");
                    break;
                case 5:
                    System.out.println("*   *\n  *  \n*   *");
                    break;
                case 6:
                    System.out.println("* * *\n     \n* * *");
                    break;
                default:
                    System.out.println("No value.");
            }
            System.out.println();
        }
    }
}
