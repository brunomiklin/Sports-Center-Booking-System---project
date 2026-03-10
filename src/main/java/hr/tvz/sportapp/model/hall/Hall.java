package hr.tvz.sportapp.model.hall;


import hr.tvz.sportapp.model.booking.Booking;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Predsatvlja objekt sportke dvorane koja se može rezervirati i zakazati.
 *
 * Klasa {@code Hall} implementira sučelja {@link Reservable} i {@link Schedulable}.
 * Svaka dvorana ima ogranićeni broj mogućih bookinga, kapacitet i naziv, broj vrata
 * i podrižani sport.
 * 
 */
non-sealed public class Hall implements Reservable, Schedulable, Serializable {

    private static final int MAX_BOOKINGS = 5;

    private String name,doorNumber;
    private Integer capacity;
    private SportType supportedSport;
    private final List<Booking> bookings = new ArrayList<>();

    public Hall(){}

    public Hall(String name, String doorNumber, Integer capacity, SportType supportedSport) {
        this.name = name;
        this.doorNumber = doorNumber;
        this.capacity = capacity;
        this.supportedSport = supportedSport;
    }

    @Override
    public boolean isAvailable(LocalDateTime time, Integer durationMinutes) {
        if (time == null || durationMinutes == null || durationMinutes <= 0) return false;

        LocalDateTime newStart = time;
        LocalDateTime newEnd = time.plusMinutes(durationMinutes);

        return bookings.stream().noneMatch(b -> {
            LocalDateTime existingStart = b.dateTime();
            LocalDateTime existingEnd = existingStart.plusMinutes(b.trainingTime());

            // overlap if newStart < existingEnd AND newEnd > existingStart
            return newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);
        });
    }

    @Override
    public List<Booking> getBookingsForDate(LocalDate date) {
        return bookings.stream()
                .filter(b -> b.dateTime().toLocalDate().equals(date))
                .sorted(Comparator.comparing(Booking::dateTime))
                .toList();
    }

    @Override
    public void addBooking(Booking newBooking)
   {
       if(newBooking==null) throw new IllegalArgumentException("Bookin je prazan");

       if(bookings.size()>=MAX_BOOKINGS){
           throw new IllegalArgumentException("Korisnik ne može sadržavati više booking-a");
       }

       if(!isAvailable(newBooking.dateTime(),newBooking.trainingTime()))
       {
           throw new IllegalStateException("Termin nije dostupan za dodavanje!");
       }

       bookings.add(newBooking);
   }
    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    public String getName() { return name; }
    public Integer getCapacity() { return capacity; }
    public SportType getSport() { return supportedSport; }

    public String getId() {
        return name + "#" + doorNumber;
    }

    public String getDoorNumber() {
        return doorNumber;
    }
    
    public void clearBookings() { bookings.clear(); }
    public void addBookingUnsafe(Booking b) { bookings.add(b); }

    @Override
    public String toString() {
        return name + " | " + capacity + " mjesta | " + supportedSport;
    }
}
