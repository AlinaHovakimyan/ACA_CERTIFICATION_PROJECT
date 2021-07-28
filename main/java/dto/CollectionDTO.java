package dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Generic Data Transfer Object to transfer collections/lists of the objects
 */
@ApiModel("Collection")
public class CollectionDTO<V> {
    public CollectionDTO(List<V> items) {
        this.items = items;
    }

    //getters,setters
    @ApiModelProperty("List of requested items")
    public List<V> getItems() {
        return this.items;
    }

    protected List<V> items;
}
