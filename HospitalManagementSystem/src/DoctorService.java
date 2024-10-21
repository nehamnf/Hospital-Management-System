import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorService {
    private Connection connection;

    public  DoctorService(Connection connection){
        this.connection=connection;
    }

    public void addDoctor(Doctor d){
        String query="INSERT INTO doctors(name ,specialization) VALUES(?,?)";
        try(PreparedStatement statement= connection.prepareStatement(query)){
            statement.setString(1,d.getName());
            statement.setString(2,d.getSpecialization());
            int rowInserted=statement.executeUpdate();
            if(rowInserted > 0){
                System.out.println("Doctor Data inserted into DB successfully");
            }else {
                System.out.println("Failed to insert Doctors data into DB");
            }
        }catch (SQLException e){
            System.out.println("Error adding Doctor: "+e.getMessage());
        }
    }

    public Doctor viewDoctorById(int id){
        String query="SELECT * FROM doctors where id=?";
        try(PreparedStatement statement= connection.prepareStatement(query)){
            statement.setInt(1,id);
            ResultSet resultSet= statement.executeQuery();
            if (resultSet.next()){
                return new Doctor(resultSet.getInt("id"),resultSet.getString("name"),
                        resultSet.getString("specialization"));
            }
        }catch (SQLException e){
            System.out.println("Error while fetching Doctor Details: "+e.getMessage());
        }
        return  null;
    }

    public List<Doctor> viewAllDoctors(){
        List<Doctor> doctors= new ArrayList<>();
        String query="SELECT * FROM doctors";
        try(PreparedStatement statement= connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                doctors.add(new Doctor(resultSet.getInt("id"), resultSet.getString("name"),
                     resultSet.getString("specialization")));
            }
        }catch (SQLException e){
            System.out.println("Error while fetching Doctor Details: "+e.getMessage());
        }
        return doctors;
    }
}
