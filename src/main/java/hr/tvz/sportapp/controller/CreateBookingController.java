package hr.tvz.sportapp.controller;

import hr.tvz.sportapp.model.booking.Booking;
import hr.tvz.sportapp.model.hall.Hall;
import hr.tvz.sportapp.model.person.coach.Coach;
import hr.tvz.sportapp.repository.AppRepository;
import hr.tvz.sportapp.repository.RepositoryTaker;
import hr.tvz.sportapp.utility.AlertUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class CreateBookingController implements RepositoryTaker, Initializable {
    private AppRepository repo;
    @FXML
    private ComboBox<Coach> coachNameCombo;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeFld;

    @FXML
    private Spinner<Integer> durationSpinner;

    @FXML
    private ComboBox<Hall> hallNameCombo;


    @FXML
    public void takeMeHome()
    {
        SceneManager.showMain();
    }

    @FXML
    public void onCreate()
    {
        try
        {
            String coachOib = coachNameCombo.getValue().getOIB();
            String hallId = hallNameCombo.getValue().getId();
            LocalDate date = datePicker.getValue();
            String timeText = timeFld.getText();
            LocalTime time;
            LocalDateTime dateTime = null;
            try {
                time = LocalTime.parse(timeText);
                 dateTime = LocalDateTime.of(date,time);
            }catch (DateTimeParseException dtpe)
            {
                AlertUtil.showInfo("Vrijeme mora biti u formatu HH:mm.");
            }
            Integer trainingTime = durationSpinner.getValue();
            Booking booking = new Booking(coachOib,hallId,dateTime,trainingTime);
            repo.addBooking(booking);
            AlertUtil.showInfo("Rezeravcija uspješno kreirana!");
        }catch (IllegalArgumentException iae)
        {
            AlertUtil.showInfo(iae.getMessage());
        }catch (NullPointerException npe)
        {
            AlertUtil.showInfo("Sva polja su obavezna!");
        }
        catch (IllegalStateException ise)
        {
            AlertUtil.showInfo(ise.getMessage());
        }


    }

    @Override
    public void setRepo(AppRepository repo) {
        this.repo = repo;
        hallNameCombo.setItems(FXCollections.observableArrayList(repo.getHalls()));
        coachNameCombo.setItems(FXCollections.observableArrayList(repo.getCoach()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        durationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(20,180));

    }
}
