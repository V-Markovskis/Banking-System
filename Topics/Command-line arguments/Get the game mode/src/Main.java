class Problem {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i+=2) {
            if (args[i].equals("mode")) {
                System.out.println(args[i+1]);
                return;
            }
        }
        System.out.println("default");
    }
}