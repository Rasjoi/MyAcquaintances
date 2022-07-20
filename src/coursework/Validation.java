package coursework;

public class Validation {
    /**
     * Проверка первой буквы на заглавную
     * @param str
     * @return заглавная ли первая буква
     */
    public static boolean isFirstCapital(String str) {
        if (str.length() < 1)
            return false;
        if (Character.isUpperCase(str.charAt(0)))
            return true;
        return false;
    }

    /**
     * Почистить строку (пока что убирает пробелы)
     * @param str
     * @return результат
     */
    public static String cleanString(String str){
        return str.trim().replaceAll(" +", " ");
    }
}
