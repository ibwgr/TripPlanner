package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dieterbiedermann on 06.01.17.
 */
public class TripPlannerMain extends JFrame {

    private JLabel titleLabel, viewTitleLabel;
    private GridBagLayout mainLayout;
    private GridBagConstraints constraintsHeader, constraintsView;

    public TripPlannerMain() {

        mainLayout = new GridBagLayout();
        this.setLayout(mainLayout);

        constraintsHeader = new GridBagConstraints();
        constraintsHeader.gridx = 0;
//        constraintsHeader.gridy = 0;
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

        JPanel headerPanel = new JPanel(new GridLayout(1,2));
        headerPanel.setBackground(Color.decode("#96BFE1"));

        titleLabel = new JLabel("TripPlanner");
        titleLabel.setFont(new Font("TimesRoman", Font.PLAIN, 36));
        titleLabel.setPreferredSize(new Dimension(200, 60));
        headerPanel.add(titleLabel);
//        headerPanel.setBorder(new CompoundBorder(new EmptyBorder(4, 4, 4, 4), new MatteBorder(0, 0, 1, 0, Color.BLACK)));

        viewTitleLabel = new JLabel();
        viewTitleLabel.setFont(new Font("TimesRoman", Font.PLAIN, 24));
        headerPanel.add(viewTitleLabel);

        this.add(headerPanel, constraintsHeader);

        this.setMinimumSize(new Dimension(640,480));
        this.setTitle("TripPlanner");
    }

    public void addView(Component component) {
        this.add(new JScrollPane(component), constraintsView);
    }

    public void setViewTitle(String viewTitle) {
        viewTitleLabel.setText(viewTitle);
    }

    public static void main(String[] args) {

    }

}
