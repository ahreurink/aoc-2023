import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {
    @org.junit.jupiter.api.BeforeAll
    static void setup() {
        Day7.highCard = Day7::cardToValue_2;
        Day7.conditions = List.of(h -> Day7.checkDuplicatesWithJokers(h, 5), // five of a kind with jokers
                h -> Day7.checkDuplicatesWithJokers(h, 4),  // four of a kind with jokers
                Day7::hasFullHouseWithJokers,
                h -> Day7.checkDuplicatesWithJokers(h, 3),  // three of a kind with jokers
                Day7::hasTwoPairWithJokers,
                h -> Day7.checkDuplicatesWithJokers(h, 2));  // pair with jokers
    }

    @Test
    void testHandComparisons() {
        assertEquals(-1, Day7.compareHands("JJJJJ", "KKKKK", Day7.conditions));
        assertEquals(-1, Day7.compareHands("JQJJJ", "KKKKK", Day7.conditions));
        assertEquals(-1, Day7.compareHands("JJQQJ", "KKKKK", Day7.conditions));
        assertEquals(-1, Day7.compareHands("JJQQQ", "KKKKK", Day7.conditions));
        assertEquals(1, Day7.compareHands("KKKKK", "JJJJJ", Day7.conditions));
        assertEquals(1, Day7.compareHands("KKKKK", "JQJJJ", Day7.conditions));
        assertEquals(1, Day7.compareHands("KKKKK", "JJQQJ", Day7.conditions));
        assertEquals(1, Day7.compareHands("KKKKK", "JJQQQ", Day7.conditions));

        assertEquals(-1, Day7.compareHands("KKKKQ", "JJJJJ", Day7.conditions));
        assertEquals(-1, Day7.compareHands("QKKKK", "JJJJJ", Day7.conditions));
        assertEquals(1, Day7.compareHands("JKKKK", "JJJJJ", Day7.conditions));
        assertEquals(1, Day7.compareHands("JJJJJ", "KKKKQ", Day7.conditions));
        assertEquals(1, Day7.compareHands("JJJJJ", "QKKKK", Day7.conditions));
        assertEquals(-1, Day7.compareHands("JJJJJ", "JKKKK", Day7.conditions));

        assertEquals(1, Day7.compareHands("QJJJJ", "QKKKK", Day7.conditions));
        assertEquals(1, Day7.compareHands("JJJJQ", "KKKKQ", Day7.conditions));
        assertEquals(-1, Day7.compareHands("JQQQQ", "JKKKK", Day7.conditions));
        assertEquals(-1, Day7.compareHands("2QQQQ", "34444", Day7.conditions));

        assertEquals(1, Day7.compareHands("AAAQQ", "QQAAA", Day7.conditions));
        assertEquals(1, Day7.compareHands("QQJJJ", "QQAAA", Day7.conditions));
        assertEquals(1, Day7.compareHands("QQQQQ", "QQQQA", Day7.conditions));
        assertEquals(-1, Day7.compareHands("JJJJJ", "JJJQQ", Day7.conditions));
        assertEquals(1, Day7.compareHands("AAAAA", "AAAQQ", Day7.conditions));
        assertEquals(1, Day7.compareHands("JJQQA", "JQQAA", Day7.conditions));
    }
}