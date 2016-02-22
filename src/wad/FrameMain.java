package wad;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FrameMain extends JFrame {
	private static final long serialVersionUID = 1L;
	private Manager manager;
	private Word word;

	private final JTextField jTextTitle = new JTextField();

	private final JTextArea jTextDef = new JTextArea();

	private final JTextArea jTextExample = new JTextArea();
	private final JButton jButtonShow = new JButton("Show");
	private final JButton jButtonNext = new JButton("Next");
	private final JButton jButtonYes = new JButton("Yes");
	private final JButton jButtonNo = new JButton("No");
	private final JButton jButtonAdd = new JButton("Add");
	private final JTextField jTextResp = new JTextField();
	private final JLabel jLabelStats = new JLabel();
	private final JLabel jLabelStats2 = new JLabel();
	private final JButton jButtonClear = new JButton("Clear");
	private final JTextField jTextNew = new JTextField("0");
	private final JCheckBox jCheckNew = new JCheckBox("New");
	private final JButton jButtonReview = new JButton("Review");
	private final JButton jButtonMine = new JButton("Mine");
	private final JTextArea jTextMine = new JTextArea();
	private final JScrollPane jScrollMine = new JScrollPane(jTextMine);
	private final JButton jButtonFind = new JButton("Find");
	private final JButton jButtonChange = new JButton("Change");
	private final JButton jButtonUpdate = new JButton("Update");
	private final JButton jButtonSave = new JButton("Save");

	public FrameMain(String configPath) {
		try {
			manager = new Manager(configPath);
			jbInit();
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(FrameMain.this, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void jbInit() throws Exception {
		Container cont = getContentPane();
		cont.setLayout(null);
		setTitle("WAD 63");
		setSize(new Dimension(700, 650));
		setResizable(false);

		Font fontDialog14 = new Font("Dialog", Font.BOLD, 14);
		Font fontTahoma16 = new Font("Tahoma", Font.PLAIN, 16);

		jTextResp.setBounds(new Rectangle(15, 10, 665, 25));
		jTextResp.setFont(fontTahoma16);
		jTextResp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onShow(e);
			}
		});

		jButtonShow.setText("Show");
		jButtonShow.setBounds(new Rectangle(200, 40, 80, 25));
		jButtonShow.setFont(fontDialog14);
		jButtonShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onShow(e);
			}
		});

		jButtonYes.setText("Yes");
		jButtonYes.setBounds(new Rectangle(300, 40, 80, 25));
		jButtonYes.setFont(fontDialog14);
		jButtonYes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onYes(e);
			}
		});

		jButtonNo.setText("No");
		jButtonNo.setBounds(new Rectangle(400, 40, 80, 25));
		jButtonNo.setFont(fontDialog14);
		jButtonNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onNo(e);
			}
		});

		jButtonNext.setText("Next");
		jButtonNext.setBounds(new Rectangle(500, 40, 80, 25));
		jButtonNext.setFont(fontDialog14);
		jButtonNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onNext(e);
			}
		});

		jTextNew.setText("0");
		jTextNew.setBounds(new Rectangle(595, 40, 25, 25));
		jTextNew.setFont(fontTahoma16);

		jCheckNew.setText("New");
		jCheckNew.setBounds(new Rectangle(625, 40, 60, 25));
		jCheckNew.setFont(fontDialog14);

		jTextTitle.setBounds(new Rectangle(15, 70, 665, 25));
		jTextTitle.setFont(fontTahoma16);

		jTextDef.setBounds(new Rectangle(15, 110, 515, 205));
		jTextDef.setLineWrap(true);
		jTextDef.setWrapStyleWord(true);
		jTextDef.setFont(fontTahoma16);

		jTextExample.setBounds(new Rectangle(15, 335, 515, 205));
		jTextExample.setLineWrap(true);
		jTextExample.setWrapStyleWord(true);
		jTextExample.setFont(fontTahoma16);

		jButtonAdd.setText("Add");
		jButtonAdd.setBounds(new Rectangle(295, 550, 85, 25));
		jButtonAdd.setFont(fontDialog14);
		jButtonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onAdd(e);
			}
		});

		jLabelStats.setText("");
		jLabelStats.setBounds(new Rectangle(15, 555, 330, 15));
		jLabelStats.setFont(new Font("Verdana", Font.BOLD, 16));

		jLabelStats2.setText("");
		jLabelStats2.setBounds(new Rectangle(15, 590, 330, 15));
		jLabelStats2.setFont(new Font("Verdana", Font.BOLD, 14));

		jButtonClear.setText("Clear");
		jButtonClear.setBounds(new Rectangle(395, 585, 85, 25));
		jButtonClear.setFont(fontDialog14);
		jButtonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClear(e);
			}
		});

		jButtonReview.setText("Rev");
		jButtonReview.setBounds(new Rectangle(495, 585, 85, 25));
		jButtonReview.setFont(fontDialog14);
		jButtonReview.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onReview(e);
			}
		});

		jButtonMine.setText("Mine");
		jButtonMine.setBounds(new Rectangle(595, 585, 85, 25));
		jButtonMine.setFont(fontDialog14);
		jButtonMine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onMine(e);
			}
		});
		jScrollMine.setBounds(new Rectangle(545, 110, 135, 430));
		jTextMine.setFont(fontTahoma16);
		jTextMine.setLineWrap(true);

		jButtonFind.setText("Find");
		jButtonFind.setBounds(new Rectangle(395, 550, 85, 25));
		jButtonFind.setFont(fontDialog14);
		jButtonFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onFind();
			}
		});

		jButtonChange.setText("Title");
		jButtonChange.setBounds(new Rectangle(495, 550, 85, 25));
		jButtonChange.setFont(fontDialog14);
		jButtonChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onChangeTitle();
			}
		});

		jButtonUpdate.setText("Update");
		jButtonUpdate.setBounds(new Rectangle(595, 550, 85, 25));
		jButtonUpdate.setFont(fontDialog14);
		jButtonUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onUpdate();
			}
		});

		jButtonSave.setText("Save");
		jButtonSave.setBounds(new Rectangle(295, 585, 85, 25));
		jButtonSave.setFont(fontDialog14);
		jButtonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onSave();
			}
		});

		cont.add(jCheckNew, null);
		cont.add(jTextNew, null);
		cont.add(jButtonReview, null);
		cont.add(jButtonClear, null);
		cont.add(jLabelStats, null);
		cont.add(jLabelStats2, null);
		cont.add(jTextResp, null);
		cont.add(jButtonAdd, null);
		cont.add(jButtonNo, null);
		cont.add(jButtonYes, null);
		cont.add(jButtonNext, null);
		cont.add(jButtonShow, null);
		cont.add(jTextExample, null);
		cont.add(jTextDef, null);
		cont.add(jTextTitle, null);
		cont.add(jButtonMine, null);
		cont.add(jScrollMine, null);
		cont.add(jButtonFind, null);
		cont.add(jButtonChange, null);
		cont.add(jButtonUpdate, null);
		cont.add(jButtonSave, null);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				try {
					if (manager.getDataHashCode() != manager.getSavedHashCode()) {
						manager.save();
					}
					FrameMain.this.dispose();
				}
				catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(FrameMain.this, e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		updateStats();
		showCountByDay();
	}

	private void onShow(ActionEvent e) {
		if (word == null) {
			return;
		}
		jTextTitle.setText(word.title);
		jTextExample.setText(word.example);
		String title = word.title.split("\\d")[0];
		if (jTextResp.getText().equals(title)) {
			jTextResp.setForeground(new Color(0, 192, 0));
			jButtonYes.requestFocusInWindow();
		}
		else {
			jTextResp.setForeground(new Color(192, 0, 0));
		}
	}

	private void onNext(ActionEvent e) {
		int newCount = 0;
		try {
			newCount = Integer.parseInt(jTextNew.getText());
		}
		catch (NumberFormatException ex) {
			newCount = 0;
		}
		word = manager.getNextWord(newCount, jCheckNew.isSelected());
		jTextNew.setText(Integer.toString(manager.getNewCount()));
		jTextTitle.setText("");
		jTextResp.setText("");
		jTextDef.setText(word == null ? "" : word.definition);
		jTextExample.setText("");
		jButtonYes.setSelected(false);
		jButtonNo.setSelected(false);
		jTextResp.setForeground(Color.black);
		if (word == null) {
			jTextDef.setText("");
		}
		else {
			jTextDef.setText((manager.isGraduation(word) ? "***GRADUATION***\n" : "") + word.definition);
		}
		updateStats();
		jTextResp.requestFocusInWindow();
	}

	private void updateStats() {
		int[] stats = manager.getStats();
		StringBuilder statsStr = new StringBuilder();
		int total = 0;
		for (int i = 0; i < stats.length; ++i) {
			long count = stats[i];
			total += count;
			if (i > 3) {
				statsStr.append(Manager.intervalLbl[i]);
				statsStr.append(":");
				statsStr.append(count);
				statsStr.append(" ");
			}
		}
		statsStr.append("C:");
		statsStr.append(manager.getCandCount());

		StringBuilder statsStr2 = new StringBuilder();
		statsStr2.append("T:" + (manager.getGraduatedCount() + total) + " ");
		statsStr2.append("G:" + manager.getGraduatedCount() + " ");
		statsStr2.append(total);
		statsStr2.append("(");
		statsStr2.append(manager.getRemainingNewCount());
		statsStr2.append(")");

		jLabelStats.setText(statsStr.toString());
		jLabelStats2.setText(statsStr2.toString());
	}

	private void onAdd(ActionEvent e) {
		Word newWord = new Word();
		newWord.title = jTextTitle.getText().trim();
		newWord.definition = jTextDef.getText();
		newWord.example = jTextExample.getText();
		newWord.isNew = true;
		if (manager.addWord(newWord, jCheckNew.isSelected())) {
			updateStats();
			clearWord();
		}
	}

	private void onYes(ActionEvent e) {
		if (word == null) {
			return;
		}
		Word graduatedWord = manager.addAttempt(word.title, new Date(), true);
		if (graduatedWord != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("GRADUATED!");
			sb.append("\n\n");
			sb.append(graduatedWord.title);
			sb.append("\n\n");
			sb.append(graduatedWord.definition);
			sb.append("\n\n");
			sb.append(graduatedWord.example);
			jTextMine.setText(sb.toString());
		}
		onNext(e);
	}

	private void onNo(ActionEvent e) {
		if (word == null) {
			return;
		}
		manager.addAttempt(word.title, new Date(), false);
		onNext(e);
	}

	private void onReview(ActionEvent e) {
		manager.prepareReviewList();
	}

	private void onClear(ActionEvent e) {
		clearWord();
		jTextMine.setText("");
	}

	private void clearWord() {
		jTextTitle.setText("");
		jTextResp.setText("");
		jTextDef.setText("");
		jTextExample.setText("");
		jTextResp.setForeground(Color.black);
		word = null;
	}

	private void onMine(ActionEvent e) {
		String selectedWords = manager.mine();
		jTextMine.setText(selectedWords);
	}

	private void onFind() {
		String wordTitle = jTextResp.getText().trim();
		Word foundWord = manager.findWord(wordTitle);
		if (foundWord != null) {
			word = foundWord;
			jTextTitle.setText(word.title);
			jTextDef.setText(word.definition);
			jTextExample.setText(word.example);
		}
	}

	private void onChangeTitle() {
		String title = jTextTitle.getText().trim();
		String newTitle = jTextResp.getText().trim();
		if (title.isEmpty() || newTitle.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Title is empty", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (manager.updateTitle(title, newTitle)) {
			jTextTitle.setText(jTextResp.getText());
		}
	}

	private void onUpdate() {
		if (word != null) {
			word.definition = jTextDef.getText();
			word.example = jTextExample.getText();
		}
	}

	private void showCountByDay() {
		TreeMap<Integer, Integer[]> countByDay = manager.getCountByDay();
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, Integer[]> entry : countByDay.descendingMap().entrySet()) {
			sb.append(entry.getKey());
			sb.append(" : ");
			if (entry.getValue()[0] != null) {
				sb.append(entry.getValue()[0]);
			}
			if (entry.getValue()[1] != null) {
				sb.append("(");
				sb.append(entry.getValue()[1]);
				sb.append(")");
			}
			sb.append('\n');
		}
		jTextMine.setText(sb.toString());
	}

	private void onSave() {
		try {
			if (manager.getDataHashCode() != manager.getSavedHashCode()) {
				manager.save();
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(FrameMain.this, ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
