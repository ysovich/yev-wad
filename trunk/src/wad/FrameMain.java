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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class FrameMain extends JFrame {
	private static final long serialVersionUID = 1L;
	private Manager manager;
	private Word word;
	private final JLabel jLabelTitle = new JLabel("Title");
	private final JTextField jTextTitle = new JTextField();
	private final JLabel jLabelDef = new JLabel("Definition");
	private final JTextArea jTextDef = new JTextArea();
	private final JLabel jLabelExample = new JLabel("Example");
	private final JTextArea jTextExample = new JTextArea();
	private final JButton jButtonShow = new JButton("Show");
	private final JButton jButtonNext = new JButton("Next");
	private final JRadioButton jRadioYes = new JRadioButton("Yes");
	private final JRadioButton jRadioNo = new JRadioButton("No");
	private final JButton jButtonUndo = new JButton("Undo");
	private final JButton jButtonAdd = new JButton("Add");
	private final JTextField jTextResp = new JTextField();
	private final JLabel jLabelStats = new JLabel();
	private final JLabel jLabelStats2 = new JLabel();
	private final JButton jButtonClear = new JButton("Clear");
	private final JTextField jTextOld = new JTextField("0");
	private final JLabel jLabelOld = new JLabel();
	private final JTextField jTextNew = new JTextField("0");
	private final JLabel jLabelNew = new JLabel();
	private final JButton jButtonReview = new JButton("Review");
	private final JButton jButtonMine = new JButton("Mine");
	private final JTextArea jTextMine = new JTextArea();
	private final JScrollPane jScrollMine = new JScrollPane(jTextMine);
	private final JButton jButtonEdit = new JButton("Edit");

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
		setTitle("WAD 47");
		setSize(new Dimension(700, 620));
		setResizable(false);

		jTextResp.setBounds(new Rectangle(15, 10, 665, 25));
		jTextResp.setFont(new Font("Tahoma", 0, 16));

		jButtonShow.setText("Show");
		jButtonShow.setBounds(new Rectangle(420, 40, 80, 25));
		jButtonShow.setEnabled(false);
		jButtonShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onShow(e);
			}
		});
		jRadioYes.setText("Yes");
		jRadioYes.setBounds(new Rectangle(510, 45, 50, 15));
		jRadioYes.setEnabled(false);
		jRadioYes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onYes(e);
			}
		});
		jRadioNo.setText("No");
		jRadioNo.setBounds(new Rectangle(560, 45, 40, 15));
		jRadioNo.setEnabled(false);
		jRadioNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onNo(e);
			}
		});
		jButtonUndo.setText("Undo");
		jButtonUndo.setBounds(new Rectangle(610, 40, 69, 25));
		jButtonUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onUndo(e);
			}
		});

		jLabelTitle.setText("Title");
		jLabelTitle.setBounds(new Rectangle(15, 50, 130, 15));
		jTextTitle.setBounds(new Rectangle(15, 70, 665, 25));
		jTextTitle.setFont(new Font("Tahoma", 0, 16));

		jButtonNext.setText("Next");
		jButtonNext.setBounds(new Rectangle(420, 100, 80, 25));
		jButtonNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onNext(e);
			}
		});
		jTextOld.setText("0");
		jTextOld.setBounds(new Rectangle(510, 100, 25, 25));
		jLabelOld.setText("Old");
		jLabelOld.setBounds(new Rectangle(535, 100, 25, 25));
		jTextNew.setText("0");
		jTextNew.setBounds(new Rectangle(560, 100, 25, 25));
		jLabelNew.setText("New");
		jLabelNew.setBounds(new Rectangle(585, 100, 25, 25));
		jButtonReview.setText("Rev");
		jButtonReview.setBounds(new Rectangle(620, 100, 59, 25));
		jButtonReview.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onReview(e);
			}
		});

		jLabelDef.setText("Definition");
		jLabelDef.setBounds(new Rectangle(15, 110, 200, 15));
		jTextDef.setBounds(new Rectangle(15, 130, 515, 175));
		jTextDef.setLineWrap(true);
		jTextDef.setWrapStyleWord(true);
		jTextDef.setFont(new Font("Tahoma", 0, 16));
		jLabelExample.setText("Example");
		jLabelExample.setBounds(new Rectangle(15, 315, 200, 15));
		jTextExample.setBounds(new Rectangle(15, 335, 515, 205));
		jTextExample.setLineWrap(true);
		jTextExample.setWrapStyleWord(true);
		jTextExample.setFont(new Font("Tahoma", 0, 16));

		jButtonAdd.setText("Add");
		jButtonAdd.setBounds(new Rectangle(335, 550, 75, 25));
		jButtonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onAdd(e);
			}
		});
		jLabelStats.setText("");
		jLabelStats.setBounds(new Rectangle(15, 548, 330, 15));
		jLabelStats.setFont(new Font("Verdana", Font.BOLD, 16));
		jLabelStats2.setText("");
		jLabelStats2.setBounds(new Rectangle(15, 568, 330, 15));
		jButtonClear.setText("Clear");
		jButtonClear.setBounds(new Rectangle(515, 550, 75, 25));
		jButtonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClear(e);
			}
		});

		jButtonMine.setText("Mine");
		jButtonMine.setBounds(new Rectangle(605, 550, 75, 25));
		jButtonMine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onMine(e);
			}
		});
		jScrollMine.setBounds(new Rectangle(545, 130, 135, 410));
		jTextMine.setFont(new Font("Tahoma", 0, 16));
		jTextMine.setLineWrap(true);
		jButtonEdit.setText("Edit");
		jButtonEdit.setBounds(new Rectangle(425, 550, 75, 25));
		jButtonEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onEdit(e);
			}
		});

		cont.add(jLabelNew, null);
		cont.add(jTextNew, null);
		cont.add(jButtonReview, null);
		cont.add(jLabelOld, null);
		cont.add(jTextOld, null);
		cont.add(jButtonClear, null);
		cont.add(jLabelStats, null);
		cont.add(jLabelStats2, null);
		cont.add(jTextResp, null);
		cont.add(jButtonAdd, null);
		cont.add(jRadioNo, null);
		cont.add(jRadioYes, null);
		cont.add(jButtonUndo, null);
		cont.add(jButtonNext, null);
		cont.add(jButtonShow, null);
		cont.add(jTextExample, null);
		cont.add(jLabelExample, null);
		cont.add(jTextDef, null);
		cont.add(jLabelDef, null);
		cont.add(jTextTitle, null);
		cont.add(jLabelTitle, null);
		cont.add(jButtonMine, null);
		cont.add(jScrollMine, null);
		cont.add(jButtonEdit, null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		class ClosingListener extends WindowAdapter {
			private int dataHashCode;

			public ClosingListener(int dataHashCode) {
				this.dataHashCode = dataHashCode;
			}

			@Override
			public void windowClosing(WindowEvent evt) {
				try {
					if (manager.getDataHashCode() == dataHashCode) {
						int confirm =
								JOptionPane.showConfirmDialog(FrameMain.this, "No change, save anyway?", "Save",
										JOptionPane.YES_NO_OPTION);
						if (JOptionPane.YES_OPTION == confirm) {
							manager.save();
						}
					}
					else {
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
		}
		ClosingListener closingListener = new ClosingListener(manager.getDataHashCode());
		addWindowListener(closingListener);
		updateStats();
		showCountByDay();
	}

	private void onShow(ActionEvent e) {
		jTextTitle.setText(word.title);
		jTextExample.setText(word.example);
		String title = word.title.split("\\d")[0];
		if (jTextResp.getText().equals(title)) {
			jTextResp.setForeground(new Color(0, 192, 0));
			jRadioYes.requestFocusInWindow();
		}
		else {
			jTextResp.setForeground(new Color(192, 0, 0));
			jRadioNo.requestFocusInWindow();
		}
	}

	private void onNext(ActionEvent e) {
		int oldCount = 0;
		try {
			oldCount = Integer.parseInt(jTextOld.getText());
		}
		catch (NumberFormatException ex) {
			oldCount = 0;
		}
		int newCount = 0;
		try {
			newCount = Integer.parseInt(jTextNew.getText());
		}
		catch (NumberFormatException ex) {
			newCount = 0;
		}
		word = manager.getNextWord(oldCount, newCount);
		jTextOld.setText(Integer.toString(manager.getOldCount()));
		jTextNew.setText(Integer.toString(manager.getNewCount()));
		jTextTitle.setText("");
		jTextResp.setText("");
		jTextDef.setText(word == null ? "" : word.definition);
		jTextExample.setText("");
		jRadioYes.setSelected(false);
		jRadioNo.setSelected(false);
		jTextResp.setForeground(Color.black);
		if (word == null) {
			jTextDef.setText("");
			jRadioYes.setEnabled(false);
			jRadioNo.setEnabled(false);
			jButtonShow.setEnabled(false);
		}
		else {
			jTextDef.setText((manager.isGraduation(word) ? "***GRADUATION***\n" : "") + word.definition);
			jRadioYes.setEnabled(true);
			jRadioNo.setEnabled(true);
			jButtonShow.setEnabled(true);
		}
		updateStats();
		jTextResp.requestFocusInWindow();
	}

	private void updateStats() {
		int[] stats = manager.getStats();
		StringBuilder statsStr = new StringBuilder();
		StringBuilder statsStr2 = new StringBuilder();
		statsStr2.append("G:" + manager.getGraduatedCount() + " ");
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
			else {
				statsStr2.append(Manager.intervalLbl[i]);
				statsStr2.append(":");
				statsStr2.append(count);
				statsStr2.append(" ");
			}
		}
		statsStr2.append(total);
		statsStr2.append("(");
		statsStr2.append(manager.getRemainingNewCount());
		statsStr2.append(")");
		statsStr.append("C:");
		statsStr.append(manager.getCandCount());
		jLabelStats.setText(statsStr.toString());
		jLabelStats2.setText(statsStr2.toString());
	}

	private void onAdd(ActionEvent e) {
		Word newWord = new Word();
		newWord.title = jTextTitle.getText();
		newWord.definition = jTextDef.getText();
		newWord.example = jTextExample.getText();
		newWord.isNew = true;
		if (manager.addWord(newWord)) {
			updateStats();
			clearWord();
		}
	}

	private void onYes(ActionEvent e) {
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
		jButtonNext.requestFocusInWindow();
	}

	private void onNo(ActionEvent e) {
		manager.addAttempt(word.title, new Date(), false);
		jButtonNext.requestFocusInWindow();
	}

	private void onUndo(ActionEvent e) {
		if (word != null) {
			if (manager.undoAttempt(word.title)) {
				jRadioYes.setSelected(false);
				jRadioNo.setSelected(false);
			}
		}
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
		jRadioYes.setSelected(false);
		jRadioNo.setSelected(false);
		jTextResp.setForeground(Color.black);
	}

	private void onMine(ActionEvent e) {
		String selectedWords = manager.mine();
		jTextMine.setText(selectedWords);
	}

	private void onEdit(ActionEvent e) {
		Object[] options = { "Find", "Change Title", "Update" };
		int choice =
				JOptionPane.showOptionDialog(this, "Choose edit operation", "Edit",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (0 == choice) {
			onFind();
		}
		else if (1 == choice) {
			onChangeTitle();
		}
		else if (2 == choice) {
			onUpdate();
		}
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
		TreeMap<Integer, Integer> countByDay = manager.getCountByDay();
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, Integer> entry : countByDay.descendingMap().entrySet()) {
			sb.append(entry.getKey());
			sb.append(" : ");
			sb.append(entry.getValue());
			sb.append('\n');
		}
		jTextMine.setText(sb.toString());
	}

}
