package xml.controller.dto;

import java.util.ArrayList;

/**
 * Created by Dorian on 12.6.2016.
 */
public class ActsAndAmendemntsIdsDTO {

    private ArrayList<Long> actsIds;
    private ArrayList<Long> amendmentsIds;

    public ActsAndAmendemntsIdsDTO() {
        actsIds = new ArrayList<Long>();
        amendmentsIds = new ArrayList<Long>();
    }

    public ArrayList<Long> getActsIds() {
        return actsIds;
    }

    public void setActsIds(ArrayList<Long> actsIds) {
        this.actsIds = actsIds;
    }

    public ArrayList<Long> getAmendmentsIds() {
        return amendmentsIds;
    }

    public void setAmendmentsIds(ArrayList<Long> amendmentsIds) {
        this.amendmentsIds = amendmentsIds;
    }
}
