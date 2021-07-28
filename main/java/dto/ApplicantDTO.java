package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import instances.Applicant;

/**
 * Data Transfer Object for the Applicant class
 */
@ApiModel("Applicant")
public class ApplicantDTO {
    public ApplicantDTO(Applicant applicant) {
        this.name = applicant.getName();
        this.email = applicant.getEmail();
        this.phoneNumber = applicant.getPhoneNumber();
        this.address = applicant.getAddress();
        this.status = applicant.getStatus().getValue();
        this.course = applicant.getCourse();
    }

    //getters,setters
    @ApiModelProperty("Name of the applicant")
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @ApiModelProperty("Email of the applicant")
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @ApiModelProperty("Phone number of the applicant")
    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String number) { this.phoneNumber = number; }

    @ApiModelProperty("Applicant's postal address")
    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    @ApiModelProperty(
            value = "Applicant's status of taking a course",
            allowableValues = "ON_HOLD, IN_PROGRESS, COMPLETED"
    )
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @ApiModelProperty("Course, to which applicant is assigned")
    public String getCourse() { return course; }

    public void setCourse(String course) { this.course = course; }

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

    @JsonProperty
    private String phoneNumber;

    @JsonProperty
    private String address;

    @JsonProperty
    private String status;

    @JsonProperty
    private String course;
}
