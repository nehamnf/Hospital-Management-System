import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AppointmentService {
    private Connection connection;

    public AppointmentService(Connection connection){
        this.connection=connection;
    }

    public void bookAppointment(Appointment appointment){
        if(appointment.getDate().isBefore(LocalDate.now())){
            System.out.println("Appointment Date can not be in past");
            return;
        }
        if(checkAvailability(appointment.getDoctor_id(),appointment.getDate())){
            String query="INSERT INTO appointments(patient_id,doctor_id,appointment_date) value(?,?,?)";
            try(PreparedStatement statement=connection.prepareStatement(query)) {
                statement.setInt(1,appointment.getPatient_id());
                statement.setInt(2,appointment.getDoctor_id());
                statement.setObject(3,appointment.getDate());
                int rowInserted= statement.executeUpdate();
                if(rowInserted >0){
                    System.out.println("Appointment Booked Successfully");
                }else {
                    System.out.println("Error in booking Doctors Appointment");
                }
            }catch (SQLException e){
                System.out.println("Error taking doctors appointment");
            }
        }else{
            System.out.println("Appointment not available!!!!!");
        }
    }

    private boolean checkAvailability(int doctor_id, LocalDate date){
        String query = "SELECT count(*) from appointments where doctor_id=? and appointment_date=?";
        try(PreparedStatement statement= connection.prepareStatement(query)){
            statement.setInt(1,doctor_id);
            statement.setObject(2,date);
            ResultSet resultSet= statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1)  ==0;
            }
        }catch (SQLException e) {
            System.out.println("Error checking doctor availability: "+e.getMessage());
        }
        return false;
    }
}
