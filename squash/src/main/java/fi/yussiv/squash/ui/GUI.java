package fi.yussiv.squash.ui;

import fi.yussiv.squash.Huffman;
import fi.yussiv.squash.LZW;
import fi.yussiv.squash.domain.HuffmanTree;
import fi.yussiv.squash.io.FileIO;
import fi.yussiv.squash.io.HuffmanFile;
import fi.yussiv.squash.io.HuffmanParser;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * A very crude GUI for demoing purposes.
 *
 * Much of this is copy-pasted from
 * http://www.java2s.com/Code/Java/Swing-JFC/DemonstrationofFiledialogboxes.htm
 */
public class GUI extends JFrame {

    private JTextField inputFile = new JTextField();
    private JTextField outputFile = new JTextField();

    private JButton open = new JButton("Input");
    private JButton save = new JButton("Output");
    private JRadioButton huffman = new JRadioButton("Huffman");
    private JRadioButton lzw = new JRadioButton("LZW");
    private JRadioButton encode = new JRadioButton("Encode");
    private JRadioButton decode = new JRadioButton("Decode");
    private JTextArea inputInfo = new JTextArea(5, 20);
    private JTextArea outputInfo = new JTextArea(5, 20);

    public GUI() {
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
        JPanel methodPanel = new JPanel();
        methodPanel.setLayout(new BoxLayout(methodPanel, BoxLayout.X_AXIS));
        JPanel ioPanel = new JPanel();
        ioPanel.setLayout(new BoxLayout(ioPanel, BoxLayout.X_AXIS));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        open.addActionListener(new InputFileChooserListener());
        save.addActionListener(new OutputFileChooserListener());
        open.setHorizontalAlignment(SwingConstants.LEFT);
        save.setHorizontalAlignment(SwingConstants.LEFT);

        leftPanel.add(open);
        rightPanel.add(save);

        leftPanel.add(inputFile);
        rightPanel.add(outputFile);
        
        inputInfo.setEditable(false);
        outputInfo.setEditable(false);

        leftPanel.add(inputInfo);
        rightPanel.add(outputInfo);

        ButtonGroup codingScheme = new ButtonGroup();
        ButtonGroup actions = new ButtonGroup();

        codingScheme.add(lzw);
        codingScheme.add(huffman);
        actions.add(encode);
        actions.add(decode);
        encode.setSelected(true);
        lzw.setSelected(true);

        actionPanel.add(new JLabel("Action: "));
        actionPanel.add(encode);
        actionPanel.add(decode);

        methodPanel.add(new JLabel("Method: "));
        methodPanel.add(lzw);
        methodPanel.add(huffman);

        ioPanel.add(leftPanel);
        ioPanel.add(rightPanel);

        JButton exec = new JButton("Execute");
        exec.addActionListener(new ExecuteListener());

        cp.add(actionPanel);
        cp.add(methodPanel);
        cp.add(ioPanel);
        cp.add(exec);

        pack();
    }

    public void printInputText(String contents) {
        inputInfo.setText(inputInfo.getText() + contents + "\n");
    }

    public void printOutputText(String contents) {
        outputInfo.setText(outputInfo.getText() + contents + "\n");
    }

    class ExecuteListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            byte[] input;
            byte[] output;
            HuffmanTree ht = null;

            try {
                input = FileIO.readBytesFromFile(inputFile.getText());
            } catch (Exception ex) {
                printInputText("Opening file failed: " + ex.getMessage());
                return;
            }

            if (encode.isSelected()) {
                if (lzw.isSelected()) {
                    output = LZW.encode(input);
                    printOutputText("Encoded size " + output.length + " bytes");
                } else {
                    ht = Huffman.generateParseTree(input);
                    byte[] encoded = Huffman.encode(input, ht);
                    output = HuffmanFile.getBytes(ht, encoded);
                    printOutputText("Dictionary size " + (output.length - encoded.length - 12) + " bytes");
                    printOutputText("Data size " + encoded.length + " bytes");
                    printOutputText("Total size " + output.length + " bytes");
                }
            } else {
                // Decoding
                if (lzw.isSelected()) {
                    output = LZW.decode(input);
                } else {
                    try {
                        HuffmanParser parser = new HuffmanParser(input);
                        output = Huffman.decode(parser.getData(), parser.getHuffmanTree());
                    } catch (Exception ex) {
                        printOutputText("Parsing error: " + ex.getMessage());
                        return;
                    }
                }
                printOutputText("Decoded size " + output.length + " bytes");
            }

            try {
                FileIO.writeBytesToFile(outputFile.getText(), output);
            } catch (Exception ex) {
                printOutputText("Could not write to file: " + ex.getMessage());
            }
        }
    }

    class InputFileChooserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            int rVal = c.showOpenDialog(GUI.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                String filename = c.getSelectedFile().getAbsolutePath();
                inputFile.setText(filename);
                try {
                    byte[] input = FileIO.readBytesFromFile(filename);
                    printInputText("File size: " + input.length + " bytes");
                } catch (Exception ex) {
                    printInputText("Opening file failed: " + ex.getMessage());
                }
            }
        }
    }

    class OutputFileChooserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
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
