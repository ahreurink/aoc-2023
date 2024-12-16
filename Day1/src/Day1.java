import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Day1 {
    public static void main(String[] args) throws IOException {
        System.out.println("result = " + aoc_1_2());
    }

    private static int aoc_1_1() throws IOException {
        try(Stream<String> lines = Files.lines(Path.of("Day2/input_lines"))){
            return lines.map(s ->
                "" + s.chars().filter(cs -> Character.isDigit((char) cs)).map(cs -> cs - '0').findFirst().getAsInt()
                + new StringBuilder(s).reverse().chars().filter(cs -> Character.isDigit((char) cs)).map(cs -> cs - '0').findFirst().getAsInt()
            ).map(Integer::parseInt ).reduce(Integer::sum).get();
        }
    }

    private static List<Integer> digitStringLengths = List.of(1, 3, 4, 5);

    private static int parseInt(String digit){
        return switch (digit) {
            case "1", "one" -> 1;
            case "2", "two" -> 2;
            case "3", "three" -> 3;
            case "4", "four" -> 4;
            case "5", "five" -> 5;
            case "6", "six" -> 6;
            case "7", "seven" -> 7;
            case "8", "eight" -> 8;
            case "9", "nine" -> 9;
            default -> -1;
        };
    }

    private static String getFirstDigit(String s) {
        int first = -1;
        int last = -1;
        for(int i = 0; i < s.length(); i++)
            for(int j : digitStringLengths) {
                int potentialDigit = parseInt(s.substring(i, Math.min(i + j, s.length())));
                if(potentialDigit != -1){
                    last = potentialDigit;
                    if(first == -1)
                        first = potentialDigit;
                }
            }
        return String.valueOf(first) + last;
    }

    private static int aoc_1_2() throws IOException {
        try(Stream<String> lines = Files.lines(Path.of("Day1/input_lines"))){
            return lines.map(Day1::getFirstDigit)
                    .map(Integer::parseInt)
                    .reduce(Integer::sum).get();
        }
    }
}