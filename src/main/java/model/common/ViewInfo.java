package model.common;

import java.awt.*;

/**
 * Hilfsklasse zum Speichern von Optionen/Metadaten zu einer View
 *
 * @author  Dieter Biedermann
 */
public class ViewInfo {

    private String title;
    private Component compo;
    private Boolean showSubTitle;
    private Boolean needsRefresh;
    private Boolean hasBeenRefreshed;

    public ViewInfo(String title, Component compo, Boolean showSubTitle, Boolean needsRefresh) {
        this.title = title;
        this.compo = compo;
        this.showSubTitle = showSubTitle;
        this.needsRefresh = needsRefresh;
        this.hasBeenRefreshed = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Component getCompo() {
        return compo;
    }

    public void setCompo(Component compo) {
        this.compo = compo;
    }

    public Boolean getShowSubTitle() {
        return showSubTitle;
    }

    public void setShowSubTitle(Boolean showSubTitle) {
        this.showSubTitle = showSubTitle;
    }

    public Boolean getNeedsRefresh() {
        return needsRefresh;
    }

    public void setNeedsRefresh(Boolean needsRefresh) {
        this.needsRefresh = needsRefresh;
    }

    public Boolean getHasBeenRefreshed() {
        return hasBeenRefreshed;
    }

    public void setHasBeenRefreshed(Boolean hasBeenRefreshed) {
        this.hasBeenRefreshed = hasBeenRefreshed;
    }

    @Override
    public String toString() {
        return title;
    }
}
