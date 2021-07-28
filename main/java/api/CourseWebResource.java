package api;

import com.google.inject.Singleton;
import dao.ApplicantDAO;
import dao.CourseDAO;
import dto.*;
import instances.Applicant;
import instances.Course;
import common.PdfHelper;
import instances.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sun.security.util.IOUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Singleton
@Path("/course")
@Api
public class CourseWebResource {
    @GET
    @Path("/{coursename}")
    @ApiOperation(value = "Gets the course information by name.", nickname = "getCourseInfo")
    public ResponseDTO<CourseDTO>
    getApplicantInformation(@PathParam("coursename") String courseName) {
        final Course course = CourseDAO.getByName(courseName);
        if (course != null) {
            return new ResponseDTO<>(new CourseDTO(course), new ResponseStatusDTO(ResponseStatus.OK.getCode(),
                    "ok", ResponseStatusDTO.StatusResult.OK));
        }
        return new ResponseDTO<>(null, new ResponseStatusDTO(ResponseStatus.BAD_REQUEST.getCode(),
                "Applicant does not exist", ResponseStatusDTO.StatusResult.ERROR));
    }
    @DELETE
    @Path("/deleteCourse/{coursename}")
    @ApiOperation(value = "Deletes the course.", nickname = "deletecourse")
    public ResponseDTO<ResponseStatusDTO> deleteCourse(@PathParam("coursename") String courseName) {
        CourseDAO.delete(courseName);
        return new ResponseDTO<>(null, new ResponseStatusDTO(ResponseStatus.OK.getCode(),
                "ok", ResponseStatusDTO.StatusResult.OK));
        //TO DO: check if course exists in the db
    }
    @POST
    @Path("/createCourse/")
    @ApiOperation(value = "Creates the course.", nickname = "createcourse")
    public ResponseDTO<ResponseStatusDTO> createCourse(CourseDTO courseDTO) {
        Course course = new Course(courseDTO.getName());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());
        course.setTeacher(courseDTO.getTeacher());
        course.setDescription(courseDTO.getDescription());
        course.setApplicants(courseDTO.getApplicants());
        CourseDAO.save(course);
        return new ResponseDTO<>(null, new ResponseStatusDTO(ResponseStatus.OK.getCode(),
                "ok", ResponseStatusDTO.StatusResult.OK));
    }
    @POST
    @Path("/update/")
    @ApiOperation(value = "update the course.", nickname = "updatecourse")
    public ResponseDTO<ResponseStatusDTO> updateCourse(CourseDTO courseDTO) {
       Course course = CourseDAO.getByName(courseDTO.getName());
        if(course.getStartDate() != courseDTO.getStartDate()) {
            CourseDAO.updateDateField(courseDTO.getName(), "start_date", courseDTO.getStartDate());
        }
        if(course.getEndDate() != courseDTO.getEndDate()) {
            CourseDAO.updateDateField(courseDTO.getName(), "end_date", courseDTO.getEndDate());
        }
        if(course.getTeacher() != courseDTO.getTeacher()) {
            CourseDAO.updateField(courseDTO.getName(), "teacher_name", courseDTO.getTeacher());
        }
        if(course.getDescription() != courseDTO.getDescription()) {
            CourseDAO.updateField(courseDTO.getName(), "description", courseDTO.getDescription());
        }
        return new ResponseDTO<>(null, new ResponseStatusDTO(ResponseStatus.OK.getCode(),
                "ok", ResponseStatusDTO.StatusResult.OK));
    }

    @POST
    @Path("/getCertificates/{coursename}")
    @ApiOperation(value = "get Certificates of all applicants who has completed the course.", nickname = "getCertificates")
    public ResponseDTO<ResponseStatusDTO> getCertificates(@PathParam("coursename") String courseName) {
        try {
            FileOutputStream zipFile = new FileOutputStream(new File("certificates.zip"));
            ZipOutputStream certificates = new ZipOutputStream(zipFile);
            Course course = CourseDAO.getByName(courseName);
            for(Applicant applicant : course.getApplicants()) {
                if (applicant.getStatus() == Status.COMPLETED) {
                    PDDocument pdfFile = PdfHelper.createPDF(applicant.getName(), course);
                    certificates.putNextEntry(new ZipEntry(pdfFile));
                    certificates.closeEntry();
                }
            }
            certificates.finish();
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            certificates.save(output);
            certificates.close();
            return Response.ok().entity(certificates).build();
        } catch (java.io.IOException ex) {
            System.out.println(ex.getMessage());
        }
        return Response.serverError().build();
    }
}
