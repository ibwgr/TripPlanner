package view.travel;

import com.teamdev.jxmaps.MapViewOptions;
import controller.common.MainController;
import controller.travel.CompleteTripController;
import controller.travel.SearchController;
import model.common.Poi;
import model.travel.Activity;
import org.jdesktop.swingx.JXDatePicker;
import view.common.GridPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class CompleteTripView extends JPanel {

    MainController mainController;
    CompleteTripController completeTripController;

    JList searchResult;
    JTextField searchText;
    JTextArea commentText;
    JXDatePicker datePicker;
    MapPolygon mapView;

    public CompleteTripView(MainController mainController) {
        this.mainController = mainController;
        completeTripController = new CompleteTripController(this, mainController);

        this.setLayout(new BorderLayout());

        // NORTH: empty

        // CENTER: show big map
/*
        JPanel centerPanel = new JPanel(new GridLayout(1,1));
        centerPanel.add(new JScrollPane(searchResult = new JList<>()));
        searchResult.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                mapView.setWindow((Poi) searchResult.getSelectedValue());
            }
        });
*/

        MapViewOptions options = new MapViewOptions();
        options.importPlaces();
        mapView = new MapPolygon(options);
//        centerPanel.add(mapView);

        mapView.setMarkerList(Activity.searchByUserAndTrip(mainController.getUser(),mainController.getTrip()));

        this.add(mapView, BorderLayout.CENTER);

        // SOUTH: empty


    }

}
