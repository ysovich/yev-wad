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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
	private String ATTEMPTS_PATH;
	private String DICT_PATH;
	private String DICT_DOWN_PATH;

	private Map<String, WordHistory> historyMap;
	private int stats[];
	private int oldCount;

	public Manager(String configPath) {
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

	public void load() {
		FileInputStream istr = null;
		try {
			istr = new FileInputStream(WORDS_PATH);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder builder = docFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new InputSource(istr));
			istr.close();
			istr = null;

			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();

			historyMap = new HashMap<String, WordHistory>();
			NodeList nodeList = (NodeList) xpath.evaluate("/words/word", xmlDoc, XPathConstants.NODESET);
			int len = nodeList.getLength();
			for (int i = 0; i < len; ++i) {
				Element wordEl = (Element) nodeList.item(i);
				Word word = new Word();
				word.title = wordEl.getAttribute("title");
				Element defEl = (Element) xpath.evaluate("./definition", wordEl, XPathConstants.NODE);
				word.definition = defEl.getTextContent();
				Element exampleEl = (Element) xpath.evaluate("./example", wordEl, XPathConstants.NODE);
				word.example = exampleEl.getTextContent();

				WordHistory wordHistory = new WordHistory();
				wordHistory.word = word;
				wordHistory.attemptList = new ArrayList<Attempt>();
				historyMap.put(word.title, wordHistory);
			}

			istr = new FileInputStream(ATTEMPTS_PATH);
			xmlDoc = builder.parse(new InputSource(istr));
			istr.close();
			istr = null;
			nodeList = (NodeList) xpath.evaluate("/attempts/attempt", xmlDoc, XPathConstants.NODESET);
			len = nodeList.getLength();
			for (int i = 0; i < len; ++i) {
				Element attemptEl = (Element) nodeList.item(i);
				Attempt attempt = new Attempt();
				attempt.wordTitle = attemptEl.getAttribute("wordTitle");
				attempt.date = dateFormat.parse(attemptEl.getAttribute("date"));
				attempt.correct = Boolean.parseBoolean(attemptEl.getAttribute("correct"));

				WordHistory wordHistory = historyMap.get(attempt.wordTitle);
				wordHistory.attemptList.add(attempt);
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		catch (SAXException e) {
			throw new RuntimeException(e);
		}
		catch (ParseException e) {
			throw new RuntimeException(e);
		}
		catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (istr != null) {
				try {
					istr.close();
				}
				catch (IOException e) {
					new RuntimeException(e);
				}
			}
		}
	}

	public void addWord(Word word) {
		if (word.title == null || word.title.length() == 0) {
			JOptionPane.showMessageDialog(null, "Missing title");
			return;
		}
		if (historyMap.containsKey(word.title)) {
			JOptionPane.showMessageDialog(null, "Title already exist");
			return;
		}
		FileInputStream istr = null;
		FileOutputStream ostr = null;
		try {
			istr = new FileInputStream(WORDS_PATH);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder builder = docFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new InputSource(istr));
			istr.close();
			istr = null;

			Element docEl = xmlDoc.getDocumentElement();
			Element wordEl = xmlDoc.createElement("word");
			docEl.appendChild(wordEl);
			wordEl.setAttribute("title", word.title);
			Element defEl = xmlDoc.createElement("definition");
			wordEl.appendChild(defEl);
			defEl.setTextContent(word.definition);
			Element exampleEl = xmlDoc.createElement("example");
			wordEl.appendChild(exampleEl);
			exampleEl.setTextContent(word.example);

			ostr = new FileOutputStream(WORDS_PATH);
			DOMSource domSrc = new DOMSource(xmlDoc);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform(domSrc, new StreamResult(ostr));
			ostr.close();
			ostr = null;

			load();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		catch (SAXException e) {
			throw new RuntimeException(e);
		}
		catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (istr != null) {
					istr.close();
				}
				if (istr != null) {
					ostr.close();
				}
			}
			catch (IOException e) {
				new RuntimeException(e);
			}
		}
	}

	public void removeWord(String wordTitle) {
		FileInputStream istr = null;
		FileOutputStream ostr = null;
		try {
			istr = new FileInputStream(WORDS_PATH);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder builder = docFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new InputSource(istr));
			istr.close();
			istr = null;

			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();

			Element wordEl = null;
			NodeList nodeList = (NodeList) xpath.evaluate("/words/word", xmlDoc, XPathConstants.NODESET);
			if (nodeList != null) {
				for (int i = 0; i < nodeList.getLength(); ++i) {
					Element el = (Element) nodeList.item(i);
					if (wordTitle.equals(el.getAttribute("title"))) {
						wordEl = el;
						break;
					}
				}
			}

			if (wordEl != null) {
				wordEl.getParentNode().removeChild(wordEl);

				ostr = new FileOutputStream(WORDS_PATH);
				DOMSource domSrc = new DOMSource(xmlDoc);
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer();
				transformer.transform(domSrc, new StreamResult(ostr));
				ostr.close();
				ostr = null;

				removeAllAttempts(wordTitle);

				load();
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		catch (SAXException e) {
			throw new RuntimeException(e);
		}
		catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
		catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (istr != null) {
					istr.close();
				}
				if (istr != null) {
					ostr.close();
				}
			}
			catch (IOException e) {
				new RuntimeException(e);
			}
		}
	}

	public void addAttempt(Attempt attempt) {
		FileInputStream istr = null;
		FileOutputStream ostr = null;
		try {
			istr = new FileInputStream(ATTEMPTS_PATH);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder builder = docFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new InputSource(istr));
			istr.close();
			istr = null;

			Element docEl = xmlDoc.getDocumentElement();

			boolean graduated = false;
			if (attempt.correct) {
				Date yearAgo = new Date(System.currentTimeMillis() - YEAR);
				WordHistory wordHist = historyMap.get(attempt.wordTitle);
				for (Attempt prevAttempt : wordHist.attemptList) {
					if (prevAttempt.correct && prevAttempt.date.before(yearAgo)) {
						graduated = JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Graduated!",
								"Graduated", JOptionPane.YES_NO_OPTION);
						break;
					}
				}
			}

			if (!attempt.correct || graduated) {
				NodeList nodeList = docEl.getChildNodes();
				for (int i = 0; i < nodeList.getLength(); ++i) {
					Node node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						if (attempt.wordTitle.equals(((Element) node).getAttribute("wordTitle"))) {
							docEl.removeChild(node);
						}
					}
				}
			}
			if (graduated) {
				removeWord(attempt.wordTitle);
			}
			else {
				Element attemptEl = xmlDoc.createElement("attempt");
				docEl.appendChild(attemptEl);
				attemptEl.setAttribute("wordTitle", attempt.wordTitle);
				attemptEl.setAttribute("date", dateFormat.format(attempt.date));
				attemptEl.setAttribute("correct", attempt.correct.toString());
			}

			ostr = new FileOutputStream(ATTEMPTS_PATH);
			DOMSource domSrc = new DOMSource(xmlDoc);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform(domSrc, new StreamResult(ostr));
			ostr.close();
			ostr = null;

			load();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		catch (SAXException e) {
			throw new RuntimeException(e);
		}
		catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (istr != null) {
					istr.close();
				}
				if (istr != null) {
					ostr.close();
				}
			}
			catch (IOException e) {
				new RuntimeException(e);
			}
		}
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
	public Word getNextWord(int newOldCount) {
		oldCount = newOldCount;
		stats = new int[interval.length];
		List<Word> candList = new ArrayList<Word>();
		List<Word> doneList = new ArrayList<Word>();
		for (Map.Entry<String, WordHistory> entry : historyMap.entrySet()) {
			WordHistory hist = entry.getValue();
			long dur = 0;
			Date now = new Date();
			Date lastDate = now;
			if (hist.attemptList.size() > 1) {
				Attempt prev = hist.attemptList.get(0);
				for (int i = 1; i < hist.attemptList.size(); ++i) {
					Attempt cur = hist.attemptList.get(i);
					if (cur.correct) {
						long curDur = calcDurationDays(cur.date, prev.date);
						if (curDur == 0) {
							curDur = cur.date.getTime() - prev.date.getTime();
						}
						if (curDur > dur) {
							dur = curDur;
						}
					}
					else {
						dur = 0;
					}
					lastDate = cur.date;
					prev = cur;
				}
			}

			if (dur > 0) {
				if (dur >= interval[0]) {
					++stats[0];
					Date firstAttemptDate = hist.attemptList.get(0).date;
					if (now.getTime() - firstAttemptDate.getTime() > YEAR) {
						candList.add(hist.word);
					}
					else if (now.getTime() - lastDate.getTime() > DAY * 90) {
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

		Word selWord = null;
		Random rnd = new Random();
		if (!candList.isEmpty()) {
			selWord = candList.get(rnd.nextInt(candList.size()));
		}
		if (oldCount > 0 && selWord == null && !doneList.isEmpty()) {
			selWord = doneList.get(rnd.nextInt(doneList.size()));
			--oldCount;
		}

		return selWord;
	}

	public void removeAllAttempts(String wordTitle) {
		FileInputStream istr = null;
		FileOutputStream ostr = null;
		try {
			istr = new FileInputStream(ATTEMPTS_PATH);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder builder = docFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new InputSource(istr));
			istr.close();
			istr = null;

			Element docEl = xmlDoc.getDocumentElement();

			NodeList nodeList = docEl.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); ++i) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					if (wordTitle.equals(((Element) node).getAttribute("wordTitle"))) {
						docEl.removeChild(node);
					}
				}
			}

			ostr = new FileOutputStream(ATTEMPTS_PATH);
			DOMSource domSrc = new DOMSource(xmlDoc);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform(domSrc, new StreamResult(ostr));
			ostr.close();
			ostr = null;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		catch (SAXException e) {
			throw new RuntimeException(e);
		}
		catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (istr != null) {
					istr.close();
				}
				if (istr != null) {
					ostr.close();
				}
			}
			catch (IOException e) {
				new RuntimeException(e);
			}
		}
	}

	public int[] getStats() {
		return stats;
	}

	public void setOldCount(int oldCount) {
		this.oldCount = oldCount;
	}

	public int getOldCount() {
		return oldCount;
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

	void updateTitle(String wordTitle, String newTitle) {
		FileInputStream istr = null;
		FileOutputStream ostr = null;
		try {
			istr = new FileInputStream(WORDS_PATH);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder builder = docFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new InputSource(istr));
			istr.close();
			istr = null;

			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();

			Element wordEl = null;
			NodeList nodeList = (NodeList) xpath.evaluate("/words/word", xmlDoc, XPathConstants.NODESET);
			if (nodeList != null) {
				for (int i = 0; i < nodeList.getLength(); ++i) {
					Element el = (Element) nodeList.item(i);
					if (wordTitle.equals(el.getAttribute("title"))) {
						wordEl = el;
						break;
					}
				}
			}

			if (wordEl != null) {
				wordEl.setAttribute("title", newTitle);

				ostr = new FileOutputStream(WORDS_PATH);
				DOMSource domSrc = new DOMSource(xmlDoc);
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer();
				transformer.transform(domSrc, new StreamResult(ostr));
				ostr.close();
				ostr = null;

				updateAllAttempts(wordTitle, newTitle);

				load();
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		catch (SAXException e) {
			throw new RuntimeException(e);
		}
		catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
		catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (istr != null) {
					istr.close();
				}
				if (istr != null) {
					ostr.close();
				}
			}
			catch (IOException e) {
				new RuntimeException(e);
			}
		}
	}

	public void updateAllAttempts(String wordTitle, String newTitle) {
		FileInputStream istr = null;
		FileOutputStream ostr = null;
		try {
			istr = new FileInputStream(ATTEMPTS_PATH);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder builder = docFactory.newDocumentBuilder();
			Document xmlDoc = builder.parse(new InputSource(istr));
			istr.close();
			istr = null;

			Element docEl = xmlDoc.getDocumentElement();

			NodeList nodeList = docEl.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); ++i) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if (wordTitle.equals(element.getAttribute("wordTitle"))) {
						element.setAttribute("wordTitle", newTitle);
					}
				}
			}

			ostr = new FileOutputStream(ATTEMPTS_PATH);
			DOMSource domSrc = new DOMSource(xmlDoc);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform(domSrc, new StreamResult(ostr));
			ostr.close();
			ostr = null;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		catch (SAXException e) {
			throw new RuntimeException(e);
		}
		catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (istr != null) {
					istr.close();
				}
				if (istr != null) {
					ostr.close();
				}
			}
			catch (IOException e) {
				new RuntimeException(e);
			}
		}
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

}
