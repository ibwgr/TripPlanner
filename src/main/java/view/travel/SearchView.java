package view.travel;

import model.common.Poi;
import model.common.PoiCategory;

import java.util.ArrayList;
import java.util.Date;

/**
 * Interface für die SearchViews
 *
 * @author  Dieter Biedermann
 */
public interface SearchView {

    String getSearchText();

    void setSearchResult(ArrayList<Poi> searchResult);

    Poi getPoi();

    Date getDate();

    String getComment();

    void setPoiInList(Poi poi);

    PoiCategory getPoiCategory();

    Poi getCity();

    double getRadius();

}
