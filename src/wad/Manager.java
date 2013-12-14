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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Manager {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	private static final XPath xpath = XPathFactory.newInstance().newXPath();
	private static final long MINUTE = 1000 * 60;
	private static final long HOUR = 60 * MINUTE;
	private static final long DAY = 24 * HOUR;
	private static final long YEAR = 365 * DAY;
	private static final long interval[] = { 28 * DAY, 7 * DAY, 3 * DAY, DAY, HOUR, MINUTE, 0 };
	public static final String intervalLbl[] = { "Y", "M", "W", "T", "D", "H", "M" };
	private String WORDS_PATH;
	private String ATTEMPTS_PATH;
	private String DICT_PATH;
	private String DICT_DOWN_PATH;

	private LinkedHashMap<String, WordHistory> historyMap;
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
			ATTEMPTS_PATH = prop.getProperty("attempts");
			DICT_PATH = prop.getProperty("dict");
			DICT_DOWN_PATH = prop.getProperty("down");
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		load();
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

	public void load() {
		try {
			Document xmlDoc = loadXml(WORDS_PATH);
			historyMap = new LinkedHashMap<String, WordHistory>();
			NodeList nodeList = (NodeList) xpath.evaluate("/words/word", xmlDoc, XPathConstants.NODESET);
			int len = nodeList.getLength();
			for (int i = 0; i < len; ++i) {
				Element wordEl = (Element) nodeList.item(i);
				Word word = new Word();
				word.title = wordEl.getAttribute("title");
				word.isNew = Boolean.TRUE.toString().equals(wordEl.getAttribute("new"));
				Element defEl = (Element) xpath.evaluate("./definition", wordEl, XPathConstants.NODE);
				word.definition = defEl.getTextContent();
				Element exampleEl = (Element) xpath.evaluate("./example", wordEl, XPathConstants.NODE);
				word.example = exampleEl.getTextContent();

				WordHistory wordHistory = new WordHistory();
				wordHistory.word = word;
				historyMap.put(word.title, wordHistory);
			}

			xmlDoc = loadXml(ATTEMPTS_PATH);
			nodeList = (NodeList) xpath.evaluate("/attempts/attempt", xmlDoc, XPathConstants.NODESET);
			len = nodeList.getLength();
			for (int i = 0; i < len; ++i) {
				Element attemptEl = (Element) nodeList.item(i);
				Attempt attempt = new Attempt();
				attempt.wordTitle = attemptEl.getAttribute("wordTitle");
				attempt.date = dateFormat.parse(attemptEl.getAttribute("date"));

				WordHistory wordHistory = historyMap.get(attempt.wordTitle);
				if (wordHistory == null) {
					throw new RuntimeException(attempt.wordTitle);
				}
				wordHistory.attemptList.add(attempt);
			}
		}
		catch (ParseException e) {
			throw new RuntimeException(e);
		}
		catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	public void save() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder builder = docFactory.newDocumentBuilder();
			Document xmlDocWords = builder.newDocument();
			Element wordsEl = xmlDocWords.createElement("words");
			xmlDocWords.appendChild(wordsEl);

			Document xmlDocAttempts = builder.newDocument();
			Element attemptsEl = xmlDocAttempts.createElement("attempts");
			xmlDocAttempts.appendChild(attemptsEl);

			for (WordHistory wordHistory : historyMap.values()) {
				Element wordEl = xmlDocWords.createElement("word");
				wordsEl.appendChild(wordEl);
				wordEl.setAttribute("title", wordHistory.word.title);
				if (wordHistory.word.isNew) {
					wordEl.setAttribute("new", Boolean.toString(wordHistory.word.isNew));
				}
				Element defEl = xmlDocWords.createElement("definition");
				wordEl.appendChild(defEl);
				defEl.setTextContent(wordHistory.word.definition);
				Element exampleEl = xmlDocWords.createElement("example");
				wordEl.appendChild(exampleEl);
				exampleEl.setTextContent(wordHistory.word.example);

				for (Attempt attempt : wordHistory.attemptList) {
					Element attemptEl = xmlDocAttempts.createElement("attempt");
					attemptsEl.appendChild(attemptEl);
					attemptEl.setAttribute("wordTitle", attempt.wordTitle);
					attemptEl.setAttribute("date", dateFormat.format(attempt.date));
				}
			}

			saveXml(xmlDocWords, WORDS_PATH);
			saveXml(xmlDocAttempts, ATTEMPTS_PATH);
		}
		catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public void addWord(Word word) {
		if (word.title == null || word.title.length() == 0) {
			JOptionPane.showMessageDialog(null, "Missing title");
			return;
		}
		if (word.definition == null || word.definition.length() == 0) {
			JOptionPane.showMessageDialog(null, "Missing definition");
			return;
		}
		if (historyMap.containsKey(word.title)) {
			JOptionPane.showMessageDialog(null, "Title already exist");
			return;
		}
		WordHistory wordHistory = new WordHistory();
		wordHistory.word = word;
		historyMap.put(word.title, wordHistory);
	}

	public void removeWord(String wordTitle) {
		historyMap.remove(wordTitle);
	}

	public void addAttempt(Attempt attempt, boolean isCorrect) {
		WordHistory wordHist = historyMap.get(attempt.wordTitle);
		boolean graduated = false;
		if (isCorrect) {
			Date now = new Date();
			for (Attempt prevAttempt : wordHist.attemptList) {
				if (calcDurationDays(now, prevAttempt.date) > YEAR) {
					int confirm = JOptionPane.showConfirmDialog(null, "Graduated!", "Graduated",
							JOptionPane.YES_NO_OPTION);
					graduated = JOptionPane.YES_OPTION == confirm;
					break;
				}
			}
		}

		if (graduated) {
			historyMap.remove(attempt.wordTitle);
		}
		else if (isCorrect) {
			wordHist.attemptList.add(attempt);
		}
		else {
			wordHist.attemptList.clear();
		}
		lastAttempts.addFirst(attempt.wordTitle);
		lastAttempts.pollLast();
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
		stats = new int[interval.length];
		ArrayList<Word> candList = new ArrayList<Word>();
		ArrayList<Word> doneList = new ArrayList<Word>();
		for (WordHistory hist : historyMap.values()) {
			if (hist.word.isNew) {
				continue;
			}
			long dur = 0;
			Date now = new Date();
			Date lastDate = now;
			if (hist.attemptList.size() > 1) {
				Attempt prev = hist.attemptList.get(0);
				for (int i = 1; i < hist.attemptList.size(); ++i) {
					Attempt cur = hist.attemptList.get(i);
					long curDur = calcDurationDays(cur.date, prev.date);
					if (curDur == 0) {
						curDur = cur.date.getTime() - prev.date.getTime();
					}
					if (curDur > dur) {
						dur = curDur;
					}
					lastDate = cur.date;
					prev = cur;
				}
			}

			if (dur > 0) {
				if (dur >= interval[0]) {
					++stats[0];
					Date firstAttemptDate = hist.attemptList.get(0).date;
					if (calcDurationDays(now, firstAttemptDate) > YEAR) {
						candList.add(hist.word);
					}
					else if (calcDurationDays(now, lastDate) > DAY * 90) {
						candList.add(hist.word);
					}
					else {
						doneList.add(hist.word);
					}
				}
				else {
					for (int i = 1; i < interval.length; ++i) {
						if (dur >= interval[i]) {
							if (interval[i] >= HOUR) {
								if (calcDurationDays(now, lastDate) >= interval[i - 1]) {
									candList.add(hist.word);
								}
							}
							else {
								if (now.getTime() - lastDate.getTime() >= interval[i - 1]) {
									candList.add(hist.word);
								}
							}
							++stats[i];
							break;
						}
					}
				}
			}
			else {
				candList.add(hist.word);
				++stats[stats.length - 1];
			}
		}

		candCount = candList.size();

		Word selWord = null;
		Random rnd = new Random();
		if (!candList.isEmpty()) {
			for (String lastAttempt : lastAttempts) {
				if (candList.size() > 1) {
					WordHistory wordHist = historyMap.get(lastAttempt);
					if (wordHist != null) {
						candList.remove(wordHist.word);
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
		WordHistory wordHistory = historyMap.get(newTitle);
		if (wordHistory == null) {
			wordHistory = historyMap.get(wordTitle);
			if (wordHistory != null) {
				historyMap.remove(wordTitle);
				wordHistory.word.title = newTitle;
				for (Attempt attempt : wordHistory.attemptList) {
					attempt.wordTitle = newTitle;
				}
				historyMap.put(newTitle, wordHistory);
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
		for (WordHistory wordHistory : historyMap.values()) {
			if (wordHistory.word.isNew) {
				++retCount;
			}
		}
		return retCount;
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

		for (Iterator<WordHistory> it = historyMap.values().iterator(); it.hasNext()
				&& retWordCount > 0;) {
			WordHistory wordHistory = it.next();
			if (wordHistory.word.isNew) {
				wordHistory.word.isNew = false;
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
