package org.example.entity.parsers;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.example.entity.Appointment;
import org.example.entity.AppointmentResult;
import org.example.entity.MedicalCard;
import org.example.entity.ObjectFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class JAXBParser implements XMLParser {

    private final PathUtil pathUtil = new PathUtil();

    public JAXBParser(){}

    public Appointment loadAppointment(final String xmlFileName) throws JAXBException, SAXException {
        return loadData(xmlFileName, pathUtil.getAppointmentXsdPath());
    }

    public AppointmentResult loadAppointmentResult(final String xmlFileName) throws JAXBException, SAXException {
        return loadData(xmlFileName, pathUtil.getAppointmentResultXsdPath());
    }

    public MedicalCard loadMedicalCard(final String xmlFileName) throws JAXBException, SAXException {
        return loadData(xmlFileName, pathUtil.getMedicalCardXsdPath());
    }

    public  void saveAppointment(Appointment appointment, final String xmlFileName) throws JAXBException, SAXException {
        saveData(appointment, xmlFileName, pathUtil.getAppointmentXsdPath(), Constants.APPOINTMENT_SCHEMA_LOCATION_URI, Appointment.class);
    }

    public  void saveAppointmentResult(AppointmentResult appointmentResult, final String xmlFileName) throws JAXBException, SAXException {
        saveData(appointmentResult, xmlFileName, pathUtil.getAppointmentResultXsdPath(), Constants.APPOINTMENT_RESULT_SCHEMA_LOCATION_URI, AppointmentResult.class);
    }

    public  void saveMedicalCard(MedicalCard medicalCard, final String xmlFileName) throws JAXBException, SAXException {
        saveData(medicalCard, xmlFileName, pathUtil.getMedicalCardXsdPath(), Constants.MEDICAL_CARD_SCHEMA_LOCATION_URI, MedicalCard.class);
    }


    private static <T> void saveData(T data, final String xmlFileName, final String xsdFileName, final String schemaLocationURI, Class<?> factory) throws JAXBException, SAXException {
            JAXBContext context = JAXBContext.newInstance(factory);
            Marshaller marshaller = context.createMarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            if (xsdFileName != null) {
                Schema schema = schemaFactory.newSchema(new File(xsdFileName));

                marshaller.setSchema(schema);
                marshaller.setEventHandler(validationEvent -> {
                    System.err.println("====================================");
                    System.err.println(xmlFileName + " is NOT valid against "
                            + xsdFileName + ":\n" + validationEvent.getMessage());
                    System.err.println("====================================");
                    return false;
                });
            }

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(data, new File(xmlFileName));
    }

    private static <T> T loadData(final String xmlFileName, final String xsdFileName) throws JAXBException, SAXException {
        JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        if(xsdFileName != null) {
            Schema schema = null;
            if(xsdFileName.isEmpty()) {
                schema = schemaFactory.newSchema();
            }
            else {
                schema = schemaFactory.newSchema(new File(xsdFileName));
            }

            unmarshaller.setSchema(schema);

            unmarshaller.setEventHandler(validationEvent -> {
                System.err.println("====================================");
                System.err.println(xmlFileName + " is NOT valid against "
                        + xsdFileName + ":\n" + validationEvent.getMessage());
                System.err.println("====================================");
                return false;
            });
        }

        T result = (T) unmarshaller.unmarshal(new File(xmlFileName));
        return result;
    }
}
