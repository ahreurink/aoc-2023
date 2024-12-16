import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day8 {
    private static String[] createMapTarget(String secondInputPart){
        List<String> parsedInput = Arrays.stream(secondInputPart.replace("(", "")
                .replace(")", "")
                .split(",")).toList();
        return new String[]{parsedInput.get(0).trim(),
                            parsedInput.get(1).trim()};
    }

    static int countSteps(String instructions, Map<String, String[]> map){
        String[] options = map.get("AAA");
        String newLocation = null;
        int count = 0;
        while (!"ZZZ".equals(newLocation)) {
            System.out.println(newLocation);
            for (char instruction : instructions.toCharArray()) {
                count += 1;
                System.out.println(count + " : " + instruction);
                if (instruction == 'L')
                    newLocation = options[0];
                else if (instruction == 'R')
                    newLocation = options[1];
                else
                    throw new RuntimeException("Input is not L or R, something is probably wrong");
                options = map.get(newLocation);
                if("ZZZ".equals(newLocation))
                    break;
            }
        }
        return count;
    }

    static boolean matchLastLetter(String s, char c){
        return s.charAt(s.length() - 1) == c;
    }

    static long countStepsParallel(String instructions, Map<String, String[]> map){
        List<String> nodes = new java.util.ArrayList<>(map.keySet().stream()
                .filter(s -> matchLastLetter(s, 'A'))
                .toList());

        long count = 0;
        while (!nodes.stream().allMatch(s -> matchLastLetter(s, 'Z'))) {
            System.out.println(nodes);
            System.out.println();
            char instruction = instructions.charAt((int) (count % instructions.length()));
            count += 1;
            for(int i=0; i < nodes.size(); i++){
                String[] options = map.get(nodes.get(i));
                if (instruction == 'L')
                    nodes.set(i, options[0]);
                else if (instruction == 'R')
                    nodes.set(i, options[1]);
                else
                    throw new RuntimeException("Input is not L or R, something is probably wrong");
            }
        }
        return count;
    }

    private static final int part = 2;
    private static final String FILE_PATH = "Day8/input_lines_test";

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(Path.of(FILE_PATH));

        String instructions = inputLines.get(0);

        Map<String, String[]> map = inputLines.stream().skip(2)
                .map(s -> s.split("="))
                .collect(Collectors.toMap(p -> p[0].trim(), p -> createMapTarget(p[1])));

        if (part == 1) {
            System.out.println("result = " + countSteps(instructions, map));
        }
        if (part == 2) {
            System.out.println("result = " +  countStepsParallel(instructions, map));
        }
    }
}