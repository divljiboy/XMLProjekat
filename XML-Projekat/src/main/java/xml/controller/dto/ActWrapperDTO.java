package xml.controller.dto;

/**
 * Created by Dorian on 17.6.2016.
 */
public class ActWrapperDTO {
    public Long idAct;
    public Integer za;
    public Integer protiv;
    public Integer suzdrzano;

    public ActWrapperDTO() {
    }

    public Long getIdAct() {
        return idAct;
    }

    public void setIdAct(Long idAct) {
        this.idAct = idAct;
    }

    public Integer getZa() {
        return za;
    }

    public void setZa(Integer za) {
        this.za = za;
    }

    public Integer getProtiv() {
        return protiv;
    }

    public void setProtiv(Integer protiv) {
        this.protiv = protiv;
    }

    public Integer getSuzdrzano() {
        return suzdrzano;
    }

    public void setSuzdrzano(Integer suzdrzano) {
        this.suzdrzano = suzdrzano;
    }
}
