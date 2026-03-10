package hr.tvz.sportapp.controller;

import hr.tvz.sportapp.model.booking.Booking;
import hr.tvz.sportapp.model.hall.HallService;
import hr.tvz.sportapp.model.person.coach.CoachService;
import hr.tvz.sportapp.model.person.user.User;
import hr.tvz.sportapp.repository.AppRepository;
import hr.tvz.sportapp.repository.RepositoryTaker;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class JoinBookingController implements Initializable, RepositoryTaker {

    private AppRepository repo;
    @FXML
    private TableColumn<Booking,String> bCoachCol;

    @FXML
    private TableColumn<Booking,String> bDateTimeCol;

    @FXML
    private TableColumn<Booking, String> bDurationCol;

    @FXML
    private TableColumn<Booking, String> bHallCol;

    @FXML
    private TableView<Booking> bookingsTable;

    @FXML
    private DatePicker dateFilter;

    @FXML
    private CheckBox futureOnlyCheck;



    @FXML
    private TableColumn<User, String> uFirstNameCol;

    @FXML
    private TableColumn<User, String> uLastNameCol;

    @FXML
    private TableColumn<User, String> uOibCol;

    @FXML
    private ListView<User> userBookingsList;

    @FXML
    private TextField userSearchField;

    @FXML
    private TableView<User> usersTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void setRepo(AppRepository repo) {
        this.repo = repo;
        setUserToCol();
        setBookingToCol();
    }


    private void setUserToCol()
    {
        usersTable.setItems(FXCollections.observableArrayList(repo.getUsers()));
        uOibCol.setCellValueFactory(new Callback<>() {
                                        public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {return new ReadOnlyStringWrapper(param.getValue().getOIB());
                                        }
                                    }
        );
        uFirstNameCol.setCellValueFactory(new Callback<>() {
                                              public ObservableValue<String> call(
                                                      TableColumn.CellDataFeatures<User, String> param) {return new ReadOnlyStringWrapper(param.getValue().getFirstName());
                                              }
                                          }
        );
        uLastNameCol.setCellValueFactory(new Callback<>() {
                                             public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> param) {
                                                 return new ReadOnlyStringWrapper(param.getValue().getLastName());
                                             }}
        );
    }
    private void setBookingToCol()
    {
        bookingsTable.setItems(FXCollections.observableArrayList(repo.getBookings()));
        bHallCol.setCellValueFactory(new Callback<>(){
            public ObservableValue call (TableColumn.CellDataFeatures<Booking,String> param){
                return new ReadOnlyStringWrapper(
                        HallService.findHallById(param.getValue().hallId(),repo.getHalls()).getName()
                );
            }
        });
        bCoachCol.setCellValueFactory(new Callback<>(){
            public ObservableValue call (TableColumn.CellDataFeatures<Booking,String> param){
                return new ReadOnlyStringWrapper(
                        CoachService.findCoachByOib(param.getValue().hallId(),repo.getCoach()).getFirstName() +
                                CoachService.findCoachByOib(param.getValue().hallId(),repo.getCoach()).getLastName()
                );
            }
        });
        bDateTimeCol.setCellValueFactory(new Callback<>(){
            public ObservableValue call (TableColumn.CellDataFeatures<Booking,String> param){
                return new ReadOnlyStringWrapper(
                        param.getValue().dateTime().toString()
                );
            }
        });
        bDurationCol.setCellValueFactory(new Callback<>(){
            public ObservableValue call (TableColumn.CellDataFeatures<Booking,String> param){
                return new ReadOnlyStringWrapper(
                        param.getValue().trainingTime().toString()
                );
            }
        });
    }
}
