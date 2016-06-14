package xml.controller.dto;

/**
 * Created by Dorian on 14.6.2016.
 */
public class SearchCriteriaDTO {

    private String criteria;
    private Integer idSearch;

    public SearchCriteriaDTO() {
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public Integer getIdSearch() {
        return idSearch;
    }

    public void setIdSearch(Integer idSearch) {
        this.idSearch = idSearch;
    }
}
