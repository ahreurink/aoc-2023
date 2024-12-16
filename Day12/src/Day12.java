import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class Day12 {

    static final int part = 1;
    static final String FILE_PATH = "Day12/input_lines_test";

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(Path.of(FILE_PATH));

        inputLines.stream().map(s -> s.replace(",", "").replaceAll("\\d*", ""));

        System.out.println(inputLines);

        if (part == 1){
            System.out.println("result = " + 1);
        }

        if (part == 2){
            System.out.println("result = " + 1);
        }
    }
}