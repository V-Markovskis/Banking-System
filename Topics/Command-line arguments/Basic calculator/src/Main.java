class Problem {
    public static void main(String[] args) {

        if (args[0].equals("+")) {
            int i = Integer.parseInt(args[1]) + Integer.parseInt(args[2]);
            System.out.println(i);
        } else if (args[0].equals("-")) {
            int j = Integer.parseInt(args[1]) - Integer.parseInt(args[2]);
            System.out.println(j);
        } else if (args[0].equals("*")) {
            int k = Integer.parseInt(args[1]) * Integer.parseInt(args[2]);
            System.out.println(k);
        } else {
            System.out.println("Unknown operator");
        }
    }
}