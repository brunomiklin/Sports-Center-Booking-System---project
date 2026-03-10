package hr.tvz.sportapp.model.hall;



import hr.tvz.sportapp.model.booking.Booking;

import java.time.LocalDate;
import java.util.List;

/**
 * Predstavlja sučelje koje označava da se određeni objekt može rezervirati (rasporediti).
 *
 * Implementacija ovog sučelja omogućuje dodavanje i dohvaćanje rezervacija
 * određenog objekta, poput dvorane ili drugog raspoloživog resursa.
 *
 * Sučelje je označeno kao sealed, što znači da ga mogu implementirati
 * samo klase koje su izričito navedene u odredbi {@code permits}.
 *
 * U ovom slučaju, sučelje može implementirati samo klasa {@link Hall}.
 */
public sealed interface Schedulable permits Hall {
    /**
     * Dodaje novu rezervaciju za objekt koji implementira ovo sučelje.
     *
     * @param b objekt rezervacije koji treba dodati
     */
     void addBooking(Booking b);

    /**
     * Dohvaća sve rezervacije za zadani datum.
     *
     * @param date datum za koji se dohvaćaju rezervacije
     */
    List<Booking> getBookingsForDate(LocalDate date);
}
