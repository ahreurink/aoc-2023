import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


public class Day10 {
    static int width;
    static int height;
    static char[][] map;

    static int[] getStartPoint(char[][] map){
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                if(map[i][j] == 'S')
                    return new int[]{i, j};
        throw new RuntimeException("No entry point found");
    }

    static char lookAt(int[] point){
        return map[point[0]][point[1]];
    }

    static char lookLeft(int[] point){
        if(point[1] > 0)
            return map[point[0]][point[1]-1];
        else
            return '0';
    }

    static char lookRight(int[] point){
        if(point[1] < width - 1)
            return map[point[0]][point[1]+1];
        else
            return '0';
    }

    static char lookUp(int[] point){
        if(point[0] > 0)
            return map[point[0]-1][point[1]];
        else
            return '0';
    }

    static char lookDown(int[] point){
        if(point[0] < height - 1)
            return map[point[0]+1][point[1]];
        else
            return '0';
    }

    static int[] seeker1 = null;
    static int[] seeker2 = null;
    static void setSeekers(int i, int j, Direction initial){
        if (seeker1 == null) {
            seeker1 = new int[]{i, j};
            directionOne = initial;
        }
        else if (seeker2 == null) {
            seeker2 = new int[]{i, j};
            directionTwo = initial;
        }
    }

    enum Direction{UP, DOWN, LEFT, RIGHT}
    static Direction directionOne;
    static Direction directionTwo;
    static void initializeSeekers(int[] startPoint){
        if(lookUp(startPoint) == '|' || lookUp(startPoint) == 'F' || lookUp(startPoint) == '7')
            setSeekers(startPoint[0] - 1, startPoint[1], Direction.UP);
        if(lookDown(startPoint) == '|' || lookDown(startPoint) == 'L' || lookDown(startPoint) == 'J')
            setSeekers(startPoint[0] + 1, startPoint[1], Direction.DOWN);
        if(lookLeft(startPoint) == '-' || lookDown(startPoint) == 'L' || lookDown(startPoint) == 'F')
            setSeekers(startPoint[0], startPoint[1]-1, Direction.LEFT);
        if(lookRight(startPoint) == '-' || lookRight(startPoint) == 'J' || lookRight(startPoint) == '7')
            setSeekers(startPoint[0], startPoint[1]+1, Direction.RIGHT);
    }

    static Direction goUp(int[] seeker){
        seeker[0]--;
        return Direction.UP;
    }

    static Direction goDown(int[] seeker){
        seeker[0]++;
        return Direction.DOWN;
    }

    static Direction goLeft(int[] seeker){
        seeker[1]--;
        return Direction.LEFT;
    }

    static Direction goRight(int[] seeker){
        seeker[1]++;
        return Direction.RIGHT;
    }

    static Direction incrementStep(int[] seeker, Direction going){
        if(going == Direction.UP) { // Coming from below
            switch (lookAt(seeker)) {
                case '|' -> { return goUp(seeker); }
                case 'F' -> { return goRight(seeker); }
                case '7' -> { return goLeft(seeker); }
            };
        }
        if(going == Direction.DOWN) { // coming from above
            switch (lookAt(seeker)) {
                case '|' -> { return goDown(seeker); }
                case 'L' -> { return goRight(seeker); }
                case 'J' -> { return goLeft(seeker); }
            };
        }
        if(going == Direction.RIGHT) { // coming from left
            switch (lookAt(seeker)) {
                case '-' -> { return goRight(seeker); }
                case 'J' -> { return goUp(seeker); }
                case '7' -> { return goDown(seeker); }
            };
        }
        if(going == Direction.LEFT) { // coming from right
            switch (lookAt(seeker)) {
                case '-' -> { return goLeft(seeker); }
                case 'L' -> { return goUp(seeker); }
                case 'F' -> { return goDown(seeker); }
            };
        }
        throw new RuntimeException("No navigable value found " + Arrays.toString(seeker));
    }

    static boolean[][] partOfPipe;
    static void setPipeTile(int[] point){
        partOfPipe[point[0]][point[1]] = true;
    }

    static void replaceStartPoint(int i, int j){
        if(map[i][j] != 'S')
            throw new RuntimeException("not the starting point");
        if(i==2 && j == 0)
            map[i][j] = 'F';
        else if(i==72 && j == 119)
            map[i][j] = 'J';
        else throw new RuntimeException("Not a valid start point");
    }

    static int calculateSurface(){
        int sum = 0;

        for(int i = 0; i < height - 1; i++) {
            System.out.println("Row " + i);
            for (int j = 0; j < width - 1; j++) {
//                if(map[i][j])
            }
        }
        return sum;
    }

    static int traceLoop(){
        int steps = 1;
        while(!Arrays.equals(seeker1, seeker2)){
            if(part == 2){
                setPipeTile(seeker1);
                setPipeTile(seeker2);
            }
            directionOne = incrementStep(seeker1, directionOne);
            directionTwo = incrementStep(seeker2, directionTwo);
            steps += 1;
        }
        return steps;
    }

    static final int part = 2;
    static final String FILE_PATH = "Day10/input_lines";

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(Path.of(FILE_PATH));
        width = inputLines.get(0).length();
        height = inputLines.size();
        map = inputLines.stream().map(String::toCharArray).toArray(char[][]::new);

        for (int n = 0 ; n < map.length ; n++)
            System.out.println(Arrays.toString(map[n]));

        int[] startPoint = getStartPoint(map);

        if(part == 2){
            partOfPipe = new boolean[height][width];
            for(int i = 0; i<height; i++)
                for(int j = 0; j<height; j++)
                    partOfPipe[i][j] = false;
            setPipeTile(startPoint);
        }

        initializeSeekers(startPoint);
        int steps = traceLoop();
        if(part == 2)
            setPipeTile(seeker1); // set last tile, at which point both are equal

        for(boolean[] pipeRow : partOfPipe)
            System.out.println(Arrays.toString(pipeRow));

        if (part == 1)
            System.out.println("result = " + steps);
        if (part == 2){
            replaceStartPoint(startPoint[0], startPoint[1]);
            System.out.println("result = " + calculateSurface());
        }
    }
}