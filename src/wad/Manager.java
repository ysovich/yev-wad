package wad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Manager {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	private static final long MINUTE = 1000 * 60;
	private static final long HOUR = 60 * MINUTE;
	private static final long DAY = 24 * HOUR;
	private static final long YEAR = 365 * DAY;
	private static final long interval[] = { 28 * DAY, 7 * DAY, 3 * DAY, DAY, HOUR, MINUTE, 0 };
	public static final String intervalLbl[] = { "Y", "M", "W", "T", "D", "H", "M" };
	private String WORDS_PATH;
	private String DICT_PATH;
	private String DICT_DOWN_PATH;

	private LinkedHashMap<String, Word> wordMap;
	private int stats[];
	private int oldCount;
	private int newCount;
	private LinkedList<String> lastAttempts = new LinkedList<String>();
	private int candCount;

	public Manager(String configPath) {
		lastAttempts.push("");
		lastAttempts.push("");
		lastAttempts.push("");
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(configPath));
			WORDS_PATH = prop.getProperty("words");
			DICT_PATH = prop.getProperty("dict");
			DICT_DOWN_PATH = prop.getProperty("down");
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		load();
		prepareCandidateList(new ArrayList<Word>(), new ArrayList<Word>());
	}

	public int[] getStats() {
		return stats;
	}

	public int getOldCount() {
		return oldCount;
	}

	public int getNewCount() {
		return newCount;
	}

	public int getCandCount() {
		return candCount;
	}

	public int getDataHashCode() {
		return wordMap.hashCode();
	}

	public void load() {
		wordMap = new LinkedHashMap<String, Word>();
		Document xmlDoc = loadXml(WORDS_PATH);
		Element rootEl = xmlDoc.getDocumentElement();
		NodeList rootChildList = rootEl.getChildNodes();
		int len = rootChildList.getLength();
		for (int i = 0; i < len; ++i) {
			Node rcNode = rootChildList.item(i);
			if (Node.ELEMENT_NODE == rcNode.getNodeType()) {
				Element wordEl = (Element) rcNode;
				Word word = new Word();
				word.title = wordEl.getAttribute("t");
				word.isNew = Boolean.TRUE.toString().equals(wordEl.getAttribute("n"));
				wordMap.put(word.title, word);

				NodeList wordChildList = wordEl.getChildNodes();
				int wcLen = wordChildList.getLength();
				for (int j = 0; j < wcLen; ++j) {
					Node wcNode = wordChildList.item(j);
					if (Node.ELEMENT_NODE == wcNode.getNodeType()) {
						Element childEl = (Element) wcNode;
						if ("d".equals(childEl.getNodeName())) {
							word.definition = childEl.getTextContent();
						}
						else if ("e".equals(childEl.getNodeName())) {
							word.example = childEl.getTextContent();
						}
						else if ("a".equals(childEl.getNodeName())) {
							try {
								Date attemptDate = dateFormat.parse(childEl.getTextContent());
								word.attemptList.add(attemptDate);
							}
							catch (ParseException e) {
								throw new RuntimeException(e);
							}
						}
					}
				}
			}
		}
	}

	public void save() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder builder = docFactory.newDocumentBuilder();
			Document xmlDoc = builder.newDocument();
			Element wadEl = xmlDoc.createElement("wad");
			xmlDoc.appendChild(wadEl);

			for (Word word : wordMap.values()) {
				Element wordEl = xmlDoc.createElement("w");
				wadEl.appendChild(wordEl);
				wordEl.setAttribute("t", word.title);
				if (word.isNew) {
					wordEl.setAttribute("n", Boolean.toString(word.isNew));
				}
				Element defEl = xmlDoc.createElement("d");
				wordEl.appendChild(defEl);
				defEl.setTextContent(word.definition);
				Element exampleEl = xmlDoc.createElement("e");
				wordEl.appendChild(exampleEl);
				exampleEl.setTextContent(word.example);

				for (Date attemptDate : word.attemptList) {
					Element attemptEl = xmlDoc.createElement("a");
					wordEl.appendChild(attemptEl);
					attemptEl.setTextContent(dateFormat.format(attemptDate));
				}
			}

			saveXml(xmlDoc, WORDS_PATH);
		}
		catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean addWord(Word word) {
		if (word.title == null || word.title.length() == 0) {
			JOptionPane.showMessageDialog(null, "Missing title");
			return false;
		}
		if (word.definition == null || word.definition.length() == 0) {
			JOptionPane.showMessageDialog(null, "Missing definition");
			return false;
		}
		if (wordMap.containsKey(word.title)) {
			JOptionPane.showMessageDialog(null, "Title already exist");
			return false;
		}
		wordMap.put(word.title, word);
		return true;
	}

	public void removeWord(String wordTitle) {
		wordMap.remove(wordTitle);
	}

	public Word addAttempt(String wordTitle, Date attemptDate, boolean isCorrect) {
		Word graduatedWord = null;
		Word word = wordMap.get(wordTitle);
		boolean graduated = false;
		if (isCorrect) {
			Date now = new Date();
			for (Date prevAttemptDate : word.attemptList) {
				if (calcDurationDays(now, prevAttemptDate) > YEAR) {
					graduated = true;
					break;
				}
			}
		}

		if (graduated) {
			wordMap.remove(wordTitle);
			graduatedWord = word;
		}
		else if (isCorrect) {
			word.attemptList.add(attemptDate);
		}
		else {
			word.attemptList.clear();
		}
		lastAttempts.addFirst(wordTitle);
		lastAttempts.pollLast();
		return graduatedWord;
	}

	/**
	 * Selects the next word to present.
	 * <ol>
	 * <li>find dur - the longest time between attempts<br>
	 * example: |-2min-|--2hr--|---2d---| dur=2d
	 * <li>if dur > largest interval (1 month) -> Y,<br>
	 * add to candList if time since first attempt > 1 year, or time since last
	 * attempt > 3 months
	 * <li>else find the two intervals between which the dur falls<br>
	 * example: 2d falls between 1d and 3d<br>
	 * if time since last attempt is greater than longer interval, add to candList
	 * <li>select random word from candList
	 * </ol>
	 * 
	 * @param newOldCount
	 * @return selected word
	 */
	public Word getNextWord(int newOldCount, int newNewCount) {
		oldCount = newOldCount;
		newCount = updateNewWords(newNewCount);

		ArrayList<Word> candList = new ArrayList<Word>();
		ArrayList<Word> doneList = new ArrayList<Word>();

		prepareCandidateList(candList, doneList);

		Word selWord = null;
		Random rnd = new Random();
		if (!candList.isEmpty()) {
			for (String lastAttempt : lastAttempts) {
				if (candList.size() > 1) {
					Word word = wordMap.get(lastAttempt);
					if (word != null) {
						candList.remove(word);
					}
				}
			}
			selWord = candList.get(rnd.nextInt(candList.size()));
		}
		if (oldCount > 0 && selWord == null && !doneList.isEmpty()) {
			selWord = doneList.get(rnd.nextInt(doneList.size()));
			--oldCount;
		}

		return selWord;
	}

	private void prepareCandidateList(ArrayList<Word> candList, ArrayList<Word> doneList) {
		stats = new int[interval.length];
		
		for (Word word : wordMap.values()) {
			if (word.isNew) {
				continue;
			}
			long dur = 0;
			Date now = new Date();
			Date lastDate = now;
			if (word.attemptList.size() > 1) {
				Date prevDate = word.attemptList.get(0);
				for (int i = 1; i < word.attemptList.size(); ++i) {
					Date curDate = word.attemptList.get(i);
					long curDur = calcDurationDays(curDate, prevDate);
					if (curDur == 0) {
						curDur = curDate.getTime() - prevDate.getTime();
					}
					if (curDur > dur) {
						dur = curDur;
					}
					lastDate = curDate;
					prevDate = curDate;
				}
			}

			if (dur > 0) {
				if (dur >= interval[0]) {
					++stats[0];
					Date firstAttemptDate = word.attemptList.get(0);
					if (calcDurationDays(now, firstAttemptDate) > YEAR) {
						candList.add(word);
					}
					else if (calcDurationDays(now, lastDate) > DAY * 90) {
						candList.add(word);
					}
					else {
						doneList.add(word);
					}
				}
				else {
					for (int i = 1; i < interval.length; ++i) {
						if (dur >= interval[i]) {
							if (interval[i] >= HOUR) {
								if (calcDurationDays(now, lastDate) >= interval[i - 1]) {
									candList.add(word);
								}
							}
							else {
								if (now.getTime() - lastDate.getTime() >= interval[i - 1]) {
									candList.add(word);
								}
							}
							++stats[i];
							break;
						}
					}
				}
			}
			else {
				candList.add(word);
				++stats[stats.length - 1];
			}
		}

		candCount = candList.size();
	}

	public String mine() {
		StringBuilder selectedWords = new StringBuilder();
		try {
			HashSet<String> down = new HashSet<String>();
			HashSet<String> dict = new HashSet<String>();

			BufferedReader inDown = new BufferedReader(new FileReader(DICT_DOWN_PATH));
			String line = inDown.readLine();
			while (line != null) {
				down.add(line);
				line = inDown.readLine();
			}
			inDown.close();
			selectedWords.append("down " + down.size());
			selectedWords.append('\n');

			BufferedReader inDict = new BufferedReader(new FileReader(DICT_PATH));
			line = inDict.readLine();
			while (line != null) {
				dict.add(line);
				line = inDict.readLine();
			}
			inDict.close();
			selectedWords.append("dict " + dict.size());
			selectedWords.append('\n');

			dict.removeAll(down);
			selectedWords.append("togo " + dict.size());
			selectedWords.append("\n\n");

			for (int i = 0; i < 10; ++i) {
				String[] dictArray = dict.toArray(new String[0]);

				Random rnd = new Random();
				int index = rnd.nextInt(dictArray.length);
				String cand = dictArray[index];

				selectedWords.append(cand);
				selectedWords.append('\n');

				PrintWriter outDoun = new PrintWriter(new FileWriter(DICT_DOWN_PATH, true));
				outDoun.println(cand);
				dict.remove(cand);
				outDoun.close();
			}
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}
		return selectedWords.toString();
	}

	public boolean updateTitle(String wordTitle, String newTitle) {
		boolean isUpdated = false;
		Word word = wordMap.get(newTitle);
		if (word == null) {
			word = wordMap.get(wordTitle);
			if (word != null) {
				wordMap.remove(wordTitle);
				word.title = newTitle;
				wordMap.put(newTitle, word);
				isUpdated = true;
			}
			else {
				JOptionPane.showMessageDialog(null, "Word not found " + wordTitle);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Word already exists " + newTitle);
		}
		return isUpdated;
	}

	public int getRemainingNewCount() {
		int retCount = 0;
		for (Word word : wordMap.values()) {
			if (word.isNew) {
				++retCount;
			}
		}
		return retCount;
	}

	public Word findWord(String wordTitle) {
		return wordMap.get(wordTitle);
	}

	private Date clearDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		cal.clear();
		cal.set(year, month, day);
		return cal.getTime();
	}

	private long calcDurationDays(Date to, Date from) {
		long durDays = clearDate(to).getTime() - clearDate(from).getTime();
		durDays = Math.round(((double) durDays) / DAY) * DAY;
		return durDays;
	}

	private int updateNewWords(int newWordCount) {
		if (newWordCount <= 0) {
			return 0;
		}
		int retWordCount = newWordCount;

		for (Iterator<Word> it = wordMap.values().iterator(); it.hasNext() && retWordCount > 0;) {
			Word word = it.next();
			if (word.isNew) {
				word.isNew = false;
				--retWordCount;
			}
		}

		return retWordCount;
	}

	private void saveXml(Document xmlDoc, String path) {
		FileOutputStream ostr = null;
		try {
			ostr = new FileOutputStream(path);
			DOMSource domSrc = new DOMSource(xmlDoc);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(domSrc, new StreamResult(ostr));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (ostr != null) {
					ostr.close();
				}
			}
			catch (IOException e) {
				new RuntimeException(e);
			}
		}
	}

	private Document loadXml(String path) {
		FileInputStream istr = null;
		try {
			istr = new FileInputStream(path);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder builder = docFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new InputSource(istr));
			return xmlDoc;
		}
		catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		catch (SAXException e) {
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (istr != null) {
					istr.close();
				}
			}
			catch (IOException e) {
				new RuntimeException(e);
			}
		}
	}

}
