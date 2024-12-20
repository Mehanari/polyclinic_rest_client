
package org.example.entity;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the polyclinic package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: polyclinic
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MedicalCard }
     * 
     */
    public MedicalCard createMedicalCard() {
        return new MedicalCard();
    }

    /**
     * Create an instance of {@link PersonalInfo }
     * 
     */
    public PersonalInfo createPersonalInfo() {
        return new PersonalInfo();
    }

    /**
     * Create an instance of {@link Identification }
     * 
     */
    public Identification createIdentification() {
        return new Identification();
    }

    /**
     * Create an instance of {@link MedicalCard.Appointments }
     * 
     */
    public MedicalCard.Appointments createMedicalCardAppointments() {
        return new MedicalCard.Appointments();
    }

    /**
     * Create an instance of {@link MedicalCard.Results }
     * 
     */
    public MedicalCard.Results createMedicalCardResults() {
        return new MedicalCard.Results();
    }

    /**
     * Create an instance of {@link Appointment }
     * 
     */
    public Appointment createAppointment() {
        return new Appointment();
    }

    /**
     * Create an instance of {@link AppointmentResult }
     * 
     */
    public AppointmentResult createAppointmentResult() {
        return new AppointmentResult();
    }

}
