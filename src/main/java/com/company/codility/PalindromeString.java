package com.company.codility;

public class PalindromeString {

    private static boolean isPalRec(String str, int s, int e) {

        if (s == e)
            return true;

        if ((str.charAt(s)) != (str.charAt(e)))
            return false;

        if (s < e + 1)
            return isPalRec(str, s + 1, e - 1);

        return true;
    }

    private static String isPalindrome(String str) {
        int n = str.length();

        if (n == 0 || n == 1)
            return "Yes";

        return isPalRec(str, 0, n - 1) ? "Yes" : "No";
    }

    public static void main(String[] args) {

        String str = "abcdedcba"; //Yes
        System.out.println(isPalindrome(str));

        str = "a"; //Yes
        System.out.println(isPalindrome(str));

        str = "abccba"; //Yes
        System.out.println(isPalindrome(str));

        str = "radar"; //Yes
        System.out.println(isPalindrome(str));

        str = "malayalam"; //Yes
        System.out.println(isPalindrome(str));

        str = ""; //Yes
        System.out.println(isPalindrome(str));

        str = "abcd"; //No
        System.out.println(isPalindrome(str));
    }
}
