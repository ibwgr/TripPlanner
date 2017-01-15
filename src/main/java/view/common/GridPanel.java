package view.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GridPanel extends JPanel {

    private Dimension labelDimension;
    JPanel gridBagPanel;
    GridBagConstraints constraints;
    ArrayList<Component> compoList = new ArrayList<>();

    public GridPanel(int labelWidth, int labelHeight) {
        labelDimension = new Dimension(labelWidth, labelHeight);
        gridBagPanel = new JPanel(new GridBagLayout());

        // Constraints: jedes neue Element unterhalb des vorherigen Elements anordnen
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;

        this.add(gridBagPanel);
    }

    public void addComponentToPanel(Component component) {
        compoList.add(component);
    }

    public JRadioButton createRadioButton(String text, String cmd, boolean selected, ButtonGroup buttonGroup) {
        JRadioButton radioButton = new JRadioButton(text, selected);
        radioButton.setActionCommand(cmd);
        buttonGroup.add(radioButton);
        return radioButton;
    }

    public JButton createButton(String text, String cmd, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setActionCommand(cmd);
        button.addActionListener(actionListener);
        return button;
    }

    public JButton createButton(String text, String cmd, ActionListener actionListener, Boolean enabled) {
        JButton button = createButton(text,cmd,actionListener);
        button.setEnabled(enabled);
        return button;
    }

    public JLabel createLabel(String text, String cmd, ActionListener actionListener) {
        JLabel label = new JLabel(text);
        return label;
    }

    public void addPanelWithLabel(String text, Boolean clearComponent) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(labelDimension);
//        label.setHorizontalAlignment(JLabel.RIGHT);
        JPanel jpanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        jpanel.add(label);
        for (Component compo:compoList) {
            jpanel.add(compo);
        }
        gridBagPanel.add(jpanel, constraints);
        if (clearComponent) {
            compoList.clear();
        }
    }

    public void addPanel(Boolean clearComponent) {
        JPanel jpanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        for (Component compo:compoList) {
            jpanel.add(compo);
        }
        gridBagPanel.add(jpanel, constraints);
        if (clearComponent) {
            compoList.clear();
        }
    }

    public void addComponentDirect(Component component) {
        gridBagPanel.add(component, constraints);
    }

}
