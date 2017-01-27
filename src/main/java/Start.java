import view.common.TripPlannerMain;

/**
 * Mit dieser Klasse wird die TripPlanner Applikation gestartet.
 *
 * @author  Reto Kaufmann
 */
public class Start {
    public static void main(String[] args) {
        // START TRIPPLANNER
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        tripPlannerMain.setVisible(true);
    }
}
