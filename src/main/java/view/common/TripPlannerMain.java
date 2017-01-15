package view.common;

import controller.common.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TripPlannerMain extends JFrame {

    private JLabel titleLabel, viewTitleLabel, usernameLabel, errorMessageLabel, subTitleLabel;
    private JPanel errorPanel, headerPanel, subHeaderPanel;
    private JButton closeErrorPanel, backButton, forwardButton, closeViewButton;
    private GridBagLayout mainLayout;
    private GridBagConstraints constraintsHeader, constraintsView;
    private MainController mainController;
    private ArrayList<Component> componentList = new ArrayList<>();
    private JPanel contentPanel;
    private JMenuBar jJMenuBar = null;
    private JMenu loginMenu = null, helpMenu = null, testMenu = null;
    private JMenuItem loginMenuItem = null, exitMenuItem = null, aboutMenuItem = null, citySearchMenuItem = null;

    private Color headerColor = Color.decode("#96BFE1");

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

        // Header Panel
        // Anzeige von Applikationsnamen, Name der geöffneten View,
        // Name des Users und Navigationsbuttons
        headerPanel = new JPanel(new GridLayout(2,3));
        headerPanel.setBackground(headerColor);
        headerPanel.setSize(new Dimension(300, 60));

        titleLabel = new JLabel("TripPlanner");
        titleLabel.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        headerPanel.add(titleLabel);

        headerPanel.add(new JLabel());

        usernameLabel = new JLabel();
        usernameLabel.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        headerPanel.add(usernameLabel);

        viewTitleLabel = new JLabel();
        viewTitleLabel.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        headerPanel.add(viewTitleLabel);

        Dimension buttonDimension = new Dimension(10,15);
        backButton = new JButton("<");
        backButton.setActionCommand("back");
        backButton.addActionListener(mainController);
        backButton.setSize(buttonDimension);
        forwardButton = new JButton(">");
        forwardButton.setActionCommand("forward");
        forwardButton.addActionListener(mainController);
        forwardButton.setSize(buttonDimension);
        closeViewButton = new JButton("X");
        closeViewButton.setActionCommand("close_view");
        closeViewButton.addActionListener(mainController);
        closeViewButton.setSize(buttonDimension);
        JPanel flowPanel1 = new JPanel(new FlowLayout());
        flowPanel1.setBackground(headerColor);
        flowPanel1.add(backButton);
        flowPanel1.add(forwardButton);
        headerPanel.add(flowPanel1);
        JPanel flowPanel2 = new JPanel(new FlowLayout());
        flowPanel2.setBackground(headerColor);
        flowPanel2.add(closeViewButton);
        headerPanel.add(flowPanel2);

        this.add(headerPanel, constraintsHeader);

        // Subheader Panel
        // Zum Anzeigen des Namen der geöffneten Reise
        subHeaderPanel = new JPanel(new FlowLayout());
        subTitleLabel = new JLabel();
        subTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        subHeaderPanel.add(subTitleLabel);
        subHeaderPanel.setVisible(false);
        subHeaderPanel.setBackground(Color.decode("#fdf3f3"));
        subHeaderPanel.setSize(new Dimension(200, 60));
        this.add(subHeaderPanel, constraintsHeader);

        // Error Panel
        // Zum Anzeigen von Fehlermeldungen
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

        this.setTitle("TripPlanner");

        contentPanel = new JPanel(new GridLayout(rows,cols));
        this.add(contentPanel, constraintsView);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setJMenuBar(getJJMenuBar());
        this.setSize(new Dimension(800,600));

        // Die LoginView wird als erste View geöffnet,
        // damit sich der User als Benutzer oder Administrator einloggen kann
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

    public void closeErrorPanel() {
        errorPanel.setVisible(false);
    }

    public void setUsername(String username) {
        usernameLabel.setText("User: " + username);
    }

    public void setSubTitle(String subTitle) {
        subHeaderPanel.setVisible(true);
        subTitleLabel.setText(subTitle);
    }

    public void setBackButtonEnabled(Boolean enabled) {
        backButton.setEnabled(enabled);
    }

    public void setForwardButtonEnabled(Boolean enabled) {
        forwardButton.setEnabled(enabled);
    }

    private JMenuBar getJJMenuBar()
    {
        if (jJMenuBar == null)
        {
            jJMenuBar = new JMenuBar();
            jJMenuBar.add(getLoginMenu());
            jJMenuBar.add(getHelpMenu());
            jJMenuBar.add(getTestMenu());
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

    private JMenu getTestMenu()
    {
        if (testMenu == null)
        {
            testMenu = new JMenu();
            testMenu.setText("Test");
            testMenu.add(getCitySearchMenuItem());
        }
        return testMenu;
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

    private JMenuItem getCitySearchMenuItem()
    {
        if (citySearchMenuItem == null)
        {
            citySearchMenuItem = new JMenuItem();
            citySearchMenuItem.setText("CitySearch");
            citySearchMenuItem.addActionListener(e -> mainController.openCitySearchView());
        }
        return citySearchMenuItem;
    }

}
