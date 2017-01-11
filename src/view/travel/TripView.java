package view.travel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.common.MainController;
import model.travel.Trip;

import java.awt.*;
import java.util.ArrayList;

public class TripView extends JPanel {

    private JLabel testLabel;

    //JTextField userField;
    //JPasswordField passwordField;
    //JButton loginButton;

    //LoginController loginController;   ... muss ein TripController sein!
    MainController mainController;

    public TripView(MainController mainController) {

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.setAutoCreateColumnsFromModel(true);
        table.setPreferredSize(new Dimension(400,200));
        table.setAutoCreateRowSorter(true);

        // Create a couple of columns
        model.addColumn("Nr");
        model.addColumn("Trip Name");
        model.addColumn("From");
        model.addColumn("To");
        model.addColumn("Activities");

        // TripListe (from Model)
        ArrayList<Trip> tripList = Trip.searchByUser(mainController.getUser());
        for (Trip trip : tripList) {
            // Append a row
            model.addRow(new Object[]{trip.getId(), trip.getName(),trip.getMinDate(),trip.getMaxDate(), trip.getCountActivities()});
        }

        this.add(table);
    }

}
