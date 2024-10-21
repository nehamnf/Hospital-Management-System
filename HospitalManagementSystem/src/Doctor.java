import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection){
        this.connection=connection;
    }

    public void viewDoctors(){
        try {
            String query = "select * from doctors";
            PreparedStatement prpStmnt = connection.prepareStatement(query);
            ResultSet resultSet= prpStmnt.executeQuery();
            System.out.println("+------------+-----------------------------+-------------------+");
            System.out.println("| Doctors Id | Name                        | Specialization    |");
            System.out.println("+------------+-----------------------------+-------------------+");
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                String spec=resultSet.getString("specialization");
                System.out.printf("| %d          | %-28s| %-18s|\n",id,name,spec);
            }
            System.out.println("+------------+-----------------------------+-------------------+");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public  boolean getDoctorById(int id) {
        boolean found=false;
        try {
            String query = "select * from doctors where id=?";
            PreparedStatement prpStmt = connection.prepareStatement(query);
            prpStmt.setInt(1, id);
            ResultSet resultSet = prpStmt.executeQuery();
            while (resultSet.next()) {
                int did = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String spec = resultSet.getString("specialization");
                System.out.println("Doctor data :\nDoctor Id: " + did + " ,Name: " + name + " ,Specialization: " + spec);
                found=true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  found;
    }
}
