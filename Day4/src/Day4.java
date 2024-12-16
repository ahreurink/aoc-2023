import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {
    public static void main(String[] args) throws IOException {
        System.out.println("result = " + aoc_4_2());
    }
    private static final String FILE_PATH = "Day4/input_lines";

    private static int calculateScore(List<Set<Integer>> game){
        int score = 0;
        for(Integer obtainedNumber : game.get(1)){
            if(game.get(0).contains(obtainedNumber)){
                if(score == 0)
                    score = 1;
                else
                    score = score * 2;
            }
        }
        return score;
    }
    private static List<Set<Integer>> toNumberSets(String[] gameNumberSets){
        return Arrays.stream(gameNumberSets)
                        .map(s -> Arrays.stream(s.split(" "))
                                .filter(emptyString -> emptyString.length() > 0)
                                .map(Integer::parseInt)
                                .collect(Collectors.toSet()))
                        .collect(Collectors.toList());
    }
    private static int aoc_4_1() throws IOException {
        try(Stream<String> lines = Files.lines(Path.of(FILE_PATH))){
            return lines.map(s ->  s.split(":")[1])
                    .map(s -> s.split("\\|"))
                    .map(Day4::toNumberSets)
                    .map(Day4::calculateScore)
                    .reduce(Integer::sum).get();
        }
    }

    private static int calculateNewCopies(List<Set<Integer>> game){
        int score = 0;
        for(Integer obtainedNumber : game.get(1)){
            if(game.get(0).contains(obtainedNumber))
                score += 1;
        }
        return score;
    }
    private static int aoc_4_2() throws IOException {
        try(Stream<String> lines = Files.lines(Path.of(FILE_PATH))){
            Map<Integer, Integer> score = new HashMap<>();
            List<List<Set<Integer>>> games = lines.map(s ->  s.split(":")[1])
                    .map(s -> s.split("\\|"))
                    .map(Day4::toNumberSets)
                    .toList();

            for(int i = 1; i<=games.size(); i++){
                score.put(i, 0);
            }

            for(int i = 1; i<=games.size(); i++){
                score.put(i, score.get(i) + 1);
                for(int j = i+1; j <= i+calculateNewCopies(games.get(i-1)); j++){
                    score.put(j, score.get(j) + score.get(i));
                }
            }
            return score.values().stream().reduce(Integer::sum).get();
        }
    }
}