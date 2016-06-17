package xml.controller.dto;

import java.util.ArrayList;

/**
 * Created by Dorian on 12.6.2016.
 */
public class ActsAndAmendemntsIdsDTO {

    private ArrayList<ActWrapperDTO> actsIds;
    private ArrayList<Long> amendmentsIds;

    public ActsAndAmendemntsIdsDTO() {
        actsIds = new ArrayList<ActWrapperDTO>();
        amendmentsIds = new ArrayList<Long>();
    }

    public ArrayList<ActWrapperDTO> getActsIds() {
        return actsIds;
    }

    public void setActsIds(ArrayList<ActWrapperDTO> actsIds) {
        this.actsIds = actsIds;
    }

    public ArrayList<Long> getAmendmentsIds() {
        return amendmentsIds;
    }

    public void setAmendmentsIds(ArrayList<Long> amendmentsIds) {
        this.amendmentsIds = amendmentsIds;
    }
}
