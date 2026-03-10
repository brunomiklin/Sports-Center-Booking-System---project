package hr.tvz.sportapp.repository;

import hr.tvz.sportapp.model.booking.Booking;
import hr.tvz.sportapp.model.hall.Hall;
import hr.tvz.sportapp.exceptions.InvalidOibException;
import hr.tvz.sportapp.model.person.coach.Coach;
import hr.tvz.sportapp.model.person.user.User;

import java.util.List;
import java.util.Set;

public interface AppRepository {

    Set<User> getUsers();
    void addUser(User user) throws InvalidOibException;
    Set<Coach> getCoach();
    void addCoach(Coach coach) throws InvalidOibException;

    Set<Hall> getHalls();
    void addHall(Hall hall);

    List<Booking> getBookings();
    void addBooking(Booking booking);


}
