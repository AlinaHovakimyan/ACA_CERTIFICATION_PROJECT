package api;

import dto.*;
import instances.Applicant;
import dao.ApplicantDAO;

import com.google.inject.Singleton;
import instances.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Singleton
@Path("/applicant")
@Api
public class ApplicantWebResource {
    @GET
    @Path("/{applicantname}")
    @ApiOperation(value = "Gets the applicant information by name.", nickname = "getApplicantInfo")
    public ResponseDTO<ApplicantDTO>
    getApplicantInformation(@PathParam("applicantname") String applicantName) {
    final Applicant applicant = ApplicantDAO.getByName(applicantName);
    if(applicant != null) {
        return new ResponseDTO<>(new ApplicantDTO(applicant), new ResponseStatusDTO(ResponseStatus.OK.getCode(),
                "ok", ResponseStatusDTO.StatusResult.OK));
    }
        return new ResponseDTO<>(null, new ResponseStatusDTO(ResponseStatus.BAD_REQUEST.getCode(),
                "Applicant does not exist", ResponseStatusDTO.StatusResult.ERROR));
    }

    @GET
    @Path("/{applicantemail}")
    @ApiOperation(value = "Gets the applicant information by email.", nickname = "getApplicantInfo")
    public ResponseDTO<ApplicantDTO>
    getApplicantInformationByEmail(@PathParam("applicantemail") String applicantEmail) {
        final Applicant applicant = ApplicantDAO.getByEmail(applicantEmail);
        if(applicant != null) {
            return new ResponseDTO<>(new ApplicantDTO(applicant), new ResponseStatusDTO(ResponseStatus.OK.getCode(),
                    "ok", ResponseStatusDTO.StatusResult.OK));
        }
        return new ResponseDTO<>(null, new ResponseStatusDTO(ResponseStatus.BAD_REQUEST.getCode(),
                "Applicant does not exist", ResponseStatusDTO.StatusResult.ERROR));
    }

    @GET
    @Path("/{course}")
    @ApiOperation(value = "Gets the all applicants tacking the course.", nickname = "getApplicants")
    public ResponseDTO<CollectionDTO<ApplicantDTO>>
    getApplicants(@PathParam("course") String course) {
        final List<Applicant> applicants = ApplicantDAO.getByCourse(course);
        List<ApplicantDTO> applicantDTOs = new ArrayList<>();
        for(Applicant applicant : applicants) {
            applicantDTOs.add(new ApplicantDTO(applicant));
        }
        if(applicants != null) {
            return new ResponseDTO<>(new CollectionDTO<>(applicantDTOs), new ResponseStatusDTO(ResponseStatus.OK.getCode(),
                    "ok", ResponseStatusDTO.StatusResult.OK));
        }
        return new ResponseDTO<>(null, new ResponseStatusDTO(ResponseStatus.BAD_REQUEST.getCode(),
                "Applicant does not exist", ResponseStatusDTO.StatusResult.ERROR));
    }

    @DELETE
    @Path("/deleteApplicant/{applicantname}")
    @ApiOperation(value = "Deletes the applicant.", nickname = "deleteapplicant")
    public ResponseDTO<ResponseStatusDTO> deleteApplicant(@PathParam("applicantname") String applicantName) {
        ApplicantDAO.delete(applicantName);
        return new ResponseDTO<>(null, new ResponseStatusDTO(ResponseStatus.OK.getCode(),
                    "ok", ResponseStatusDTO.StatusResult.OK));
        //TO DO: check if applicant exists in the db
    }

    @POST
    @Path("/createApplicant/")
    @ApiOperation(value = "Creates the applicant.", nickname = "createapplicant")
    public ResponseDTO<ResponseStatusDTO> createApplicant(ApplicantDTO applicantDTO) {
        Applicant applicant = new Applicant();
        applicant.setName(applicantDTO.getName());
        applicant.setEmail(applicantDTO.getEmail());
        applicant.setPhoneNumber(applicantDTO.getPhoneNumber());
        applicant.setAddress(applicantDTO.getAddress());
        Status status = Status.ON_HOLD;
        switch (applicantDTO.getStatus()) {
            case "COMPLETED":
                status = Status.COMPLETED;
            case "IN_PROGRESS":
                status = Status.IN_PROGRESS;
        }
        applicant.setStatus(status);
        ApplicantDAO.save(applicant);
        return new ResponseDTO<>(null, new ResponseStatusDTO(ResponseStatus.OK.getCode(),
                "ok", ResponseStatusDTO.StatusResult.OK));
    }
    @POST
    @Path("/update/")
    @ApiOperation(value = "update the applicant.", nickname = "updateapplicant")
    public ResponseDTO<ResponseStatusDTO> updateApplicant(ApplicantDTO applicantDTO) {
        Applicant applicant = ApplicantDAO.getByName(applicantDTO.getName());
        if(applicant.getEmail() != applicantDTO.getEmail()) {
                ApplicantDAO.updateField(applicantDTO.getName(), "email", applicantDTO.getEmail());
        }
        if(applicant.getPhoneNumber() != applicantDTO.getPhoneNumber()) {
            ApplicantDAO.updateField(applicantDTO.getName(), "phone_number", applicantDTO.getPhoneNumber());
        }
        if(applicant.getAddress() != applicantDTO.getAddress()) {
            ApplicantDAO.updateField(applicantDTO.getName(), "address", applicantDTO.getAddress());
        }
        if(applicant.getStatus().getValue() != applicantDTO.getStatus()) {
            ApplicantDAO.updateField(applicantDTO.getName(), "status", applicantDTO.getStatus());
        }
    return new ResponseDTO<>(null, new ResponseStatusDTO(ResponseStatus.OK.getCode(),
                "ok", ResponseStatusDTO.StatusResult.OK));
    }
}
