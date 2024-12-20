
package org.example.entity;

import jakarta.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="reason" type="{http://it.nure.ua/clinic/appointmentResult}mandatoryText"/&gt;
 *         &lt;element name="anamnesis" type="{http://it.nure.ua/clinic/appointmentResult}mandatoryText"/&gt;
 *         &lt;element name="objectively" type="{http://it.nure.ua/clinic/appointmentResult}mandatoryText"/&gt;
 *         &lt;element name="radiationDose" type="{http://it.nure.ua/clinic/appointmentResult}radiationDose"/&gt;
 *         &lt;element name="diagnosis" type="{http://it.nure.ua/clinic/appointmentResult}mandatoryText"/&gt;
 *         &lt;element name="prescription" type="{http://it.nure.ua/clinic/appointmentResult}mandatoryText"/&gt;
 *         &lt;element name="recommendations" type="{http://it.nure.ua/clinic/appointmentResult}optionalText"/&gt;
 *         &lt;element name="actions" type="{http://it.nure.ua/clinic/appointmentResult}mandatoryText"/&gt;
 *         &lt;element name="conclusion" type="{http://it.nure.ua/clinic/appointmentResult}mandatoryText"/&gt;
 *         &lt;element name="appointmentTime" type="{http://www.w3.org/2001/XMLSchema}time"/&gt;
 *         &lt;element name="appointmentDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="patientCardNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="doctorID" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="appointmentId" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reason",
    "anamnesis",
    "objectively",
    "radiationDose",
    "diagnosis",
    "prescription",
    "recommendations",
    "actions",
    "conclusion",
    "appointmentTime",
    "appointmentDate",
    "patientCardNumber",
    "doctorID"
})
@XmlRootElement(name = "appointmentResult", namespace = "http://it.nure.ua/clinic/appointmentResult")
public class AppointmentResult {

    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult", required = true)
    protected String reason;
    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult", required = true)
    protected String anamnesis;
    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult", required = true)
    protected String objectively;
    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult", required = true)
    protected BigDecimal radiationDose;
    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult", required = true)
    protected String diagnosis;
    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult", required = true)
    protected String prescription;
    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult", required = true)
    protected String recommendations;
    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult", required = true)
    protected String actions;
    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult", required = true)
    protected String conclusion;
    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult", required = true)
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar appointmentTime;
    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar appointmentDate;
    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult")
    protected int patientCardNumber;
    @XmlElement(namespace = "http://it.nure.ua/clinic/appointmentResult")
    protected int doctorID;
    @XmlAttribute(name = "id", required = true)
    protected int id;
    @XmlAttribute(name = "appointmentId")
    protected Integer appointmentId;

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the anamnesis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnamnesis() {
        return anamnesis;
    }

    /**
     * Sets the value of the anamnesis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnamnesis(String value) {
        this.anamnesis = value;
    }

    /**
     * Gets the value of the objectively property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectively() {
        return objectively;
    }

    /**
     * Sets the value of the objectively property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectively(String value) {
        this.objectively = value;
    }

    /**
     * Gets the value of the radiationDose property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRadiationDose() {
        return radiationDose;
    }

    /**
     * Sets the value of the radiationDose property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRadiationDose(BigDecimal value) {
        this.radiationDose = value;
    }

    /**
     * Gets the value of the diagnosis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Sets the value of the diagnosis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiagnosis(String value) {
        this.diagnosis = value;
    }

    /**
     * Gets the value of the prescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrescription() {
        return prescription;
    }

    /**
     * Sets the value of the prescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrescription(String value) {
        this.prescription = value;
    }

    /**
     * Gets the value of the recommendations property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecommendations() {
        return recommendations;
    }

    /**
     * Sets the value of the recommendations property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecommendations(String value) {
        this.recommendations = value;
    }

    /**
     * Gets the value of the actions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActions() {
        return actions;
    }

    /**
     * Sets the value of the actions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActions(String value) {
        this.actions = value;
    }

    /**
     * Gets the value of the conclusion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConclusion() {
        return conclusion;
    }

    /**
     * Sets the value of the conclusion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConclusion(String value) {
        this.conclusion = value;
    }

    /**
     * Gets the value of the appointmentTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * Sets the value of the appointmentTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAppointmentTime(XMLGregorianCalendar value) {
        this.appointmentTime = value;
    }

    /**
     * Gets the value of the appointmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Sets the value of the appointmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAppointmentDate(XMLGregorianCalendar value) {
        this.appointmentDate = value;
    }

    /**
     * Gets the value of the patientCardNumber property.
     * 
     */
    public int getPatientCardNumber() {
        return patientCardNumber;
    }

    /**
     * Sets the value of the patientCardNumber property.
     * 
     */
    public void setPatientCardNumber(int value) {
        this.patientCardNumber = value;
    }

    /**
     * Gets the value of the doctorID property.
     * 
     */
    public int getDoctorID() {
        return doctorID;
    }

    /**
     * Sets the value of the doctorID property.
     * 
     */
    public void setDoctorID(int value) {
        this.doctorID = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the appointmentId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the value of the appointmentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAppointmentId(Integer value) {
        this.appointmentId = value;
    }

}
