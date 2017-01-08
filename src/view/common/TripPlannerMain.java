package view.common;

import controller.common.MainController;
import sun.applet.Main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class TripPlannerMain extends JFrame {

    private JLabel titleLabel, viewTitleLabel, errorMessageLabel;
    private JPanel errorPanel, headerPanel;
    private JButton closeErrorPanel;
    private GridBagLayout mainLayout;
    private GridBagConstraints constraintsHeader, constraintsView;
    private MainController mainController;
    private ArrayList<Component> componentList = new ArrayList<>();
    private JPanel contentPanel;

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

        headerPanel = new JPanel(new GridLayout(1,2));
        headerPanel.setBackground(Color.decode("#96BFE1"));
        headerPanel.setSize(new Dimension(200, 60));

        titleLabel = new JLabel("TripPlanner");
        titleLabel.setFont(new Font("TimesRoman", Font.PLAIN, 36));
        headerPanel.add(titleLabel);

        viewTitleLabel = new JLabel();
        viewTitleLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
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
    }

    public void refreshView() {
        contentPanel.removeAll();
        for (Component component: componentList) {
            JPanel borderPanel = new JPanel();
            borderPanel.add(component, BorderLayout.NORTH);
            contentPanel.add(new JScrollPane(borderPanel));
        }
        contentPanel.updateUI();
    }

    public void addView(Component component) {
        componentList.add(component);
        refreshView();
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

    public void setViewTitle(String viewTitle) {
        viewTitleLabel.setText(viewTitle);
    }

    public void closeErrorPanel() {
        errorPanel.setVisible(false);
    }
}
