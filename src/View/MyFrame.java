package View;

import Resposes.Response;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static Appl.LibraryServer.getSystemResponse;

public class MyFrame extends JFrame implements ActionListener {

    JLabel label;
    JButton button;
    JTextField userInput;

    public MyFrame() {
        //Sets Frame
        this.setTitle("Library Management System");
        ImageIcon library = new ImageIcon("libraryIcon.png");
        this.setIconImage(library.getImage());

        //the part I will inevitably break
        addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        ArrayList<String> param = new ArrayList<>();
                        param.add("quit");
                        getSystemResponse(param);
                        System.exit(0);
                    }
                }
        );


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, (int) dim.getWidth(), (int) dim.getHeight());
        setLocationRelativeTo(null);

        //Sets top label
        label = new JLabel("Please Enter a Request Below:");
        label.setFont(new Font("MV Boli", Font.PLAIN, 30));
        label.setHorizontalAlignment(JLabel.CENTER);

        //Sets user input section
        userInput = new JTextField();
        userInput.setFont(new Font("MV Boli", Font.PLAIN, 30));
        userInput.setHorizontalAlignment(JTextField.CENTER);

        //Sets the input submit button
        button = new JButton("Submit");
        button.setFont(new Font("MV Boli", Font.PLAIN, 30));
        button.addActionListener(this);

        //Adds all Components to frame and makes visible
        this.add(label, BorderLayout.NORTH);
        this.add(userInput, BorderLayout.CENTER);
        this.add(button, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            String request = userInput.getText();
            ArrayList<String> Parameters = new ArrayList<String>();
            Parameters.addAll(Arrays.asList(request.split(",")));

            if(Parameters.size() > 0) {

                //remove leading and trailing spaces
                for (String p : Parameters){
                    int index = Parameters.indexOf(p);
                   Parameters.set(index,p.trim());
                }

                Response systemResponse = getSystemResponse(Parameters);

                //Sets Frame
                JFrame frame = new JFrame();
                frame.setTitle("Response");
                ImageIcon library = new ImageIcon("libraryIcon.png");
                frame.setIconImage(library.getImage());
                frame.setResizable(true);
                frame.setSize(1000,1000);

                //Sets Text Area
                JTextArea response = new JTextArea(systemResponse.getResponse());
                response.setLineWrap(true);
                response.setFont(new Font("MV Boli", Font.PLAIN, 30));

                frame.add(response);
                //frame.pack();
                frame.setVisible(true);
            }
        }
    }
}
