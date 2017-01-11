package view.travel;

import controller.common.MainController;
import controller.travel.CitySearchController;
import model.common.Poi;
import view.common.GridPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class CitySearchView extends JPanel {

    MainController mainController;
    CitySearchController citySearchController;

    JList searchResult;
    JTextField searchText;

    public CitySearchView(MainController mainController) {
/*
        super(2,1,100, 20);
*/

        this.mainController = mainController;
        citySearchController = new CitySearchController(this, mainController);

        this.setLayout(new BorderLayout());

        // NORTH: Search criteria
        searchText = new JTextField(10);
        JButton button = new JButton("Search");
        button.setActionCommand("searchCity");
        button.addActionListener(citySearchController);
        JPanel flowPanel1 = new JPanel(new FlowLayout());
//        flowPanel = new GridPanel(1,1,100,20);
        flowPanel1.add(new JLabel("City name:"));
        flowPanel1.add(searchText);
        flowPanel1.add(button);
        this.add(flowPanel1, BorderLayout.NORTH);

        // CENTER: Result
        this.add(new JScrollPane(searchResult = new JList<>()), BorderLayout.CENTER);

        // SOUTH: add Activity, forward to Poi Search


        /*
        addComponentToPanel(searchText = new JTextField(10));
        addComponentToPanel(createButton("Search", "searchCity", citySearchController));
        addComponentToPanel(new JScrollPane(searchResult = new JList<>()));
        addPanelWithLabel("City name:",true);
*/
//        addPanelWithLabel("Result:",true);

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
}
