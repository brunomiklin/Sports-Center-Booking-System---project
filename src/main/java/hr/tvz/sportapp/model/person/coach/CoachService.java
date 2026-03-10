package hr.tvz.sportapp.model.person.coach;

import hr.tvz.sportapp.exceptions.InvalidOibException;
import hr.tvz.sportapp.repository.AppRepository;
import hr.tvz.sportapp.repository.RepositoryTaker;

import java.util.Optional;
import java.util.Set;

public class CoachService implements RepositoryTaker {
    private static AppRepository repo;

    public static void ensureUniqueOib(String oib) throws InvalidOibException {
        if (oib == null || oib.isBlank())
            throw new IllegalArgumentException("OIB je obavezan.");

        boolean existsInUsers = repo.getUsers().stream().anyMatch(u -> oib.equals(u.getOIB()));
        boolean existsInCoaches = repo.getCoach().stream().anyMatch(c -> oib.equals(c.getOIB()));

        if (existsInUsers || existsInCoaches) {
            throw new InvalidOibException("Već postoji osoba s OIB-om: " + oib);
        }
    }

    public static void ensureUniqueEmail(Optional<String> email) {

        String normalized = email.get().trim().toLowerCase();

        boolean exists = repo.getCoach().stream()
                .map(Coach::getEmail)
                .anyMatch(u -> u.equals(normalized));

        if (exists) {
            throw new IllegalArgumentException("Email je već zauzet: " + email);
        }
    }
    public static Coach findCoachByOib(String coachOib, Set<Coach> coaches) {
        return coaches.stream()
                .filter(c -> c.getOIB().equals(coachOib))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Ne postoji hallId: " + coachOib));
    }

    @Override
    public void setRepo(AppRepository repo) {
        this.repo = repo;
    }
}
