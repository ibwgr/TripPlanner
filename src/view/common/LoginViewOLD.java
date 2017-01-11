package view.common;

import controller.common.LoginController;

import javax.swing.*;
import java.awt.*;

public class LoginViewOLD extends JDialog {

    private JPanel jContentPane = null;
    private JPanel centerPanel = null;
    private JPanel southPanel = null;
    private JLabel labelUsername = null;
    private JTextField textFieldUsername = null;
    private JLabel labelPassword = null;
    private JPasswordField textFieldPassword = null;
    private JButton btOk = null;
    private JButton btAbbruch = null;


    /**
     * jetzt rufen wir unseren eigenen constructor mit 3 param. auf!
     */
    public LoginViewOLD() {
        //super();
        this(null, "Irgendein Titel", true);
    }

    /**
     * Eigener constructor mit 3 parametern
     * 1. parameter = frame
     * 2. parameter = titelzeile (in Window Titel ersichtlich)
     * 3. parameter = modal (im Vordergrund, nicht wegzukriegen)
     */
    public LoginViewOLD(JFrame pParentFrame
            , String pTitel
            , Boolean pModal) {
        super(pParentFrame, pTitel, pModal);
        System.out.println("login-view");
        initialize();
        //this.tfEreignisId.setEnabled(false);
        //this.tfOrganisator.setEnabled(false);
    }


    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(610, 300);
        this.setTitle("Login");
        this.setLocation(new java.awt.Point(290, 270));
        this.setContentPane(getJContentPane());
        this.setAlwaysOnTop(true);
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getCenterPanel());
            jContentPane.add(getSouthPanel());
        }
        return jContentPane;
    }

    /**
     * This method initializes centerPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.gridx = 3;
            gridBagConstraints12.insets = new java.awt.Insets(5, 0, 25, 0);
            gridBagConstraints12.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints12.gridy = 9;
            GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
            gridBagConstraints41.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints41.gridy = 2;
            gridBagConstraints41.weightx = 1.0;
            gridBagConstraints41.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints41.gridx = 3;
            GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
            gridBagConstraints31.gridx = 2;
            gridBagConstraints31.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints31.gridy = 2;
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints21.gridy = 8;
            gridBagConstraints21.weightx = 1.0;
            gridBagConstraints21.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints21.gridwidth = 3;
            gridBagConstraints21.gridx = 1;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 0;
            gridBagConstraints11.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.gridy = 8;
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.gridy = 3;
            gridBagConstraints5.weightx = 1.0;
            gridBagConstraints5.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints5.gridwidth = 3;
            gridBagConstraints5.gridheight = 2;
            gridBagConstraints5.gridx = 1;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.gridy = 3;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.gridy = 2;
            gridBagConstraints3.weightx = 1.0;
            gridBagConstraints3.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints3.gridx = 1;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridy = 2;
            labelPassword = new JLabel();
            labelPassword.setText("Password");
            labelPassword.setToolTipText("Please enter your password");
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridy = 1;
            gridBagConstraints1.weightx = 1.0;
            gridBagConstraints1.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints1.gridheight = 1;
            gridBagConstraints1.gridx = 1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.gridy = 1;
            labelUsername = new JLabel();
            labelUsername.setText("Username");
            labelUsername.setToolTipText("Please enter your userid");
            centerPanel = new JPanel();
            centerPanel.setBounds(0, 0, 594, 225);
            centerPanel.setLayout(new GridBagLayout());
            centerPanel.setBackground(new java.awt.Color(204, 204, 204));
            centerPanel.add(labelUsername, gridBagConstraints);
            centerPanel.add(getTextFieldUsername(), gridBagConstraints1);
            centerPanel.add(labelPassword, gridBagConstraints2);
            centerPanel.add(getTextFieldPassword(), gridBagConstraints3);
        }
        return centerPanel;
    }

    /**
     * This method initializes southPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getSouthPanel() {
        if (southPanel == null) {
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 1;
            gridBagConstraints7.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints7.gridy = 0;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints6.gridy = 0;
            gridBagConstraints6.gridx = 0;
            southPanel = new JPanel();
            southPanel.setBounds(0, 225, 594, 36);
            southPanel.setLayout(new GridBagLayout());
            southPanel.setBackground(new java.awt.Color(204, 204, 204));
            southPanel.add(getBtOk(), gridBagConstraints6);
            southPanel.add(getBtAbbruch(), gridBagConstraints7);
        }
        return southPanel;
    }

    /**
     * This method initializes textFieldUsername
     *
     * @return javax.swing.JTextField
     */
    private JTextField getTextFieldUsername() {
        if (textFieldUsername == null) {
            textFieldUsername = new JTextField();
            textFieldUsername.setPreferredSize(new java.awt.Dimension(8, 20));
            textFieldUsername.setText("benutzer"); // todo entfernen
        }
        return textFieldUsername;
    }

    /**
     * This method initializes textFieldPassword
     *
     * @return javax.swing.JTextField
     */
    private JTextField getTextFieldPassword() {
        if (textFieldPassword == null) {
            textFieldPassword = new JPasswordField();
            textFieldPassword.setPreferredSize(new java.awt.Dimension(44, 20));
            textFieldPassword.setText("benutzer"); // todo entfernen
        }
        return textFieldPassword;
    }


    /**
     * This method initializes btOk
     *
     * @return javax.swing.JButton
     */
    private JButton getBtOk() {
        if (btOk == null) {
            btOk = new JButton();
            btOk.setText("Ok");
            btOk.setPreferredSize(new java.awt.Dimension(90, 26));
            btOk.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // todo: in controller nehmen!
                    System.out.println("actionPerformed()");
                    ok();
                }
            });
        }
        return btOk;
    }

    /**
     * ok Methode
     *  todo: in controller nehmen!
     */
    private void ok() {
        System.out.println("ok");
        String tString;

/*
        if (this.textFieldUsername.getText() != null &&this.textFieldPassword.getText() != null) {
            try {
                LoginController lc = new LoginController();
                lc.doLogin(this.textFieldUsername.getText(),this.textFieldPassword.getText());
                // Maske schliessen und Ressourcen freigeben
                this.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Fehler bei der Eingabe: " + e);
            }
        }
*/
    }


    /**
     * This method initializes btAbbruch
     *
     * @return javax.swing.JButton
     */
    private JButton getBtAbbruch() {
        if (btAbbruch == null) {
            btAbbruch = new JButton();
            btAbbruch.setText("Abbruch");
            btAbbruch.setPreferredSize(new java.awt.Dimension(90, 26));
            btAbbruch.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.out.println("actionPerformed()");
                    abbruch();
                }
            });
        }
        return btAbbruch;
    }

    /**
     * abbruch Methode
     */
    private void abbruch() {
        System.out.println("abbruch - FINISH");
        // Maske schliessen und Ressourcen freigeben
        this.dispose();
    }


}
