package wad;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a word entry in the vocabulary database.
 * 
 * A Word contains the vocabulary term, its definition, usage examples,
 * a flag indicating if it's newly added, and a history of review attempts.
 * 
 * Implementation Notes:
 * <ul>
 *   <li>Title and definition follow the pattern of word ~ example usage</li>
 *   <li>Example field contains inflected forms or related words</li>
 *   <li>Attempt timestamps are recorded each time the word is reviewed</li>
 *   <li>Implements proper equals() and hashCode() for use in collections</li>
 * </ul>
 * 
 * @since 1.0
 */
public class Word {
	public String title;
	public String definition;
	public String example;
	public boolean isNew;
	public ArrayList<Date> attemptList = new ArrayList<Date>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attemptList == null) ? 0 : attemptList.hashCode());
		result = prime * result + ((definition == null) ? 0 : definition.hashCode());
		result = prime * result + ((example == null) ? 0 : example.hashCode());
		result = prime * result + (isNew ? 1231 : 1237);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Word other = (Word) obj;
		if (attemptList == null) {
			if (other.attemptList != null) {
				return false;
			}
		}
		else if (!attemptList.equals(other.attemptList)) {
			return false;
		}
		if (definition == null) {
			if (other.definition != null) {
				return false;
			}
		}
		else if (!definition.equals(other.definition)) {
			return false;
		}
		if (example == null) {
			if (other.example != null) {
				return false;
			}
		}
		else if (!example.equals(other.example)) {
			return false;
		}
		if (isNew != other.isNew) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		}
		else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}
}
