import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day5 {
    public static void main(String[] args) throws IOException {
        System.out.println("result = " + aoc_5_2());
    }
    private static final String FILE_PATH = "Day5/input_lines";


    private static long[] parseAlamancLine(String line){
        String[] almanacLine = line.split(" ");
        return new long[]{Long.parseLong(almanacLine[0]),Long.parseLong(almanacLine[1]),Long.parseLong(almanacLine[2])};
    }

    private static long traverseMap(List<long[]> map, long n){
        for(long[] range : map){
            long lowerBound = range[1];
            long upperBound = range[1] + range[2];
            if(n >= lowerBound && n < upperBound){
                return n - range[1] + range[0];
            }
        }
        return n;
    }

    private static long aoc_5_1() throws IOException {
        List<String> almanac = Files.readAllLines(Path.of(FILE_PATH));
        List<Long> seeds = new ArrayList<>();
        List<long[]> map1 = new ArrayList<>();
        List<long[]> map2 = new ArrayList<>();
        List<long[]> map3 = new ArrayList<>();
        List<long[]> map4 = new ArrayList<>();
        List<long[]> map5 = new ArrayList<>();
        List<long[]> map6 = new ArrayList<>();
        List<long[]> map7 = new ArrayList<>();

        for(int i = 0; i < almanac.size(); i++){
            int skipNum;
            if(i == 0){
                seeds.addAll(Stream.of(
                        almanac.get(i).split(": ")[1]
                                .split(" "))
                        .map(Long::parseLong)
                        .toList());
                skipNum = 1; // skip empty line
            }
            else{
                int offset = 1;
                while(i + offset != almanac.size() && !almanac.get(i + offset).equals("")){
                    switch (almanac.get(i)) {
                        case "seed-to-soil map:" -> map1.add(parseAlamancLine(almanac.get(i + offset)));
                        case "soil-to-fertilizer map:" -> map2.add(parseAlamancLine(almanac.get(i + offset)));
                        case "fertilizer-to-water map:" -> map3.add(parseAlamancLine(almanac.get(i + offset)));
                        case "water-to-light map:" -> map4.add(parseAlamancLine(almanac.get(i + offset)));
                        case "light-to-temperature map:" -> map5.add(parseAlamancLine(almanac.get(i + offset)));
                        case "temperature-to-humidity map:" -> map6.add(parseAlamancLine(almanac.get(i + offset)));
                        case "humidity-to-location map:" -> map7.add(parseAlamancLine(almanac.get(i + offset)));
                        default -> throw new RuntimeException();
                    }
                        offset += 1;
                    }
                skipNum = switch (almanac.get(i)) {
                    case "seed-to-soil map:" -> map1.size();
                    case "soil-to-fertilizer map:" -> map2.size();
                    case "fertilizer-to-water map:" -> map3.size();
                    case "water-to-light map:" -> map4.size();
                    case "light-to-temperature map:" -> map5.size();
                    case "temperature-to-humidity map:" -> map6.size();
                    case "humidity-to-location map:" -> map7.size();
                    default -> throw new RuntimeException();
                };
                skipNum += 1;
            }
            i += skipNum;
        }

        return seeds.stream()
            .map( n -> traverseMap(map1, n))
            .map( n -> traverseMap(map2, n))
            .map( n -> traverseMap(map3, n))
            .map( n -> traverseMap(map4, n))
            .map( n -> traverseMap(map5, n))
            .map( n -> traverseMap(map6, n))
            .map( n -> traverseMap(map7, n))
            .min(Long::compareTo).get();
    }

    private static long aoc_5_2() throws IOException {
        List<String> almanac = Files.readAllLines(Path.of(FILE_PATH));
        List<long[]> map1 = new ArrayList<>();
        List<long[]> map2 = new ArrayList<>();
        List<long[]> map3 = new ArrayList<>();
        List<long[]> map4 = new ArrayList<>();
        List<long[]> map5 = new ArrayList<>();
        List<long[]> map6 = new ArrayList<>();
        List<long[]> map7 = new ArrayList<>();

        for(int i = 2; i < almanac.size(); i++){
            int offset = 1;
            while(i + offset != almanac.size() && !almanac.get(i + offset).equals("")){
                switch (almanac.get(i)) {
                    case "seed-to-soil map:" -> map1.add(parseAlamancLine(almanac.get(i + offset)));
                    case "soil-to-fertilizer map:" -> map2.add(parseAlamancLine(almanac.get(i + offset)));
                    case "fertilizer-to-water map:" -> map3.add(parseAlamancLine(almanac.get(i + offset)));
                    case "water-to-light map:" -> map4.add(parseAlamancLine(almanac.get(i + offset)));
                    case "light-to-temperature map:" -> map5.add(parseAlamancLine(almanac.get(i + offset)));
                    case "temperature-to-humidity map:" -> map6.add(parseAlamancLine(almanac.get(i + offset)));
                    case "humidity-to-location map:" -> map7.add(parseAlamancLine(almanac.get(i + offset)));
                    default -> throw new RuntimeException();
                }
                offset += 1;
            }
            i += 1 + switch (almanac.get(i)) {
                        case "seed-to-soil map:" -> map1.size();
                        case "soil-to-fertilizer map:" -> map2.size();
                        case "fertilizer-to-water map:" -> map3.size();
                        case "water-to-light map:" -> map4.size();
                        case "light-to-temperature map:" -> map5.size();
                        case "temperature-to-humidity map:" -> map6.size();
                        case "humidity-to-location map:" -> map7.size();
                        default -> throw new RuntimeException();
                    };
        }

        List<Long> seeds =
            Stream.of(
                almanac.get(0).split(": ")[1]
                    .split(" "))
            .map(Long::parseLong)
            .toList();

        return IntStream.range(0, seeds.size() / 2)
                .map(i -> i * 2)
                .peek(n -> System.out.println(n/2 + "/" + seeds.size()/2))
                .mapToObj(i -> new long[]{seeds.get(i), seeds.get(i + 1)})
                .flatMapToLong(l -> LongStream.range(l[0], l[0]+l[1]))
                .map(n -> traverseMap(map1, n))
                .map(n -> traverseMap(map2, n))
                .map(n -> traverseMap(map3, n))
                .map(n -> traverseMap(map4, n))
                .map(n -> traverseMap(map5, n))
                .map(n -> traverseMap(map6, n))
                .map(n -> traverseMap(map7, n))
                .min().getAsLong();
    }
}