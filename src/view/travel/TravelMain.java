package view.travel;

import view.common.TripPlannerMain;

import javax.swing.*;
import java.awt.*;

public class TravelMain extends TripPlannerMain {

    JTabbedPane tabbedPane;
    JPanel tripPanel, poiSearchPanel, activityPanel;

    public TravelMain() {
        super(1,1);

        tripPanel = new TripView();
        poiSearchPanel = new JPanel();
        activityPanel = new JPanel();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Trip", tripPanel);
        tabbedPane.addTab("Search", poiSearchPanel);
        tabbedPane.addTab("Activity", activityPanel);

        this.addView(tabbedPane);
        this.setSize(new Dimension(500,300));
    }

    public static void main(String[] args) {
        TravelMain travelMain = new TravelMain();
        travelMain.setVisible(true);
    }

}
