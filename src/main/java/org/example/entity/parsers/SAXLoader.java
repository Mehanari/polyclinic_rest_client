package org.example.entity.parsers;

import org.example.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class SAXLoader implements XMLLoader {
    private final PathUtil pathUtil = new PathUtil();

    @Override
    public Appointment loadAppointment(String xmlFileName) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        return loadData(xmlFileName, pathUtil.getAppointmentXsdPath(), new AppointmentHandler());
    }

    @Override
    public AppointmentResult loadAppointmentResult(String xmlFileName) throws DatatypeConfigurationException, ParserConfigurationException, IOException, SAXException {
        return loadData(xmlFileName, pathUtil.getAppointmentResultXsdPath(), new AppointmentResultHandler());
    }

    @Override
    public MedicalCard loadMedicalCard(String xmlFileName) throws Exception {
        return loadData(xmlFileName, pathUtil.getMedicalCardXsdPath(), new MedicalCardHandler());
    }

    private <T> T loadData(String xmlFileName, String xsdFileName, DataRetriever<T> handler) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(xsdFileName));
        parserFactory.setSchema(schema);
        SAXParser parser = parserFactory.newSAXParser();
        parser.parse(new File(xmlFileName), handler);

        return handler.retrieveData();
    }

    private abstract static class DataRetriever<T> extends DefaultHandler {
        abstract T retrieveData();

        protected String removePrefix(String qName) {
            return qName.substring(qName.indexOf(':') + 1);
        }
    }

    private static class AppointmentHandler extends DataRetriever<Appointment> {
        private Appointment appointment;
        private final DatatypeFactory datatypeFactory;
        private final StringBuilder data;

        public AppointmentHandler() throws DatatypeConfigurationException {
            appointment = new Appointment();
            datatypeFactory = DatatypeFactory.newInstance();
            data = new StringBuilder();
        }

        public void reset(){
            appointment = new Appointment();
        }

        public Appointment retrieveData() {
            return appointment;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            data.setLength(0);

            if(Constants.APPOINTMENT_TAG.equals(removePrefix(qName))){
                appointment.setId(Integer.parseInt(attributes.getValue(Constants.ID_ATTRIBUTE)));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            data.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            try{
                switch (removePrefix(qName)){
                    case Constants.DATE_TAG:
                        appointment.setDate(datatypeFactory.newXMLGregorianCalendar(data.toString()));
                        break;
                    case Constants.START_TIME_TAG:
                        appointment.setStartTime(datatypeFactory.newXMLGregorianCalendar(data.toString()));
                        break;
                    case Constants.END_TIME_TAG:
                        appointment.setEndTime(datatypeFactory.newXMLGregorianCalendar(data.toString()));
                        break;
                    case  Constants.ROOM_NUMBER_TAG:
                        appointment.setRoomNumber(Integer.parseInt(data.toString()));
                        break;
                    case Constants.PATIENT_CARD_NUMBER_TAG:
                        appointment.setPatientCardNumber(Integer.parseInt(data.toString()));
                        break;
                }
            }
            catch (Exception e){
                throw new SAXException("Error parsing element: " + qName, e);
            }
        }
    }

    private static class AppointmentResultHandler extends DataRetriever<AppointmentResult> {
        private AppointmentResult appointmentResult;
        private final DatatypeFactory datatypeFactory;
        private final StringBuilder data;

        public AppointmentResultHandler() throws DatatypeConfigurationException {
            appointmentResult = new AppointmentResult();
            datatypeFactory = DatatypeFactory.newInstance();
            data = new StringBuilder();
        }

        public AppointmentResult retrieveData() {
            return appointmentResult;
        }

        public void reset(){
            appointmentResult = new AppointmentResult();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            data.setLength(0);

            if(Constants.APPOINTMENT_RESULT_TAG.equals(removePrefix(qName))){
                appointmentResult.setAppointmentId(Integer.parseInt(attributes.getValue(Constants.APPOINTMENT_ID_ATTRIBUTE)));
                appointmentResult.setId(Integer.parseInt(attributes.getValue(Constants.ID_ATTRIBUTE)));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            data.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            try{
                switch (removePrefix(qName)){
                    case Constants.REASON_TAG:
                        appointmentResult.setReason(data.toString());
                        break;
                    case Constants.ANAMNESIS_TAG:
                        appointmentResult.setAnamnesis(data.toString());
                        break;
                    case Constants.OBJECTIVELY_TAG:
                        appointmentResult.setObjectively(data.toString());
                        break;
                    case Constants.RADIATION_DOSE_TAG:
                        appointmentResult.setRadiationDose(BigDecimal.valueOf(Double.parseDouble(data.toString())));
                        break;
                    case Constants.DIAGNOSIS_TAG:
                        appointmentResult.setDiagnosis(data.toString());
                        break;
                    case Constants.PRESCRIPTION_TAG:
                        appointmentResult.setPrescription(data.toString());
                        break;
                    case Constants.RECOMMENDATIONS_TAG:
                        appointmentResult.setRecommendations(data.toString());
                        break;
                    case Constants.ACTIONS_TAG:
                        appointmentResult.setActions(data.toString());
                        break;
                    case Constants.CONCLUSION_TAG:
                        appointmentResult.setConclusion(data.toString());
                        break;
                    case Constants.APPOINTMENT_TIME_TAG:
                        appointmentResult.setAppointmentTime(datatypeFactory.newXMLGregorianCalendar(data.toString()));
                        break;
                    case Constants.APPOINTMENT_DATE_TAG:
                        appointmentResult.setAppointmentDate(datatypeFactory.newXMLGregorianCalendar(data.toString()));
                        break;
                    case Constants.PATIENT_CARD_NUMBER_TAG:
                        appointmentResult.setPatientCardNumber(Integer.parseInt(data.toString()));
                        break;
                    case Constants.DOCTOR_ID_TAG:
                        appointmentResult.setDoctorID(Integer.parseInt(data.toString()));
                        break;
                }
            }
            catch (Exception e){
                throw new SAXException("Error parsing element: " + qName, e);
            }
        }
    }

    private static class MedicalCardHandler extends DataRetriever<MedicalCard>{
        private MedicalCard medicalCard;
        private final StringBuilder data;
        private final DatatypeFactory datatypeFactory;
        private final AppointmentHandler appointmentHandler;
        private final AppointmentResultHandler appointmentResultHandler;
        private boolean insideAppointment;
        private boolean insideAppointmentResult;

        public MedicalCardHandler() throws DatatypeConfigurationException {
            medicalCard = new MedicalCard();
            data = new StringBuilder();
            datatypeFactory = DatatypeFactory.newInstance();
            appointmentHandler = new AppointmentHandler();
            appointmentResultHandler = new AppointmentResultHandler();
            insideAppointment = false;
            insideAppointmentResult = false;
        }

        public void reset(){
            medicalCard = new MedicalCard();
            insideAppointment = false;
            insideAppointmentResult = false;
            appointmentHandler.reset();
            appointmentResultHandler.reset();
        }

        @Override
        MedicalCard retrieveData() {
            return medicalCard;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            data.setLength(0);

            if (insideAppointment){
                appointmentHandler.startElement(uri, localName, qName, attributes);
                return;
            }
            else if(insideAppointmentResult){
                appointmentResultHandler.startElement(uri, localName, qName, attributes);
                return;
            }

            if(Constants.APPOINTMENTS_TAG.equals(removePrefix(qName))){
                medicalCard.setAppointments(new MedicalCard.Appointments());
            }
            else if (Constants.RESULTS_TAG.equals(removePrefix(qName))){
                medicalCard.setResults(new MedicalCard.Results());
            }
            else if (Constants.PERSONAL_INFO_TAG.equals(removePrefix(qName))){
                medicalCard.setPersonalInfo(new PersonalInfo());
            }
            else if (Constants.IDENTIFICATION_TAG.equals(removePrefix(qName))){
                medicalCard.setIdentification(new Identification());
            }
            else if(Constants.APPOINTMENT_TAG.equals(removePrefix(qName))){
                insideAppointment = true;
                appointmentHandler.startElement(uri, localName, qName, attributes);
            }
            else if(Constants.APPOINTMENT_RESULT_TAG.equals(removePrefix(qName))){
                insideAppointmentResult = true;
                appointmentResultHandler.startElement(uri, localName, qName, attributes);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (insideAppointment){
                appointmentHandler.characters(ch, start, length);
                return;
            }
            else if(insideAppointmentResult){
                appointmentResultHandler.characters(ch, start, length);
                return;
            }
            data.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (insideAppointment){
                appointmentHandler.endElement(uri, localName, qName);
                if(Constants.APPOINTMENT_TAG.equals(removePrefix(qName))){
                    medicalCard.getAppointments().getAppointment().add(appointmentHandler.retrieveData());
                    insideAppointment = false;
                    appointmentHandler.reset();
                }
                return;
            }
            else if(insideAppointmentResult){
                appointmentResultHandler.endElement(uri, localName, qName);
                if(Constants.APPOINTMENT_RESULT_TAG.equals(removePrefix(qName))){
                    medicalCard.getResults().getAppointmentResult().add(appointmentResultHandler.retrieveData());
                    insideAppointmentResult = false;
                    appointmentResultHandler.reset();
                }
                return;
            }

            try{
                switch (removePrefix(qName)){
                    case Constants.CARD_NUMBER_TAG:
                        medicalCard.setCardNumber(Integer.parseInt(data.toString()));
                        break;
                    case Constants.FIRST_NAME_TAG:
                        medicalCard.getPersonalInfo().setFirstName(data.toString());
                        break;
                    case Constants.LAST_NAME_TAG:
                        medicalCard.getPersonalInfo().setLastName(data.toString());
                        break;
                    case Constants.PATRONYMIC_TAG:
                        medicalCard.getPersonalInfo().setPatronymic(data.toString());
                        break;
                    case Constants.BIRTH_DATE_TAG:
                        medicalCard.getPersonalInfo().setBirthDate(datatypeFactory.newXMLGregorianCalendar(data.toString()));
                        break;
                    case Constants.GENDER_TAG:
                        medicalCard.getPersonalInfo().setGender(data.toString());
                        break;
                    case Constants.WORKPLACE_TAG:
                        medicalCard.setWorkplace(data.toString());
                        break;
                    case Constants.ADDRESS_TAG:
                        medicalCard.setAddress(data.toString());
                        break;
                    case Constants.EMAIL_TAG:
                        medicalCard.setEmail(data.toString());
                        break;
                    case Constants.PHONE_TAG:
                        medicalCard.setPhone(data.toString());
                        break;
                    case Constants.ID_CARD_NUMBER_TAG:
                        medicalCard.getIdentification().setIdCardNumber(data.toString());
                        break;
                    case Constants.DRIVER_LICENSE_NUMBER_TAG:
                        medicalCard.getIdentification().setDriverLicenseNumber(data.toString());
                        break;
                    case Constants.PASSPORT_NUMBER_TAG:
                        medicalCard.getIdentification().setPassportNumber(data.toString());
                        break;
                }
            }
            catch (Exception e){
                throw new SAXException("Error parsing element: " + qName, e);
            }
        }
    }
}
