import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int minOfMaxs = 0;
        int kMax = 0;
        int seed = a;


        for (int i = a; i <= b; i++) {
            Random random = new Random(i);
            kMax = 0;
            for (int j = 0; j < n; j++) {
                int num = random.nextInt(k);
                if (num > kMax) {
                    kMax = num;
                }
            }
            if (minOfMaxs == 0) {
                minOfMaxs = kMax;
            } else {
                if (minOfMaxs > kMax) {
                    minOfMaxs = kMax;
                    seed = i;
                }
            }
        }
        System.out.println(seed);   // -> Get the seed
        System.out.println(minOfMaxs); // -> The MINIMUM of all MAXIMUM numbers
    }
}