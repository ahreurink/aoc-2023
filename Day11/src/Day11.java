import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class Day11 {
    static List<Character> stringToList(String s){
        List<Character> result = new ArrayList<>();
        for(char c : s.toCharArray()){
            result.add(c);
        }
        return result;
    }

    static List<Character> emptyRow(int size){
        List<Character> result = new ArrayList<>();
        IntStream.range(0, size).forEach(i -> result.add('.'));
        return result;
    }

    static int width;
    static int height;

    static List<Integer> findEmptyRows(List<List<Character>> sky){
        List<Integer> insertionRowIndex = new ArrayList<>();
        for(int index = 0; index < height; index++){
            List<Character> row = sky.get(index);
            if(!row.contains('#'))
                insertionRowIndex.add(index);
        }
        return insertionRowIndex;
    }

    static List<Integer> findEmptyColumns(List<List<Character>> sky){
        List<Integer> insertionColumnIndex = new ArrayList<>();
        for (int j = 0; j < width; j++) {
            final int column = j;
            if(sky.stream().noneMatch(row -> row.get(column) == '#')){
                insertionColumnIndex.add(column);
            }
        }
        return insertionColumnIndex;
    }

    static void insertAtEmptyRows(List<List<Character>> sky, List<Integer> insertionPoints){
        for(int i = insertionPoints.size() - 1; i >= 0; i--)
            sky.add(insertionPoints.get(i), emptyRow(width));
    }

    static void insertAtEmptyColumns(List<List<Character>> sky, List<Integer> insertionPoints) {
        for (int j = insertionPoints.size() - 1; j >= 0; j--) {
            final int col = insertionPoints.get(j);
            sky.forEach(s -> s.add(col, '.'));
        }
    }

    static void insertAtEmptyRows10x(List<List<Character>> sky, List<Integer> insertionPoints){
        for(int i = insertionPoints.size() - 1; i >= 0; i--)
            for(int x = 0; x < 1_000_000; x++)
                sky.add(insertionPoints.get(i), emptyRow(width));
    }

    static void insertAtEmptyColumns10x(List<List<Character>> sky, List<Integer> insertionPoints) {
        for (int j = insertionPoints.size() - 1; j >= 0; j--) {
            final int col = insertionPoints.get(j);
            for(int i = 0; i < 1_000_000; i++)
                sky.forEach(s -> s.add(col, '.'));
        }
    }

    static int countGalaxies(List<List<Character>> sky){
        List<int[]> galaxies = new ArrayList<>();
        for(int i = 0; i < sky.size(); i++)
            for(int j = 0; j < sky.get(0).size() ; j++)
                if(sky.get(i).get(j) == '#')
                    galaxies.add(new int[]{i,j});

        int sum = 0;
        for(int i = 0; i < galaxies.size(); i++){
            for(int j = 0; j < galaxies.size(); j++){
                if(i != j){
                    int[] galaxy1 = galaxies.get(i);
                    int[] galaxy2 = galaxies.get(j);
                    int distance = (Math.abs(galaxy1[0] - galaxy2[0]) + Math.abs(galaxy1[1] - galaxy2[1]));
                    sum += distance;
                }
            }
        }
        return sum/2;
    }

    static boolean isInBetween(int testNum, int num1, int num2){
        if(testNum < num1 && testNum > num2 || testNum > num1 && testNum < num2)
            return true;
        return false;
    }

    static long countGalaxies10x(List<List<Character>> sky, List<Integer> emptyRows, List<Integer> emptyColumns){
        List<int[]> galaxies = new ArrayList<>();
        for(int i = 0; i < sky.size(); i++)
            for(int j = 0; j < sky.get(0).size() ; j++)
                if(sky.get(i).get(j) == '#')
                    galaxies.add(new int[]{i,j});

        long sum = 0;
        for(int i = 0; i < galaxies.size(); i++){
            for(int j = 0; j < galaxies.size(); j++){
                if(i != j){
                    int[] galaxy1 = galaxies.get(i);
                    int[] galaxy2 = galaxies.get(j);
                    int extraRows = (int) emptyRows.stream().filter(n -> isInBetween(n, galaxy1[0], galaxy2[0])).count();
                    int extraCols = (int) emptyColumns.stream().filter(n -> isInBetween(n, galaxy1[1], galaxy2[1])).count();
                    sum += (Math.abs(galaxy1[0] - galaxy2[0]) + extraRows * (1_000_000 - 1) +
                            Math.abs(galaxy1[1] - galaxy2[1])) + extraCols * (1_000_000 - 1)  ;
                }
            }
        }
        return sum/2;
    }

    static final int part = 2;
    static final String FILE_PATH = "Day11/input_lines";

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(Path.of(FILE_PATH));
        width = inputLines.get(0).length();
        height = inputLines.size();
        List<List<Character>> sky = new ArrayList<>(inputLines.stream().map(Day11::stringToList).toList());

        if (part == 1){
            List<Integer> insertionPoints = findEmptyRows(sky);
            insertAtEmptyRows(sky, insertionPoints);

            insertionPoints = findEmptyColumns(sky);
            insertAtEmptyColumns(sky, insertionPoints);
            System.out.println("result = " + countGalaxies(sky));
        }

        if (part == 2){
            System.out.println("result = " + countGalaxies10x(sky, findEmptyRows(sky), findEmptyColumns(sky)));
        }
    }
}