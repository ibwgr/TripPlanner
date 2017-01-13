package view.travel;

import model.common.Poi;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public interface SearchView {

    String getSearchText();

    void setSearchResult(ArrayList<Poi> searchResult);

    Poi getPoi();

    Date getDate();

    String getComment();

    void setPoiInList(Poi poi);

}
