package uz.softex.securitystore;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NumbersToRussion {
    public static void main(String[] args) {
        Long number = new Scanner(System.in).nextLong();
        if (number == 0) {
            System.out.println("ноль");
            main(args);
        }
        if (1_000_000_000_000L <= number) {
            System.out.println(" bu milliarddan katta boldi ");
            main(args);
        }
        System.out.println(9|17);
        System.out.println(number);
        Map<String, String> numbers = new HashMap<>();
        numbers.put("1", "один");
        numbers.put("2", "два");
        numbers.put("3", "три");
        numbers.put("4", "четыре");
        numbers.put("5", "пять");
        numbers.put("6", "шесть");
        numbers.put("7", "семь");
        numbers.put("8", "восемь");
        numbers.put("9", "девять");
        numbers.put("10", "десять");
        numbers.put("50", "десят");
        numbers.put("100", "сто");
        numbers.put("200", "сти");
        numbers.put("300", "ста");
        numbers.put("1000", "тысяча");
        numbers.put("2000", "тысячи");
        numbers.put("5000", "тысяч");
        numbers.put("1000000", "миллион");
        numbers.put("2000000", "миллиона");
        numbers.put("5000000", "миллионов");
        numbers.put("5000000000", "миллиардов");
        numbers.put("1000000000", "миллиард");
        numbers.put("2000000000", "миллиарда");

        String result = "";
        String numberOf = "";

        Integer length = String.valueOf(number).length();
        for (int i = length - 1; i >= 0; i--) {
            Long element = Long.valueOf(String.valueOf(number).charAt(length - i - 1) + "");
            numberOf += element;
            Long ten = (long) Math.pow(10, i);
            if (i % 3 == 2 && element != 0) {
                if (element == 2) result += "двести ";
                else if (element == 1)
                    result += "сто ";
                else {
                    result += numbers.get(String.valueOf(element));
                    result += element >= 5 && element < 10 ? "сот " : "ста ";
                }
            } else if (i % 3 == 1 && element != 0) {
                if (element == 9) result += "девяносто ";
                else if (element == 1 && Long.valueOf(String.valueOf(number).charAt(length - i) + "") == 0)
                    result += "десять ";
                else if (element == 2 || element == 3) result += numbers.get(String.valueOf(element)) + "дцать ";
                else if (element == 4) result += "сорок ";
                else if (element != 1) {
                    result += numbers.get(String.valueOf(element)) + "десят ";
                }
            } else if (i % 3 == 0 && length - i - 2 >= 0 && Long.valueOf(String.valueOf(String.valueOf(number).charAt(length - i - 2))) == 1 && element != 0) {
                if (element == 2) result += "двенадцать ";
                if (element == 3 | element == 1)
                    result += numbers.get(String.valueOf(element)) + "надцать ";
                else if (element >= 4 && element < 10) {
                    result += numbers.get(String.valueOf(element)).substring(0, numbers.get(String.valueOf(element)).length() - 1) + "надцать ";
                }
                if (i != 0) result += numbers.get(String.valueOf(ten * 5)) + " ";
                numberOf = "";
            } else {
                if (element != 0) {
                    if (element == 2 && ten < 1_000_000 && i != 0) result += "две ";
                    else if (element == 1 && ten < 1_000_000 && i != 0) result += "одна ";
                    else {
                        result += numbers.get(String.valueOf(element)) + " ";
                        element = element >= 2 && element < 5 ? 2 : element;
                        element = element >= 5 && element < 10 ? 5 : element;
                    }
                    if (i != 0) result += numbers.get(String.valueOf(ten * element)) + " ";
                    numberOf = "";
                } else if (i != 0 && i % 3 == 0) {
                    if (!numberOf.replaceAll("0", "").equals("")) {
                        result += numbers.get(String.valueOf(ten * 5)) + " ";
                        numberOf = "";
                    }
                }
            }
        }
        result = result.replaceAll("null", "");
        System.out.println(result);
        main(args);
    }
}