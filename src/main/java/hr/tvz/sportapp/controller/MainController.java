package hr.tvz.sportapp.controller;

import hr.tvz.sportapp.repository.AppRepository;
import hr.tvz.sportapp.repository.JsonRepository;
import hr.tvz.sportapp.repository.RepositoryTaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements RepositoryTaker, Initializable {

    private AppRepository repo;
    @FXML
    private Label repoLabel;

    @FXML
    private void showHallInput(ActionEvent event) {
        SceneManager.showInputHall();
    }
    @FXML
   private void showUserInput(ActionEvent event) {
        SceneManager.showInputUsers();
    }

    @FXML
    private void showCreateBooking(ActionEvent event)
    {
        SceneManager.showCreateBooking();
    }

    @FXML
    private void showJoinBooking(ActionEvent event)
    {
        SceneManager.showJoinBooking();
    }


    @Override
    public void setRepo(AppRepository repo) {
        this.repo = repo;
        if(repo instanceof JsonRepository)
        {
            repoLabel.setText("Storage: JSON");
        }
        else
        {
            repoLabel.setText("Storage: DataBase");
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
