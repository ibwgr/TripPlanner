package controller.admin;

import controller.common.MainController;
import view.admin.ProgressView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgressController implements ActionListener {


    private ProgressView progressView;
    private MainController mainController;

    public ProgressController(ProgressView progressView, MainController mainController) {
        this.progressView = progressView;
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
