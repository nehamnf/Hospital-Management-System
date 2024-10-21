import java.time.LocalDate;

public class Appointment {
    private int id;
    private int patient_id;
    private  int doctor_id;
    private LocalDate date;

    public Appointment(int id, int patient_id, int doctor_id, LocalDate date) {
        this.id = id;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patient_id=" + patient_id +
                ", doctor_id=" + doctor_id +
                ", date=" + date +
                '}';
    }
}
