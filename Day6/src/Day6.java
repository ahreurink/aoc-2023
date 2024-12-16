import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day6 {
    public static void main(String[] args) throws IOException {
        int count = 0;

        List<Double> squares = new ArrayList<>();
        for(int i = 1; i <= Math.sqrt(10_000); i++){
            squares.add(Math.pow(i, 2));
        }

        List<Double> cards = new ArrayList<>();
        for(double i = 1; i <= 10_000 ; i++){
            cards.add(i);
        }

        boolean finished = false;
        while(! finished){
            count += 1;
            int numberOfCards = cards.size();
            cards = new ArrayList<>();

            for(double i = 1; i <= numberOfCards; i++){
                if(! squares.contains(i))
                    cards.add(i);
            }
            if(cards.size() == 1){
                finished = true;
            }
        }

        System.out.println("steps = " + count);
//        System.out.println("result = " + aoc_6_1());
    }
    private static final String FILE_PATH = "Day6/input_lines";

    private static boolean wonGame(int pressTime, int totalTime, int minimalDistance){
        int travelTime = totalTime - pressTime;
        int speed = pressTime;
        int distanceTraveled = travelTime * speed;
        return distanceTraveled > minimalDistance;
    }
    private static long aoc_6_1() throws IOException {
        List<String> table = Files.readAllLines(Path.of(FILE_PATH));
        List<Integer> times = Arrays.stream(table.get(0).split(":")[1].trim().split(" "))
                .filter(s -> s.length() != 0).map(Integer::parseInt).toList();
        List<Integer> distances = Arrays.stream(table.get(1).split(":")[1].trim().split(" "))
                .filter(s -> s.length() != 0).map(Integer::parseInt).toList();

        return IntStream.range(0, times.size())
                .mapToLong(
                    index -> IntStream.range(1, times.get(index))
                                .filter(pressTime -> wonGame(pressTime, times.get(index), distances.get(index)))
                                .count())
            .reduce((i1, i2) -> i1*i2)
            .getAsLong();
    }

    private static boolean wonSingleGame(long pressTime, long time,long distance){
        long travelTime = time - pressTime;
        long speed = pressTime;
        long distanceTraveled = travelTime * speed;
        return distanceTraveled > distance;
    }
    private static long aoc_6_2_naive() throws IOException {
        List<String> table = Files.readAllLines(Path.of(FILE_PATH));
        int time = Integer.parseInt(table.get(0).split(":")[1].replace(" ", ""));
        int distance = Integer.parseInt(table.get(1).split(":")[1].replace(" ", ""));

        return IntStream.range(1, time)
                .filter(pressTime -> wonSingleGame(pressTime, time, distance))
                .count();
    }

    private static long aoc_6_2() throws IOException {
        List<String> table = Files.readAllLines(Path.of(FILE_PATH));
        long time = Long.parseLong(table.get(0).split(":")[1].replace(" ", ""));
        long distance = Long.parseLong(table.get(1).split(":")[1].replace(" ", ""));

        return LongStream.range(1, time).map(i -> time - i)
                    .filter(pressTime -> wonSingleGame(pressTime, time, distance)).findFirst().getAsLong() -
                LongStream.range(1, time)
                    .filter(pressTime -> wonSingleGame(pressTime, time, distance)).findFirst().getAsLong() + 1;
    }
}