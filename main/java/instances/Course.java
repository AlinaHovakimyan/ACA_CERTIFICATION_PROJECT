package instances;

import javax.annotation.Nonnull;

import java.util.Date;
import java.util.List;

/**
 * Class which contains data about one Course
 */
public class Course {
    public Course(String name) {
        this.name = name;
    }
    public Course(@Nonnull String name, @Nonnull Date start_date,
                  @Nonnull Date end_date, @Nonnull String teacher,
                  @Nonnull String description, List<Applicant> applicants) {
        this.name = name;
        this.startDate = start_date;
        this.endDate = end_date;
        this.teacher = teacher;
        this.description = description;
        this.applicants = applicants;
    }

    //getters,setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date start_date) {
        this.startDate = start_date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Applicant> applicants) {
        this.applicants = applicants;
    }

    public void addApplicant(Applicant applicant) {
        this.applicants.add(applicant);
    }

    private String name;
    private Date startDate;
    private Date endDate;
    private String teacher;
    private String description;
    private List<Applicant> applicants;
}
