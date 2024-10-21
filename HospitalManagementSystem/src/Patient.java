import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }

    public void addPatient(){
        System.out.println("Enter the Patient name: ");
        String name= scanner.nextLine();
        System.out.println("Enter the Patient Age: ");
        int age= scanner.nextInt();
        System.out.println("Enter patient gender: ");
        String gender= scanner.next();
        try{
            String insertQuery= "INSERT INTO patients(name,age,gender) values(?,?,?)";
            PreparedStatement prpStmnt= connection.prepareStatement(insertQuery);
            prpStmnt.setString(1,name);
            prpStmnt.setInt(2,age);
            prpStmnt.setString(3,gender);
            int rowInserted=prpStmnt.executeUpdate();
            if(rowInserted >0){
                System.out.println("Patient Data inserted into DB successfully");
            }else {
                System.out.println("Failed to insert patient data");
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public void viewPatients(){
        try {
            String query = "select * from patients";
            PreparedStatement prpStmnt = connection.prepareStatement(query);
            ResultSet resultSet= prpStmnt.executeQuery();
            System.out.println("+------------+-----------------------------+-------+-------------+");
            System.out.println("| Patient Id | Name                        | AGE   | Gender      |");
            System.out.println("+------------+-----------------------------+-------+-------------+");
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                int age=resultSet.getInt("age");
                String gender= resultSet.getString("gender");
                System.out.printf("| %d          | %-28s| %-6d| %-12s|\n",id,name,age,gender);
            }
            System.out.println("+------------+-----------------------------+-------+-------------+");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public  boolean getPatientById(int id) {
        boolean found=false;
        try {
            String query = "select * from patients where id=?";
            PreparedStatement prpStmt = connection.prepareStatement(query);
            prpStmt.setInt(1, id);
            ResultSet resultSet = prpStmt.executeQuery();
            while (resultSet.next()) {
                int pid = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.println("Patient data :\nPatient Id: " + pid + " ,Name: " + name + " ,Age: " + age + " ,Gender: " + gender);
                found=true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  found;
    }
}
