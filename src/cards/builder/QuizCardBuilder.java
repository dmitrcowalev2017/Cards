package cards.builder;
//sl
//comment from IDEA
import cards.QuizCard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizCardBuilder {
    private List<QuizCard> cards;
    private JTextArea question;
    private JTextArea answer;
    private JFrame frame;

    public static void main(String[] args) {
        new QuizCardBuilder().go();
    }

    public void go() {
        frame = new JFrame("Playsse");
        JPanel panel = new JPanel();

        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        question = new JTextArea(6,20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);
        JScrollPane qScroller = new JScrollPane(question);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        

        answer = new JTextArea(6,20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        JScrollPane aScroller = new JScrollPane(answer);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        answer.setFont(bigFont);

        JLabel qLabel = new JLabel("Question");
        JLabel aLabel = new JLabel("Answer");

        JButton nextButton = new JButton("NexCard");
        nextButton.addActionListener(new NextButtonListener());

        panel.add(qLabel);
        panel.add(qScroller);
        panel.add(aLabel);
        panel.add(aScroller);
        panel.add(nextButton);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        newItem.addActionListener(new NewListener());
        saveItem.addActionListener(new SaveListener());
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setVisible(true);
        cards = new ArrayList<QuizCard>();
    }

    private void clearGUI() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

    private class NewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cards.clear();
            clearGUI();
        }
    }

    private class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cards.add(new QuizCard(question.getText(), answer.getText()));
            clearGUI();
        }
    }

    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cards.add(new QuizCard(question.getText(), answer.getText()));

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showSaveDialog(frame);
            saveInFile(fileChooser.getSelectedFile());
        }
    }

    private void saveInFile(File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (QuizCard card : cards) {
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}