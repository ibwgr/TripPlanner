package view.travel;

import com.teamdev.jxmaps.MapViewOptions;
import controller.common.MainController;
import controller.travel.ActivityController;
import model.travel.Activity;
import view.common.GridPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

public class ActivityView extends JPanel {

    /*
    TODO
    Fehlend: Headers noch nicht schoen, Groesse passt noch nicht, Soll nicht editierbar sein
    Auf FlowLayout umstellen!
     */

    ActivityController activityController;
    MainController mainController;

    JTable table;
    MapXXXXX mapView;

    // Getters, damit der ActivityController die View Werte lesen ann
    public JTable getTable() {
        return table;
    }

    // Constructor
    public ActivityView(MainController mainController) {

        this.mainController = mainController;
        activityController = new ActivityController(this, mainController);

        // generelles panel
        this.setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        table.setAutoCreateColumnsFromModel(true);
        table.setPreferredSize(new Dimension(400,200));
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setDefaultEditor(Object.class, null); // damit Feld nicht editiert werden kann
      //table.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        // SelectionListener in Controller!
        table.getSelectionModel().addListSelectionListener(activityController);
        // MouseListener in Controller!
        table.addMouseListener(activityController);

        // Create columns
        String[] columnNames = {"Nr", "Date", "POI", "Cat", "Comment"};
        for (String column : columnNames){
            tableModel.addColumn(column);
        }
        // TripListe (from Controller)
        ArrayList<Activity> activityList = activityController.getActivityList();
        for (Activity activity : activityList) {
            // Append a row
            tableModel.addRow(new Object[]{activity.getId(), activity.getDate(), activity.getPoi().getName(), activity.getPoi().getPoiCategory().getName(), activity.getComment()});
        }
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
        mapView = new MapXXXXX(options);
      //mapView.setMarker(mainController.getActivity());
      //mapView.setMarker(Activity.searchById(1L));  // TODO
      //mapView.setMarkerList(Activity.searchByTrip(mainController.getTrip()));
        centerPanel.add(mapView);


        // TODO, plaziert nach Mergekonflikt, Dieter's Button
        GridPanel buttonPanel = new GridPanel(300,16);
        buttonPanel.addComponentDirect(buttonPanel.createButton("show map", "show_map", activityController));
        centerPanel.add(buttonPanel, BorderLayout.EAST);

        // alles aufs generelle panel
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }


    //https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths
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

}
