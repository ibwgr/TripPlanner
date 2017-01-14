package view.travel;

import controller.common.MainController;
import controller.travel.ActivityController;
import model.travel.Activity;
import model.travel.Trip;
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

    public ActivityView(MainController mainController) {

        this.mainController = mainController;
        activityController = new ActivityController(this, mainController);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        //table.setAutoCreateColumnsFromModel(true);
        //table.setPreferredSize(new Dimension(400,200));
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

//        // todo in controller, aber wie?
//        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
//            public void valueChanged(ListSelectionEvent event) {
//                // do some actions here, for example
//                // print first column value from selected row
//                System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
//                Long tripId = (Long) table.getValueAt(table.getSelectedRow(), 0);
//                activityController.setCurrentTripId(tripId);
//            }
//        });


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
        resizeColumnWidth(table);

        // Generelles Panel fuer Gesamtanzeige
        JPanel anzeigePanel = new JPanel(new BorderLayout());

        // Spezielles Panel fuer die Buttons (rechts)
        GridPanel buttonPanel = new GridPanel(300,16);

        //
        anzeigePanel.add(new JScrollPane( table ), BorderLayout.CENTER);
        anzeigePanel.add(buttonPanel, BorderLayout.EAST);

//        // Detailbutton
//        JButton detailButton = new JButton("Detail");
//        detailButton.setActionCommand("detail");
//        detailButton.addActionListener(tripController);
//        buttonPanel.add(detailButton);



        // alles aufs Hauptpanel platzieren
        this.add(anzeigePanel);
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
