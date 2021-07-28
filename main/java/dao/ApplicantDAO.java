package dao;

import com.google.inject.Singleton;

import instances.Applicant;
import instances.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the applicants
 */
@Singleton
public class ApplicantDAO {
    public static void save(Applicant appl) {
        final String insertApplicantInstanceQuery = "INSERT INTO `applicants` (`applicant_name`,`email`,`phone_number`,`address`, `status`) VALUES ('" +
                appl.getName() + "','" + appl.getEmail() + "','" + appl.getPhoneNumber() + "','" + appl.getAddress() + "','" + appl.getStatus().getValue() +
                "');";
        sendQueryToServer(insertApplicantInstanceQuery);
        final String insertApplicantCourseQuery = "INSERT IGNORE INTO `applicant_course_mapping` (`applicant_name`,`course_name`) VALUES ('" +
                appl.getName() + "','" + appl.getCourse() + "');";
        sendQueryToServer(insertApplicantCourseQuery);
    }

    public static void updateField(String name, String field, String newValue) {
        String tableName = (field == "course_name") ? "applicant_course_mapping" : "applicants";
        final String updateApplicantInstanceQuery = "UPDATE `" + tableName + "` SET " + field + " '" + newValue + "' WHERE applicant_name='" + name + "';";
        sendQueryToServer(updateApplicantInstanceQuery);
    }

    public static void delete(String name) {
        final String deleteApplicantInstanceQuery = "DELETE FROM `applicants` WHERE applicant_name='" + name + "';";
        sendQueryToServer(deleteApplicantInstanceQuery);
        final String deleteApplicantCourseQuery = "DELETE FROM `applicant_course_mapping` WHERE applicant_name='" + name + "';";
        sendQueryToServer(deleteApplicantCourseQuery);
    }

    private static Applicant get(String query) {
        Applicant applicant = new Applicant();
        final ResultSet rs = sendQueryToServer(query);
        try {
            applicant.setName(rs.getString("applicant_name"));
            applicant.setEmail(rs.getString("email"));
            applicant.setPhoneNumber(rs.getString("phone_number"));
            applicant.setAddress(rs.getString("address"));
            final String statusString = rs.getString("status");
            Status status = Status.ON_HOLD;
            switch (statusString) {
                case "COMPLETED":
                    status = Status.COMPLETED;
                case "IN_PROGRESS":
                    status = Status.IN_PROGRESS;
            }
            applicant.setStatus(status);
            final String getApplicantCourseQuery = "SELECT course_name FROM `applicant_course_mapping` WHERE applicant_name='" + rs.getString("applicant_name") + "';";
            final String courseName = sendQueryToServer(getApplicantCourseQuery).getString("course_name");
            applicant.setCourse(courseName);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return applicant;
    }

    public static Applicant getByName(String name) {
        final String getApplicantByNameQuery = "SELECT * FROM `applicants` WHERE applicant_name='" + name + "';";
        return get(getApplicantByNameQuery);
    }

    public static Applicant getByEmail(String email) {
        final String getApplicantByEmailQuery = "SELECT * FROM `applicants` WHERE email='" + email + "';";
        return get(getApplicantByEmailQuery);
    }

    public static List<Applicant> getByCourse(String name) {
        List<Applicant> applicants = new ArrayList<>();
        final String getApplicantsOfCourseQuery = "SELECT * FROM `applicants` INNER JOIN `applicant_course_mapping` ON " +
                " applicants.applicant_name=applicant_course_mapping.applicant_name WHERE course_name='" + name + "';";
        final ResultSet rs = sendQueryToServer(getApplicantsOfCourseQuery);
        try {
            while (rs.next()) {
                Applicant applicant = new Applicant();
                applicant.setName(rs.getString("applicant_name"));
                applicant.setEmail(rs.getString("email"));
                applicant.setPhoneNumber(rs.getString("phone_number"));
                applicant.setAddress(rs.getString("address"));
                applicant.setCourse(name);
                final String statusString = rs.getString("status");
                Status status = Status.ON_HOLD;
                switch (statusString) {
                    case "COMPLETED":
                        status = Status.COMPLETED;
                    case "IN_PROGRESS":
                        status = Status.IN_PROGRESS;
                }
                applicant.setStatus(status);
                applicants.add(applicant);
            }
        }catch (SQLException ex) {
                System.out.println(ex.getMessage());
        }
        return applicants;
    }

    static ResultSet sendQueryToServer(String query) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "a");
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
