package hr.tvz.sportapp.model.person.user;

import hr.tvz.sportapp.exceptions.InvalidOibException;
import hr.tvz.sportapp.repository.AppRepository;
import hr.tvz.sportapp.repository.RepositoryTaker;

public class UserService implements RepositoryTaker {
    private static AppRepository repo;

    public static void ensureUniqueOib(String oib) {
        if (oib == null || oib.isBlank())
            throw new IllegalArgumentException("OIB je obavezan.");

        boolean existsInUsers = repo.getUsers().stream().anyMatch(u -> oib.equals(u.getOIB()));
        boolean existsInCoaches = repo.getCoach().stream().anyMatch(c -> oib.equals(c.getOIB()));

        if (existsInUsers || existsInCoaches) {
            throw new IllegalArgumentException("Već postoji osoba s OIB-om: " + oib);
        }
    }

    public static void ensureUniqueUsername(String username) throws InvalidOibException {
        // ako dopuštaš prazan username, onda samo return
        if (username == null || username.isBlank()) return;

        String normalized = username.trim().toLowerCase();

        boolean exists = repo.getUsers().stream()
                .map(User::getUsername)
                .filter(u -> u != null && !u.isBlank())
                .map(u -> u.trim().toLowerCase())
                .anyMatch(u -> u.equals(normalized));

        if (exists) {
            throw new InvalidOibException("Username je već zauzet: " + username);
        }
    }

    @Override
    public void setRepo(AppRepository repo) {
        this.repo = repo;
    }
}
