
package xml.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="Stanje" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Preambula" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{aktovi}Referenca"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
 *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{aktovi}AMetadata"/>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stanje",
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
    "ovlascenoLice",
    "timestamp",
    "signature"
})
@XmlRootElement(name = "Pravni_akt", namespace = "aktovi")
public class PravniAkt {

    @XmlElement(name = "Stanje", namespace = "aktovi", required = true)
    protected String stanje;
    @XmlElement(name = "Preambula", namespace = "aktovi")
    protected PravniAkt.Preambula preambula;
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
    @XmlElement(name = "Timestamp", namespace = "aktovi", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;
    @XmlAttribute(name = "Id")
    protected Long id;
    @XmlAttribute(name = "Taksonomija")
    protected String taksonomija;
    @XmlAttribute(name = "Predlagac")
    protected String predlagac;
    @XmlAttribute(name = "Datum_objave")
    protected String datumObjave;
    @XmlAttribute(name = "Ko_je_usvojio")
    protected String koJeUsvojio;
    @XmlAttribute(name = "Za")
    protected BigInteger za;
    @XmlAttribute(name = "Protiv")
    protected BigInteger protiv;
    @XmlAttribute(name = "Suzdrzano")
    protected BigInteger suzdrzano;

    /**
     * Gets the value of the stanje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStanje() {
        return stanje;
    }

    /**
     * Sets the value of the stanje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStanje(String value) {
        this.stanje = value;
    }

    /**
     * Gets the value of the preambula property.
     * 
     * @return
     *     possible object is
     *     {@link PravniAkt.Preambula }
     *     
     */
    public PravniAkt.Preambula getPreambula() {
        return preambula;
    }

    /**
     * Sets the value of the preambula property.
     * 
     * @param value
     *     allowed object is
     *     {@link PravniAkt.Preambula }
     *     
     */
    public void setPreambula(PravniAkt.Preambula value) {
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
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignature(SignatureType value) {
        this.signature = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the taksonomija property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaksonomija() {
        return taksonomija;
    }

    /**
     * Sets the value of the taksonomija property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaksonomija(String value) {
        this.taksonomija = value;
    }

    /**
     * Gets the value of the predlagac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPredlagac() {
        return predlagac;
    }

    /**
     * Sets the value of the predlagac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPredlagac(String value) {
        this.predlagac = value;
    }

    /**
     * Gets the value of the datumObjave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatumObjave() {
        return datumObjave;
    }

    /**
     * Sets the value of the datumObjave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatumObjave(String value) {
        this.datumObjave = value;
    }

    /**
     * Gets the value of the koJeUsvojio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoJeUsvojio() {
        return koJeUsvojio;
    }

    /**
     * Sets the value of the koJeUsvojio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoJeUsvojio(String value) {
        this.koJeUsvojio = value;
    }

    /**
     * Gets the value of the za property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getZa() {
        return za;
    }

    /**
     * Sets the value of the za property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setZa(BigInteger value) {
        this.za = value;
    }

    /**
     * Gets the value of the protiv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getProtiv() {
        return protiv;
    }

    /**
     * Sets the value of the protiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setProtiv(BigInteger value) {
        this.protiv = value;
    }

    /**
     * Gets the value of the suzdrzano property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSuzdrzano() {
        return suzdrzano;
    }

    /**
     * Sets the value of the suzdrzano property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSuzdrzano(BigInteger value) {
        this.suzdrzano = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{aktovi}Referenca"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "content"
    })
    public static class Preambula {

        @XmlElementRef(name = "Referenca", namespace = "aktovi", type = JAXBElement.class, required = false)
        @XmlMixed
        protected List<Serializable> content;

        /**
         * Gets the value of the content property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the content property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContent().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link JAXBElement }{@code <}{@link TReferenca }{@code >}
         * {@link String }
         * 
         * 
         */
        public List<Serializable> getContent() {
            if (content == null) {
                content = new ArrayList<Serializable>();
            }
            return this.content;
        }

    }

}
