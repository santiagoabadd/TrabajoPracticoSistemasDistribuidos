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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ordenCompraResumenSoap complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ordenCompraResumenSoap"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigoArticulo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="estado" type="{http://spring.io/guides/gs-producing-web-service}estadoOrden"/&gt;
 *         &lt;element name="codigoTienda" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="totalCantidadSolicitada" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ordenCompraResumenSoap", propOrder = {
    "codigoArticulo",
    "estado",
    "codigoTienda",
    "totalCantidadSolicitada"
})
public class OrdenCompraResumenSoap {

    @XmlElement(required = true)
    protected String codigoArticulo;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EstadoOrden estado;
    @XmlElement(required = true)
    protected String codigoTienda;
    protected int totalCantidadSolicitada;

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
     * Obtiene el valor de la propiedad totalCantidadSolicitada.
     * 
     */
    public int getTotalCantidadSolicitada() {
        return totalCantidadSolicitada;
    }

    /**
     * Define el valor de la propiedad totalCantidadSolicitada.
     * 
     */
    public void setTotalCantidadSolicitada(int value) {
        this.totalCantidadSolicitada = value;
    }

}
