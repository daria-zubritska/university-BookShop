package ua.university.kma.BookShop.Service;

import ua.university.kma.BookShop.dto.model.Book;

import java.util.regex.Pattern;

public class ValidationService {

    public static final int LENGTH = 13;
    public static final int OLD_LENGTH = 10;

    public static final String USERNAME_REGEX = "^[A-Za-z0-9]+$";
    public static final String PSW_REGEX = "^.{8,20}$";

    public static boolean isBookValid(Book book) {
        
        if (book.getTitle().equals("") || book.getAuthor().equals("") || book.getIsbn().equals("")) return false;

        return isIsbnValid(book.getIsbn());
    }

    public static boolean isIsbnValid(String numberSequence) {

        if (numberSequence == null) throw new NullPointerException();
        if (!Pattern.matches("^\\d+(-?\\d+)*$", numberSequence)) return false;

        String normalizedSequence = removeHyphen(numberSequence);
        if (normalizedSequence.length() == 13) return isValidAsIsbn13(normalizedSequence);
        else if (normalizedSequence.length() == 10) return isValidAsIsbn10(normalizedSequence);
        else return false;
    }

    private static boolean isValidAsIsbn13(String number) {

        if (number == null) throw new NullPointerException();
        if (!Pattern.matches("^\\d{" + LENGTH + "}$", number)) throw new IllegalArgumentException();

        char[] digits = number.toCharArray();
        final int myDigit = computeIsbn13CheckDigit(digits);
        int checkDigit = digits[LENGTH - 1] - '0';
        return myDigit == 10 && checkDigit == 0 || myDigit == checkDigit;
    }

    private static int computeIsbn13CheckDigit(char[] digits) {

        if (digits == null) throw new NullPointerException();
        if (digits.length != LENGTH && digits.length != LENGTH - 1) throw new IllegalArgumentException();

        for (char c : digits) {
            if (c < '0' || '9' < c) throw new IllegalArgumentException();
        }

        int[] weights = {1, 3};
        int sum = 0;
        for (int i = 0; i < LENGTH - 1; ++i) {
            sum += (digits[i] - '0') * weights[i % 2];
        }
        return 10 - sum % 10;
    }

    private static boolean isValidAsIsbn10(String number) {

        if (number == null) throw new NullPointerException();
        if (!Pattern.matches("^\\d{" + OLD_LENGTH + "}$", number)) throw new IllegalArgumentException();

        char[] digits = number.toCharArray();
        final int myDigit = computeIsbn10CheckDigit(digits);
        if (myDigit == 10) return digits[9] == 'X';
        final int checkDigit = digits[9] - '0';
        return myDigit == 11 && checkDigit == 0 || myDigit == checkDigit;
    }

    private static int computeIsbn10CheckDigit(char[] digits) {

        if (digits == null) throw new NullPointerException();
        if (digits.length != OLD_LENGTH && digits.length != OLD_LENGTH - 1) throw new IllegalArgumentException();

        for (char c : digits) {
            if (c < '0' || '9' < c) throw new IllegalArgumentException();
        }

        int sum = 0;
        for (int i = 0, weight = 10; i < 9; ++i, --weight) {
            sum += (digits[i] - '0') * weight;
        }
        return 11 - sum % 11;
    }

    private static String removeHyphen(String s) {

        if (s == null) throw new NullPointerException();

        return s.replace("-", "");
    }

    public static boolean isRegFormValid(String username, String password) {

        if (!isPswValid(password)) return false;
        if (!isUsernameValid(username)) return false;

        return true;
    }

    private static boolean isUsernameValid(String username) {
        return Pattern.matches(USERNAME_REGEX, username);
    }

    private static boolean isPswValid(String psw) {
        return Pattern.matches(PSW_REGEX, psw);
    }

}
