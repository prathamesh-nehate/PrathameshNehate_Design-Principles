package calculator.presentation;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import calculator.logic.*;

public class CalculatorGUI implements ActionListener {
    private JPanel numberKeyPanel;
    private GridLayout numberKeyGrid;
    private JTextField InputOutputArea;
    private JButton[] numberKey;
    private JFrame f;
    private Evaluator Eval;
    private String[] Label;

    public CalculatorGUI(){
        f = new JFrame("Calculator");
        InputOutputArea = new JTextField("", 20);
        numberKeyPanel = new JPanel();
        Label = new String[]{ "7", "8", "9", "/","(", "4", "5", "6", "*",")", "1", "2", "3", "-","AC", "0", ".", "=", "+" ,"C"};
        numberKeyGrid = new GridLayout(4, 5, 5, 5);
        numberKey = new JButton[Label.length];
        Eval = new Evaluator();
        this.drawGUI();
    }

    void drawGUI() {
        InputOutputArea.setEditable(false);
        for (int i = 0; i < Label.length; i++) {
            numberKey[i] = new JButton(Label[i]);
            numberKey[i].addActionListener(this);
            numberKeyPanel.add(numberKey[i]);
        }
        numberKeyPanel.setLayout(numberKeyGrid);

        f.add(InputOutputArea, BorderLayout.PAGE_START);
        f.add(numberKeyPanel, BorderLayout.PAGE_END);

        f.setSize(300, 175);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s == "=") {
            InputOutputArea.setText(Eval.evaluate(InputOutputArea.getText()));

        }else if(s == "C"){
            String temp = InputOutputArea.getText();
            InputOutputArea.setText(temp.substring(0,temp.length()-1));
        }else if(s == "AC"){
            InputOutputArea.setText("");
        }
        else {
            InputOutputArea.setText(InputOutputArea.getText() + s);
        }
    }
}
