import org.apache.commons.lang3.SystemUtils;
import view.common.TripPlannerMain;

import javax.swing.*;
import javax.swing.UIManager.*;

public class Start {
    public static void main(String[] args) {
        // START TRIPPLANNER
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        tripPlannerMain.setVisible(true);
    }
}
