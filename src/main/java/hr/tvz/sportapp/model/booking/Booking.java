package hr.tvz.sportapp.model.booking;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record Booking(String id,String coachOib, String hallId, LocalDateTime dateTime, Integer trainingTime) implements Serializable {

    public Booking(String coachOib, String hallId, LocalDateTime dateTime, Integer trainingTime) {
        this(UUID.randomUUID().toString(), coachOib, hallId, dateTime, trainingTime);
    }


    public Booking {
        if (id==null) throw new IllegalArgumentException("Booking ID je obavezan.");
        if (coachOib == null) throw new IllegalArgumentException("Trener je obavezan.");
        if (hallId == null) throw new IllegalArgumentException("Dvorana je obavezna.");
        if (dateTime == null) throw new IllegalArgumentException("Datum i vrijeme su obavezni.");
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Datum ne smije biti u prošlosti.");
        }
        if (trainingTime == null) throw new IllegalArgumentException("Trajanje treninga je obavezno.");
        if (trainingTime <= 0) throw new IllegalArgumentException("Trajanje mora biti veće od 0 minuta.");

    }

}
