package org.example.entity.parsers;

import org.example.entity.*;
import org.example.entity.parsers.validation.XMLValidator;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class StAXLoader implements XMLLoader {
    private PathUtil pathUtil = new PathUtil();

    @Override
    public Appointment loadAppointment(String xmlFileName) throws IOException, SAXException, DatatypeConfigurationException, XMLStreamException {
        XMLValidator.validateXML(xmlFileName, pathUtil.getAppointmentXsdPath());
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(xmlFileName));
        return readAppointment(reader);
    }

    private Appointment readAppointment(XMLStreamReader reader) throws XMLStreamException, DatatypeConfigurationException {
        Appointment appointment = new Appointment();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        int tagsLeft = Constants.APPOINTMENT_TAGS_COUNT;
        while(tagsLeft > 0){
            if(reader.isStartElement()){
                switch (reader.getLocalName()) {
                    case Constants.APPOINTMENT_TAG:
                        appointment.setId(Integer.parseInt(reader.getAttributeValue(null, Constants.ID_ATTRIBUTE)));
                        break;
                    case Constants.DATE_TAG:
                        appointment.setDate(datatypeFactory.newXMLGregorianCalendar(reader.getElementText()));
                        break;
                    case Constants.START_TIME_TAG:
                        appointment.setStartTime(datatypeFactory.newXMLGregorianCalendar(reader.getElementText()));
                        break;
                    case Constants.END_TIME_TAG:
                        appointment.setEndTime(datatypeFactory.newXMLGregorianCalendar(reader.getElementText()));
                        break;
                    case Constants.PATIENT_CARD_NUMBER_TAG:
                        appointment.setPatientCardNumber(Integer.parseInt(reader.getElementText()));
                        break;
                    case Constants.ROOM_NUMBER_TAG:
                        appointment.setRoomNumber(Integer.parseInt(reader.getElementText()));
                        break;
                }
                tagsLeft--;
            }
            reader.next();
        }
        return appointment;
    }

    @Override
    public AppointmentResult loadAppointmentResult(String xmlFileName) throws IOException, SAXException, XMLStreamException, DatatypeConfigurationException {
        XMLValidator.validateXML(xmlFileName, pathUtil.getAppointmentResultXsdPath());
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(xmlFileName));
        return readAppointmentResult(reader);
    }

    private AppointmentResult readAppointmentResult(XMLStreamReader reader) throws DatatypeConfigurationException, XMLStreamException {
        AppointmentResult appointmentResult = new AppointmentResult();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        int tagsLeft = Constants.APPOINTMENT_RESULT_TAGS_COUNT;
        while (tagsLeft > 0) {
            if (reader.isStartElement()) {
                switch (reader.getLocalName()) {
                    case Constants.APPOINTMENT_RESULT_TAG:
                        appointmentResult.setId(Integer.parseInt(reader.getAttributeValue(null, Constants.ID_ATTRIBUTE)));
                        appointmentResult.setAppointmentId(Integer.parseInt(reader.getAttributeValue(null, Constants.APPOINTMENT_ID_ATTRIBUTE)));
                        break;
                    case Constants.APPOINTMENT_ID_ATTRIBUTE:
                        break;
                    case Constants.REASON_TAG:
                        appointmentResult.setReason(reader.getElementText());
                        break;
                    case Constants.ANAMNESIS_TAG:
                        appointmentResult.setAnamnesis(reader.getElementText());
                        break;
                    case Constants.OBJECTIVELY_TAG:
                        appointmentResult.setObjectively(reader.getElementText());
                        break;
                    case Constants.RADIATION_DOSE_TAG:
                        appointmentResult.setRadiationDose(BigDecimal.valueOf(Double.parseDouble(reader.getElementText())));
                        break;
                    case Constants.DIAGNOSIS_TAG:
                        appointmentResult.setDiagnosis(reader.getElementText());
                        break;
                    case Constants.PRESCRIPTION_TAG:
                        appointmentResult.setPrescription(reader.getElementText());
                        break;
                    case Constants.RECOMMENDATIONS_TAG:
                        appointmentResult.setRecommendations(reader.getElementText());
                        break;
                    case Constants.ACTIONS_TAG:
                        appointmentResult.setActions(reader.getElementText());
                        break;
                    case Constants.CONCLUSION_TAG:
                        appointmentResult.setConclusion(reader.getElementText());
                        break;
                    case Constants.APPOINTMENT_TIME_TAG:
                        appointmentResult.setAppointmentTime(datatypeFactory.newXMLGregorianCalendar(reader.getElementText()));
                        break;
                    case Constants.APPOINTMENT_DATE_TAG:
                        appointmentResult.setAppointmentDate(datatypeFactory.newXMLGregorianCalendar(reader.getElementText()));
                        break;
                    case Constants.PATIENT_CARD_NUMBER_TAG:
                        appointmentResult.setPatientCardNumber(Integer.parseInt(reader.getElementText()));
                        break;
                    case Constants.DOCTOR_ID_TAG:
                        appointmentResult.setDoctorID(Integer.parseInt(reader.getElementText()));
                        break;
                }
                tagsLeft--;
            }
            reader.next();
        }
        return appointmentResult;
    }

    @Override
    public MedicalCard loadMedicalCard(String xmlFileName) throws IOException, SAXException, XMLStreamException, DatatypeConfigurationException {
        XMLValidator.validateXML(xmlFileName, pathUtil.getMedicalCardXsdPath());
        MedicalCard medicalCard = new MedicalCard();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(xmlFileName));
        while (reader.hasNext()) {
            reader.next();
            if (reader.isStartElement()) {
                switch (reader.getLocalName()) {
                    case Constants.CARD_NUMBER_TAG:
                        medicalCard.setCardNumber(Integer.parseInt(reader.getElementText()));
                        break;
                    case Constants.PERSONAL_INFO_TAG:
                        medicalCard.setPersonalInfo(new PersonalInfo());
                        break;
                    case Constants.FIRST_NAME_TAG:
                        medicalCard.getPersonalInfo().setFirstName(reader.getElementText());
                        break;
                    case Constants.LAST_NAME_TAG:
                        medicalCard.getPersonalInfo().setLastName(reader.getElementText());
                        break;
                    case Constants.PATRONYMIC_TAG:
                        medicalCard.getPersonalInfo().setPatronymic(reader.getElementText());
                        break;
                    case Constants.BIRTH_DATE_TAG:
                        medicalCard.getPersonalInfo().setBirthDate(datatypeFactory.newXMLGregorianCalendar(reader.getElementText()));
                        break;
                    case Constants.GENDER_TAG:
                        medicalCard.getPersonalInfo().setGender(reader.getElementText());
                        break;
                    case Constants.WORKPLACE_TAG:
                        medicalCard.setWorkplace(reader.getElementText());
                        break;
                    case Constants.ADDRESS_TAG:
                        medicalCard.setAddress(reader.getElementText());
                        break;
                    case Constants.EMAIL_TAG:
                        medicalCard.setEmail(reader.getElementText());
                        break;
                    case Constants.PHONE_TAG:
                        medicalCard.setPhone(reader.getElementText());
                        break;
                    case Constants.IDENTIFICATION_TAG:
                        medicalCard.setIdentification(new Identification());
                        break;
                    case Constants.ID_CARD_NUMBER_TAG:
                        medicalCard.getIdentification().setIdCardNumber(reader.getElementText());
                        break;
                    case Constants.DRIVER_LICENSE_NUMBER_TAG:
                        medicalCard.getIdentification().setDriverLicenseNumber(reader.getElementText());
                        break;
                    case Constants.PASSPORT_NUMBER_TAG:
                        medicalCard.getIdentification().setPassportNumber(reader.getElementText());
                        break;
                    case Constants.APPOINTMENTS_TAG:
                        medicalCard.setAppointments(new MedicalCard.Appointments());
                        break;
                    case Constants.APPOINTMENT_TAG:
                        medicalCard.getAppointments().getAppointment().add(readAppointment(reader));
                        break;
                    case Constants.RESULTS_TAG:
                        medicalCard.setResults(new MedicalCard.Results());
                        break;
                    case Constants.APPOINTMENT_RESULT_TAG:
                        medicalCard.getResults().getAppointmentResult().add(readAppointmentResult(reader));
                        break;
                }
            }
        }
        return medicalCard;
    }
}
