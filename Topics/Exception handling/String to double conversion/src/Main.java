//import java.util.Scanner;
class Converter {

    /**
     * It returns a double value or 0 if an exception occurred
     */
    public static double convertStringToDouble(String s) {
        //Scanner sc = new Scanner(System.in);
        try {
            //s = sc.nextLine();
            Double.parseDouble(s);
        } catch (Exception asd) {
            return 0;
        }
        return Double.parseDouble(s);
    }
}