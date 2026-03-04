import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private String type;              // vet visit, vaccination, grooming...
    private LocalDateTime dateTime;   // appointment date & time
    private String notes;             // optional

    public Appointment(String type, LocalDateTime dateTime, String notes) {
        this.type = type;
        this.dateTime = dateTime;
        this.notes = notes;
    }

    public Appointment() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String n = (notes == null || notes.trim().isEmpty()) ? "-" : notes.trim();
        return "Type: " + type + ", DateTime: " + dateTime.format(fmt) + ", Notes: " + n;
    }
}