package wad;

import java.util.ArrayList;
import java.util.Date;

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
