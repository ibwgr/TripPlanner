package controller.travel;

import controller.common.MainController;
import view.travel.BigMapView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompleteTripController implements ActionListener {

    private BigMapView bigMapView;
    private MainController mainController;

    public CompleteTripController(BigMapView bigMapView, MainController mainController) {
        this.bigMapView = bigMapView;
        this.mainController = mainController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "start_new":
                /**
                 * schliesst das Progress Panel und öffnet wieder das Input Panel
                 */
                mainController.openLastView();
                break;
        }

    }
}
