import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pet {
    private String petId;                 // unique
    private String name;
    private String speciesBreed;
    private int age;
    private String ownerName;
    private String contactInfo;
    private LocalDate registrationDate;
    private List<Appointment> appointments = new ArrayList<>();

    public Pet(String petId, String name, String speciesBreed, int age,
               String ownerName, String contactInfo, LocalDate registrationDate) {
        this.petId = petId;
        this.name = name;
        this.speciesBreed = speciesBreed;
        this.age = age;
        this.ownerName = ownerName;
        this.contactInfo = contactInfo;
        this.registrationDate = registrationDate;
    }

    public Pet() {}

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciesBreed() {
        return speciesBreed;
    }

    public void setSpeciesBreed(String speciesBreed) {
        this.speciesBreed = speciesBreed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    @Override
    public String toString() {
        return "PetID=" + petId +
                ", Name=" + name +
                ", Species/Breed=" + speciesBreed +
                ", Age=" + age +
                ", Owner=" + ownerName +
                ", Contact=" + contactInfo +
                ", Registered=" + registrationDate +
                ", Appointments=" + appointments.size();
    }
}