public class PalindromeInteger {

    private static boolean isPalindrome(int n) {
        int divisor = 1;
        while (n / divisor >= 10)
            divisor *= 10;

        while (n != 0) {
            int leading = n / divisor;
            int trailing = n % 10;

            if (leading != trailing)
                return false;

            n = (n % divisor) / 10;

            divisor = divisor / 100;
        }
        return true;
    }

    private static String isPalindromeInt(int num) {

        if (num < 0)
            num = (-num);

        return isPalindrome(num) ? "Yes" : "No";
    }

    public static void main(String[] args) {

        int n = 1234321; // Yes
        System.out.println(isPalindromeInt(n));

        n = 0; // Yes
        System.out.println(isPalindromeInt(n));

        n = 100000; // No
        System.out.println(isPalindromeInt(n));

        n = -1245421; // Yes
        System.out.println(isPalindromeInt(n));

        n = 1001; // Yes
        System.out.println(isPalindromeInt(n));

        n = 101; // Yes
        System.out.println(isPalindromeInt(n));

        n = 12345; // No
        System.out.println(isPalindromeInt(n));
    }
}
