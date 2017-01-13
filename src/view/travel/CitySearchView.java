package view.travel;

import controller.common.MainController;
import controller.travel.CitySearchController;
import model.common.Poi;
import org.jdesktop.swingx.JXDatePicker;
import view.common.GridPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class CitySearchView extends JPanel {

    MainController mainController;
    CitySearchController citySearchController;

    JList searchResult;
    JTextField searchText;
    JTextArea commentText;
    JTextField dateField;
    JXDatePicker datePicker;

    public CitySearchView(MainController mainController) {
        this.mainController = mainController;
        citySearchController = new CitySearchController(this, mainController);

        this.setLayout(new BorderLayout());

        // NORTH: Search criteria
        GridPanel gridPanel1 = new GridPanel(100,20);
        gridPanel1.addComponentToPanel(searchText = new JTextField(10));
        gridPanel1.addComponentToPanel(gridPanel1.createButton("Search", "searchCity", citySearchController));
        gridPanel1.addPanelWithLabel("City name:", true);
        this.add(gridPanel1, BorderLayout.NORTH);

        // CENTER: Result
        this.add(new JScrollPane(searchResult = new JList<>()), BorderLayout.CENTER);

//        MapView mapView = new MapView();

        // SOUTH: add Activity, forward to Poi Search
        GridPanel gridPanel2 = new GridPanel(300,20);

        gridPanel2.addComponentToPanel(gridPanel2.createButton("Search POI", "searchPoi", citySearchController));
        gridPanel2.addPanelWithLabel("Add city to the activity or search for POI:", true);


//        gridPanel2.addPanelWithLabel(new JXDatePicker());

        // Create date picker with current date initially selected.
//        JXDatePicker datePicker = new JXDatePicker ();
        // Create date picker with specified date initially selected.
/*
        Calendar cal = Calendar.getInstance ();
        cal.set (2006, 7, 1); // August 1, 2006
        long millis = cal.getTimeInMillis ();
*/

//        gridPanel2.addComponentToPanel(dateField = new JTextField(10));
        gridPanel2.addComponentToPanel(datePicker = new JXDatePicker());
        gridPanel2.addPanelWithLabel("Date:", true);

        commentText = new JTextArea(5,30);
        gridPanel2.addComponentToPanel(new JScrollPane(commentText));
        gridPanel2.addPanelWithLabel("Comment:", true);

        gridPanel2.addComponentToPanel(gridPanel2.createButton("Add Activity", "add_activity", citySearchController));
        gridPanel2.addPanelWithLabel("", true);

        this.add(gridPanel2, BorderLayout.SOUTH);

        // Default Action für Enter innerhalb des Eingabefeldes
        searchText.setActionCommand("searchCity");
        searchText.addActionListener(citySearchController);

    }

    public String getSearchText() {
        return searchText.getText();
    }

    public void setSearchResult(ArrayList<Poi> searchResult) {
        this.searchResult.setListData(new Vector(searchResult));
    }

    public Poi getPoi() {
        return (Poi) searchResult.getSelectedValue();
    }

    public Date getDate() {
        return datePicker.getDate();
    }

    public String getComment() {
        return commentText.getText();
    }
}
