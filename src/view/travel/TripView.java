package view.travel;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import controller.common.MainController;
import controller.travel.TripController;
import model.travel.Trip;

public class TripView extends JPanel {

    private JLabel testLabel;

    /*
    TODO
    Fehlend: Headers noch nicht schoen, Groesse passt noch nicht, Soll nicht editierbar sein
    Fehlend: Details Button mit entspr. Row
     */

    TripController tripController;
    MainController mainController;

    public TripView(MainController mainController) {

        this.mainController = mainController;
        tripController = new TripController(this, mainController);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        //table.setAutoCreateColumnsFromModel(true);
        //table.setPreferredSize(new Dimension(400,200));
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Create columns
        String[] columnNames = {"Nr", "Trip Name", "From-Date", "To-Date", "Act."};
        for (String column : columnNames){
            tableModel.addColumn(column);
        }
        // TripListe (from Controller)
        ArrayList<Trip> tripList = tripController.getTripList();
        for (Trip trip : tripList) {
            // Append a row
            tableModel.addRow(new Object[]{trip.getId(), trip.getName(),trip.getMinDate(),trip.getMaxDate(), trip.getCountActivities()});
        }

        resizeColumnWidth(table);

        // Tabelle platzieren
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane( table ), BorderLayout.CENTER);

        // Detailbutton platzieren
        JPanel southPanel = new JPanel(new BorderLayout());
        JButton detailButton = new JButton("Detail");
        detailButton.setActionCommand("detail");
        detailButton.addActionListener(tripController);
        southPanel.add(detailButton, BorderLayout.SOUTH);


        // alles aufs Hauptpanel platzieren
        this.add(centerPanel);
        this.add(southPanel);
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
