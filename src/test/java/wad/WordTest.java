package wad;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Word class.
 * Tests basic word creation, equality, hashing, and attempt tracking.
 */
public class WordTest {
    
    private Word word1;
    private Word word2;
    private Word word3;
    
    @Before
    public void setUp() {
        word1 = new Word();
        word1.title = "example";
        word1.definition = "a thing characteristic of its kind";
        word1.example = "this is an ~";
        word1.isNew = false;
        
        word2 = new Word();
        word2.title = "example";
        word2.definition = "a thing characteristic of its kind";
        word2.example = "this is an ~";
        word2.isNew = false;
        
        word3 = new Word();
        word3.title = "different";
        word3.definition = "not the same";
        word3.example = "this is ~";
        word3.isNew = true;
    }
    
    @Test
    public void testWordCreation() {
        assertNotNull("Word should be created", word1);
        assertEquals("Title should be set", "example", word1.title);
        assertEquals("Definition should be set", "a thing characteristic of its kind", word1.definition);
        assertEquals("Example should be set", "this is an ~", word1.example);
        assertFalse("isNew should be false", word1.isNew);
    }
    
    @Test
    public void testWordEquality() {
        assertEquals("Identical words should be equal", word1, word2);
        assertNotEquals("Different words should not be equal", word1, word3);
    }
    
    @Test
    public void testWordHashCode() {
        assertEquals("Equal words should have same hash code", word1.hashCode(), word2.hashCode());
        assertNotEquals("Different words should have different hash codes", word1.hashCode(), word3.hashCode());
    }
    
    @Test
    public void testAttemptListInitialization() {
        assertNotNull("Attempt list should be initialized", word1.attemptList);
        assertTrue("Attempt list should be empty on creation", word1.attemptList.isEmpty());
    }
    
    @Test
    public void testAddAttempt() {
        Date attempt = new Date();
        word1.attemptList.add(attempt);
        
        assertEquals("Attempt list should contain one attempt", 1, word1.attemptList.size());
        assertEquals("First attempt should be the added date", attempt, word1.attemptList.get(0));
    }
    
    @Test
    public void testMultipleAttempts() {
        Date attempt1 = new Date();
        Date attempt2 = new Date(attempt1.getTime() + 1000);
        Date attempt3 = new Date(attempt2.getTime() + 1000);
        
        word1.attemptList.add(attempt1);
        word1.attemptList.add(attempt2);
        word1.attemptList.add(attempt3);
        
        assertEquals("Should contain 3 attempts", 3, word1.attemptList.size());
        assertEquals("Attempts should be in order", attempt1, word1.attemptList.get(0));
        assertEquals("Second attempt should be second", attempt2, word1.attemptList.get(1));
        assertEquals("Third attempt should be third", attempt3, word1.attemptList.get(2));
    }
    
    @Test
    public void testWordEqualityWithNullFields() {
        Word word4 = new Word();
        Word word5 = new Word();
        
        assertEquals("Two words with all null fields should be equal", word4, word5);
    }
    
    @Test
    public void testWordNotEqualToNull() {
        assertNotEquals("Word should not equal null", word1, null);
    }
    
    @Test
    public void testWordNotEqualToDifferentClass() {
        assertNotEquals("Word should not equal non-Word objects", word1, "example");
    }
    
    @Test
    public void testWordEqualityWithDifferentAttempts() {
        word1.attemptList.add(new Date());
        assertNotEquals("Words with different attempt lists should not be equal", word1, word2);
    }
    
    @Test
    public void testWordIsNewFlag() {
        word1.isNew = true;
        assertNotEquals("Words with different isNew flags should not be equal", word1, word2);
    }
    
    @Test
    public void testWordWithPartiallyNullFields() {
        Word word4 = new Word();
        word4.title = "test";
        word4.definition = null;
        word4.example = "example";
        
        Word word5 = new Word();
        word5.title = "test";
        word5.definition = null;
        word5.example = "example";
        
        assertEquals("Words with same null pattern should be equal", word4, word5);
    }
}
