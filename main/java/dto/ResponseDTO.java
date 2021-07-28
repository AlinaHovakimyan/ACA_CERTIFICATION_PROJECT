package dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Generic Data Transfer Object for the Responses to the HTTP requests
 * @param <V> type of the response
 */
@ApiModel(
        value = "ResponseContainer",
        description = "JSON container for the HTTP response along with status"
)
public class ResponseDTO<V> {
    private ResponseStatusDTO status;
    private V response;

    public ResponseDTO() {
        this.status = new ResponseStatusDTO();
    }

    public ResponseDTO(V response) {
        this(response, new ResponseStatusDTO());
    }

    public ResponseDTO(V response, ResponseStatusDTO status) {
        this.response = response;
        this.status = status;
    }

    @ApiModelProperty
    public V getResponse() {
        return this.response;
    }

    @ApiModelProperty(
            required = true
    )
    public ResponseStatusDTO getStatus() {
        return this.status;
    }

    public void setStatus(ResponseStatusDTO value) {
        this.status = value;
    }
}

