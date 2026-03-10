package hr.tvz.sportapp.repository;
import hr.tvz.sportapp.model.booking.Booking;
import hr.tvz.sportapp.model.booking.UserBookingLink;
import hr.tvz.sportapp.model.hall.Hall;
import hr.tvz.sportapp.model.hall.HallService;
import hr.tvz.sportapp.exceptions.InvalidOibException;
import hr.tvz.sportapp.model.person.coach.Coach;
import hr.tvz.sportapp.model.person.coach.CoachService;
import hr.tvz.sportapp.model.person.user.User;
import hr.tvz.sportapp.model.person.user.UserService;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class JsonRepository implements AppRepository {
    private Set<User> users = new HashSet<>();
    private Set<Coach> coaches = new HashSet<>();
    private Set<Hall> halls = new HashSet<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<UserBookingLink> userBookings = new ArrayList<>();
    private final Jsonb jsonb = JsonbBuilder.create();
    public JsonRepository() {
        load();
        rebuildHallBookings();
    }
    public void load() {
        try {
            if (Files.exists(Paths.get("files/user.json"))) {
                users = jsonb.fromJson(
                        Files.readString(Paths.get("files/user.json")),
                        new HashSet<User>(){}.getClass().getGenericSuperclass()
                );
            }

            if (Files.exists(Paths.get("files/coach.json"))) {
                coaches = jsonb.fromJson(
                        Files.readString(Paths.get("files/coach.json")),
                        new HashSet<Coach>(){}.getClass().getGenericSuperclass()
                );
            }

            if (Files.exists(Paths.get("files/hall.json"))) {
                halls = jsonb.fromJson(
                        Files.readString(Paths.get("files/hall.json")),
                        new HashSet<Hall>(){}.getClass().getGenericSuperclass()
                );
            }
            if (Files.exists(Paths.get("files/bookings.json"))) {
                bookings = jsonb.fromJson(
                        Files.readString(Paths.get("files/bookings.json")),
                        new ArrayList<Booking>(){}.getClass().getGenericSuperclass()
                );
            }
            if (Files.exists(Paths.get("files/user_bookings.json"))) {
                userBookings = jsonb.fromJson(
                        Files.readString(Paths.get("files/user_bookings.json")),
                        new ArrayList<UserBookingLink>(){}.getClass().getGenericSuperclass()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException("Greška kod učitavanja JSON-a", e);
        }
    }
    public void save() {
        try {
            Files.writeString(Paths.get("files/user.json"), jsonb.toJson(users));
            Files.writeString(Paths.get("files/coach.json"), jsonb.toJson(coaches));
            Files.writeString(Paths.get("files/hall.json"), jsonb.toJson(halls));
            Files.writeString(Paths.get("files/bookings.json"), jsonb.toJson(bookings));
            Files.writeString(Paths.get("files/user_bookings.json"), jsonb.toJson(userBookings));
        } catch (IOException e) {
            throw new RuntimeException("Greška kod spremanja JSON-a", e);
        }
    }


    @Override
    public Set<User> getUsers() {
        return new HashSet<>(users);
    }
    @Override
    public void addUser(User user) throws InvalidOibException {
        if (user == null) throw new IllegalArgumentException("User je null.");
        UserService.ensureUniqueOib(user.getOIB());
        UserService.ensureUniqueUsername(user.getUsername());
        users.add(user);
        save();
    }

    @Override
    public Set<Coach> getCoach() {
        return new HashSet<>(coaches);
    }

    @Override
    public void addCoach(Coach coach) throws InvalidOibException {
        if (coach == null) throw new IllegalArgumentException("Coach je null.");
        CoachService.ensureUniqueOib(coach.getOIB());
        CoachService.ensureUniqueEmail(coach.getEmail());
        coaches.add(coach);
        save();
    }

    @Override
    public Set<Hall> getHalls() {
        return new HashSet<>(halls);
    }

    @Override
    public void addHall(Hall hall) {
        if (hall == null) throw new IllegalArgumentException("Hall je null.");
        halls.add(hall);
        save();
    }

    @Override
    public List<Booking> getBookings() {
        return bookings.stream()
                .sorted(Comparator.comparing(Booking::dateTime))
                .toList();
    }

    @Override
    public void addBooking(Booking booking) {
        if (booking == null) throw new IllegalArgumentException("Booking je null.");
        Hall hall = HallService.findHallById(booking.hallId(),halls);
        hall.addBooking(booking);
        bookings.add(booking);
        save();
    }

    public void assignUserToBooking(User user, Booking booking) {
        if (user == null || booking == null)
            throw new IllegalArgumentException("User ili booking je null.");
        boolean exists = userBookings.stream().anyMatch(
                ub -> ub.userOib().equals(user.getOIB())
                        && ub.bookingId().equals(booking.id())
        );
        if (exists)
            throw new IllegalStateException("Korisnik je već prijavljen na termin.");
        userBookings.add(new UserBookingLink(user.getOIB(), booking.id()));
        save();
    }

    public List<Booking> getBookingsForUser(User user) {
        Set<String> ids = userBookings.stream()
                .filter(ub -> ub.userOib().equals(user.getOIB()))
                .map(UserBookingLink::bookingId)
                .collect(Collectors.toSet());

        return bookings.stream()
                .filter(b -> ids.contains(b.id()))
                .sorted(Comparator.comparing(Booking::dateTime))
                .toList();
    }

    private void rebuildHallBookings() {
        for (Hall h : halls) h.clearBookings();
        for (Booking b : bookings) HallService.findHallById(b.hallId(),halls).addBookingUnsafe(b);
    }
}
