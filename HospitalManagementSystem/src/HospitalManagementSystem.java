import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class HospitalManagementSystem {
    public static void main(String[] args) {
        try(Scanner sc= new Scanner(System.in)) {
            DatabaseConnectionManager connectionManager= new DatabaseConnectionManager();
            try(Connection connection=connectionManager.getConnection()){

                PatientService patientService= new PatientService(connection);
                DoctorService doctorService= new DoctorService(connection);
                AppointmentService appointmentService= new AppointmentService(connection);

                while (true){
                    display();
                    int choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice){
                        case 1:{
                            System.out.println("Enter Patient Name: ");
                            String patientName=sc.nextLine();
                            System.out.println("Enter Patient Age: ");
                            int patientAge=sc.nextInt();
                            System.out.println("Enter Patient Gender: ");
                            String patientGender=sc.next();
                            patientService.addPatient(new Patient(0,patientName,patientAge,patientGender));
                            break;
                        }
                        case 2:{
                            System.out.println("Enter patient Id to View: ");
                            int patientId=sc.nextInt();
                            Patient patient=patientService.viewPatientById(patientId);
                            if(patient != null){
                                System.out.println("Patient Information: "+patient);
                            }else {
                                System.out.println("Patient Not Found");
                            }
                            break;
                        }
                        case 3:{
                            patientService.viewAllPatients().forEach((patient)-> System.out.println(patient));
                            break;
                        }
                        case 4:{
                            System.out.println("Enter Doctor Name: ");
                            String doctorName=sc.nextLine();
                            System.out.println("Enter Doctor Specialization: ");
                            String doctorSpecialization=sc.nextLine();
                            doctorService.addDoctor(new Doctor(0,doctorName,doctorSpecialization));
                            break;
                        }
                        case 5:{
                            System.out.println("Enter Doctor Id to View: ");
                            int doctorId=sc.nextInt();
                            Doctor doctor=doctorService.viewDoctorById(doctorId);
                            if(doctor != null){
                                System.out.println("Doctor Information: "+doctor);
                            }else {
                                System.out.println("Doctor Not Found");
                            }
                            break;
                        }
                        case 6:{
                            doctorService.viewAllDoctors().forEach((doctor)-> System.out.println(doctor));
                            break;
                        }
                        case 7:{
                            System.out.println("To Book an Appointment enter below details");
                            System.out.println("Enter Patient Id: ");
                            int patientId= sc.nextInt();
                            System.out.println("Enter Doctor Id: ");
                            int doctorId= sc.nextInt();
                            System.out.println("Enter Date in (YYYY-MM-DD): ");
                            String date=sc.next();
                            LocalDate localDate=LocalDate.parse(date);
                            appointmentService.bookAppointment(new Appointment(0,patientId,doctorId,localDate));
                            break;
                        }
                        case 8:{
                            System.out.println("Exiting from the System");
                            return;
                        }
                        default:
                            System.out.println("Enter correct choice: ");
                    }
                }
            }catch (SQLException e){
                System.out.println("Error establishing connection to database: "+e.getMessage());
            }
        }
    }

    public static void display(){
        System.out.println("HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("1. Add Patients");
        System.out.println("2. View Patient");
        System.out.println("3. View All Patient");
        System.out.println("4. Add Doctor");
        System.out.println("5. View Doctor");
        System.out.println("6. View All Doctor");
        System.out.println("7. Book An Appointment");
        System.out.println("8. Exit");
    }
}
