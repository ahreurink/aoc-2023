import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day2 {
    public static void main(String[] args) throws IOException {
        System.out.println("result = " + aoc_2_1());
    }

    private static boolean isPossible(String line){
        int redLimit = 12;
        int greenLimit = 13;
        int blueLimit = 14;

        for(String game : line.split(";")){
            for(String color : game.split(",")){
                if(color.contains("red")){
                    int numCubes = Integer.parseInt(color.substring(1, color.length()-4));
                    if(numCubes > redLimit)
                        return false;
                }
                if(color.contains("green")){
                    int numCubes = Integer.parseInt(color.substring(1, color.length()-6));
                    if(numCubes > greenLimit)
                        return false;
                }
                if(color.contains("blue")){
                    int numCubes = Integer.parseInt(color.substring(1, color.length()-5));
                    if(numCubes > blueLimit)
                        return false;
                }
            }
        }
        return true;
    }
    private static int aoc_2() throws IOException {
        try(Stream<String> lines = Files.lines(Path.of("Day2/input_lines"))){
             return lines.map(line -> line.split(":"))
                     .filter(game -> isPossible(game[1]))
                     .map(game -> Integer.parseInt(game[0].split(" ")[1]))
                     .reduce(Integer::sum).get();
        }
    }

    private static int minConfig(String line){
        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;

        for(String game : line.split(";")){
            for(String color : game.split(",")){
                if(color.contains("red")){
                    int numCubes = Integer.parseInt(color.substring(1, color.length()-4));
                    System.out.println(numCubes);
                    if(numCubes > maxRed)
                        maxRed = numCubes;
                }
                if(color.contains("green")){
                    int numCubes = Integer.parseInt(color.substring(1, color.length()-6));
                    if(numCubes > maxGreen)
                        maxGreen = numCubes;
                }
                if(color.contains("blue")){
                    int numCubes = Integer.parseInt(color.substring(1, color.length()-5));
                    if(numCubes > maxBlue)
                        maxBlue = numCubes;
                }
            }
        }
        return maxRed * maxGreen * maxBlue;
    }

    private static int aoc_2_1() throws IOException {
        try(Stream<String> lines = Files.lines(Path.of("Day2/input_lines_test"))){
            return lines.map(line -> line.split(":"))
                    .map(game -> minConfig(game[1]))
                    .reduce(Integer::sum).get();
        }
    }
}