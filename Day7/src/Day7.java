import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day7 {
    static Map<Character, Long> createHandSet(String hand){
        return IntStream.range(0, hand.toCharArray().length).mapToObj(i -> hand.toCharArray()[i])
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    static int cardToValue(char card){
        return switch(card) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 11;
            case 'T' -> 10;
            default -> card - '0';
        };
    }

    static Function<Character, Integer> highCard;

    static int highCardComparison(String hand1, String hand2){
        for(int i = 0; i<5; i++){
            int difference = highCard.apply(hand1.charAt(i)) - highCard.apply(hand2.charAt(i));
            if(difference > 0)
                return 1;
            if(difference < 0)
                return -1;
        }
        return 0;
    }
    static int compareHands(String input1, String input2){
        String hand1 = input1.split(" ")[0];
        String hand2 = input2.split(" ")[0];
        Map<Character, Long> handSet1 = createHandSet(hand1);
        Map<Character, Long> handSet2 = createHandSet(hand2);
        if (handSet1.containsValue((long) 5)) {
            if (handSet2.containsValue((long) 5)) {
                return highCardComparison(hand1, hand2);
            } else
                return 1;
        } else if (handSet2.containsValue((long) 5)) {
            return -1;
        } // five of a kind

        if (handSet1.containsValue((long) 4)) {
            if (handSet2.containsValue((long) 4)) {
                return highCardComparison(hand1, hand2);
            } else
                return 1;
        } else if (handSet2.containsValue((long) 4)) {
            return -1;
        }// four of a kind

        if (handSet1.containsValue((long) 3) && !handSet2.containsValue((long) 3)) { // unique full house or 3k for hand 1
            return 1;
        } else if (!handSet1.containsValue((long) 3) && handSet2.containsValue((long) 3)) { // full house or three of a kind for set 2
            return -1;
        } else if (handSet2.containsValue((long) 3) && handSet1.containsValue((long) 3)) {
            if (handSet1.containsValue((long) 2) && !handSet2.containsValue((long) 2)) { // full house for set 1
                return 1;
            } else if (!handSet1.containsValue((long) 2) && handSet2.containsValue((long) 2)) { // full house for set 2
                return -1;
            } else { // both full house or both three of a kind
                return highCardComparison(hand1, hand2);
            }
        } // full house and three of a kind

        long diff = handSet1.values().stream().filter(num -> num == 2).count() -
                handSet2.values().stream().filter(num -> num == 2).count();
        if (diff != 0) // if either hand has more pairs, that hand wins
            return (int) diff;
        else
            return highCardComparison(hand1, hand2);
    }

    static int cardToValue_2(char card){
        return switch(card) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 0;
            case 'T' -> 10;
            default -> card - '0';
        };
    }

    static boolean checkDuplicatesWithJokers(Map<Character, Long> handSet, int duplicateAmount){
        long numJokers = handSet.getOrDefault('J', (long) 0);

        if(duplicateAmount == 5 && numJokers == 5)
            return true;

        return handSet.keySet().stream()
            .filter(c -> c!='J')
            .filter(c -> (handSet.get(c) + numJokers) == duplicateAmount)
            .count() > 0;
    }

    static boolean hasFullHouseWithJokers(Map<Character, Long> hand){
        return (hand.containsValue((long) 3) && hand.containsValue((long) 2)) ||
                (checkDuplicatesWithJokers(hand, 3) && hand.containsValue((long) 2)) ||
                (hand.containsValue((long) 3) && checkDuplicatesWithJokers(hand, 3));
    }

    static boolean hasTwoPairWithJokers(Map<Character, Long> handSet){
        return (handSet.values().stream().filter(v -> v == 2).count() == 2) ||
                ((handSet.values().stream().filter(v -> v == 2).count() == 1) &&
                        (handSet.getOrDefault('J', (long) 0) == 1));
    }

    static int compareHands(String hand1, String hand2, Predicate<Map<Character, Long>> handCondition){
        Map<Character, Long> handSet1 = createHandSet(hand1);
        Map<Character, Long> handSet2 = createHandSet(hand2);
        if(handCondition.test(handSet1)){
            if(handCondition.test(handSet2))
                return highCardComparison(hand1, hand2);
            else
                return 1;
        } else if(handCondition.test(handSet2)){
            return -1;
        }
        return -999;
    }

    static int compareHands(String input1, String input2, List<Predicate<Map<Character, Long>>> conditions){
        String hand1 = input1.split(" ")[0];
        String hand2 = input2.split(" ")[0];

        for(Predicate<Map<Character, Long>> condition : conditions){
            int comparisonResult = compareHands(hand1, hand2, condition);
            if (comparisonResult != -999)
                return comparisonResult;
        }

        return highCardComparison(hand1, hand2);
    }

    static final int part = 2;
    static final String FILE_PATH = "Day7/input_lines";

    static List<Predicate<Map<Character, Long>>> conditions;

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(Path.of(FILE_PATH));

        if(part == 1){
            highCard = Day7::cardToValue;
            conditions = List.of(h -> h.containsValue((long) 5), // five of a kind
                            h -> h.containsValue((long) 4),  // four of a kind
                            h -> h.containsValue((long) 3) && h.containsValue((long) 2),
                            h -> h.containsValue((long) 3),  // three of a kind
                            h -> h.values().stream().filter(num -> num == 2).count() == 2,
                            h -> h.containsValue((long) 2));  // pair
        }
        if(part == 2){
            highCard = Day7::cardToValue_2;
            conditions = List.of(h -> checkDuplicatesWithJokers(h, 5), // five of a kind with jokers
                            h -> checkDuplicatesWithJokers(h, 4),  // four of a kind with jokers
                            Day7::hasFullHouseWithJokers,
                            h -> checkDuplicatesWithJokers(h, 3),  // three of a kind with jokers
                            Day7::hasTwoPairWithJokers,
                            h -> checkDuplicatesWithJokers(h, 2));  // pair with jokers

        }
        inputLines.sort((i1, i2) -> compareHands(i1, i2, conditions));

        int result = IntStream.range(0, inputLines.size())
//                .peek(index -> System.out.println(index + " " + inputLines.get(index).split(" ")[0] + " : " + Integer.parseInt(inputLines.get(index).split(" ")[1])))
                .map(index -> (index + 1) * Integer.parseInt(inputLines.get(index).split(" ")[1]))
                .reduce(Integer::sum).getAsInt();
        System.out.println("result = " + result);
    }
}