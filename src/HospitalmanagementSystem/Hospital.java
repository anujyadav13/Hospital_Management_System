package HospitalmanagementSystem;

import java.sql.*;
import java.util.Scanner;


public class Hospital {
    private static final String url="jdbc:mysql://localhost:3306/hospital";//"jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="Anuj5808";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");// Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);
        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection,scanner);
            Doctor doctor=new Doctor(connection,scanner);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1.Add Patient");
                System.out.println("2.View Patient");
                System.out.println("3.View  Doctors");
                System.out.println("4.Book Appointment");
                System.out.println("5.Exit");
                System.out.println("Enter your choice: ");
                int choice= scanner.nextInt();
                switch (choice){
                    case 1:
                        // Add Patient
                        patient.addpatient();
                        System.out.println();
                        break;
                    case 2:
                        // view patient
                        patient.viewpatients();
                        System.out.println();
                        break;
                    case 3:
                        // view doctors
                        doctor.viewdoctors();
                        System.out.println();
                        break;
                    case 4:
                        // Book appointment
                        bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter valid choice");
                        System.out.println();
                        break;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner){
        System.out.println("Enter Patient id: ");
        int patientsId= scanner.nextInt();
        System.out.println("Doctor id: ");
        int doctorid=scanner.nextInt();
        System.out.print("enter appointment date(YYYY-MM-DD:) ");
        String appointmentDate=scanner.next();
        if(patient.getpatientById(patientsId)&& doctor.getDoctorById(doctorid)) {
            if (checkDoctorAvailability(doctorid, appointmentDate, connection)) {
                String aq = "insert into appointments(patient_id,doctor_id,appointmate_date) VALUES(?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(aq);
                    preparedStatement.setInt(1, patientsId);
                    preparedStatement.setInt(2, doctorid);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsaffected = preparedStatement.executeUpdate();
                    if (rowsaffected > 0) {
                        System.out.println("appointment book");
                    } else {
                        System.out.println("failed to book appointment");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("not available on this date");
            }
        }else{
            System.out.println("either doc or patient doesnot exist");
        }
}public static boolean checkDoctorAvailability(int doctorId,String appointmentdate,Connection connection) {
        String query = "SELECT COUNT(*) from appointments WHERE doctor_id=? AND appointment_date=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentdate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if(count == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

return false;
}}