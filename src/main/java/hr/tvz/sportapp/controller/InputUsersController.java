package hr.tvz.sportapp.controller;

import hr.tvz.sportapp.model.person.coach.Coach;
import hr.tvz.sportapp.model.person.user.User;
import hr.tvz.sportapp.repository.AppRepository;
import hr.tvz.sportapp.repository.RepositoryTaker;
import hr.tvz.sportapp.utility.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
public class InputUsersController implements RepositoryTaker {

    private AppRepository repo;

    @FXML private TextField oibField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;

    @FXML private RadioButton userRadio;
    @FXML private RadioButton coachRadio;

    @FXML private Label emailLabel;
    @FXML private TextField emailField;
    @FXML private Label phoneLabel;
    @FXML private TextField phoneField;
    @FXML private Label specializationLabel;
    @FXML private TextField specializationField;

    @FXML private Label usernameLabel;
    @FXML private TextField usernameField;
    @FXML private Label passwordLabel;
    @FXML private TextField passwordField;


    @Override
    public void setRepo(AppRepository repo) {
        this.repo = repo;
    }

    @FXML
    public void initialize() {
        userRadio.setOnAction(e -> showUserFields());
        coachRadio.setOnAction(e -> showCoachFields());
        userRadio.setSelected(true);
        showUserFields();
    }

    @FXML
    public void searchUser(ActionEvent event)
    {
       SceneManager.showUserSearch();
    }
    @FXML
    public void onSave() {

        String oib = safe(oibField);
        String firstName = safe(firstNameField);
        String lastName = safe(lastNameField);



        if (coachRadio.isSelected()) {
            String email = safe(emailField);
            String phone = safe(phoneField);
            String spec = safe(specializationField);

            if (!email.isBlank() && !email.contains("@")) {
                AlertUtil.showError("Email mora sadržavati @");
                return;
            }

            Coach coach = (Coach) new Coach.CoachBuilder(oib, firstName, lastName)
                    .specialization(spec)
                    .email(email)
                    .phoneNumber(phone)
                    .build();

            try { repo.addCoach(coach);
                AlertUtil.showInfo("Trener spremljen.");}
            catch (Exception e) { AlertUtil.showError(e.getMessage()); }


        } else {
            String username = safe(usernameField);
            String password = safe(passwordField);

            User user = new User.UserBuilder(oib, firstName, lastName)
                    .username(username)
                    .password(password)
                    .build();

            try { repo.addUser(user);
                AlertUtil.showInfo("Korisnik spremljen.");
            }
            catch (Exception e) { AlertUtil.showError(e.getMessage()); }

        }

        clearForm();
    }

    private void showUserFields() {
        setVisibleManaged(usernameLabel, true);
        setVisibleManaged(usernameField, true);
        setVisibleManaged(passwordLabel, true);
        setVisibleManaged(passwordField, true);

        setVisibleManaged(emailLabel, false);
        setVisibleManaged(emailField, false);
        setVisibleManaged(phoneLabel, false);
        setVisibleManaged(phoneField, false);
        setVisibleManaged(specializationLabel, false);
        setVisibleManaged(specializationField, false);
    }

    private void showCoachFields() {
        setVisibleManaged(emailLabel, true);
        setVisibleManaged(emailField, true);
        setVisibleManaged(phoneLabel, true);
        setVisibleManaged(phoneField, true);
        setVisibleManaged(specializationLabel, true);
        setVisibleManaged(specializationField, true);

        setVisibleManaged(usernameLabel, false);
        setVisibleManaged(usernameField, false);
        setVisibleManaged(passwordLabel, false);
        setVisibleManaged(passwordField, false);
    }

    private void setVisibleManaged(Node node, boolean value) {
        node.setVisible(value);
        node.setManaged(value);
    }

    private String safe(TextField tf) {
        return tf.getText() == null ? "" : tf.getText().trim();
    }

    private void clearForm() {
        oibField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
        specializationField.clear();
        usernameField.clear();
        passwordField.clear();
    }

    public void takeMeHome()
    {
        SceneManager.showMain();
    }

}
