package view.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class GridPanel extends JPanel {

    private Dimension labelDimension;
    JPanel gridPanel;
    ArrayList<Component> compoList = new ArrayList<>();

    public GridPanel(int rows, int cols, int labelWidth, int labelHeight) {
        labelDimension = new Dimension(labelWidth, labelHeight);
        gridPanel = new JPanel(new GridLayout(rows, cols));
        this.add(gridPanel);
    }

    void addComponentToPanel(Component component) {
        compoList.add(component);
    }

    JRadioButton createRadioButton(String text, String cmd, boolean selected, ButtonGroup buttonGroup) {
        JRadioButton radioButton = new JRadioButton(text, selected);
        radioButton.setActionCommand(cmd);
        buttonGroup.add(radioButton);
        return radioButton;
    }

    JButton createButton(String text, String cmd, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setActionCommand(cmd);
        button.addActionListener(actionListener);
        return button;
    }

    void addPanelWithLabel(String text, Boolean clearComponent) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(labelDimension);
        JPanel jpanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        jpanel.add(label);
        for (Component compo:compoList) {
            jpanel.add(compo);
        }
        gridPanel.add(jpanel);
        if (clearComponent) {
            compoList.clear();
        }
    }

}
