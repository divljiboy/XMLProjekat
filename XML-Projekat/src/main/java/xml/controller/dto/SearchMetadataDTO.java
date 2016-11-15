package xml.controller.dto;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dorian on 16.6.2016.
 */
public class SearchMetadataDTO {

    private String criteria;
    private Integer metadataType;

    public SearchMetadataDTO() {
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public Integer getMetadataType() {
        return metadataType;
    }

    public void setMetadataType(Integer metadataType) {
        this.metadataType = metadataType;
    }
}
