package com.zahir.panel;



import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel  {

    protected JLabel nameLabel;
   
	protected JLabel phoneLabel;
    protected JTextField nameField;
    protected JTextField phoneField;
    protected JButton addButton;
    protected JButton searchButton;

    public InputPanel() {
        init();
    }

    protected void init() {
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        nameLabel = new JLabel("Name");
        phoneLabel = new JLabel("Phone");
        nameField = new JTextField(20);
        phoneField = new JTextField(20);
        addButton = new JButton("Add");
        searchButton = new JButton("Search");

        {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.anchor = GridBagConstraints.LINE_END;
            constraints.ipadx = 10;
            this.add(nameLabel, constraints);
        }

        {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.gridwidth = 3;
            constraints.fill = GridBagConstraints.BOTH;
            this.add(nameField, constraints);
        }

        {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.anchor = GridBagConstraints.LINE_END;
            constraints.ipadx = 10;
            this.add(phoneLabel, constraints);
        }

        {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 1;
            constraints.gridy = 1;
            constraints.gridwidth = 3;
            constraints.fill = GridBagConstraints.BOTH;
            this.add(phoneField, constraints);
        }

        {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 2;
            constraints.gridy = 2;
            constraints.anchor = GridBagConstraints.LINE_START;
            this.add(addButton, constraints);
        }

        {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 3;
            constraints.gridy = 2;
            constraints.ipadx = 10;
            this.add(searchButton, constraints);
        }


    }
}
