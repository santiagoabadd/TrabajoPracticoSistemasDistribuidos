//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2024.10.28 a las 02:01:01 PM ART 
//


package com.unla.soap_client.service1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para ordenCompraSoap complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ordenCompraSoap"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="codigoTienda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="items" type="{http://spring.io/guides/gs-producing-web-service}itemOrdenSoap" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="estado" type="{http://spring.io/guides/gs-producing-web-service}estadoOrden"/&gt;
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idOrdenDespacho" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="fechaSolicitud" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="fechaRecepcion" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ordenCompraSoap", propOrder = {
    "id",
    "codigoTienda",
    "items",
    "estado",
    "observaciones",
    "idOrdenDespacho",
    "fechaSolicitud",
    "fechaRecepcion"
})
public class OrdenCompraSoap {

    protected long id;
    @XmlElement(required = true)
    protected String codigoTienda;
    protected List<ItemOrdenSoap> items;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EstadoOrden estado;
    protected String observaciones;
    protected Long idOrdenDespacho;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaSolicitud;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaRecepcion;

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
     * Obtiene el valor de la propiedad codigoTienda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoTienda() {
        return codigoTienda;
    }

    /**
     * Define el valor de la propiedad codigoTienda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoTienda(String value) {
        this.codigoTienda = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the items property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemOrdenSoap }
     * 
     * 
     */
    public List<ItemOrdenSoap> getItems() {
        if (items == null) {
            items = new ArrayList<ItemOrdenSoap>();
        }
        return this.items;
    }

    /**
     * Obtiene el valor de la propiedad estado.
     * 
     * @return
     *     possible object is
     *     {@link EstadoOrden }
     *     
     */
    public EstadoOrden getEstado() {
        return estado;
    }

    /**
     * Define el valor de la propiedad estado.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoOrden }
     *     
     */
    public void setEstado(EstadoOrden value) {
        this.estado = value;
    }

    /**
     * Obtiene el valor de la propiedad observaciones.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Define el valor de la propiedad observaciones.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservaciones(String value) {
        this.observaciones = value;
    }

    /**
     * Obtiene el valor de la propiedad idOrdenDespacho.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdOrdenDespacho() {
        return idOrdenDespacho;
    }

    /**
     * Define el valor de la propiedad idOrdenDespacho.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdOrdenDespacho(Long value) {
        this.idOrdenDespacho = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaSolicitud.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * Define el valor de la propiedad fechaSolicitud.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaSolicitud(XMLGregorianCalendar value) {
        this.fechaSolicitud = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaRecepcion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaRecepcion() {
        return fechaRecepcion;
    }

    /**
     * Define el valor de la propiedad fechaRecepcion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaRecepcion(XMLGregorianCalendar value) {
        this.fechaRecepcion = value;
    }

}
