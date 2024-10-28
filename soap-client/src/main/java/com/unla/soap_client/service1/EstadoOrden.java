//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2024.10.27 a las 09:02:48 PM ART 
//


package com.unla.soap_client.service1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para estadoOrden.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="estadoOrden"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SOLICITADA"/&gt;
 *     &lt;enumeration value="RECHAZADA"/&gt;
 *     &lt;enumeration value="ACEPTADA"/&gt;
 *     &lt;enumeration value="RECIBIDA"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "estadoOrden")
@XmlEnum
public enum EstadoOrden {

    SOLICITADA,
    RECHAZADA,
    ACEPTADA,
    RECIBIDA;

    public String value() {
        return name();
    }

    public static EstadoOrden fromValue(String v) {
        return valueOf(v);
    }

}
