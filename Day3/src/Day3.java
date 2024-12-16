import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day3 {
    public static void main(String[] args) throws IOException {
        System.out.println("result = " + aoc_3_1());
    }
    private static final int LEN = 140; //140;
    private static final String FILE_PATH = "Day3/input_lines";
    private static boolean checkNeighbors(char[][] schema, int i, int j, int len){
        for(int ii = Math.max(0, i - 1); ii < Math.min(i + 2, schema.length); ii++){
            for(int jj = Math.max(0, j - 1); jj < Math.min(j+len + 1, schema[0].length); jj++){
                if( !(ii == i && jj == j) &&
                    !(ii == i && len>1 && jj == j+1) &&
                    !(ii == i && len>2 && jj == j+2)){ // not the numbers themselves
                    if(schema[ii][jj] != '.' && !Character.isDigit(schema[ii][jj]))
                        return true;
                }
            }
        }
        return false;
    }

    private static int aoc_3() throws IOException {
        char[][] schema;
        try(Stream<String> lines = Files.lines(Path.of(FILE_PATH))){
            schema = lines
                    .map(String::toCharArray)
                    .toArray(size -> new char[LEN][]);
        }

        int sum = 0;
        for(int i = 0; i < LEN; i++){
            for(int j = 0; j < LEN; j++){
                if(Character.isDigit(schema[i][j])){
                    if(j == LEN-1 || ! Character.isDigit(schema[i][j+1])){ //single digit number
//                        System.out.println("" + schema[i][j] + " " + checkNeighbors(schema, i, j, 1));
                        if(checkNeighbors(schema, i, j, 1))
                            sum += Integer.parseInt("" + schema[i][j]);
                    }
                    else if(Character.isDigit(schema[i][j+1]) &&
                            (j==LEN-2 || ! Character.isDigit(schema[i][j+2]))){ // two-digit number
//                        System.out.println("" + schema[i][j] + schema[i][j+1] + " " + checkNeighbors(schema, i, j, 2));
                        if(checkNeighbors(schema, i, j, 2))
                            sum += Integer.parseInt("" + schema[i][j] + schema[i][j+1]);
                        j+=1;
                    }
                    else {
                        if(checkNeighbors(schema, i, j, 3))
                            sum += Integer.parseInt("" + schema[i][j] + schema[i][j+1] + schema[i][j+2]);
//                        System.out.println("" + schema[i][j] + schema[i][j+1] + schema[i][j+2]+ " " + checkNeighbors(schema, i, j, 3));
                        j+=2;
                    }
                }
            }
        }

        return sum;
    }

    private static int findRatio(char[][] schema, int i, int j){
        int MAX_NUM_LEN = 3;
        List<Integer> partNumbers = new ArrayList<>();
        for(int ii = Math.max(0, i - 1); ii < Math.min(i + 2, schema.length); ii++){
            for(int jj = Math.max(0, j - MAX_NUM_LEN); jj < Math.min(j + 2, schema[0].length); jj++){
                if( !(ii == i && jj == j) && Character.isDigit(schema[ii][jj])){ // Number, not the gear itself
                    if (jj == LEN - 1 || (!Character.isDigit(schema[ii][jj + 1]) && jj > j-2)) { //single digit number
                        partNumbers.add(Integer.parseInt("" + schema[ii][jj]));
                    }
                    else if (Character.isDigit(schema[ii][jj + 1]) &&
                            (jj == LEN - 2 || (jj != j-3 && !Character.isDigit(schema[ii][jj + 2])))) { //two-digit number
                        partNumbers.add(Integer.parseInt("" + schema[ii][jj] + schema[ii][jj + 1]));
                        jj += 1;
                    }
                    else if(Character.isDigit(schema[ii][jj + 1]) && Character.isDigit(schema[ii][jj + 2])) { // three-digit number
                        partNumbers.add(Integer.parseInt("" + schema[ii][jj] + schema[ii][jj + 1] + schema[ii][jj + 2]));
                        jj += 2;
                    }
                }
            }
        }
        System.out.println(partNumbers);
        if(partNumbers.size() == 2)
            return (partNumbers.get(0) * partNumbers.get(1));
        else
            return 0;
    }

    private static int aoc_3_1() throws IOException {
        char[][] schema;
        try(Stream<String> lines = Files.lines(Path.of(FILE_PATH))){
            schema = lines
                    .map(String::toCharArray)
                    .toArray(size -> new char[LEN][]);
        }

        int sum = 0;
        for(int i = 0; i < LEN; i++){
            for(int j = 0; j < LEN; j++){
                if(schema[i][j] == '*'){
                    System.out.println("i,j= " + i + " " + j + " : " + findRatio(schema, i, j));
                    sum += findRatio(schema, i, j);
                }
            }
        }

        return sum;
    }
}