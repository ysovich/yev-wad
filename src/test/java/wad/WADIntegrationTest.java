package wad;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

/**
 * Edge case and integration tests for the WAD application.
 * Tests boundary conditions, null handling, and complex scenarios.
 */
public class WADIntegrationTest {
    
    private ArrayList<Word> wordList;
    
    @Before
    public void setUp() {
        wordList = new ArrayList<>();
    }
    
    @Test
    public void testEmptyWordList() {
        assertEquals("Empty word list should have size 0", 0, wordList.size());
        assertTrue("Empty list should be empty", wordList.isEmpty());
    }
    
    @Test
    public void testAddingWordToList() {
        Word word = new Word();
        word.title = "hello";
        wordList.add(word);
        
        assertEquals("List should contain 1 word", 1, wordList.size());
        assertEquals("Word should be retrievable", "hello", wordList.get(0).title);
    }
    
    @Test
    public void testLargeWordDatabase() {
        int size = 10000;
        for (int i = 0; i < size; i++) {
            Word word = new Word();
            word.title = "word" + i;
            word.definition = "definition " + i;
            wordList.add(word);
        }
        
        assertEquals("List should contain 10000 words", size, wordList.size());
        assertEquals("Last word should be word9999", "word9999", wordList.get(size - 1).title);
    }
    
    @Test
    public void testWordWithEmptyTitle() {
        Word word = new Word();
        word.title = "";
        word.definition = "definition";
        
        assertNotNull("Word should be created", word);
        assertEquals("Title should be empty string", "", word.title);
    }
    
    @Test
    public void testWordWithVeryLongDefinition() {
        Word word = new Word();
        word.title = "test";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            builder.append("This is a long definition. ");
        }
        word.definition = builder.toString();
        
        assertNotNull("Word should be created", word);
        assertTrue("Definition should be very long", word.definition.length() > 10000);
    }
    
    @Test
    public void testWordWithSpecialCharacters() {
        Word word = new Word();
        word.title = "café";
        word.definition = "A place where ~ is served. ~s are popular in France.";
        word.example = "I love going to the ~";
        
        assertEquals("Title with accent should be preserved", "café", word.title);
        assertTrue("Definition with special chars should contain tilde", word.definition.contains("~"));
    }
    
    @Test
    public void testWordWithUnicodeCharacters() {
        Word word = new Word();
        word.title = "mañana";
        word.definition = "μαθηματικά";
        word.example = "中文 ~ 日本語";
        
        assertNotNull("Word should handle unicode", word);
        assertTrue("Should preserve unicode title", word.title.contains("ñ"));
        assertTrue("Should preserve unicode definition", word.definition.contains("μ"));
    }
    
    @Test
    public void testVeryOldAttempt() throws Exception {
        Word word = new Word();
        word.title = "oldword";
        
        // Create a date from 10 years ago
        Date tenYearsAgo = new Date(System.currentTimeMillis() - (365L * 10 * 24 * 60 * 60 * 1000));
        word.attemptList.add(tenYearsAgo);
        
        assertEquals("Should contain one attempt", 1, word.attemptList.size());
        assertTrue("Old attempt should be before current time", 
            word.attemptList.get(0).before(new Date()));
    }
    
    @Test
    public void testManyAttemptsForSingleWord() {
        Word word = new Word();
        word.title = "frequentword";
        
        Date now = new Date();
        for (int i = 0; i < 1000; i++) {
            word.attemptList.add(new Date(now.getTime() + i * 1000));
        }
        
        assertEquals("Should have 1000 attempts", 1000, word.attemptList.size());
        assertTrue("Attempts should be ordered", 
            word.attemptList.get(0).before(word.attemptList.get(999)));
    }
    
    @Test
    public void testWordEqualityAfterModification() {
        Word word1 = new Word();
        word1.title = "test";
        
        Word word2 = new Word();
        word2.title = "test";
        
        assertEquals("Words should be equal initially", word1, word2);
        
        word1.attemptList.add(new Date());
        assertNotEquals("Words should not be equal after adding attempt", word1, word2);
    }
    
    @Test
    public void testNullDefinitionHandling() {
        Word word = new Word();
        word.title = "word";
        word.definition = null;
        word.example = "example";
        
        assertNull("Definition can be null", word.definition);
        assertNotNull("Word should still be valid", word);
    }
    
    @Test
    public void testNullExampleHandling() {
        Word word = new Word();
        word.title = "word";
        word.definition = "definition";
        word.example = null;
        
        assertNull("Example can be null", word.example);
        assertNotNull("Word should still be valid", word);
    }
    
    @Test
    public void testLinkedHashMapBehavior() {
        LinkedHashMap<String, Word> wordMap = new LinkedHashMap<>();
        
        Word word1 = new Word();
        word1.title = "first";
        wordMap.put("1", word1);
        
        Word word2 = new Word();
        word2.title = "second";
        wordMap.put("2", word2);
        
        Word word3 = new Word();
        word3.title = "third";
        wordMap.put("3", word3);
        
        // LinkedHashMap preserves insertion order
        ArrayList<String> titles = new ArrayList<>();
        for (Word w : wordMap.values()) {
            titles.add(w.title);
        }
        
        assertEquals("First word should be first", "first", titles.get(0));
        assertEquals("Second word should be second", "second", titles.get(1));
        assertEquals("Third word should be third", "third", titles.get(2));
    }
    
    @Test
    public void testConcurrentModificationScenario() {
        wordList = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            Word word = new Word();
            word.title = "word" + i;
            wordList.add(word);
        }
        
        // Simulate finding and updating
        for (Word w : new ArrayList<>(wordList)) {
            if (w.title.contains("5")) {
                w.definition = "updated";
            }
        }
        
        assertTrue("Should safely iterate and modify", 
            wordList.get(5).definition.equals("updated"));
    }
    
    @Test
    public void testWordListSearchPerformance() {
        // Add 1000 words
        for (int i = 0; i < 1000; i++) {
            Word word = new Word();
            word.title = "word" + i;
            wordList.add(word);
        }
        
        // Search for specific word
        long startTime = System.currentTimeMillis();
        Word found = null;
        for (Word w : wordList) {
            if (w.title.equals("word500")) {
                found = w;
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        
        assertNotNull("Should find word", found);
        assertEquals("Should find correct word", "word500", found.title);
        assertTrue("Search should complete quickly", (endTime - startTime) < 100); // less than 100ms
    }
    
    @Test
    public void testWordCloning() {
        Word original = new Word();
        original.title = "original";
        original.definition = "def";
        original.example = "ex";
        original.isNew = false;
        Date attempt = new Date();
        original.attemptList.add(attempt);
        
        // Manual clone
        Word cloned = new Word();
        cloned.title = original.title;
        cloned.definition = original.definition;
        cloned.example = original.example;
        cloned.isNew = original.isNew;
        cloned.attemptList.addAll(original.attemptList);
        
        assertEquals("Cloned word should be equal", original, cloned);
        assertTrue("Cloned word should be independent", cloned.attemptList.remove(attempt));
        assertNotEquals("Original and clone should differ after modification", original, cloned);
    }
}
