package ui;

import fi.yussiv.squash.Huffman;
import fi.yussiv.squash.LZW;
import fi.yussiv.squash.domain.HuffmanTree;
import fi.yussiv.squash.io.FileIO;
import fi.yussiv.squash.io.HuffmanWrapper;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * This is pretty much copy-pasted from
 * http://www.java2s.com/Code/Java/Swing-JFC/DemonstrationofFiledialogboxes.htm.
 */
public class GUI extends JFrame {

    private JTextField inputFile = new JTextField(), outputFile = new JTextField();

    private JButton open = new JButton("Input"), save = new JButton("Output");
    private JRadioButton huffman;
    private JRadioButton lzw;
    private JRadioButton encode;
    private JRadioButton decode;
    private JTextArea inputInfo;
    private JTextArea outputInfo;

    public GUI() {
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.X_AXIS));
        JPanel ioPanel = new JPanel();
        ioPanel.setLayout(new BoxLayout(ioPanel, BoxLayout.X_AXIS));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JPanel p = new JPanel();
        open.addActionListener(new OpenL());
        save.addActionListener(new SaveL());
        open.setHorizontalAlignment(SwingConstants.LEFT);
        save.setHorizontalAlignment(SwingConstants.LEFT);

        leftPanel.add(open);
        rightPanel.add(save);

        inputFile.setEditable(false);
        outputFile.setEditable(false);

        leftPanel.add(inputFile);
        rightPanel.add(outputFile);

        p = new JPanel();

        inputInfo = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(inputInfo);
        inputInfo.setEditable(false);

        outputInfo = new JTextArea(5, 20);
        JScrollPane scrollPane2 = new JScrollPane(inputInfo);
        outputInfo.setEditable(false);

        outputInfo.setText("output");
        inputInfo.setText("input");

        leftPanel.add(inputInfo);
        rightPanel.add(outputInfo);

        ButtonGroup codingScheme = new ButtonGroup();
        huffman = new JRadioButton("Huffman");
        lzw = new JRadioButton("LZW");
        ButtonGroup actions = new ButtonGroup();
        encode = new JRadioButton("Encode");
        decode = new JRadioButton("Decode");

        codingScheme.add(lzw);
        codingScheme.add(huffman);
        actions.add(encode);
        actions.add(decode);

        optionPanel.add(encode);
        optionPanel.add(decode);
        optionPanel.add(lzw);
        optionPanel.add(huffman);

        ioPanel.add(leftPanel);
        ioPanel.add(rightPanel);

        JButton exec = new JButton("Execute");
        exec.addActionListener(new ExecuteListener());

        cp.add(optionPanel);
        cp.add(ioPanel);
        cp.add(exec);

        pack();
    }

    public void printInputText(String contents) {
        inputInfo.setText(contents);
    }

    public void printOutputText(String contents) {
        outputInfo.setText(contents);
    }

    class ExecuteListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            byte[] input = FileIO.readBytesFromFile(inputFile.getText());
            if (encode.isSelected()) {
                if (lzw.isSelected()) {
                    byte[] output = LZW.encode(input);
                    printOutputText("Encoded size " + output.length + " bytes");
                } else {
                    HuffmanTree ht = Huffman.generateParseTree(input);
                    byte[] output = Huffman.encode(input, ht);
                    printOutputText("Encoded size " + output.length + " bytes");
                }
            } else {
                if(lzw.isSelected()) {
                    byte[] output = LZW.decode(input);
                    printOutputText("Decoded size " + output.length + " bytes");
                } else {
                    HuffmanWrapper hw = (HuffmanWrapper) FileIO.readObjectFromFile(inputFile.getText());
                    byte[] output = Huffman.decode(hw.data, hw.tree);
                    printOutputText("Decoded size " + output.length + " bytes");
                }
            }
        }
    }

    class OpenL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            // Demonstrate "Open" dialog:
            int rVal = c.showOpenDialog(GUI.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                String filename = c.getSelectedFile().getAbsolutePath();
                inputFile.setText(filename);
                byte[] input = FileIO.readBytesFromFile(filename);
                printInputText("File size: " + input.length + " bytes");
            }
        }
    }

    class SaveL implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            // Demonstrate "Save" dialog:
            int rVal = c.showSaveDialog(GUI.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                outputFile.setText(c.getSelectedFile().getAbsolutePath());
            }
        }
    }

    public static void run(JFrame frame, int width, int height) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }
}
