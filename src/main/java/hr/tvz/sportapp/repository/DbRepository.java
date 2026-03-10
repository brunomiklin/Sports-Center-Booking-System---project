package hr.tvz.sportapp.repository;

import hr.tvz.sportapp.exceptions.DataBaseException;
import hr.tvz.sportapp.database.UserDbRepository;
import hr.tvz.sportapp.model.booking.Booking;
import hr.tvz.sportapp.model.hall.Hall;
import hr.tvz.sportapp.exceptions.InvalidOibException;
import hr.tvz.sportapp.model.person.coach.Coach;
import hr.tvz.sportapp.model.person.user.User;
import hr.tvz.sportapp.utility.AlertUtil;

import java.util.List;
import java.util.Set;

public class DbRepository implements AppRepository{

    @Override
    public Set<User> getUsers() {
        try {
           return UserDbRepository.getInstance().getAll();
        }catch (DataBaseException e)
        {
          throw new RuntimeException(e);
        }

    }

    @Override
    public void addUser(User user) throws InvalidOibException {
        try {
            UserDbRepository.getInstance().insertIntoDb(user);
        }catch (DataBaseException e)
        {
            e.printStackTrace();
            AlertUtil.showError(e.getMessage());
        }
    }

    @Override
    public Set<Coach> getCoach() {
        return Set.of();
    }

    @Override
    public void addCoach(Coach coach) throws InvalidOibException {

    }

    @Override
    public Set<Hall> getHalls() {
        return Set.of();
    }

    @Override
    public void addHall(Hall hall) {

    }

    @Override
    public List<Booking> getBookings() {
        return List.of();
    }

    @Override
    public void addBooking(Booking booking) {

    }
}
