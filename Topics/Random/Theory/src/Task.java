import java.util.Random;

public class Task {
  public static void main(String[] args) {
    Random random = new Random();
   String cardNumber = "400000" + (random.nextInt(999_999_999 - 100_000_000 + 1) + 100_000_000) + "" + 0;
    System.out.println(cardNumber);
  }
}
