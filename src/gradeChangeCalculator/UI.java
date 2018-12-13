package gradeChangeCalculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class UI {

	private GradeChangeCalculator gcc;
	private JFrame frame;
	private JPanel menuPanel;
	private JPanel coursePanel;
	private JScrollPane courseScrollPanel;

	private JLabel finalGradeLabel;

	public UI(GradeChangeCalculator gcc) {
		this.gcc = gcc;

		// Main frame set-up
		frame = new JFrame();
		// frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Top menu panel (toolbar) set-up
		menuPanel = new JPanel();
		menuPanel.setBackground(Color.BLUE);
		menuPanel.setPreferredSize(new Dimension(960, 100)); // 960/540 = half of 1080p

		finalGradeLabel = new JLabel();
		updateFinal();
		menuPanel.add(finalGradeLabel, BorderLayout.BEFORE_FIRST_LINE);

		menuPanel.setVisible(true);
		frame.add(menuPanel, BorderLayout.PAGE_START);

		// Scrollpane for coursePanel set-up
		courseScrollPanel = new JScrollPane();
		courseScrollPanel.setVisible(true);
		frame.add(courseScrollPanel, BorderLayout.CENTER);

		// Course panel set-up
		coursePanel = new JPanel();
		coursePanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 10, 0, 0);
		String labelNames[] = { "Due Date", "Category", "Assignment", "Score", "Percentage" };
		for (int i = 0; i < 5; i++) {
			c.gridx = i;
			JLabel label = new JLabel(labelNames[i]);
			label.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
			coursePanel.add(label, c);
		}

		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;
		for (Grade g : gcc.getCourse().getGrades()) {
			c.gridy++;
			JTextField fields[] = new JTextField[5];

			JTextField date = new JTextField(g.getDate());
			date.setEditable(false);
			date.setHighlighter(null);
			fields[0] = date;

			JTextField category = new JTextField(g.getCategory());
			category.setEditable(false);
			category.setHighlighter(null);
			fields[1] = category;

			JTextField assignment = new JTextField(g.getAssignment());
			assignment.setEditable(false);
			assignment.setHighlighter(null);
			fields[2] = assignment;

			JTextField score = new JTextField(g.getScore().toString());
			score.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						coursePanel.requestFocusInWindow();
					} else if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '/'
							&& e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
						e.consume();
					}
				}
			});
			score.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					String text = score.getText();
					if (!text.contains("/") || text.indexOf('/') != text.lastIndexOf('/') || text.length() < 3) {
						score.setText(g.getScore().getNumerator() + "/" + g.getScore().getDenominator());
					} else {
						double numerator = Double.parseDouble(text.split("/")[0]);
						double denominator = Double.parseDouble(text.split("/")[1]);
						Fraction newScore = new Fraction(numerator / denominator);
						gcc.getCourse().changeGrade(g, newScore);
						updateFinal();
					}
				}
			});
			fields[3] = score;

			JTextField percentage = new JTextField("" + g.getScore().getRoundedPercentage(2));
			fields[4] = percentage;

			for (int i = 0; i < fields.length; i++) {
				c.gridx = i;
				fields[i].setBorder(null);
				fields[i].setBackground(null);
				coursePanel.add(fields[i], c);
			}

		}

		coursePanel.setVisible(true);
		courseScrollPanel.setViewportView(coursePanel);

		frame.pack();
		frame.setVisible(true);
	}

	public void updateFinal() {
		String finalGrade = "" + gcc.getCourse().getFinalGrade().getRoundedPercentage(2);
		finalGradeLabel.setText(finalGrade);
	}
}
