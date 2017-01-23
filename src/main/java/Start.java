import view.common.TripPlannerMain;

import javax.swing.*;
import javax.swing.UIManager.*;

public class Start {
    public static void main(String[] args) {

        // Setzen Look and Feel, je nach Betriebssystem, wenn vorhanden, sonst DEFAULT
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, set the GUI to the default look and feel.
            // Set cross-platform Java Look and Feel (also called "Metal")
            try {
                UIManager.setLookAndFeel(
                        UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (UnsupportedLookAndFeelException e1) {
                e1.printStackTrace();
            }
        }

        // START TRIPPLANNER
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        tripPlannerMain.setVisible(true);


    }
}
