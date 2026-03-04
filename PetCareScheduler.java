import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class PetCareScheduler {

    private static final String PETS_FILE = "pets.csv";
    private static final String APPOINTMENTS_FILE = "appointments.csv";

    // pets keyed by petId (prevents duplication easily)
    private static final Map<String, Pet> pets = new HashMap<>();

    private static final Scanner sc = new Scanner(System.in);

    // Allowed appointment types (validation)
    private static final Set<String> ALLOWED_TYPES = new HashSet<>(
            Arrays.asList("vet visit", "vaccination", "grooming")
    );

    // Date formatters
    private static final DateTimeFormatter REG_DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter APPT_DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        // Load existing data from files at startup (private method requirement)
        loadDataFromFiles();

        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    registerPet();
                    break;
                case 2:
                    scheduleAppointment();
                    break;
                case 3:
                    displayRecordsMenu();
                    break;
                case 4:
                    generateReports();
                    break;
                case 5:
                    saveDataToFiles();
                    break;
                case 0:
                    // Save before exit (nice touch)
                    saveDataToFiles();
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== PetCare Scheduler ===");
        System.out.println("1) Register a pet");
        System.out.println("2) Schedule an appointment");
        System.out.println("3) Display records");
        System.out.println("4) Generate reports");
        System.out.println("5) Save data");
        System.out.println("0) Exit");
    }

    // -----------------------------
    // Task 3.1: Register Pet
    // -----------------------------
    private static void registerPet() {
        System.out.println("\n-- Register a New Pet --");

        String petId;
        while (true) {
            petId = readNonEmpty("Enter unique Pet ID: ");
            if (pets.containsKey(petId)) {
                System.out.println("Error: Pet ID already exists. Please enter a different ID.");
            } else {
                break;
            }
        }

        String name = readNonEmpty("Enter pet name: ");
        String speciesBreed = readNonEmpty("Enter species/breed: ");

        int age;
        while (true) {
            age = readInt("Enter age (integer >= 0): ");
            if (age < 0) {
                System.out.println("Error: Age cannot be negative.");
            } else {
                break;
            }
        }

        String ownerName = readNonEmpty("Enter owner name: ");
        String contactInfo = readNonEmpty("Enter contact info (phone/email): ");

        LocalDate regDate;
        while (true) {
            String regStr = readNonEmpty("Enter registration date (yyyy-MM-dd): ");
            try {
                regDate = LocalDate.parse(regStr, REG_DATE_FMT);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format. Example: 2026-03-04");
            }
        }

        Pet p = new Pet(petId, name, speciesBreed, age, ownerName, contactInfo, regDate);
        pets.put(petId, p);

        System.out.println("Pet registered successfully!");
        System.out.println(p);
    }

    // -----------------------------
    // Task 3.2: Schedule Appointment
    // Validations:
    // - Pet exists
    // - Type valid
    // - DateTime is future
    // -----------------------------
    private static void scheduleAppointment() {
        System.out.println("\n-- Schedule an Appointment --");

        String petId = readNonEmpty("Enter Pet ID: ");
        Pet p = pets.get(petId);
        if (p == null) {
            System.out.println("Error: Pet ID not found. Please register the pet first.");
            return;
        }

        String type;
        while (true) {
            type = readNonEmpty("Enter appointment type (vet visit / vaccination / grooming): ").toLowerCase();
            if (!ALLOWED_TYPES.contains(type)) {
                System.out.println("Error: Invalid type. Allowed: " + ALLOWED_TYPES);
            } else {
                break;
            }
        }

        LocalDateTime dt;
        while (true) {
            String dtStr = readNonEmpty("Enter appointment date & time (yyyy-MM-dd HH:mm): ");
            try {
                dt = LocalDateTime.parse(dtStr, APPT_DT_FMT);
                if (!dt.isAfter(LocalDateTime.now())) {
                    System.out.println("Error: Appointment must be in the future.");
                    continue;
                }
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date/time format. Example: 2026-03-10 14:30");
            }
        }

        System.out.print("Enter notes (optional, press Enter to skip): ");
        String notes = sc.nextLine();

        Appointment a = new Appointment(type, dt, notes);
        p.addAppointment(a);

        System.out.println("Appointment scheduled successfully!");
        System.out.println(a);
    }

    // -----------------------------
    // Task 3.4: Display Records
    // -----------------------------
    private static void displayRecordsMenu() {
        System.out.println("\n-- Display Records --");
        System.out.println("1) All registered pets");
        System.out.println("2) All appointments for a specific pet");
        System.out.println("3) Upcoming appointments for all pets");
        System.out.println("4) Past appointment history for each pet");
        int ch = readInt("Choose an option: ");

        switch (ch) {
            case 1:
                displayAllPets();
                break;
            case 2:
                displayAppointmentsForPet();
                break;
            case 3:
                displayUpcomingAppointmentsAllPets();
                break;
            case 4:
                displayPastHistoryEachPet();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void displayAllPets() {
        System.out.println("\n== All Registered Pets ==");
        if (pets.isEmpty()) {
            System.out.println("No pets registered yet.");
            return;
        }
        for (Pet p : pets.values()) {
            System.out.println(p);
        }
    }

    private static void displayAppointmentsForPet() {
        String petId = readNonEmpty("Enter Pet ID: ");
        Pet p = pets.get(petId);
        if (p == null) {
            System.out.println("Error: Pet ID not found.");
            return;
        }
        System.out.println("\n== Appointments for Pet " + petId + " ==");
        if (p.getAppointments().isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        for (Appointment a : sortByDateTime(p.getAppointments())) {
            System.out.println(a);
        }
    }

    private static void displayUpcomingAppointmentsAllPets() {
        System.out.println("\n== Upcoming Appointments (All Pets) ==");
        boolean any = false;
        LocalDateTime now = LocalDateTime.now();

        for (Pet p : pets.values()) {
            for (Appointment a : p.getAppointments()) {
                if (a.getDateTime().isAfter(now)) {
                    any = true;
                    System.out.println("PetID=" + p.getPetId() + " | " + p.getName() + " | " + a);
                }
            }
        }

        if (!any) System.out.println("No upcoming appointments found.");
    }

    private static void displayPastHistoryEachPet() {
        System.out.println("\n== Past Appointment History (Each Pet) ==");
        LocalDateTime now = LocalDateTime.now();
        if (pets.isEmpty()) {
            System.out.println("No pets registered yet.");
            return;
        }

        for (Pet p : pets.values()) {
            System.out.println("\nPetID=" + p.getPetId() + " (" + p.getName() + ")");
            List<Appointment> past = new ArrayList<>();
            for (Appointment a : p.getAppointments()) {
                if (a.getDateTime().isBefore(now)) past.add(a);
            }
            if (past.isEmpty()) {
                System.out.println("  No past appointments.");
            } else {
                for (Appointment a : sortByDateTime(past)) {
                    System.out.println("  " + a);
                }
            }
        }
    }

    // -----------------------------
    // Task 3.5: Reports
    // - Next week appointments
    // - Overdue vet visit (no vet in last 6 months)
    // -----------------------------
    private static void generateReports() {
        System.out.println("\n-- Reports --");
        reportUpcomingNextWeek();
        reportOverdueVetVisit();
    }

    private static void reportUpcomingNextWeek() {
        System.out.println("\n== Report: Pets with appointments in the next week ==");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekLater = now.plusWeeks(1);

        boolean any = false;
        for (Pet p : pets.values()) {
            for (Appointment a : p.getAppointments()) {
                if (a.getDateTime().isAfter(now) && a.getDateTime().isBefore(weekLater)) {
                    any = true;
                    System.out.println("PetID=" + p.getPetId() + " | " + p.getName() + " | " + a);
                }
            }
        }
        if (!any) System.out.println("No appointments scheduled within the next week.");
    }

    private static void reportOverdueVetVisit() {
        System.out.println("\n== Report: Pets overdue for a vet visit (no vet visit in last 6 months) ==");
        LocalDate today = LocalDate.now();

        boolean any = false;
        for (Pet p : pets.values()) {
            LocalDate lastVet = getLastVetVisitDate(p);
            if (lastVet == null) {
                any = true;
                System.out.println("PetID=" + p.getPetId() + " | " + p.getName() +
                        " | Status: No vet visit recorded");
            } else {
                Period gap = Period.between(lastVet, today);
                if (gap.getYears() > 0 || gap.getMonths() >= 6) {
                    any = true;
                    System.out.println("PetID=" + p.getPetId() + " | " + p.getName() +
                            " | Last vet visit: " + lastVet + " (over 6 months)");
                }
            }
        }

        if (!any) System.out.println("No pets are overdue for a vet visit.");
    }

    private static LocalDate getLastVetVisitDate(Pet p) {
        LocalDateTime latest = null;
        for (Appointment a : p.getAppointments()) {
            if ("vet visit".equalsIgnoreCase(a.getType())) {
                if (latest == null || a.getDateTime().isAfter(latest)) {
                    latest = a.getDateTime();
                }
            }
        }
        return (latest == null) ? null : latest.toLocalDate();
    }

    // -----------------------------
    // Task 3.3: Store data (CSV)
    // -----------------------------
    private static void saveDataToFiles() {
        // Save pets
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PETS_FILE))) {
            // header
            bw.write("petId,name,speciesBreed,age,ownerName,contactInfo,registrationDate");
            bw.newLine();

            for (Pet p : pets.values()) {
                bw.write(csv(p.getPetId()) + "," +
                        csv(p.getName()) + "," +
                        csv(p.getSpeciesBreed()) + "," +
                        p.getAge() + "," +
                        csv(p.getOwnerName()) + "," +
                        csv(p.getContactInfo()) + "," +
                        p.getRegistrationDate().format(REG_DATE_FMT));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving pets file: " + e.getMessage());
            return;
        }

        // Save appointments
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENTS_FILE))) {
            bw.write("petId,type,dateTime,notes");
            bw.newLine();

            for (Pet p : pets.values()) {
                for (Appointment a : p.getAppointments()) {
                    bw.write(csv(p.getPetId()) + "," +
                            csv(a.getType()) + "," +
                            a.getDateTime().format(APPT_DT_FMT) + "," +
                            csv(a.getNotes()));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments file: " + e.getMessage());
            return;
        }

        System.out.println("Data saved successfully to " + PETS_FILE + " and " + APPOINTMENTS_FILE);
    }

    // -----------------------------
    // Load data (private method as required)
    // -----------------------------
    private static void loadDataFromFiles() {
        loadPets();
        loadAppointments();
    }

    private static void loadPets() {
        File f = new File(PETS_FILE);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine(); // header
            if (line == null) return;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = splitCsvLine(line, 7);
                if (parts == null) continue;

                String petId = uncsv(parts[0]);
                String name = uncsv(parts[1]);
                String speciesBreed = uncsv(parts[2]);
                int age = Integer.parseInt(parts[3].trim());
                String ownerName = uncsv(parts[4]);
                String contactInfo = uncsv(parts[5]);
                LocalDate regDate = LocalDate.parse(parts[6].trim(), REG_DATE_FMT);

                Pet p = new Pet(petId, name, speciesBreed, age, ownerName, contactInfo, regDate);
                pets.put(petId, p);
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not fully load pets file. " + e.getMessage());
        }
    }

    private static void loadAppointments() {
        File f = new File(APPOINTMENTS_FILE);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine(); // header
            if (line == null) return;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = splitCsvLine(line, 4);
                if (parts == null) continue;

                String petId = uncsv(parts[0]);
                Pet p = pets.get(petId);
                if (p == null) {
                    // appointment for unknown pet -> skip safely
                    continue;
                }

                String type = uncsv(parts[1]);
                LocalDateTime dt = LocalDateTime.parse(parts[2].trim(), APPT_DT_FMT);
                String notes = uncsv(parts[3]);

                p.addAppointment(new Appointment(type, dt, notes));
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not fully load appointments file. " + e.getMessage());
        }
    }

    // -----------------------------
    // Helpers: input & CSV
    // -----------------------------
    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.println("Error: Input cannot be empty.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            try {
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }
    }

    private static List<Appointment> sortByDateTime(List<Appointment> list) {
        List<Appointment> copy = new ArrayList<>(list);
        copy.sort(Comparator.comparing(Appointment::getDateTime));
        return copy;
    }

    // CSV escaping: wrap in quotes, escape quotes inside
    private static String csv(String s) {
        if (s == null) s = "";
        String escaped = s.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }

    private static String uncsv(String s) {
        if (s == null) return "";
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            s = s.substring(1, s.length() - 1);
        }
        return s.replace("\"\"", "\"");
    }

    // Split CSV line into expected parts (supports quoted commas)
    private static String[] splitCsvLine(String line, int expectedParts) {
        List<String> parts = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '\"') {
                // handle double quote escape inside quoted string
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                    cur.append('\"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                parts.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        parts.add(cur.toString());

        if (parts.size() != expectedParts) {
            // If data is malformed, skip safely
            return null;
        }
        return parts.toArray(new String[0]);
    }
}