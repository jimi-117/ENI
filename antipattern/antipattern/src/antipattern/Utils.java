package antipattern;


import java.text.DecimalFormat;

public class Utils {
    private static DecimalFormat df = new DecimalFormat("#0.00");

    public static String formatPrice(double p) {
        return df.format(p) + " €";
    }

    public static boolean looksLikeEmail(String s) {
        if (s == null) return false;
        if (!s.contains("@")) return false;
        if (s.length() < 5) return false;
        return true;
    }

    public static boolean looksLikePhone(String s) {
        if (s == null) return false;
        if (s.startsWith("+")) return true;
        if (s.length() >= 9) return true;
        return false;
    }
}
