package hr.tvz.sportapp.model.person.coach;

import hr.tvz.sportapp.model.person.Person;
import java.io.Serializable;

/**
 * Predstavlja trenera koji nasljeđuje klasu Person i ima mogućnost
 * kreiranja rezervacija termina za treninge u dvoranama.
 *
 * Trener ima specijalizaciju i može držati ograničen broj rezervacija.
 *
 * Konstrukcija objekta koristi Builder obrazac za lakše i sigurnije kreiranje trenera.
 *
 *
 * dok {@link #} ispisuje sve rezervacije trenera.
 */
public class Coach extends Person implements Serializable {
    private static final Integer MAX_BOOKINGS = 5;

    private String specialization;
    public Coach(){
        super();
    }

    /**
     * Privatni konstruktor koji se koristi unutar CoachBuildera.
     *
     * @param builder objekt buildera sa svim potrebnim podacima
     */
    private Coach(CoachBuilder builder) {
        super(builder);
        this.specialization = builder.specialization;
    }

    /**
     * Builder klase za kreiranje instanci trenera.
     */
    public static class CoachBuilder extends Person.PersonBuilder {
        private String specialization = "";

        /**
         * Konstruktor buildera s obaveznim podacima.
         *
         * @param OIB identifikacijski broj trenera
         * @param firstName ime trenera
         * @param lastName prezime trenera
         */
        public CoachBuilder(String OIB, String firstName, String lastName) {

            super(OIB, firstName, lastName);
        }

        /**
         * Postavlja specijalizaciju trenera.
         *
         * @param specialization specijalizacija
         * @return trenutni builder za moguću daljnju konfiguraciju
         */
        public CoachBuilder specialization(String specialization) {
            this.specialization = specialization;
            return this;
        }

        /**
         * Kreira instancu trenera s prethodno postavljenim podacima.
         *
         * @return novi trener
         */
        public Coach build() {
            return new Coach(this);
        }
    }


    public String getSpecialization() {
        return specialization;
    }


    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
