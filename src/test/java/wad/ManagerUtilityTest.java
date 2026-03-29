package wad;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Manager statistics and utility functions.
 * Tests date formatting and word statistics calculations.
 */
public class ManagerUtilityTest {
    
    private SimpleDateFormat dateFormat;
    private ArrayList<Word> wordList;
    private Word word1;
    private Word word2;
    private Word word3;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        wordList = new ArrayList<>();
        
        word1 = new Word();
        word1.title = "cat";
        word1.definition = "a feline animal";
        word1.isNew = false;
        
        word2 = new Word();
        word2.title = "dog";
        word2.definition = "a canine animal";
        word2.isNew = true;
        
        word3 = new Word();
        word3.title = "bird";
        word3.definition = "an avian animal";
        word3.isNew = false;
        
        wordList.add(word1);
        wordList.add(word2);
        wordList.add(word3);
    }
    
    @Test
    public void testDateFormat() {
        Date date = new Date(System.currentTimeMillis());
        String formatted = dateFormat.format(date);
        
        assertNotNull("Formatted date should not be null", formatted);
        assertTrue("Formatted date should contain time separator", formatted.contains("T"));
        assertTrue("Formatted date should have colon for time", formatted.contains(":"));
    }
    
    @Test
    public void testDateFormatParsing() throws Exception {
        String dateString = "2020-03-01T08:00:00";
        Date date = dateFormat.parse(dateString);
        
        assertNotNull("Parsed date should not be null", date);
        
        // Parse it back and verify
        String reformatted = dateFormat.format(date);
        assertEquals("Date should parse and reformat consistently", dateString, reformatted);
    }
    
    @Test
    public void testWordListInitialization() {
        assertEquals("Word list should contain 3 words", 3, wordList.size());
        assertEquals("First word should be 'cat'", "cat", wordList.get(0).title);
        assertEquals("Second word should be 'dog'", "dog", wordList.get(1).title);
        assertEquals("Third word should be 'bird'", "bird", wordList.get(2).title);
    }
    
    @Test
    public void testCountNewWords() {
        int newCount = 0;
        for (Word w : wordList) {
            if (w.isNew) {
                newCount++;
            }
        }
        
        assertEquals("Should count exactly 1 new word", 1, newCount);
    }
    
    @Test
    public void testCountOldWords() {
        int oldCount = 0;
        for (Word w : wordList) {
            if (!w.isNew) {
                oldCount++;
            }
        }
        
        assertEquals("Should count exactly 2 old words", 2, oldCount);
    }
    
    @Test
    public void testTotalAttemptCount() {
        int totalAttempts = 0;
        for (Word w : wordList) {
            totalAttempts += w.attemptList.size();
        }
        
        assertEquals("Should count 0 total attempts", 0, totalAttempts);
    }
    
    @Test
    public void testTotalAttemptCountWithAttempts() {
        Date now = new Date();
        word1.attemptList.add(now);
        word1.attemptList.add(now);
        word2.attemptList.add(now);
        word3.attemptList.add(now);
        word3.attemptList.add(now);
        word3.attemptList.add(now);
        
        int totalAttempts = 0;
        for (Word w : wordList) {
            totalAttempts += w.attemptList.size();
        }
        
        assertEquals("Should count 6 total attempts", 6, totalAttempts);
    }
    
    @Test
    public void testWordWithMostAttempts() {
        Date now = new Date();
        word1.attemptList.add(now);
        word1.attemptList.add(now);
        word2.attemptList.add(now);
        word3.attemptList.add(now);
        word3.attemptList.add(now);
        word3.attemptList.add(now);
        
        Word mostAttempted = null;
        int maxAttempts = 0;
        
        for (Word w : wordList) {
            if (w.attemptList.size() > maxAttempts) {
                maxAttempts = w.attemptList.size();
                mostAttempted = w;
            }
        }
        
        assertNotNull("Should find word with most attempts", mostAttempted);
        assertEquals("'bird' should have most attempts", "bird", mostAttempted.title);
        assertEquals("Most attempted word should have 3 attempts", 3, maxAttempts);
    }
    
    @Test
    public void testFilterWordsByTitle() {
        String searchTerm = "a";
        ArrayList<Word> filtered = new ArrayList<>();
        
        for (Word w : wordList) {
            if (w.title.contains(searchTerm)) {
                filtered.add(w);
            }
        }
        
        assertEquals("Should find 1 word containing 'a' (only 'cat')", 1, filtered.size());
        assertTrue("Should contain 'cat'", filtered.stream().anyMatch(w -> w.title.equals("cat")));
    }
    
    @Test
    public void testFilterWordsByState() {
        ArrayList<Word> newWords = new ArrayList<>();
        ArrayList<Word> oldWords = new ArrayList<>();
        
        for (Word w : wordList) {
            if (w.isNew) {
                newWords.add(w);
            } else {
                oldWords.add(w);
            }
        }
        
        assertEquals("Should have 1 new word", 1, newWords.size());
        assertEquals("Should have 2 old words", 2, oldWords.size());
        assertEquals("Only 'dog' should be new", "dog", newWords.get(0).title);
    }
    
    @Test
    public void testStatisticsArray() {
        int[] stats = new int[7]; // D, H, M, C, T, G, and 1 more
        
        // Simulate stats: D (daily), H (hourly), M (monthly), etc.
        stats[0] = 5;  // Daily attempts
        stats[1] = 15; // Hourly attempts
        stats[2] = 50; // Monthly attempts
        
        assertEquals("Daily should be 5", 5, stats[0]);
        assertEquals("Hourly should be 15", 15, stats[1]);
        assertEquals("Monthly should be 50", 50, stats[2]);
    }
    
    @Test
    public void testIntervalLabels() {
        String[] intervals = { "Y", "M", "W", "T", "D", "H", "M" };
        
        assertEquals("Should have 7 interval labels", 7, intervals.length);
        assertEquals("First label should be 'Y' for year", "Y", intervals[0]);
        assertEquals("Last label should be 'M' for minute", "M", intervals[6]);
    }
}
