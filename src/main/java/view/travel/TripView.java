package view.travel;

import controller.common.MainController;
import controller.travel.TripController;
import model.travel.Trip;
import view.common.FormPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Diese Klasse zeigt alle Trips des Users an
 * Neue Trips koennen erfasst werden
 *
 * @author  Reto Kaufmann
 */
public class TripView extends JPanel {

    TripController tripController;
    MainController mainController;

    DefaultTableModel tableModel;
    JTable table;

    JButton detailButton;
    JButton deleteButton;
    JButton newActivityButton;
    JLabel dummyLabel;
    JTextField newTripNameField;
    JButton newTripSaveButton;

    // Getters, damit der TripController die View Werte lesen ann
    public JTextField getNewTripNameField() {
        return newTripNameField;
    }
    public JTable getTable() {
        return table;
    }

    // Constructor
    public TripView(MainController mainController) {

        this.mainController = mainController;
        mainController.setSubTitle(" ");
        mainController.setSubTitleVisible(true);


        tripController = new TripController(this, mainController);

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
        //
        // SelectionListener in Controller!
        table.getSelectionModel().addListSelectionListener(tripController);
        // MouseListener in Controller!
        table.addMouseListener(tripController);

        // TableModel Data and Columns
        setUpTableTableColumns();
        setUpTableTableData();

        // spaltenbreite automatisch
        resizeColumnWidth(table);

        // Generelles Panel fuer Gesamtanzeige
/*
        JPanel anzeigePanel = new JPanel(new BorderLayout());
        this.add(anzeigePanel);
*/

        this.setLayout(new BorderLayout());
        // Spezielles Panel fuer die Buttons (rechts)
        FormPanel sideButtonPanel = new FormPanel(20,150);
        // Spezielles Panel fuer die Buttons (unten)
        FormPanel bottomButtonPanel = new FormPanel(150,20);
//        bottomButtonPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        bottomButtonPanel.setToolTipText("Add a completely new trip");

        // alles aufs AnzeigePanel
        this.add(new JScrollPane( table ), BorderLayout.CENTER);
        this.add(sideButtonPanel, BorderLayout.EAST);
        this.add(bottomButtonPanel, BorderLayout.SOUTH);

        // SIDE Buttons
        sideButtonPanel.addComponentDirect(detailButton = sideButtonPanel.createButton("Detail", "detail", tripController));

        sideButtonPanel.addComponentDirect(deleteButton = sideButtonPanel.createButton("Delete", "delete", tripController));
        deleteButton.setForeground(new Color(244, 100, 66));

        sideButtonPanel.addComponentDirect(dummyLabel = sideButtonPanel.createLabel(" "));
        sideButtonPanel.addComponentDirect(dummyLabel = sideButtonPanel.createLabel(" "));

        sideButtonPanel.addComponentDirect(newActivityButton = sideButtonPanel.createButton("New Activity", "newActivty", tripController));

        // BOTTOM Buttons
        bottomButtonPanel.addComponentToPanel(dummyLabel = bottomButtonPanel.createLabel("ADD NEW TRIP"));
        bottomButtonPanel.addPanel(true);

        bottomButtonPanel.addComponentToPanel(newTripNameField = new JTextField(20));
        bottomButtonPanel.addPanelWithLabel("Trip Name / Label:", true);
        newTripNameField.setActionCommand("saveNewTrip");
        newTripNameField.addActionListener(tripController);

        bottomButtonPanel.addComponentToPanel(newTripSaveButton = bottomButtonPanel.createButton("Save", "saveNewTrip", tripController));
        bottomButtonPanel.addPanelWithLabel("",true);

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
