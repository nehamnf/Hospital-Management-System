import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://127.0.0.1:3306/";
    private static final String userName="";
    private static final String password="";

    public static void main(String[] args) {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(url);
        dataSource.setUser(userName);
        dataSource.setPassword(password);

        try{
            Connection connection= dataSource.getConnection();
            Scanner sc = new Scanner(System.in);
            Patient p= new Patient(connection,sc);
            Doctor d= new Doctor(connection);

            while (true){
                System.out.println(" HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient ");
                System.out.println("2. View Patient ");
                System.out.println("3. View Doctors ");
                System.out.println("4. Book Appointment ");
                System.out.println("5. Exit ");
                System.out.println("Enter your choice: ");
                int choice= sc.nextInt();
                sc.nextLine();
                switch (choice){
                    case 1:
                        p.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        p.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        d.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(connection,sc,p,d);
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter correct choice: ");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void bookAppointment(Connection connection,Scanner sc,Patient p, Doctor d){
        System.out.println("Enter Patient Id: ");
        int pid= sc.nextInt();
        System.out.println("Enter Doctors Id: ");
        int did=sc.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
        String appDate= sc.next();
        if(p.getPatientById(pid) && d.getDoctorById(did)){
            if(checkDoctorsAvailibility(did,appDate,connection)){
                try {
                    String insertQuery = "INSERT INTO appointments(patient_id,doctor_id,appointment_date) value(?,?,?)";
                    PreparedStatement prpStmnt = connection.prepareStatement(insertQuery);
                    prpStmnt.setInt(1,pid);
                    prpStmnt.setInt(2,did);
                    prpStmnt.setString(3, appDate);
                    int rowInserted= prpStmnt.executeUpdate();
                    if(rowInserted > 0){
                        System.out.println("Appointment is Successful");
                    }else {
                        System.out.println("Failed to book an appointment");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else{
                System.out.println("Doctor appointment is not available");
            }
        }else{
            System.out.println("Doctor Id or patient Id is not correct");
        }
    }

    public static boolean checkDoctorsAvailibility(int id, String date,Connection connection){
        try {
            String query = "SELECT count(*) from appointments where doctor_id=? and appointment_date=?";
            PreparedStatement prpStmnt= connection.prepareStatement(query);
            prpStmnt.setInt(1,id);
            prpStmnt.setString(2,date);
            ResultSet resultSet= prpStmnt.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
