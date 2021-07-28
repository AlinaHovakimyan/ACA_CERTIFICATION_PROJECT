package dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Data Transfer Object for the Response Statuses
 */
@ApiModel("ResponseStatus")
public class ResponseStatusDTO {
    private ResponseStatusDTO.StatusResult result;
    private String message;
    private long code;

    public ResponseStatusDTO() {
        this((long)ResponseStatus.OK.getCode(), "", ResponseStatusDTO.StatusResult.OK);
    }

    public ResponseStatusDTO(long code, String message, ResponseStatusDTO.StatusResult result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @ApiModelProperty(
            value = "HTTP Response code corresponding to this response",
            required = true
    )
    public long getCode() {
        return this.code;
    }

    @ApiModelProperty("Descriptive message of the status of this response")
    public String getMessage() {
        return this.message;
    }

    @ApiModelProperty(
            required = true,
            allowableValues = "OK, ERROR"
    )
    public ResponseStatusDTO.StatusResult getResult() {
        return this.result;
    }

    public void setResult(ResponseStatusDTO.StatusResult result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public static enum StatusResult {
        OK,
        ERROR;

        private StatusResult() {
        }
    }
}

