# WAD - Word-A-Day

A Java-based desktop application for daily vocabulary learning. WAD is a flashcard-style word learning tool with tracking, review capabilities, and statistics management to help you learn a new word each day.

## Features

- **Word Management**: Add, view, and organize words with definitions and examples
- **Flashcard Interface**: Interactive GUI for reviewing words
- **Progress Tracking**: Monitor learning progress with statistics
- **Review System**: Mark words as known or unknown to track mastery
- **New Word Tracking**: Keep track of newly added words and review frequency
- **Statistics**: View detailed statistics about your learning progress
- **Configurable**: Customize the application via properties file

## Project Structure

```
yev-wad/
├── src/
│   ├── main/
│   │   ├── java/wad/
│   │   │   ├── Wad.java          # Main application entry point
│   │   │   ├── FrameMain.java    # Primary GUI window
│   │   │   ├── Manager.java      # Word management logic
│   │   │   └── Word.java         # Word data model
│   │   └── resources/
│   │       └── wad.properties    # Configuration file
│   └── test/java/                # Unit tests
├── pom.xml                       # Maven build configuration
└── README.md
```

## Requirements

- Java 8 or higher
- Maven 3.6+ (for building)

## Building

To build the application:

```bash
mvn clean package
```

This generates an executable JAR file in the `target/` directory as `wad.jar`.

## Running

### With default configuration:

```bash
java -jar target/wad.jar
```

This will use the default configuration path: `C:\Data\Doc\Dict\wad.properties`

### With custom configuration:

```bash
java -jar target/wad.jar "path/to/your/wad.properties"
```

Example:
```bash
java -jar target/wad.jar "C:\Home\Doc\wadesp\wad.properties"
```

### Testing with sample data:

A sample XML file with 5 vocabulary words is included in `src/main/resources/sample-words.xml.zip` for quick testing:

```bash
java -jar target/wad.jar "src/main/resources/sample-wad.properties"
```

This loads the sample vocabulary database containing words like: serendipity, ephemeral, eloquent, ubiquitous, and delectable.

## Configuration

The `wad.properties` file configures the application and specifies where your word data is stored.

### Properties File Format

Create a `wad.properties` file with the following structure:

```properties
words=C:/Home/Doc/wadesp/wad.xml.zip
dict=C:/Home/Doc/wadesp/dict.txt
down=C:/Home/Doc/wadesp/down.txt
```

**Important Notes:**
- Use forward slashes `/` in paths, not backslashes
- The `words` property must point to a **ZIP-compressed XML file** (`.xml.zip`), not a plain XML file
- The XML file inside the ZIP should be named `wad.xml`
- The `dict` file is used for word suggestions
- The `down` file tracks downloaded/reviewed words

### Creating a ZIP-compressed Word Database

If you have a plain `wad.xml` file, compress it to `wad.xml.zip`:

**On Windows (PowerShell):**
```powershell
Compress-Archive -Path "C:\path\to\wad.xml" -DestinationPath "C:\path\to\wad.xml.zip" -Force
```

**On Linux/Mac:**
```bash
zip wad.xml.zip wad.xml
```

### XML File Format

The `wad.xml` file inside the ZIP should contain word data in XML format:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<wad g="count">
  <w t="word_title">
    <d>definition or translation
Example sentence with ~ replacing the word
Another example with ~</d>
    <e>inflected_form1
inflected_form2</e>
    <a>2020-03-01T08:00:00</a>
    <a>2020-03-02T08:00:00</a>
  </w>
</wad>
```

- `<w>`: Word entry
- `t`: Word title or phrase
- `<d>`: Definition/translation followed by example sentences with the word replaced by `~`
- `<e>`: Inflected forms or conjugations of the word
- `<a>`: Attempt timestamps (when the word was reviewed)

### Encoding Considerations

Ensure your XML file uses UTF-8 encoding. If you encounter encoding errors:

1. Re-encode the file to UTF-8
2. Verify the XML declaration: `<?xml version="1.0" encoding="UTF-8"?>`
3. Re-compress it to ZIP format

## User Guide

### Interface Overview

The WAD application window has several key areas:

```
┌─────────────────────────────────────────────────┐
│  Word Title / Response Field        ID: 2490    │  ← Current word display
├──────────────┬────────────────────────────────┬─┤
│ Show | Yes   │  Word Definition & Examples    │ │  ← Learning controls
│ No | Next    │  (Examples use ~ in place of  │ │
│              │   the word)                    │ │
├──────────────┼────────────────────────────────┤ │  ← Right panel:
│ Statistics   │ Word Examples & History        │ │    Word ID : Attempts
│              │                                │ │    (shows review counts)
│ Buttons:     │                                │ │
│ Add, Find    │                                │ │
│ Title, Update│                                │ │
│              │                                │ │
│ Save, Clear  │                                │ │
│ Rev, Mine    │                                │ │
└──────────────┴────────────────────────────────┴─┘
```

### Main Learning Workflow

The typical workflow for learning a new word:

1. **Next** - Move to the next word. The definition and examples appear (with `~` replacing the word), but the word title is hidden.
2. Try to remember or guess what the word is based on the definition and examples
3. **Show** - Click to reveal the actual word/title and check if you guessed correctly
4. Click **Yes** if you got it right or knew the word
5. Click **No** if you couldn't remember it or guessed wrong
6. Return to step 1 to practice the next word

### Button Reference

#### Learning Controls (Top)
- **Next**: Move to the next word. Displays the definition and examples (with `~` replacing the word) but hides the word title. You need to try to remember/guess the word.
- **Show**: Reveal the word title/answer. Click this after trying to remember the word based on its definition.
- **Yes**: Mark the current word as known/mastered (you got it right or knew it). Records a successful attempt.
- **No**: Mark the current word as unknown/failed (you couldn't remember it). Records an unsuccessful attempt.
- **New** (checkbox): When checked, filter to show only newly added words.

#### Word Management (Bottom)
- **Add**: Add a new word to the database
- **Find**: Search for a specific word by title or ID
- **Title**: Filter words by title or search term
- **Update**: Update the currently selected word's information
- **Save**: Save changes to the XML database
- **Clear**: Clear the current selection and search filter
- **Rev** (Review): Filter to show words that need review (low attempt counts)
- **Mine**: Display your custom word list

### Understanding the Statistics

**Left Panel Statistics:**
```
D:0 H:0 M:0 C:22
```
- **D** = Words reviewed today (Daily count)
- **H** = Hourly total words reviewed
- **M** = Monthly total words reviewed
- **C** = Total correct responses for current word

**Summary Line:**
```
T:11140 G:11118 22(0)
```
- **T** = Total words in database
- **G** = Global/all-time statistics tracked
- **22(0)** = Current word ID (2490) with parenthetical additional info

### Right Panel - Word Index

The right panel displays every word in your database with its review count:
```
2556 : 1
2553 : 1
2548 : 1
2547 : 2
```

- Left number = Word ID
- Right number = Number of times reviewed

Words with higher numbers have been reviewed more frequently. This helps you identify:
- **High numbers** = Words you've studied extensively
- **1** = Words you've reviewed only once
- Words you haven't clicked on yet won't appear

### Step-by-Step Example

**Scenario: Learning Spanish vocabulary**

1. Start the app with your word database
2. Click **Next** to see the first word
3. The app shows the definition and examples:
   - "small shop"
   - "Voy al ~ a comprar más huevos" (Go to the ~ to buy more eggs)
4. The word title is hidden - you need to guess what Spanish word means "small shop"
5. You think "tienda" - that's your guess!
6. Click **Show** to reveal the answer: "**el expendio**"
7. Did you get it right? 
   - Yes → Click **Yes** (you knew it or guessed correctly)
   - No → Click **No** (you didn't remember or guessed wrong)
8. Click **Next** to see the next word and try again
9. The word now shows `ID : 1` in the right panel (you've reviewed it once)
10. Remember to click **Save** when you're done practicing to persist your progress

### Filtering and Finding Words

**To find a specific word:**
1. Click **Find**
2. Enter the word title or partial match
3. Results appear in the title field
4. Click **Title** to focus on that word

**To review only new words:**
1. Check the **New** checkbox
2. Only newly added words will appear
3. Uncheck to return to normal browsing

**To review words needing attention:**
1. Click **Rev** (Review)
2. Words with fewer attempts appear first

### Adding and Updating Words

**To add a new word:**
1. Click **Add**
2. Enter the word title/phrase in the title field
3. Click **Update** to set the definition and examples
4. Click **Save** to persist to the database

**To update an existing word:**
1. Find the word using **Find** or **Title**
2. Click **Update** to edit definition/examples
3. Click **Save** to save changes

### Tips for Effective Learning

- **Mark honestly** - Click **Yes** only if you truly knew it
- **Review regularly** - Use the **Rev** button to focus on challenging words
- **Check stats** - The left panel shows your daily progress
- **Save often** - Always click **Save** after adding or modifying words
- **Use examples** - Pay attention to the example sentences with `~` placeholders

## Usage

## Architecture

- **Wad**: Entry point that initializes the application window and centers it on the screen
- **FrameMain**: Main GUI frame containing all UI components and event handling
- **Word**: Data model representing a word with title, definition, example, and attempt history
- **Manager**: Business logic for managing word collection and learning statistics

## Technical Details

- Built with **Java Swing** for cross-platform GUI
- Uses **Maven** for build automation
- Properties-based configuration system
- Stores word data in ZIP-compressed XML format
- Tracks word learning attempts with timestamps
- Supports custom word databases via configuration files

## License

Please refer to the project for license information.
