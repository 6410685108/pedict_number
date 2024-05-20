import java.util.Random;


public class RandomNumber {
    static Random rand = new Random();
    private static int randomNum = rand.nextInt(100) + 1;
    public static int getNum() {
        return randomNum;
    }
    public static int newNum() {
        randomNum = rand.nextInt(100) + 1;
        return randomNum;
    }
}
