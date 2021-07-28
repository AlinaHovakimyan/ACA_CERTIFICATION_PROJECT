package dao;

import com.google.inject.Singleton;

import java.sql.*;

import java.util.Date;
import instances.Applicant;
import instances.Course;

/**
 * Data Access Object for the courses
 */
@Singleton
public class CourseDAO {
    public static void save(Course course) {
        final String insertCourseInstanceQuery = "INSERT INTO `courses` (`course_name`,`start_date`,`end_date`,`teacher_name`, `description`) VALUES ('" +
                course.getName() + "','" + course.getStartDate() + "','" + course.getEndDate() + "','" + course.getTeacher() + "','" + course.getDescription() +
                "');";
        sendQueryToServer(insertCourseInstanceQuery);
        for (Applicant appl : course.getApplicants()) {
            final String insertApplicantCourseQuery = "INSERT IGNORE INTO `applicant_course_mapping` (`applicant_name`,`course_name`) VALUES ('" +
                    appl.getName() + "','" + course.getName() + "');";
            sendQueryToServer(insertApplicantCourseQuery);
        }
    }

    public static void updateField(String name, String field, String newValue) {
        String tableName = (field == "course_name") ? "applicant_course_mapping" : "courses";
        final String updateApplicantInstanceQuery = "UPDATE `" + tableName + "` SET " + field + " '" + newValue + "' WHERE course_name='" + name + "';";
        sendQueryToServer(updateApplicantInstanceQuery);
    }
    public static void updateDateField(String name, String field, Date newValue) {
        final String updateApplicantInstanceQuery = "UPDATE `courses` SET " + field + " '" + newValue + "' WHERE course_name='" + name + "';";
        sendQueryToServer(updateApplicantInstanceQuery);
    }

    public static void delete(String name) {
        final String deleteApplicantInstanceQuery = "DELETE FROM `courses` WHERE course_name='" + name + "';";
        sendQueryToServer(deleteApplicantInstanceQuery);
        final String deleteApplicantCourseQuery = "DELETE FROM `applicant_course_mapping` WHERE course_name='" + name + "';";
        sendQueryToServer(deleteApplicantCourseQuery);
    }

    public static Course getByName(String name) {
        Course course = new Course(name);
        final String getCourseByNameQuery = "SELECT * FROM `courses` WHERE course_name='" + name + "';";
        final ResultSet rs = sendQueryToServer(getCourseByNameQuery);
        try {
            course.setStartDate(rs.getDate("start_date"));
            course.setEndDate(rs.getDate("end_date"));
            course.setTeacher(rs.getString("teacher_name"));
            course.setDescription(rs.getString("description"));
            course.setApplicants(ApplicantDAO.getByCourse(name));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return course;
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
