package view.common;

import controller.common.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TripPlannerMain extends JFrame {

    private JLabel titleLabel, viewTitleLabel, usernameLabel, errorMessageLabel;
    private JPanel errorPanel, headerPanel;
    private JButton closeErrorPanel;
    private GridBagLayout mainLayout;
    private GridBagConstraints constraintsHeader, constraintsView;
    private MainController mainController;
    private ArrayList<Component> componentList = new ArrayList<>();
    private JPanel contentPanel;
    private JMenuBar jJMenuBar = null;
    private JMenu loginMenu = null;
    private JMenu helpMenu = null;
    private JMenuItem loginMenuItem = null;
    private JMenuItem exitMenuItem = null;
    private JMenuItem aboutMenuItem = null;

    public static void main(String[] args) {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        tripPlannerMain.setVisible(true);
    }

    public TripPlannerMain(int rows, int cols) {

        mainController = new MainController(this);

        mainLayout = new GridBagLayout();
        this.setLayout(mainLayout);

        constraintsHeader = new GridBagConstraints();
        constraintsHeader.gridx = 0;
        constraintsHeader.anchor = GridBagConstraints.FIRST_LINE_START;
        constraintsHeader.fill = GridBagConstraints.HORIZONTAL;
        constraintsHeader.weightx = 0.1;

        constraintsView = new GridBagConstraints();
        constraintsView.gridx = 0;
        constraintsView.gridy = GridBagConstraints.RELATIVE;
        constraintsView.anchor = GridBagConstraints.FIRST_LINE_START;
        constraintsView.fill = GridBagConstraints.BOTH;
        constraintsView.weightx = 0.1;
        constraintsView.weighty = 0.1;

        headerPanel = new JPanel(new GridLayout(2,2));
        headerPanel.setBackground(Color.decode("#96BFE1"));
        headerPanel.setSize(new Dimension(300, 60));

        titleLabel = new JLabel("TripPlanner");
        titleLabel.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        headerPanel.add(titleLabel);

        usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        headerPanel.add(usernameLabel);

        viewTitleLabel = new JLabel();
        viewTitleLabel.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        headerPanel.add(viewTitleLabel);

        this.add(headerPanel, constraintsHeader);

        errorPanel = new JPanel(new FlowLayout());
        errorMessageLabel = new JLabel();
        errorMessageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        errorPanel.add(errorMessageLabel);
        closeErrorPanel = new JButton("close");
        closeErrorPanel.setActionCommand("close_error");
        closeErrorPanel.addActionListener(mainController);
        errorPanel.add(closeErrorPanel);
        errorPanel.setVisible(false);
        errorPanel.setBackground(Color.decode("#EB8B8B"));
        errorPanel.setSize(new Dimension(200, 60));
        this.add(errorPanel, constraintsHeader);

        //this.setMinimumSize(new Dimension(640,480));
        this.setTitle("TripPlanner");

        contentPanel = new JPanel(new GridLayout(rows,cols));
        this.add(contentPanel, constraintsView);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setJMenuBar(getJJMenuBar());
        this.setSize(new Dimension(600,420));

        mainController.openLogin();

    }

    public void refreshView() {
        contentPanel.removeAll();
        for (Component component: componentList) {
            contentPanel.add(new JScrollPane(component));
        }
        contentPanel.updateUI();
    }

    public void addView(Component component) {
        componentList.add(component);
        refreshView();
    }

    public void addView(String title, Component component) {
        setViewTitle(title);
        addView(component);
    }

    public void replaceView(Component component, int index) {
        if (componentList.get(index) != null) {
            componentList.set(index, component);
            refreshView();
        }
    }

    public void showErrorMessage(String message) {
        errorPanel.setVisible(true);
        errorMessageLabel.setText(message);
    }

    public void removeView(int index) {
        if (componentList.get(index) != null) {
            componentList.remove(index);
            refreshView();
        }
    }

    public void removeAllViews() {
        componentList.clear();
        refreshView();
    }

    public void setViewTitle(String viewTitle) {
        viewTitleLabel.setText(viewTitle);
    }


    private JMenuBar getJJMenuBar()
    {
        if (jJMenuBar == null)
        {
            jJMenuBar = new JMenuBar();
            jJMenuBar.add(getLoginMenu());
            jJMenuBar.add(getHelpMenu());
            //jJMenuBar.add(getLoggedInInformationText());
//            JMenuItem menuItem = new JMenuItem();
//            JPanel panelLabel = new JPanel();
//            JLabel lblSomeText = new JLabel("-- no user --");
//            lblSomeText.setFont(menuItem.getFont());
//            lblSomeText.setForeground(menuItem.getForeground());
//            panelLabel.add(lblSomeText);
//            jJMenuBar.add(panelLabel);
        }
        return jJMenuBar;
    }

    private JMenu getLoginMenu()
    {
        if (loginMenu == null)
        {
            loginMenu = new JMenu();
            loginMenu.setText("Login");
            loginMenu.add(getLoginMenuItem());
            loginMenu.add(getExitMenuItem());
        }
        return loginMenu;
    }

    private JMenu getHelpMenu()
    {
        if (helpMenu == null)
        {
            helpMenu = new JMenu();
            helpMenu.setText("Help");
            helpMenu.add(getAboutMenuItem());
        }
        return helpMenu;
    }

    private JMenuItem getAboutMenuItem()
    {
        if (aboutMenuItem == null)
        {
            aboutMenuItem = new JMenuItem();
            aboutMenuItem.setText("About");
            aboutMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JFrame f = new JFrame();
                    f.setVisible(false);
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    JDialog d = new JDialog(f,true);
                    d.getContentPane().add(new JLabel("TripPlanner System V1.0",JLabel.CENTER));
                    d.setTitle("About");
                    d.setSize(350,100);
                    d.setLocation(300,180);
                    d.setVisible(true);
                    d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            });
        }
        return aboutMenuItem;
    }

    private JMenuItem getExitMenuItem()
    {
        if (exitMenuItem == null)
        {
            exitMenuItem = new JMenuItem();
            exitMenuItem.setText("Exit");
            exitMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    System.exit(0);
                }
            });
        }
        return exitMenuItem;
    }

    private JMenuItem getLoginMenuItem()
    {
        if (loginMenuItem == null)
        {
            loginMenuItem = new JMenuItem();
            loginMenuItem.setText("Login");
            loginMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    mainController.openLogin();

                }
            });
        }
        return loginMenuItem;
    }

    public void closeErrorPanel() {
        errorPanel.setVisible(false);
    }

    public void setUsername(String username) {
        usernameLabel.setText("User: " + username);
    }

}
