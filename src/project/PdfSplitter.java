package project;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class MyFrame extends Frame implements ActionListener {
    Label l1,l2,l3,l4;
    TextField tf;
    Button b;

    public MyFrame() {
        super("PDF Splitter");

        // Set background color
        Color bgColor = new Color(255,255,204);
        setBackground(bgColor);

        // Create a custom font
        Font ft = new Font("Arial", Font.PLAIN, 25);

        l1 = new Label("Enter the Location of the PDF to Split");
        l1.setFont(ft);
        l2=new Label();
        l2.setFont(ft);
        l2.setPreferredSize(new Dimension(400,45));
        l3=new Label();
        l3.setFont(ft);
        l4=new Label();
        l4.setFont(ft);
        tf = new TextField(30);
        tf.setFont(Font.getFont(Font.SERIF));

        b = new Button("Split");
        b.setFont(ft);

        b.setBackground(Color.BLUE);


        b.setForeground(Color.WHITE);

        Dimension buttonSize = new Dimension(100, 30);
        b.setPreferredSize(buttonSize);

        b.addActionListener(this);

        GridBagLayout gb=new GridBagLayout();
        GridBagConstraints gbc=new GridBagConstraints();

        setLayout(gb);

        gbc.gridx=2;
        gbc.gridy=1;
        gbc.insets = new Insets(0, 0, 30, 0);
        add(l1,gbc);

        gbc.gridx=2;
        gbc.gridy=2;
        gbc.insets = new Insets(0, 0, 30, 0);
        add(tf,gbc);

        gbc.gridx=2;
        gbc.gridy=3;
        gbc.insets = new Insets(0, 0, 30, 0);
        add(b,gbc);

        gbc.gridx=2;
        gbc.gridy=4;
        gbc.insets = new Insets(0, 75, 30, 0);
//        gbc.fill= GridBagConstraints.HORIZONTAL;
        add(l2,gbc);

        gbc.gridx=2;
        gbc.gridy=5;
        gbc.insets = new Insets(0, 0, 30, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(l3,gbc);

        gbc.gridx=2;
        gbc.gridy=6;
        gbc.insets = new Insets(0, 0, 30, 0);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(l4,gbc);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String inputFile = tf.getText();
        performSplitOperation(inputFile);
    }

    private void performSplitOperation(String inputFile) {
        int splitPage = 5;

        String outputDirectory = "C:/pdfsplit/";

        try {
            // Load the PDF document
            PDDocument document = new PDDocument();
            document = PDDocument.load(new File(inputFile));


            Splitter splitter = new Splitter();

            splitter.setSplitAtPage(splitPage);

            // Split the document
            List<PDDocument> splitDoc = new ArrayList<PDDocument>();
            splitDoc = splitter.split(document);

            // Save each split document
            l2.setText("PDF Splitted Successfully");
            int pageIndex = 1;
            for (PDDocument splitDocument : splitDoc) {
                String outputFileName = outputDirectory + "output" + pageIndex + ".pdf";
                splitDocument.save(outputFileName);
                splitDocument.close();
//                System.out.println("Split document saved: " + outputFileName);
                if(pageIndex==1) {
                    l3.setText("Document " + (pageIndex) + " saved: " + outputFileName);
                }
                else if(pageIndex==2) {
                    l4.setText("Document " + (pageIndex) + " saved: " + outputFileName);
                }
                pageIndex++;

            }

            document.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

public class PdfSplitter {
    public static void main(String[] args) {
        MyFrame f = new MyFrame();
        f.setSize(600, 500);
        f.setVisible(true);
    }
}
