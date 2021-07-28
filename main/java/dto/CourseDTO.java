package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import instances.Applicant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import instances.Course;

import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object for the Course class
 */
@ApiModel("Course")
public class CourseDTO {
    public CourseDTO(Course course) {
        this.name = course.getName();
        this.startDate = course.getStartDate();
        this.endDate = course.getEndDate();
        this.teacher = course.getTeacher();
        this.description = course.getDescription();
        this.applicants = course.getApplicants();
    }

    //getters,setters
    @ApiModelProperty("Name of the course")
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @ApiModelProperty("Start date of the course")
    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    @ApiModelProperty("End date of the course")
    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    @ApiModelProperty("Name of the course's teacher")
    public String getTeacher() { return teacher; }

    public void setTeacher(String teacher) { this.teacher = teacher; }

    @ApiModelProperty("Description of the course")
    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @ApiModelProperty("List of applicants assigned for the course")
    public List<Applicant> getApplicants() { return applicants; }

    public void setApplicants(List<Applicant> applicants) { this.applicants = applicants; }

    @JsonProperty
    private String name;

    @JsonProperty
    private Date startDate;

    @JsonProperty
    private Date endDate;

    @JsonProperty
    private String teacher;

    @JsonProperty
    private String description;

    @JsonProperty
    private List<Applicant> applicants;
}
