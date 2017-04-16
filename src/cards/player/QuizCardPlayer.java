package cards.player;

import cards.QuizCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuizCardPlayer {
    private List<QuizCard> cards;
    private QuizCard currentCard;
    private  int nextIndex;
    private boolean needShowAnswer;
    private JFrame frame;
    private JTextArea textArea;
    private JButton button;

    public static void main(String[] args) {
        new QuizCardPlayer().go();
    }

    public void go() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        textArea = new JTextArea(9,20);
        textArea.setFont(bigFont);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        button = new JButton("Start");
        button.addActionListener(new ButtonListener());

        panel.add(scrollPane);
        panel.add(button);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new OpenMenuListener());
        fileMenu.add(open);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500,600);
        frame.setVisible(true);

    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (needShowAnswer) {
                textArea.setText(currentCard.getAnswer());
                needShowAnswer = false;
                button.setText("Next Card");
            } else {
                if (nextIndex < cards.size()) {
                    showNextCard();
                } else {
                    textArea.setText("END");
                    button.setEnabled(false);
                }
            }
        }
    }

    private void showNextCard() {
        currentCard = cards.get(nextIndex);
        nextIndex++;
        textArea.setText(currentCard.getQuestion());
        button.setText("Show Answer");
        needShowAnswer = true;
    }

    private class OpenMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cards = new ArrayList<QuizCard>();
			nextIndex = 0;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(frame);
            loadFile(fileChooser.getSelectedFile());
			button.setEnabled(true);
            showNextCard();
        }
    }

    private void loadFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                makeCard(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeCard(String line) {
        String[] tokens = line.split("/");
        cards.add(new QuizCard(tokens[0], tokens[1]));
    }
}