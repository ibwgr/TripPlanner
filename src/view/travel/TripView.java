package view.travel;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import controller.common.MainController;
import controller.travel.TripController;
import model.travel.Trip;
import view.common.GridPanel;

public class TripView extends JPanel {

    /*
    TODO
    Fehlend: Headers noch nicht schoen, Groesse passt noch nicht, Soll nicht editierbar sein
    Auf FlowLayout umstellen!
     */

    TripController tripController;
    MainController mainController;

    DefaultTableModel tableModel;
    JTable table;

    public TripView(MainController mainController) {

        this.mainController = mainController;
        tripController = new TripController(this, mainController);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setAutoCreateColumnsFromModel(true);
        table.setPreferredSize(new Dimension(400,200));
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // todo in controller, geht das ueberhaupt?
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                try {
                    System.out.println("JTABLE Row | " +table.getValueAt(table.getSelectedRow(), 0).toString());
                    Long tripId = (Long) table.getValueAt(table.getSelectedRow(), 0);
                    tripController.setCurrentTripId(tripId);
                } catch (IndexOutOfBoundsException e) {
                    //index out of bound, only after delete, no problem!
                }
            }
        });

        // TableModel Data and Columns
        setUpTableTableColumns();
        setUpTableTableData();

        // spaltenbreite automatisch
        resizeColumnWidth(table);

        // Generelles Panel fuer Gesamtanzeige
        JPanel anzeigePanel = new JPanel(new BorderLayout());

        // Spezielles Panel fuer die Buttons (rechts)
        GridPanel buttonPanel = new GridPanel(2,1);   // todo, funktioniert so nicht recht...

        //
        anzeigePanel.add(new JScrollPane( table ), BorderLayout.CENTER);
        anzeigePanel.add(buttonPanel, BorderLayout.EAST);

        // Detailbutton
        JButton detailButton = new JButton("Detail");
        detailButton.setActionCommand("detail");
        detailButton.addActionListener(tripController);
        buttonPanel.add(detailButton);

        // Deletebutton
        JButton deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(tripController);
        deleteButton.setForeground(new Color(244, 100, 66));
      //deleteButton.setFont(new Font("Tahoma", Font.ITALIC, 11));
        buttonPanel.add(deleteButton);


        // alles aufs Hauptpanel platzieren
        this.add(anzeigePanel);

    }

    public void refreshTable() {
        System.out.println("TripView.refreshTable ...");
        //tableModel.fireTableDataChanged();
        //tableModel.fireTableRowsDeleted();
        setUpTableTableData();
        table.repaint();
    }

    public void setUpTableTableData() {
        tableModel.setRowCount(0);
         // TripListe (from Controller)
        ArrayList<Trip> tripList = tripController.getTripList();
        for (Trip trip : tripList) {
            // Append a row
            tableModel.addRow(new Object[]{trip.getId(), trip.getName(),trip.getMinDate(),trip.getMaxDate(), trip.getCountActivities()});
        }
    }

    private void setUpTableTableColumns() {
        // Create columns
        String[] columnNames = {"Nr", "Trip Name", "From-Date", "To-Date", "Act."};
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

}
