package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {

    JLabel label;
    JButton button;
    JTextField userInput;

    public MyFrame() {
        //Sets Frame
        this.setTitle("Library Management System");
        ImageIcon library = new ImageIcon("libraryIcon.png");
        this.setIconImage(library.getImage());
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
        button.addActionListener(this);

        //Adds all Components to frame and makes visible
        this.add(label, BorderLayout.NORTH);
        this.add(userInput, BorderLayout.CENTER);
        this.add(button, BorderLayout.EAST);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            System.out.println("Yikes " + userInput.getText());
        }

    }
}
