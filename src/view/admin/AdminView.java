package view.admin;

import controller.admin.AdminController;
import controller.admin.ImportController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by dieterbiedermann on 04.01.17.
 */
public class AdminView extends JFrame {

    private JButton chooseFileButton, startImportButton;
    private JLabel fileName, fileNameLabel, fileTypeLabel, fileDelimiterLabel;
    private ButtonGroup fileTypeGroup;
    private JRadioButton fileTypeCategory, fileTypePoi;
    private ButtonGroup fileDelimiterGroup;
    private JRadioButton fileDelimiterComma, fileDelimiterSemicolon, fileDelimiterPipe;

    public AdminView() {

        AdminController adminController = new AdminController(this);

        this.setLayout(new BorderLayout());
        JPanel gridPanel = new JPanel(new GridLayout(5,1));

        chooseFileButton = new JButton("Open file");
        chooseFileButton.setActionCommand("open_file");
        chooseFileButton.addActionListener(adminController);

        fileNameLabel = new JLabel("File:");
        fileNameLabel.setPreferredSize(new Dimension(100,20));
        fileName = new JLabel();

        fileTypeLabel = new JLabel("Type:");
        fileTypeLabel.setPreferredSize(new Dimension(100,20));
        fileTypeCategory = new JRadioButton("Category", true);
        fileTypePoi = new JRadioButton("Point of interest");
        fileTypeGroup = new ButtonGroup();
        fileTypeGroup.add(fileTypeCategory);
        fileTypeGroup.add(fileTypePoi);

        fileDelimiterLabel = new JLabel("Delimiter:");
        fileDelimiterLabel.setPreferredSize(new Dimension(100,20));
        fileDelimiterPipe = new JRadioButton("|", true);
        fileDelimiterComma = new JRadioButton(",");
        fileDelimiterSemicolon = new JRadioButton(";");
        fileDelimiterGroup = new ButtonGroup();
        fileDelimiterGroup.add(fileDelimiterPipe);
        fileDelimiterGroup.add(fileDelimiterComma);
        fileDelimiterGroup.add(fileDelimiterSemicolon);

        startImportButton = new JButton("Import file");
        startImportButton.setActionCommand("import_file");
        startImportButton.addActionListener(adminController);

        gridPanel.add(chooseFileButton);
        JPanel jpanelFileName = new JPanel(new FlowLayout(FlowLayout.LEADING));
        jpanelFileName.add(fileNameLabel);
        jpanelFileName.add(fileName);
        gridPanel.add(jpanelFileName);
        JPanel jpanelFileType = new JPanel(new FlowLayout(FlowLayout.LEADING));
        jpanelFileType.add(fileTypeLabel);
        jpanelFileType.add(fileTypeCategory);
        jpanelFileType.add(fileTypePoi);
        gridPanel.add(jpanelFileType);
        JPanel jpanelDelimiter = new JPanel(new FlowLayout(FlowLayout.LEADING));
        jpanelDelimiter.add(fileDelimiterLabel);
        jpanelDelimiter.add(fileDelimiterPipe);
        jpanelDelimiter.add(fileDelimiterComma);
        jpanelDelimiter.add(fileDelimiterSemicolon);
        gridPanel.add(jpanelDelimiter);
        gridPanel.add(startImportButton);

        this.add(gridPanel, BorderLayout.NORTH);
        this.setTitle("TripPlanner - Administration");
        this.setSize(new Dimension(500,300));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        AdminView adminView = new AdminView();
        adminView.setVisible(true);
    }

    public void setFileName(String fileName) {
        this.fileName.setText(fileName);
    }

    public String getFileType() {
        return fileTypeCategory.isSelected() ? "category" : "poi";
    }

    public String getFileDelimiter() {
        String str = null;
        if (fileDelimiterPipe.isSelected()) {
            str = "|";
        } else if (fileDelimiterComma.isSelected()) {
            str = ",";
        } else if (fileDelimiterSemicolon.isSelected()) {
            str = ";";
        }
        return str;
    }
}
