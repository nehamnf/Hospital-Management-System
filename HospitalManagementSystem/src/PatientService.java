import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientService {
    private Connection connection;

    public PatientService(Connection connection){
        this.connection=connection;
    }

    public void addPatient(Patient p){
        String query="INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
        try(PreparedStatement statement= connection.prepareStatement(query)){
            statement.setString(1,p.getName());
            statement.setInt(2,p.getAge());
            statement.setString(3,p.getGender());
            int rowInserted=statement.executeUpdate();
            if(rowInserted > 0){
                System.out.println("Patient Data inserted into DB successfully");
            }else {
                System.out.println("Failed to insert Patient data into DB");
            }
        }catch (SQLException e){
            System.out.println("Error adding Patient: "+e.getMessage());
        }
    }

    public Patient viewPatientById(int id){
        String query="SELECT * FROM patients where id=?";
        try(PreparedStatement statement= connection.prepareStatement(query)){
            statement.setInt(1,id);
            ResultSet resultSet= statement.executeQuery();
            if (resultSet.next()){
                return new Patient(resultSet.getInt("id"),resultSet.getString("name"),
                        resultSet.getInt("age"),resultSet.getString("gender"));
            }
        }catch (SQLException e){
            System.out.println("Error while fetching Patient: "+e.getMessage());
        }
        return  null;
    }

    public  List<Patient> viewAllPatients(){
        List<Patient> patients= new ArrayList<>();
        String query="SELECT * FROM patients";
        try(PreparedStatement statement= connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                patients.add(new Patient(resultSet.getInt("id"), resultSet.getString("name"),
                            resultSet.getInt("age"), resultSet.getString("gender")));
            }
        }catch (SQLException e){
            System.out.println("Error while fetching Patient: "+e.getMessage());
        }
        return patients;
    }
}
