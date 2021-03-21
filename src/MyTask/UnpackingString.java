package MyTask;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class UnpackingString {

    public static String strToArr(char[] input) {                   //  метод, преобразующийи строку в новый массив символов

        String returnStr = "";
        int firstIndexBracket = 0;                                  //  позиция открывающей скобки
        int attitude = 0;                                           //  коэффициент повторений символов внутри скобок
        int count = 0;                                              //  счётчик скобок (скобки в скобках)

        for (int i = 0; i < input.length; i++) {

            if (Character.isLetter(input[i]) && count == 0)         //  если символ - буква вне скобок ---->
                returnStr += input[i];                              //  добавить к возвращаемой строке

            else if (Character.isDigit(input[i]) && count == 0)             //  если символ - число вне скобок ---->
                attitude = Integer.parseInt(String.valueOf(input[i]));      //  присвоить значение переменной

            else if (input[i] == '[') {                                     //  если символ - открывающая скобка ---->
                count++;                                                    //  изменяем счётчик
                if (count == 1)                                             //  и если внешняя ---->
                    firstIndexBracket = i;                                  //  фиксируем позицию
            }


            else if (input[i] == ']') {                     //  если символ - закрывающая скобка ---->
                count--;                                    //  изменяем счётчик
                if (count == 0) {                           //  и если внешняя ---->
                    returnStr += strToArr(Arrays.copyOfRange(input, firstIndexBracket + 1, i)).repeat(attitude);    // фиксируем позицию
                    firstIndexBracket = 0;
                    attitude = 0;
                }
            }
        }
        return returnStr;
    }

    public static boolean isValid(String input) {               //  метод, проверяющий строку на валидность
        int count = 0;
        for (int i = 0; i < input.length(); i++) {

            if (Character.isDigit(input.toCharArray()[i]))      //  является ли число только счётчиком
                if (input.toCharArray()[i + 1] != '[') {
                    return false;
                }

            else if (input.toCharArray()[i] == '[') {
                count++;
            }

            else if (input.toCharArray()[i] == ']') {           //  наличие закрывающей скобки перед открывающей
                count--;
                if (count < 0) {
                    return false;
                }
            }
        }

        if (count != 0) {                                       //  наличие незакрытых скобок
            return false;
        }
        return (Pattern.matches("[a-zA-Z\\[\\]\\d]+", input));        //   прогоняем введённую строку через шаблон (regex)
    }

    public static void main(String[] args) {                                //   старт программы
        String input;
        Scanner scanner = new Scanner(System.in);                           //   пользовательский ввод строки
        input = scanner.nextLine();
        if (isValid(input))                                                 //   проверяем строку на валидность
            System.out.println(strToArr(input.toCharArray()));              //   если валидна - преобразуем её в новый массив символов
        else
            System.out.println("Invalid String!");                          //   иначе - строка не валидна
    }
}