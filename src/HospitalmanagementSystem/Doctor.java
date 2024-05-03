package HospitalmanagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    //private Scanner scanner;

    public Doctor(Connection connection, Scanner scanner) {
        this.connection = connection;
       // this.scanner = scanner;
    }



    public void viewdoctors() {
        String query = "Select * from doctors";
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            ResultSet resultSet= preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+---------------------------+");
            System.out.println("| Doctor id  | Name               | Specialization            |");
            System.out.println("+------------+--------------------+---------------------------+");
            while (resultSet.next()){
                int id= resultSet.getInt("id");
                String name= resultSet.getString("name");

                String specialization= resultSet.getString("specialization");
                System.out.printf("| %-10s | %-18s | %-25s |\n",id,name,specialization);
                System.out.println("+------------+--------------------+---------------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean getDoctorById(int id){
        String query="Select * from doctor WHERE id=?";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else {
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
