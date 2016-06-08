
package xml.model;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TPredsednik_skupstine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPredsednik_skupstine">
 *   &lt;complexContent>
 *     &lt;extension base="{aktovi}TOdbornik">
 *       &lt;sequence>
 *         &lt;element name="Plata" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPredsednik_skupstine", namespace = "aktovi", propOrder = {
    "plata"
})
public class TPredsednikSkupstine
    extends TOdbornik
{

    @XmlElement(name = "Plata", namespace = "aktovi", required = true)
    protected BigDecimal plata;

    /**
     * Gets the value of the plata property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPlata() {
        return plata;
    }

    /**
     * Sets the value of the plata property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPlata(BigDecimal value) {
        this.plata = value;
    }

}
