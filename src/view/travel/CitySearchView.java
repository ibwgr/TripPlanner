package view.travel;

import controller.common.LoginController;
import controller.common.MainController;
import controller.travel.CitySearchController;
import model.common.Poi;
import view.common.GridPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Vector;

public class CitySearchView extends GridPanel {

    MainController mainController;
    CitySearchController citySearchController;

    JList searchResult;
    JTextField searchText;

    public CitySearchView(MainController mainController) {
        super(3,1,100, 20);

        this.mainController = mainController;
        citySearchController = new CitySearchController(this, mainController);

        addComponentToPanel(searchText = new JTextField(10));
        addComponentToPanel(createButton("Search", "searchCity", citySearchController));
        //addComponentToPanel(list = new JList<>());
        addComponentDirect(searchResult = new JList<>());

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
