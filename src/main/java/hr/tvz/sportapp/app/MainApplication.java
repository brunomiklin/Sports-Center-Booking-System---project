package hr.tvz.sportapp.app;

import hr.tvz.sportapp.controller.SceneManager;
import hr.tvz.sportapp.repository.AppRepository;
import hr.tvz.sportapp.repository.DbRepository;
import hr.tvz.sportapp.repository.JsonRepository;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
        AppRepository repo = new DbRepository();
        SceneManager.init(stage, repo);
        SceneManager.showMain();
        stage.show();
    }
}
