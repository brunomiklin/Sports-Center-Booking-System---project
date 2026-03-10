package hr.tvz.sportapp.model.hall;


import hr.tvz.sportapp.model.booking.Booking;
import hr.tvz.sportapp.model.person.user.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class HallService {

    public static Hall createHall(String name, String doorNumber, Integer capacity, SportType sportType) throws InvalidHallCapacity
    {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Ime dvorane je obavezno.");
        }
        if (doorNumber == null || doorNumber.isBlank()) {
            throw new IllegalArgumentException("Broj vrata je obavezan.");
        }
        if (capacity == null) {
            throw new InvalidHallCapacity("Kapacitet je obavezan.");
        }
        if (capacity <= 0) {
            throw new InvalidHallCapacity("Vrijednost kapaciteta dvorane ne može biti 0 ili negativna!");
        }
        if (capacity > 500) {
            throw new InvalidHallCapacity("Unijeli ste preveliki kapacitet za dvoranu!");
        }
        if (sportType == null) {
            throw new IllegalArgumentException("Sport mora biti odabran.");
        }

        return new Hall(name, doorNumber, capacity, sportType);
    }
    public static Hall findHallById(String hallId,Set<Hall> halls) {
        return halls.stream()
                .filter(h -> h.getId().equals(hallId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Ne postoji hallId: " + hallId));
    }




}
