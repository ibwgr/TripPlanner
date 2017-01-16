package controller.travel;

import controller.common.MainController;
import view.travel.CompleteTripView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompleteTripController implements ActionListener {

    private CompleteTripView completeTripView;
    private MainController mainController;

    public CompleteTripController(CompleteTripView completeTripView, MainController mainController) {
        this.completeTripView = completeTripView;
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
