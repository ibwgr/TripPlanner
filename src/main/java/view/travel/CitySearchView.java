package view.travel;

import com.teamdev.jxmaps.MapViewOptions;
import controller.common.MainController;
import controller.travel.SearchController;
import model.common.Poi;
import model.common.PoiCategory;
import org.jdesktop.swingx.JXDatePicker;
import view.common.FormPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class CitySearchView extends JPanel implements SearchView {

    MainController mainController;
    SearchController searchController;

    JList searchResult;
    JTextField searchText;
    JTextArea commentText;
    JXDatePicker datePicker;
    MapWithPoi mapView;

    public CitySearchView(MainController mainController) {
        this.mainController = mainController;
        searchController = new SearchController(this, mainController);

        this.setLayout(new BorderLayout());

        // NORTH: Search criteria
        FormPanel formPanel1 = new FormPanel(100,16);
        formPanel1.addComponentToPanel(searchText = new JTextField(10));
        formPanel1.addComponentToPanel(formPanel1.createButton("Search", "search_city", searchController));
        formPanel1.addPanelWithLabel("City name:", true);

        // Default Action für Enter innerhalb des Eingabefeldes
        searchText.setActionCommand("search_city");
        searchText.addActionListener(searchController);

        this.add(formPanel1, BorderLayout.NORTH);

        // CENTER: Result
        JPanel centerPanel = new JPanel(new GridLayout(1,2));
        centerPanel.add(new JScrollPane(searchResult = new JList<>()));
        searchResult.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                mapView.setWindow((Poi) searchResult.getSelectedValue());
            }
        });

        MapViewOptions options = new MapViewOptions();
        options.importPlaces();
        mapView = new MapWithPoi(options, this);
        centerPanel.add(mapView);

        this.add(centerPanel, BorderLayout.CENTER);

        // SOUTH: add Activity, forward to Poi Search
        FormPanel formPanel2 = new FormPanel(300,16);

        formPanel2.addComponentToPanel(formPanel2.createButton("Search Poi near city", "open_poi_search", searchController));
        formPanel2.addPanelWithLabel("Add city to the activity or search for POI:", true);

        formPanel2.addComponentToPanel(datePicker = new JXDatePicker());
        formPanel2.addPanelWithLabel("Date:", true);

        commentText = new JTextArea(5,30);
        formPanel2.addComponentToPanel(new JScrollPane(commentText));
        formPanel2.addPanelWithLabel("Comment:", true);

        formPanel2.addComponentToPanel(formPanel2.createButton("Add Activity", "add_activity", searchController));
        formPanel2.addPanelWithLabel("", true);

        this.add(formPanel2, BorderLayout.SOUTH);

    }

    @Override
    public String getSearchText() {
        return searchText.getText();
    }

    @Override
    public void setSearchResult(ArrayList<Poi> searchResult) {
        this.searchResult.setListData(new Vector<>(searchResult));
        mapView.setMarkerList(searchResult);
    }

    @Override
    public Poi getPoi() {
        return (Poi) searchResult.getSelectedValue();
    }

    @Override
    public Date getDate() {
        return datePicker.getDate();
    }

    @Override
    public String getComment() {
        return commentText.getText();
    }

    @Override
    public void setPoiInList(Poi poi) {
        searchResult.setSelectedValue(poi, true);
    }

    @Override
    public PoiCategory getPoiCategory() {
        return null;
    }

    @Override
    public Poi getCity() {
        return null;
    }

    @Override
    public double getRadius() {
        return 0;
    }
}
