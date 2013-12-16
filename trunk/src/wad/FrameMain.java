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
	private final JButton jButtonUpdate = new JButton("Update");

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
		setTitle("WAD 27");
		setSize(new Dimension(700, 620));
		jLabelTitle.setText("Title");
		jLabelTitle.setBounds(new Rectangle(25, 55, 130, 15));
		jTextTitle.setBounds(new Rectangle(25, 75, 200, 25));
		jTextTitle.setFont(new Font("Tahoma", 0, 16));
		jLabelDef.setText("Definition");
		jLabelDef.setBounds(new Rectangle(25, 110, 200, 15));
		jTextDef.setBounds(new Rectangle(25, 130, 500, 175));
		jTextDef.setLineWrap(true);
		jTextDef.setWrapStyleWord(true);
		jTextDef.setFont(new Font("Tahoma", 0, 16));
		jLabelExample.setText("Example");
		jLabelExample.setBounds(new Rectangle(25, 315, 200, 15));
		jTextExample.setBounds(new Rectangle(25, 335, 500, 205));
		jTextExample.setLineWrap(true);
		jTextExample.setWrapStyleWord(true);
		jTextExample.setFont(new Font("Tahoma", 0, 16));
		jButtonShow.setText("Show");
		jButtonShow.setBounds(new Rectangle(245, 25, 80, 25));
		jButtonShow.setEnabled(false);
		jButtonShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onShow(e);
			}
		});
		jButtonNext.setText("Next");
		jButtonNext.setBounds(new Rectangle(245, 75, 80, 25));
		jButtonNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onNext(e);
			}
		});
		jRadioYes.setText("Yes");
		jRadioYes.setBounds(new Rectangle(335, 30, 50, 15));
		jRadioYes.setEnabled(false);
		jRadioYes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onYes(e);
			}
		});
		jRadioNo.setText("No");
		jRadioNo.setBounds(new Rectangle(385, 30, 40, 15));
		jRadioNo.setEnabled(false);
		jRadioNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onNo(e);
			}
		});
		jButtonAdd.setText("Add");
		jButtonAdd.setBounds(new Rectangle(445, 75, 80, 25));
		jButtonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onAdd(e);
			}
		});
		jTextResp.setBounds(new Rectangle(25, 25, 200, 25));
		jTextResp.setFont(new Font("Tahoma", 0, 16));
		jLabelStats.setText("");
		jLabelStats.setBounds(new Rectangle(25, 555, 500, 15));
		jButtonClear.setText("Clear");
		jButtonClear.setBounds(new Rectangle(345, 75, 80, 25));
		jButtonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClear(e);
			}
		});
		jTextOld.setText("0");
		jTextOld.setBounds(new Rectangle(435, 25, 25, 25));
		jLabelOld.setText("Old");
		jLabelOld.setBounds(new Rectangle(460, 25, 25, 25));
		jTextNew.setText("0");
		jTextNew.setBounds(new Rectangle(485, 25, 25, 25));
		jLabelNew.setText("New");
		jLabelNew.setBounds(new Rectangle(510, 25, 25, 25));
		jButtonMine.setText("Mine");
		jButtonMine.setBounds(new Rectangle(545, 75, 80, 25));
		jButtonMine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onMine(e);
			}
		});
		jTextMine.setBounds(new Rectangle(545, 130, 120, 410));
		jTextMine.setFont(new Font("Tahoma", 0, 16));
		jTextMine.setLineWrap(true);
		jButtonUpdate.setText("Update");
		jButtonUpdate.setBounds(new Rectangle(545, 25, 80, 25));
		jButtonUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onUpdate(e);
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
		cont.add(jButtonUpdate, null);
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
								JOptionPane.showConfirmDialog(null, "No change, save anyway?", "Save",
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
		jTextResp.requestFocusInWindow();
	}

	private void onAdd(ActionEvent e) {
		Word newWord = new Word();
		newWord.title = jTextTitle.getText();
		newWord.definition = jTextDef.getText();
		newWord.example = jTextExample.getText();
		newWord.isNew = true;
		manager.addWord(newWord);
	}

	private void onYes(ActionEvent e) {
		manager.addAttempt(word.title, new Date(), true);
		jButtonNext.requestFocusInWindow();
	}

	private void onNo(ActionEvent e) {
		manager.addAttempt(word.title, new Date(), false);
		jButtonNext.requestFocusInWindow();
	}

	private void onClear(ActionEvent e) {
		jTextTitle.setText("");
		jTextResp.setText("");
		jTextDef.setText("");
		jTextExample.setText("");
		jTextMine.setText("");
		jRadioYes.setSelected(false);
		jRadioNo.setSelected(false);
		jTextResp.setForeground(Color.black);
	}

	private void onMine(ActionEvent e) {
		String selectedWords = manager.mine();
		jTextMine.setText(selectedWords);
	}

	private void onUpdate(ActionEvent e) {
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
}
