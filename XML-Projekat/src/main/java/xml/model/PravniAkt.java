
package xml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Preambula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Naziv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{aktovi}Glavni_deo"/>
 *         &lt;element name="Drzava" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Pokrajina" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Grad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Skupstina" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Broj_popisa_kod_organa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Datum_donosenja_propisa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Mesto_donosenja_propisa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Ovlasceno_lice" type="{aktovi}TOvlasceno_lice"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Usvojen" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "preambula",
    "naziv",
    "glavniDeo",
    "drzava",
    "pokrajina",
    "grad",
    "skupstina",
    "brojPopisaKodOrgana",
    "datumDonosenjaPropisa",
    "mestoDonosenjaPropisa",
    "ovlascenoLice"
})
@XmlRootElement(name = "Pravni_akt", namespace = "aktovi")
public class PravniAkt {

    @XmlElement(name = "Preambula", namespace = "aktovi")
    protected String preambula;
    @XmlElement(name = "Naziv", namespace = "aktovi", required = true)
    protected String naziv;
    @XmlElement(name = "Glavni_deo", namespace = "aktovi", required = true)
    protected GlavniDeo glavniDeo;
    @XmlElement(name = "Drzava", namespace = "aktovi", required = true)
    protected String drzava;
    @XmlElement(name = "Pokrajina", namespace = "aktovi", required = true)
    protected String pokrajina;
    @XmlElement(name = "Grad", namespace = "aktovi", required = true)
    protected String grad;
    @XmlElement(name = "Skupstina", namespace = "aktovi", required = true)
    protected String skupstina;
    @XmlElement(name = "Broj_popisa_kod_organa", namespace = "aktovi", required = true)
    protected String brojPopisaKodOrgana;
    @XmlElement(name = "Datum_donosenja_propisa", namespace = "aktovi", required = true)
    protected String datumDonosenjaPropisa;
    @XmlElement(name = "Mesto_donosenja_propisa", namespace = "aktovi", required = true)
    protected String mestoDonosenjaPropisa;
    @XmlElement(name = "Ovlasceno_lice", namespace = "aktovi", required = true)
    protected TOvlascenoLice ovlascenoLice;
    @XmlAttribute(name = "Usvojen")
    protected Boolean usvojen;

    /**
     * Gets the value of the preambula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreambula() {
        return preambula;
    }

    /**
     * Sets the value of the preambula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreambula(String value) {
        this.preambula = value;
    }

    /**
     * Gets the value of the naziv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Sets the value of the naziv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaziv(String value) {
        this.naziv = value;
    }

    /**
     * Gets the value of the glavniDeo property.
     * 
     * @return
     *     possible object is
     *     {@link GlavniDeo }
     *     
     */
    public GlavniDeo getGlavniDeo() {
        return glavniDeo;
    }

    /**
     * Sets the value of the glavniDeo property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlavniDeo }
     *     
     */
    public void setGlavniDeo(GlavniDeo value) {
        this.glavniDeo = value;
    }

    /**
     * Gets the value of the drzava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrzava() {
        return drzava;
    }

    /**
     * Sets the value of the drzava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrzava(String value) {
        this.drzava = value;
    }

    /**
     * Gets the value of the pokrajina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPokrajina() {
        return pokrajina;
    }

    /**
     * Sets the value of the pokrajina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPokrajina(String value) {
        this.pokrajina = value;
    }

    /**
     * Gets the value of the grad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrad() {
        return grad;
    }

    /**
     * Sets the value of the grad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrad(String value) {
        this.grad = value;
    }

    /**
     * Gets the value of the skupstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkupstina() {
        return skupstina;
    }

    /**
     * Sets the value of the skupstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkupstina(String value) {
        this.skupstina = value;
    }

    /**
     * Gets the value of the brojPopisaKodOrgana property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrojPopisaKodOrgana() {
        return brojPopisaKodOrgana;
    }

    /**
     * Sets the value of the brojPopisaKodOrgana property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrojPopisaKodOrgana(String value) {
        this.brojPopisaKodOrgana = value;
    }

    /**
     * Gets the value of the datumDonosenjaPropisa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatumDonosenjaPropisa() {
        return datumDonosenjaPropisa;
    }

    /**
     * Sets the value of the datumDonosenjaPropisa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatumDonosenjaPropisa(String value) {
        this.datumDonosenjaPropisa = value;
    }

    /**
     * Gets the value of the mestoDonosenjaPropisa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMestoDonosenjaPropisa() {
        return mestoDonosenjaPropisa;
    }

    /**
     * Sets the value of the mestoDonosenjaPropisa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMestoDonosenjaPropisa(String value) {
        this.mestoDonosenjaPropisa = value;
    }

    /**
     * Gets the value of the ovlascenoLice property.
     * 
     * @return
     *     possible object is
     *     {@link TOvlascenoLice }
     *     
     */
    public TOvlascenoLice getOvlascenoLice() {
        return ovlascenoLice;
    }

    /**
     * Sets the value of the ovlascenoLice property.
     * 
     * @param value
     *     allowed object is
     *     {@link TOvlascenoLice }
     *     
     */
    public void setOvlascenoLice(TOvlascenoLice value) {
        this.ovlascenoLice = value;
    }

    /**
     * Gets the value of the usvojen property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUsvojen() {
        return usvojen;
    }

    /**
     * Sets the value of the usvojen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUsvojen(Boolean value) {
        this.usvojen = value;
    }

}
