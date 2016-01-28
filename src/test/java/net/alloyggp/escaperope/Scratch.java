package net.alloyggp.escaperope;

public class Scratch {
    public static void main(String[] args) {
        double lastLevel = 0;
        for (int n1 = 2; n1 < 100; n1++) {
            int n2 = n1 + 1;
            double level = 8*(n2 - n1)*(log2(n2+n1))
                    / (log2(n2) - log2(n1));
            System.out.println(n1 + " to " + n2 + ": " + level);
            System.out.println("Increase of " + (level - lastLevel));
            lastLevel = level;
        }

        //2 to 3: 31.75 bits
        //3 to 4: 54.11 bits
        //4 to 5: 78.77 bits
    }

    private static double log2(int n) {
        return Math.log(n) / Math.log(2);
    }
}
