package xml.controller.dto;

/**
 * Created by Dorian on 16.6.2016.
 */
public class SearchMetadataDTO {

    private Integer metadataType;
    private Integer collectionName;
    private String criteria;

    public SearchMetadataDTO() {
    }

    public Integer getMetadataType() {
        return metadataType;
    }

    public void setMetadataType(Integer metadataType) {
        this.metadataType = metadataType;
    }

    public Integer getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(Integer collectionName) {
        this.collectionName = collectionName;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
