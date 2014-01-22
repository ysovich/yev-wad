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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
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
	private final JButton jButtonAdd = new JButton("Add");
	private final JTextField jTextResp = new JTextField();
	private final JLabel jLabelStats = new JLabel();
	private final JButton jButtonClear = new JButton("Clear");
	private final JTextField jTextOld = new JTextField("0");
	private final JLabel jLabelOld = new JLabel();
	private final JTextField jTextNew = new JTextField("0");
	private final JLabel jLabelNew = new JLabel();
	private final JButton jButtonMine = new JButton("Mine");
	private final JTextArea jTextMine = new JTextArea();
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
		setTitle("WAD 35");
		setSize(new Dimension(700, 630));
		jLabelTitle.setText("Title");
		jLabelTitle.setBounds(new Rectangle(15, 55, 130, 15));
		jTextTitle.setBounds(new Rectangle(15, 75, 460, 25));
		jTextTitle.setFont(new Font("Tahoma", 0, 16));
		jLabelDef.setText("Definition");
		jLabelDef.setBounds(new Rectangle(15, 110, 200, 15));
		jTextDef.setBounds(new Rectangle(15, 130, 500, 175));
		jTextDef.setLineWrap(true);
		jTextDef.setWrapStyleWord(true);
		jTextDef.setFont(new Font("Tahoma", 0, 16));
		jLabelExample.setText("Example");
		jLabelExample.setBounds(new Rectangle(15, 315, 200, 15));
		jTextExample.setBounds(new Rectangle(15, 335, 500, 205));
		jTextExample.setLineWrap(true);
		jTextExample.setWrapStyleWord(true);
		jTextExample.setFont(new Font("Tahoma", 0, 16));
		jButtonShow.setText("Show");
		jButtonShow.setBounds(new Rectangle(485, 25, 80, 25));
		jButtonShow.setEnabled(false);
		jButtonShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onShow(e);
			}
		});
		jButtonNext.setText("Next");
		jButtonNext.setBounds(new Rectangle(485, 75, 80, 25));
		jButtonNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onNext(e);
			}
		});
		jRadioYes.setText("Yes");
		jRadioYes.setBounds(new Rectangle(575, 30, 50, 15));
		jRadioYes.setEnabled(false);
		jRadioYes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onYes(e);
			}
		});
		jRadioNo.setText("No");
		jRadioNo.setBounds(new Rectangle(625, 30, 40, 15));
		jRadioNo.setEnabled(false);
		jRadioNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onNo(e);
			}
		});
		jButtonAdd.setText("Add");
		jButtonAdd.setBounds(new Rectangle(320, 550, 75, 25));
		jButtonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onAdd(e);
			}
		});
		jTextResp.setBounds(new Rectangle(15, 25, 460, 25));
		jTextResp.setFont(new Font("Tahoma", 0, 16));
		jLabelStats.setText("");
		jLabelStats.setBounds(new Rectangle(15, 555, 305, 15));
		jButtonClear.setText("Clear");
		jButtonClear.setBounds(new Rectangle(500, 550, 75, 25));
		jButtonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClear(e);
			}
		});
		jTextOld.setText("0");
		jTextOld.setBounds(new Rectangle(575, 75, 25, 25));
		jLabelOld.setText("Old");
		jLabelOld.setBounds(new Rectangle(600, 75, 25, 25));
		jTextNew.setText("0");
		jTextNew.setBounds(new Rectangle(625, 75, 25, 25));
		jLabelNew.setText("New");
		jLabelNew.setBounds(new Rectangle(650, 75, 25, 25));
		jButtonMine.setText("Mine");
		jButtonMine.setBounds(new Rectangle(590, 550, 75, 25));
		jButtonMine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onMine(e);
			}
		});
		jTextMine.setBounds(new Rectangle(545, 130, 120, 410));
		jTextMine.setFont(new Font("Tahoma", 0, 16));
		jTextMine.setLineWrap(true);
		jButtonEdit.setText("Edit");
		jButtonEdit.setBounds(new Rectangle(410, 550, 75, 25));
		jButtonEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onEdit(e);
			}
		});

		cont.add(jLabelNew, null);
		cont.add(jTextNew, null);
		cont.add(jLabelOld, null);
		cont.add(jTextOld, null);
		cont.add(jButtonClear, null);
		cont.add(jLabelStats, null);
		cont.add(jTextResp, null);
		cont.add(jButtonAdd, null);
		cont.add(jRadioNo, null);
		cont.add(jRadioYes, null);
		cont.add(jButtonNext, null);
		cont.add(jButtonShow, null);
		cont.add(jTextExample, null);
		cont.add(jLabelExample, null);
		cont.add(jTextDef, null);
		cont.add(jLabelDef, null);
		cont.add(jTextTitle, null);
		cont.add(jLabelTitle, null);
		cont.add(jButtonMine, null);
		cont.add(jTextMine, null);
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
			jTextDef.setText(word.definition);
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
		int total = 0;
		for (int i = 0; i < stats.length; ++i) {
			long count = stats[i];
			total += count;
			statsStr.append(Manager.intervalLbl[i]);
			statsStr.append(":");
			statsStr.append(count);
			statsStr.append(" ");
		}
		statsStr.append(total);
		statsStr.append("(");
		statsStr.append(manager.getRemainingNewCount());
		statsStr.append(")");
		statsStr.append(" C:");
		statsStr.append(manager.getCandCount());
		jLabelStats.setText(statsStr.toString());
	}

	private void onAdd(ActionEvent e) {
		Word newWord = new Word();
		newWord.title = jTextTitle.getText();
		newWord.definition = jTextDef.getText();
		newWord.example = jTextExample.getText();
		newWord.isNew = true;
		manager.addWord(newWord);
		updateStats();
		clearWord();
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
		boolean isUpdated = manager.updateTitle(title, newTitle);
		if (isUpdated) {
			jTextTitle.setText(jTextResp.getText());
		}
	}

	private void onUpdate() {
		if (word != null) {
			word.definition = jTextDef.getText();
			word.example = jTextExample.getText();
		}
	}

}
