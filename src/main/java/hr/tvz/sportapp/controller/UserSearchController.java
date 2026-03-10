package hr.tvz.sportapp.controller;

import hr.tvz.sportapp.model.person.Person;
import hr.tvz.sportapp.model.person.coach.Coach;
import hr.tvz.sportapp.model.person.user.User;
import hr.tvz.sportapp.repository.AppRepository;
import hr.tvz.sportapp.repository.RepositoryTaker;
import hr.tvz.sportapp.utility.AlertUtil;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class UserSearchController implements RepositoryTaker {

    private AppRepository repository;

    @FXML private TextField txtFld;

    @FXML private RadioButton userRadio;
    @FXML private RadioButton coachRadio;

    @FXML private TableView<Person> table;
    @FXML private TableColumn<Person, String> oibCol;
    @FXML private TableColumn<Person, String> firstNameCol;
    @FXML private TableColumn<Person, String> lastNameCol;
    @FXML private TableColumn<Person, String> extraCol;
    @FXML private ComboBox<String> choiceComboBox;

    private final ToggleGroup tg = new ToggleGroup();

    @FXML
    private void initialize() {
        userRadio.setToggleGroup(tg);
        coachRadio.setToggleGroup(tg);
        userRadio.setSelected(true);
        oibCol.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getOIB()));
        firstNameCol.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getFirstName()));
        lastNameCol.setCellValueFactory(d -> new ReadOnlyStringWrapper(d.getValue().getLastName()));
        extraCol.setCellValueFactory(d -> {
            Person p = d.getValue();
            if (p instanceof User u) {
                return new ReadOnlyStringWrapper("username: " + u.getUsername());
            }
            if (p instanceof Coach c) {
                return new ReadOnlyStringWrapper("spec: " + c.getSpecialization());
            }
            return new ReadOnlyStringWrapper("");
        });
        choiceComboBox.getItems().add("Ime");
        choiceComboBox.getItems().add("Prezime");
    }

    @FXML
    private void searchClick() {
        if (repository == null) {
            AlertUtil.showError("Repo nije inicijaliziran.");
            return;
        }

        String txtFldString = txtFld.getText() == null ? "" : txtFld.getText().trim().toLowerCase();

        List<? extends Person> result;
        switch (choiceComboBox.getValue())
        {
            case "Ime" -> {
                if (coachRadio.isSelected()) {
                    result = repository.getCoach().stream().filter(coach -> txtFldString.isBlank() || coach.getFirstName().toLowerCase().contains(txtFldString)).toList();
                } else {
                    result = repository.getUsers().stream().filter(coach -> txtFldString.isBlank() || coach.getFirstName().toLowerCase().contains(txtFldString)).toList();
                }
                table.setItems(FXCollections.observableArrayList(result));
            }
            case "Prezime" ->
            {
                if (coachRadio.isSelected()) {
                    result = repository.getCoach().stream().filter(coach ->txtFldString.isBlank() || coach.getLastName().toLowerCase().contains(txtFldString)).toList();
                } else {
                    result = repository.getUsers().stream().filter(coach -> txtFldString.isBlank() || coach.getLastName().toLowerCase().contains(txtFldString)).toList();
                }
                table.setItems(FXCollections.observableArrayList(result));
            }
        }

    }

    @FXML
    private void inputUser(ActionEvent event)
    {
            SceneManager.showInputUsers();
    }

    @Override
    public void setRepo(AppRepository repo) {
        this.repository = repo;
    }
}
