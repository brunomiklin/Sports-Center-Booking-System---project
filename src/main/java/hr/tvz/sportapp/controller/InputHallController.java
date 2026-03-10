package hr.tvz.sportapp.controller;

import hr.tvz.sportapp.model.hall.Hall;
import hr.tvz.sportapp.model.hall.HallService;
import hr.tvz.sportapp.model.hall.InvalidHallCapacity;
import hr.tvz.sportapp.model.hall.SportType;
import hr.tvz.sportapp.repository.AppRepository;
import hr.tvz.sportapp.repository.RepositoryTaker;
import hr.tvz.sportapp.utility.AlertUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class InputHallController implements Initializable,RepositoryTaker {

    private AppRepository repo;

    @FXML
    private Spinner<Integer> capacitySpinner;

    @FXML
    private TextField doorNumTxtFld;

    @FXML
    private TextField nameTxtFld;

    @FXML
    private ComboBox<SportType> sportComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<SportType> listaSportova = new ArrayList<>(Arrays.stream(SportType.values()).toList());
        sportComboBox.setItems(FXCollections.observableArrayList(listaSportova));
        capacitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,50) {

        });
    }

    @FXML
    public void onSave()
    {
        try {
            String name = nameTxtFld.getText();
            String doorNum = doorNumTxtFld.getText();
            Integer capacity = capacitySpinner.getValue();
            SportType sport = sportComboBox.getValue();
            Hall hall = HallService.createHall(name,doorNum,capacity,sport);
            repo.addHall(hall);
            AlertUtil.showInfo("Dvorana je uspješno spreljena");
            nameTxtFld.clear();
            doorNumTxtFld.clear();
        }catch (IllegalArgumentException iae)
        {
            AlertUtil.showError(iae.getMessage());
        }catch (InvalidHallCapacity ihc)
        {
            AlertUtil.showError(ihc.getMessage());
        }



    }
    @FXML
    public void takeMeHome()
    {
        SceneManager.showMain();
    }
    @Override
    public void setRepo(AppRepository repo) {
        this.repo = repo;
    }
}
