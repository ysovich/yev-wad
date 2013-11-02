package wad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FrameMain extends JFrame {
	private static final long serialVersionUID = 1L;
	private final Manager manager;
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
	private final JButton jButtonSave = new JButton("Save");
	private final JTextField jTextResp = new JTextField();
	private final JLabel jLabelStats = new JLabel();
	private final JButton jButtonClear = new JButton("Clear");
	private final JTextField jTextOld = new JTextField("0");
	private final JLabel jLabelOld = new JLabel();
	private final JButton jButtonMine = new JButton("Mine");
	private final JTextArea jTextMine = new JTextArea();

	public FrameMain(String configPath) {
		manager = new Manager(configPath);
		try {
			jbInit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.getContentPane().setLayout(null);
		this.setSize(600, 600);
		this.setTitle("WAD");
		this.setSize(new Dimension(700, 620));
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
		jRadioYes.setBounds(new Rectangle(345, 30, 50, 15));
		jRadioYes.setEnabled(false);
		jRadioYes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onYes(e);
			}
		});
		jRadioNo.setText("No");
		jRadioNo.setBounds(new Rectangle(395, 30, 40, 15));
		jRadioNo.setEnabled(false);
		jRadioNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onNo(e);
			}
		});
		jButtonSave.setText("Save");
		jButtonSave.setBounds(new Rectangle(445, 75, 80, 25));
		jButtonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onSave(e);
			}
		});
		jTextResp.setBounds(new Rectangle(25, 25, 200, 25));
		jTextResp.setFont(new Font("Tahoma", 0, 16));
		jLabelStats.setText("");
		jLabelStats.setBounds(new Rectangle(25, 560, 500, 10));
		jButtonClear.setText("Clear");
		jButtonClear.setBounds(new Rectangle(345, 75, 80, 25));
		jButtonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClear(e);
			}
		});
		jTextOld.setText("0");
		jTextOld.setBounds(new Rectangle(460, 25, 25, 25));
		jLabelOld.setText("Old");
		jLabelOld.setBounds(new Rectangle(490, 25, 25, 25));
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

		this.getContentPane().add(jLabelOld, null);
		this.getContentPane().add(jTextOld, null);
		this.getContentPane().add(jButtonClear, null);
		this.getContentPane().add(jLabelStats, null);
		this.getContentPane().add(jTextResp, null);
		this.getContentPane().add(jButtonSave, null);
		this.getContentPane().add(jRadioNo, null);
		this.getContentPane().add(jRadioYes, null);
		this.getContentPane().add(jButtonNext, null);
		this.getContentPane().add(jButtonShow, null);
		this.getContentPane().add(jTextExample, null);
		this.getContentPane().add(jLabelExample, null);
		this.getContentPane().add(jTextDef, null);
		this.getContentPane().add(jLabelDef, null);
		this.getContentPane().add(jTextTitle, null);
		this.getContentPane().add(jLabelTitle, null);
		this.getContentPane().add(jButtonMine, null);
		this.getContentPane().add(jTextMine, null);
	}

	private void onShow(ActionEvent e) {
		jTextTitle.setText(word.title);
		jTextExample.setText(word.example);
		String title = word.title.split("\\d")[0];
		if (jTextResp.getText().equals(title)) {
			jTextResp.setForeground(new Color(0, 192, 0));
		}
		else {
			jTextResp.setForeground(new Color(192, 0, 0));
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
		word = manager.getNextWord(oldCount);
		jTextOld.setText(Integer.toString(manager.getOldCount()));
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
		jLabelStats.setText(statsStr.toString());
	}

	private void onSave(ActionEvent e) {
		Word newWord = new Word();
		newWord.title = jTextTitle.getText();
		newWord.definition = jTextDef.getText();
		newWord.example = jTextExample.getText();
		manager.addWord(newWord);
	}

	private void onYes(ActionEvent e) {
		Attempt attempt = new Attempt();
		attempt.wordTitle = word.title;
		attempt.date = new Date();
		attempt.correct = Boolean.TRUE;
		manager.addAttempt(attempt);
	}

	private void onNo(ActionEvent e) {
		Attempt attempt = new Attempt();
		attempt.wordTitle = word.title;
		attempt.date = new Date();
		attempt.correct = Boolean.FALSE;
		manager.addAttempt(attempt);
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
}