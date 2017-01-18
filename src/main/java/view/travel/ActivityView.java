package view.travel;

import com.teamdev.jxmaps.MapViewOptions;
import controller.common.MainController;
import controller.travel.ActivityController;
import model.common.Poi;
import model.travel.Activity;
import model.travel.Trip;
import org.jdesktop.swingx.JXDatePicker;
import view.common.GridPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class ActivityView extends JPanel {

    ActivityController activityController;
    MainController mainController;

    DefaultTableModel tableModel;
    JTable table;
    //MapXXXXX mapView;
    JTextArea commentText;
    JXDatePicker datePicker;
    MapPolyline mapView;

    // Getters, damit der ActivityController die View Werte lesen ann
    public JTable getTable() {
        return table;
    }

    // Constructor
    public ActivityView(MainController mainController) {

        this.mainController = mainController;

        mapView = new MapPolyline(mainController, this);

        activityController = new ActivityController(this, mainController, mapView);

        // generelles panel
        this.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setAutoCreateColumnsFromModel(true);
        table.setPreferredSize(new Dimension(400,200));
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null); // damit Feld nicht editiert werden kann
      //table.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        // SelectionListener in Controller!
        table.getSelectionModel().addListSelectionListener(activityController);
        // MouseListener in Controller!
        table.addMouseListener(activityController);

        // TableModel Data and Columns
        setUpTableTableColumns();
        setUpTableTableData();

        // spaltenbreite automatisch
        resizeColumnWidth(table);

        // CENTER: Hauptpanel
        JPanel centerPanel = new JPanel(new GridLayout(1,2,10,10));

        // marginPanel
        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.add(new JScrollPane( table ), BorderLayout.CENTER);
        centerPanel.add(marginPanel);

        MapViewOptions options = new MapViewOptions();
        options.importPlaces();
      //mapView = new MapXXXXX(options);
//        if (mainController.getActivity() != null) {
            //mapView.setMarker(mainController.getActivity());
//        }
        centerPanel.add(mapView);


        // TODO, plaziert nach Mergekonflikt, Dieter's Button
        GridPanel buttonPanel = new GridPanel(100,16);
/*
        buttonPanel.addComponentDirect(buttonPanel.createButton("up", "move_up", activityController));
        buttonPanel.addComponentDirect(buttonPanel.createButton("down", "move_down", activityController));
        buttonPanel.addComponentDirect(buttonPanel.createLabel("", "", activityController));
        buttonPanel.addComponentDirect(buttonPanel.createButton("show map", "show_map", activityController));
*/
        // Variante mit Buttons nebeneinander:
        buttonPanel.addComponentToPanel(buttonPanel.createButton("up", "move_up", activityController));
        buttonPanel.addComponentToPanel(buttonPanel.createButton("down", "move_down", activityController));
        buttonPanel.addComponentToPanel(buttonPanel.createLabel("", "", activityController));
        buttonPanel.addComponentToPanel(buttonPanel.createButton("New Activity", "newActivty", activityController));
        buttonPanel.addComponentToPanel(buttonPanel.createLabel("", "", activityController));
        buttonPanel.addComponentToPanel(buttonPanel.createButton("show map", "show_map", activityController));
        buttonPanel.addPanel(true);

        buttonPanel.addComponentDirect(new JLabel(""));

        buttonPanel.addComponentToPanel(datePicker = new JXDatePicker());
        buttonPanel.addPanelWithLabel("Date:", true);

        commentText = new JTextArea(5,30);
        buttonPanel.addComponentToPanel(new JScrollPane(commentText));
        buttonPanel.addPanelWithLabel("Comment:", true);

        buttonPanel.addComponentToPanel(buttonPanel.createButton("Update", "update_activity", activityController));
        buttonPanel.addComponentToPanel(buttonPanel.createButton("Delete", "delete_activity", activityController));
        buttonPanel.addPanelWithLabel("", true);

        centerPanel.add(buttonPanel, BorderLayout.EAST);

        // alles aufs generelle panel
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }


    public void refreshTable() {
        System.out.println("ActivityView.refreshTable ...");
        tableModel.setRowCount(0);
        //tableModel.fireTableDataChanged();
        //tableModel.fireTableRowsDeleted();
        setUpTableTableData();
        table.repaint();
    }

    // TODO fehlt hier nicht noch CITY ?
    private void setUpTableTableData() {
        // TripListe (from Controller)
        ArrayList<Activity> activityList = activityController.getActivityList();
        for (Activity activity : activityList) {
            // Append a row
            tableModel.addRow(new Object[]{activity.getId(), activity.getDate(), activity.getPoi().getName(), activity.getPoi().getPoiCategory().getName(), activity.getComment()});
        }
    }

    private void setUpTableTableColumns() {
        // Create columns
        String[] columnNames = {"Nr", "Date", "POI", "Cat", "Comment"};
        for (String column : columnNames){
            tableModel.addColumn(column);
        }
    }


    // auto resizing the jtable column widths
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 20; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 150)
                width=150;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    public void setActivityInList(Activity activity) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(activity.getId())) {
                table.setRowSelectionInterval(0, i);
            }
        }
    }

    public Date getDate() {
        return datePicker.getDate();
    }

    public void setDate(Date date) {
        datePicker.setDate(date);
    }

    public String getComment() {
        return commentText.getText();
    }

    public void setComment(String comment) {
        commentText.setText(comment);
    }

}
