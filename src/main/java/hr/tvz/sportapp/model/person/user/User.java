package hr.tvz.sportapp.model.person.user;

import hr.tvz.sportapp.model.booking.Booking;
import hr.tvz.sportapp.model.person.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Predstavlja korisnika sustava koji nasljeđuje osobu (Person).
 * Koristi Builder obrazac za sigurno i čitljivo kreiranje objekta.
 */
public class User extends Person implements Serializable {

    private static final Integer MAX_BOOKINGS = 5;
    private final List<Booking> myBookings = new ArrayList<>();
    private String username;
    private String password;

    /**
     * Prazan konstruktor (koristan za serializaciju/deserializaciju).
     */
    public User() {
        super();
        this.username = "";
        this.password = "";
    }

    /**
     * Privatni konstruktor koji se koristi unutar UserBuildera.
     *
     * @param builder objekt buildera sa svim potrebnim podacima
     */
    private User(UserBuilder builder) {
        super(builder);
        this.username = builder.username == null ? "" : builder.username;
        this.password = builder.password == null ? "" : builder.password;
    }

    /**
     * Builder klase za kreiranje instanci korisnika.
     */
    public static class UserBuilder extends Person.PersonBuilder {

        private String username = "";
        private String password = "";

        /**
         * Konstruktor buildera s obaveznim podacima.
         *
         * @param OIB identifikacijski broj korisnika
         * @param firstName ime korisnika
         * @param lastName prezime korisnika
         */
        public UserBuilder(String OIB, String firstName, String lastName) {
            super(OIB, firstName, lastName);
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        @Override
        public User build() {
            return new User(this);
        }
    }


    public String getUsername() {
        return username;
    }


    public List<Booking> getMyBookings() {
        return Collections.unmodifiableList(myBookings);
    }


    public void joinBooking(Booking booking) {
        if (booking == null)
            throw new IllegalArgumentException("Booking nije odabran.");

        if (myBookings.contains(booking))
            throw new IllegalStateException("Korisnik je već pridružen ovom terminu.");

        if (myBookings.size() >= MAX_BOOKINGS)
            throw new IllegalStateException(
                    "Korisnik ne može imati više od " + MAX_BOOKINGS + " termina."
            );

        myBookings.add(booking);
    }




}
