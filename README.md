# 🏟️ Sustav za upravljanje sportskim objektima

JavaFX desktop aplikacija za upravljanje sportskim dvoranama, rezervacijama termina i korisnicima/trenerima. Podatci se trajno čuvaju u JSON datotekama.

---

## 📋 Sadržaj

- [Opis projekta](#opis-projekta)
- [Tehnologije](#tehnologije)
- [Struktura projekta](#struktura-projekta)
- [Domenski model](#domenski-model)
- [Pokretanje aplikacije](#pokretanje-aplikacije)
- [Funkcionalnosti](#funkcionalnosti)
- [Pohrana podataka](#pohrana-podataka)

---

## Opis projekta

Aplikacija omogućuje upravljanje sportskim dvoranama i terminima treninga putem grafičkog sučelja (JavaFX). Korisnici se mogu pridruživati terminima, treneri mogu kreirati rezervacije, a administrator može dodavati dvorane i korisnike.

---

## Tehnologije

| Tehnologija | Verzija |
|---|---|
| Java | 25 |
| JavaFX | 21.0.6 |
| Jakarta JSON Binding (Yasson) | 3.0.3 |
| Jakarta XML Binding (JAXB) | 4.0.0 |
| Logback | 1.5.20 |
| JUnit Jupiter | 5.12.1 |
| Maven | 3.x (Maven Wrapper) |

---

## Struktura projekta

```
src/main/java/hr/tvz/sportapp/
├── app/
│   ├── MainApplication.java       # JavaFX Application entry point
│   └── Launcher.java              # Launcher klasa
├── controller/
│   ├── MainController.java        # Glavni ekran
│   ├── InputHallController.java   # Unos dvorane
│   ├── InputUsersController.java  # Unos korisnika/trenera
│   ├── CreateBookingController.java # Kreiranje rezervacije
│   ├── JoinBookingController.java # Pridruživanje terminu
│   ├── UserSearchController.java  # Pretraga korisnika
│   └── SceneManager.java          # Upravljanje scenama
├── model/
│   ├── hall/
│   │   ├── Hall.java              # Model dvorane
│   │   ├── SportType.java         # Enum tipova sporta
│   │   ├── HallService.java       # Servisna logika za dvorane
│   │   ├── Reservable.java        # Sučelje za rezervaciju
│   │   └── Schedulable.java       # Sučelje za raspoređivanje
│   ├── person/
│   │   ├── Person.java            # Apstraktna bazna klasa
│   │   ├── user/
│   │   │   ├── User.java          # Model korisnika
│   │   │   └── UserService.java   # Servisna logika za korisnike
│   │   └── coach/
│   │       ├── Coach.java         # Model trenera
│   │       └── CoachService.java  # Servisna logika za trenere
│   └── booking/
│       ├── Booking.java           # Java record za rezervaciju
│       └── UserBookingLink.java   # Veza korisnik-rezervacija
├── repository/
│   ├── AppRepository.java         # Sučelje repozitorija
│   ├── JsonRepository.java        # JSON implementacija
│   └── RepositoryTaker.java       # Sučelje za injekciju repozitorija
└── utility/
    └── AlertUtil.java             # Pomoćne metode za JavaFX alertove

files/
├── hall.json            # Podatci o dvoranama
├── bookings.json        # Rezervacije
├── user.json            # Korisnici
├── coach.json           # Treneri
└── user_bookings.json   # Veze korisnik-rezervacija
```

---

## Domenski model

### Dvorana (`Hall`)
- Naziv, broj vrata, kapacitet, podržani sport
- Maksimalno **5 aktivnih rezervacija**
- Implementira `Reservable` i `Schedulable` — provjera dostupnosti i prikaz termina po datumu
- ID dvorane: `naziv#brojVrata`

### Tipovi sporta (`SportType`)
Podržani sportovi: `NOGOMET`, `RUKOMET`, `TENNIS`, `KOSARKA`, `PLIVANJE`, `ODBOJKA`, `JOGA`, `WELNES`

### Osoba (`Person`)
Apstraktna klasa s Builder obrascem. Polja: OIB, ime, prezime, e-mail, broj telefona.

### Korisnik (`User`)
- Nasljeđuje `Person`, ima korisničko ime i lozinku
- Može se pridružiti maksimalno **5 termina**

### Trener (`Coach`)
- Nasljeđuje `Person`
- Kreira i vodi rezervacije u dvoranama

### Rezervacija (`Booking`)
- Java `record` s poljima: ID (UUID), OIB trenera, ID dvorane, datum/vrijeme, trajanje u minutama
- Datum mora biti u budućnosti
- Trajanje mora biti > 0 minuta

---

## Pokretanje aplikacije

### Preduvjeti

- Java 25+
- Maven (ili koristite priloženi Maven Wrapper)

### Pokretanje putem Maven Wrappera

```bash
# Linux / macOS
./mvnw clean javafx:run

# Windows
mvnw.cmd clean javafx:run
```

### Pokretanje putem Mavena

```bash
mvn clean javafx:run
```

> **Napomena:** Aplikacija čita i zapisuje JSON datoteke u mapi `files/` relativno od direktorija iz kojeg se pokreće. Osigurajte da mapa `files/` postoji u radnom direktoriju.

---

## Funkcionalnosti

| Funkcionalnost | Opis |
|---|---|
| **Dodavanje dvorane** | Unos naziva, broja vrata, kapaciteta i tipa sporta |
| **Dodavanje korisnika** | Registracija korisnika s OIB-om, korisničkim imenom i lozinkom |
| **Dodavanje trenera** | Registracija trenera s OIB-om i e-mailom |
| **Kreiranje rezervacije** | Trener kreira termin u odabranoj dvorani u željenom terminu |
| **Pridruživanje terminu** | Korisnik se prijavljuje na postojeći termin |
| **Pretraga korisnika** | Pregled i pretraga registriranih korisnika |
| **Provjera dostupnosti** | Automatska provjera preklapanja termina pri rezervaciji |

---

## Pohrana podataka

Aplikacija koristi JSON repozitorij (`JsonRepository`) koji podatke čuva lokalno:

```
files/
├── user.json            # Registrirani korisnici
├── coach.json           # Registrirani treneri
├── hall.json            # Dvorane
├── bookings.json        # Svi termini
└── user_bookings.json   # Koja prijava pripada kojem korisniku
```

Repozitorij implementira sučelje `AppRepository`, što omogućuje buduću zamjenu JSON pohrane bazom podataka bez promjene ostatka aplikacije.

---

## Arhitekturne napomene

- **Builder obrazac** — korišten za kreiranje `Person`, `User` i `Coach` objekata
- **Repository obrazac** — `AppRepository` sučelje apstrahira sloj pohrane
- **MVC** — JavaFX controlleri odvojeni od domenskog modela
- **Sučelja** `Reservable` i `Schedulable` — definiraju ponašanje dvorane neovisno o implementaciji
- **Validacija** — OIB jedinstvenost, preklapanje termina, maksimalni broj rezervacija — sve provjeravano u servisnim klasama
