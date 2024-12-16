import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


public class Day9 {
    static List<Integer> calculateDifferences(List<Integer> sequence){
        return IntStream.range(0, sequence.size() -1 )
                .map(index -> sequence.get(index + 1) - sequence.get(index)).boxed().toList();
    }

    static int predictNextValue(List<Integer> list){
        List<Integer> derivative = calculateDifferences(list);

        if(derivative.stream().allMatch(num -> num == 0))
            return list.get(list.size()-1);
        return list.get(list.size()-1) + predictNextValue(derivative);
    }

    static int predictPreviousValue(List<Integer> list){
        List<Integer> derivative = calculateDifferences(list);

        if(derivative.stream().allMatch(num -> num == 0))
            return list.get(0);
        return list.get(0) - predictPreviousValue(derivative);
    }


    static final int part = 2;
    static final String FILE_PATH = "Day9/input_lines";

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(Path.of(FILE_PATH));


        if (part == 1){
            int result = inputLines.stream()
                    .map(s -> s.split(" "))
                    .map(array -> Arrays.stream(array).map(Integer::parseInt).toList())
                    .map(Day9::predictNextValue)
                    .reduce(Integer::sum).get();
            System.out.println("result = " + result);
        }

        if (part == 2){
            int result = inputLines.stream()
                    .map(s -> s.split(" "))
                    .map(array -> Arrays.stream(array).map(Integer::parseInt).toList())
                    .map(Day9::predictPreviousValue)
                    .reduce(Integer::sum).get();
            System.out.println("result = " + result);
        }
    }
}