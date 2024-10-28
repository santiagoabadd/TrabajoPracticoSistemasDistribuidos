//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2024.10.28 a las 04:26:00 AM ART 
//


package com.unla.soap_client.service1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para itemOrdenSoap complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="itemOrdenSoap"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="codigoArticulo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="color" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="talle" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cantidadSolicitada" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itemOrdenSoap", propOrder = {
    "id",
    "codigoArticulo",
    "color",
    "talle",
    "cantidadSolicitada"
})
public class ItemOrdenSoap {

    protected long id;
    @XmlElement(required = true)
    protected String codigoArticulo;
    @XmlElement(required = true)
    protected String color;
    @XmlElement(required = true)
    protected String talle;
    protected int cantidadSolicitada;

    /**
     * Obtiene el valor de la propiedad id.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoArticulo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    /**
     * Define el valor de la propiedad codigoArticulo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoArticulo(String value) {
        this.codigoArticulo = value;
    }

    /**
     * Obtiene el valor de la propiedad color.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColor() {
        return color;
    }

    /**
     * Define el valor de la propiedad color.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * Obtiene el valor de la propiedad talle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTalle() {
        return talle;
    }

    /**
     * Define el valor de la propiedad talle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTalle(String value) {
        this.talle = value;
    }

    /**
     * Obtiene el valor de la propiedad cantidadSolicitada.
     * 
     */
    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    /**
     * Define el valor de la propiedad cantidadSolicitada.
     * 
     */
    public void setCantidadSolicitada(int value) {
        this.cantidadSolicitada = value;
    }

}
