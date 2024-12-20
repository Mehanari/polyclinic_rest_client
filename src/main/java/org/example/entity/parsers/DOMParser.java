package org.example.entity.parsers;

import org.example.entity.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public class DOMParser implements XMLParser {

    private final PathUtil pathUtil = new PathUtil();


    @Override
    public Appointment loadAppointment(String xmlFileName) throws IOException, SAXException, ParserConfigurationException, DatatypeConfigurationException {
        Element root = getRootElementOfXmlFile(xmlFileName, pathUtil.getAppointmentXsdPath());
        return parseAppointment(root);
    }

    @Override
    public void saveAppointment(Appointment appointment, String xmlFileName) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Document document = createDocument();
        addAppointment(appointment, document, document, false);
        validateDocument(document, pathUtil.getAppointmentXsdPath());
        saveDocumentToFile(document, xmlFileName);
    }

    @Override
    public AppointmentResult loadAppointmentResult(String xmlFileName) throws SAXException, IOException, ParserConfigurationException, DatatypeConfigurationException {
        Element root = getRootElementOfXmlFile(xmlFileName, pathUtil.getAppointmentResultXsdPath());
        return parseAppointmentResult(root);
    }

    @Override
    public void saveAppointmentResult(AppointmentResult appointmentResult, String xmlFileName) throws SAXException, ParserConfigurationException, IOException, TransformerException {
        Document document = createDocument();
        addAppointmentResult(appointmentResult, document, document, false);
        validateDocument(document, pathUtil.getAppointmentResultXsdPath());
        saveDocumentToFile(document, xmlFileName);
    }

    @Override
    public MedicalCard loadMedicalCard(String xmlFileName) throws SAXException, IOException, ParserConfigurationException, DatatypeConfigurationException {
        Element root = getRootElementOfXmlFile(xmlFileName, pathUtil.getMedicalCardXsdPath());
        return parseMedicalCard(root);
    }

    @Override
    public void saveMedicalCard(MedicalCard medicalCard, String xmlFileName) throws SAXException, ParserConfigurationException, IOException, TransformerException {
        Document document = createDocument();
        addMedicalCard(medicalCard, document, document);
        validateDocument(document, pathUtil.getMedicalCardXsdPath());
        saveDocumentToFile(document, xmlFileName);
    }

    private Appointment parseAppointment(Node root) throws DatatypeConfigurationException {
        Appointment appointment = new Appointment();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        appointment.setId(Integer.parseInt(root.getAttributes().getNamedItem(Constants.ID_ATTRIBUTE).getNodeValue()));
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (Constants.DATE_TAG.equals(node.getLocalName())){
                appointment.setDate(datatypeFactory.newXMLGregorianCalendar(node.getTextContent()));
            }
            else if (Constants.START_TIME_TAG.equals(node.getLocalName())){
                appointment.setStartTime(datatypeFactory.newXMLGregorianCalendar(node.getTextContent()));
            }
            else if (Constants.END_TIME_TAG.equals(node.getLocalName())){
                appointment.setEndTime(datatypeFactory.newXMLGregorianCalendar(node.getTextContent()));
            }
            else if (Constants.PATIENT_CARD_NUMBER_TAG.equals(node.getLocalName())){
                appointment.setPatientCardNumber(Integer.parseInt(node.getTextContent()));
            }
            else if (Constants.ROOM_NUMBER_TAG.equals(node.getLocalName())){
                appointment.setRoomNumber(Integer.parseInt(node.getTextContent()));
            }
        }
        return appointment;
    }

    private void addAppointment(Appointment appointment, Node root, Document doc, boolean usePrefix) {
        String prefix = usePrefix ? Constants.APPOINTMENT_NAMESPACE_PREFIX : null;
        Element appointmentRoot = doc.createElementNS(Constants.APPOINTMENT_NAMESPACE_URI, Constants.APPOINTMENT_TAG);
        appointmentRoot.setPrefix(prefix);
        appointmentRoot.setAttribute(Constants.ID_ATTRIBUTE, String.valueOf(appointment.getId()));
        root.appendChild(appointmentRoot);
        createElement(doc, appointmentRoot, Constants.DATE_TAG, appointment.getDate(), Constants.APPOINTMENT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentRoot, Constants.START_TIME_TAG, appointment.getStartTime(), Constants.APPOINTMENT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentRoot, Constants.END_TIME_TAG, appointment.getEndTime(), Constants.APPOINTMENT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentRoot, Constants.PATIENT_CARD_NUMBER_TAG, String.valueOf(appointment.getPatientCardNumber()), Constants.APPOINTMENT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentRoot, Constants.ROOM_NUMBER_TAG, String.valueOf(appointment.getRoomNumber()), Constants.APPOINTMENT_NAMESPACE_URI, prefix);
    }

    private AppointmentResult parseAppointmentResult(Node root) throws DatatypeConfigurationException {
        AppointmentResult appointmentResult = new AppointmentResult();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        appointmentResult.setId(Integer.parseInt(root.getAttributes().getNamedItem(Constants.ID_ATTRIBUTE).getNodeValue()));
        appointmentResult.setAppointmentId(Integer.parseInt(root.getAttributes().getNamedItem(Constants.APPOINTMENT_ID_ATTRIBUTE).getNodeValue()));
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (Constants.REASON_TAG.equals(node.getLocalName())){
                appointmentResult.setReason(node.getTextContent());
            }
            else if (Constants.ANAMNESIS_TAG.equals(node.getLocalName())){
                appointmentResult.setAnamnesis(node.getTextContent());
            }
            else if (Constants.OBJECTIVELY_TAG.equals(node.getLocalName())){
                appointmentResult.setObjectively(node.getTextContent());
            }
            else if (Constants.RADIATION_DOSE_TAG.equals(node.getLocalName())){
                appointmentResult.setRadiationDose(BigDecimal.valueOf(Double.parseDouble(node.getTextContent())));
            }
            else if (Constants.DIAGNOSIS_TAG.equals(node.getLocalName())){
                appointmentResult.setDiagnosis(node.getTextContent());
            }
            else if (Constants.PRESCRIPTION_TAG.equals(node.getLocalName())){
                appointmentResult.setPrescription(node.getTextContent());
            }
            else if (Constants.RECOMMENDATIONS_TAG.equals(node.getLocalName())){
                appointmentResult.setRecommendations(node.getTextContent());
            }
            else if (Constants.ACTIONS_TAG.equals(node.getLocalName())){
                appointmentResult.setActions(node.getTextContent());
            }
            else if (Constants.CONCLUSION_TAG.equals(node.getLocalName())){
                appointmentResult.setConclusion(node.getTextContent());
            }
            else if (Constants.APPOINTMENT_TIME_TAG.equals(node.getLocalName())){
                appointmentResult.setAppointmentTime(datatypeFactory.newXMLGregorianCalendar(node.getTextContent()));
            }
            else if (Constants.APPOINTMENT_DATE_TAG.equals(node.getLocalName())){
                appointmentResult.setAppointmentDate(datatypeFactory.newXMLGregorianCalendar(node.getTextContent()));
            }
            else if (Constants.PATIENT_CARD_NUMBER_TAG.equals(node.getLocalName())){
                appointmentResult.setPatientCardNumber(Integer.parseInt(node.getTextContent()));
            }
            else if (Constants.DOCTOR_ID_TAG.equals(node.getLocalName())){
                appointmentResult.setDoctorID(Integer.parseInt(node.getTextContent()));
            }
        }

        return appointmentResult;
    }

    private void addAppointmentResult(AppointmentResult appointmentResult, Node root, Document doc, boolean usePrefix) {
        String prefix = usePrefix ? Constants.APPOINTMENT_RESULT_NAMESPACE_PREFIX : null;
        Element appointmentResultRoot = doc.createElementNS(Constants.APPOINTMENT_RESULT_NAMESPACE_URI, Constants.APPOINTMENT_RESULT_TAG);
        appointmentResultRoot.setPrefix(prefix);
        appointmentResultRoot.setAttribute(Constants.ID_ATTRIBUTE, String.valueOf(appointmentResult.getId()));
        appointmentResultRoot.setAttribute(Constants.APPOINTMENT_ID_ATTRIBUTE, String.valueOf(appointmentResult.getAppointmentId()));
        root.appendChild(appointmentResultRoot);
        createElement(doc, appointmentResultRoot, Constants.REASON_TAG, appointmentResult.getReason(), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentResultRoot, Constants.ANAMNESIS_TAG, appointmentResult.getAnamnesis(), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentResultRoot, Constants.OBJECTIVELY_TAG, appointmentResult.getObjectively(), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentResultRoot, Constants.RADIATION_DOSE_TAG, appointmentResult.getRadiationDose().toString(), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentResultRoot, Constants.DIAGNOSIS_TAG, appointmentResult.getDiagnosis(), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentResultRoot, Constants.PRESCRIPTION_TAG, appointmentResult.getPrescription(), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentResultRoot, Constants.RECOMMENDATIONS_TAG, appointmentResult.getRecommendations(), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentResultRoot, Constants.ACTIONS_TAG, appointmentResult.getActions(), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentResultRoot, Constants.CONCLUSION_TAG, appointmentResult.getConclusion(), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentResultRoot, Constants.APPOINTMENT_TIME_TAG, appointmentResult.getAppointmentTime(), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentResultRoot, Constants.APPOINTMENT_DATE_TAG, appointmentResult.getAppointmentDate(), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentResultRoot, Constants.PATIENT_CARD_NUMBER_TAG, String.valueOf(appointmentResult.getPatientCardNumber()), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
        createElement(doc, appointmentResultRoot, Constants.DOCTOR_ID_TAG, String.valueOf(appointmentResult.getDoctorID()), Constants.APPOINTMENT_RESULT_NAMESPACE_URI, prefix);
    }

    private MedicalCard parseMedicalCard(Node root) throws DatatypeConfigurationException {
        MedicalCard medicalCard = new MedicalCard();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (Constants.CARD_NUMBER_TAG.equals(node.getLocalName())) {
                medicalCard.setCardNumber(Integer.parseInt(node.getTextContent()));
            }
            else if (Constants.PERSONAL_INFO_TAG.equals(node.getLocalName())) {
                medicalCard.setPersonalInfo(parsePersonalInfo(node));
            }
            else if(Constants.WORKPLACE_TAG.equals(node.getLocalName())){
                medicalCard.setWorkplace(node.getTextContent());
            }
            else if (Constants.ADDRESS_TAG.equals(node.getLocalName())) {
                medicalCard.setAddress(node.getTextContent());
            }
            else if (Constants.EMAIL_TAG.equals(node.getLocalName())) {
                medicalCard.setEmail(node.getTextContent());
            }
            else if (Constants.PHONE_TAG.equals(node.getLocalName())) {
                medicalCard.setPhone(node.getTextContent());
            }
            else if (Constants.IDENTIFICATION_TAG.equals(node.getLocalName())) {
                medicalCard.setIdentification(parseIdentification(node));
            }
            else if (Constants.APPOINTMENTS_TAG.equals(node.getLocalName())) {
                medicalCard.setAppointments(parseAppointments(node));
            }
            else if (Constants.RESULTS_TAG.equals(node.getLocalName())) {
                medicalCard.setResults(parseResults(node));
            }
        }
        return medicalCard;
    }

    private PersonalInfo parsePersonalInfo(Node root) throws DatatypeConfigurationException {
        PersonalInfo personalInfo = new PersonalInfo();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (Constants.FIRST_NAME_TAG.equals(node.getLocalName())) {
                personalInfo.setFirstName(node.getTextContent());
            } else if (Constants.LAST_NAME_TAG.equals(node.getLocalName())) {
                personalInfo.setLastName(node.getTextContent());
            } else if (Constants.PATRONYMIC_TAG.equals(node.getLocalName())) {
                personalInfo.setPatronymic(node.getTextContent());
            } else if (Constants.BIRTH_DATE_TAG.equals(node.getLocalName())) {
                personalInfo.setBirthDate(datatypeFactory.newXMLGregorianCalendar(node.getTextContent()));
            } else if (Constants.GENDER_TAG.equals(node.getLocalName())) {
                personalInfo.setGender(node.getTextContent());
            }
        }
        return personalInfo;
    }

    private Identification parseIdentification(Node root){
        Identification identification = new Identification();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (Constants.ID_CARD_NUMBER_TAG.equals(node.getLocalName())) {
                identification.setIdCardNumber(node.getTextContent());
            }
            else if (Constants.PASSPORT_NUMBER_TAG.equals(node.getLocalName())) {
                identification.setPassportNumber(node.getTextContent());
            }
            else if (Constants.DRIVER_LICENSE_NUMBER_TAG.equals(node.getLocalName())) {
                identification.setDriverLicenseNumber(node.getTextContent());
            }
        }
        return identification;
    }

    private MedicalCard.Appointments parseAppointments(Node root) throws DatatypeConfigurationException {
        MedicalCard.Appointments appointments = new MedicalCard.Appointments();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (Constants.APPOINTMENT_TAG.equals(node.getLocalName())) {
                appointments.getAppointment().add(parseAppointment(node));
            }
        }
        return appointments;
    }

    private MedicalCard.Results parseResults(Node root) throws DatatypeConfigurationException {
        MedicalCard.Results results = new MedicalCard.Results();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (Constants.APPOINTMENT_RESULT_TAG.equals(node.getLocalName())) {
                results.getAppointmentResult().add(parseAppointmentResult(node));
            }
        }
        return results;
    }

    private void addMedicalCard(MedicalCard medicalCard, Node root, Document doc){
        Element medicalCardRoot = doc.createElementNS(Constants.MEDICAL_CARD_NAMESPACE_URI, Constants.MEDICAL_CARD_TAG);
        medicalCardRoot.setAttribute("xmlns:" + Constants.APPOINTMENT_NAMESPACE_PREFIX, Constants.APPOINTMENT_NAMESPACE_URI);
        medicalCardRoot.setAttribute("xmlns:" + Constants.APPOINTMENT_RESULT_NAMESPACE_PREFIX, Constants.APPOINTMENT_RESULT_NAMESPACE_URI);
        root.appendChild(medicalCardRoot);
        createElement(doc, medicalCardRoot, Constants.CARD_NUMBER_TAG, String.valueOf(medicalCard.getCardNumber()), Constants.MEDICAL_CARD_NAMESPACE_URI);
        addPersonalInfo(medicalCard.getPersonalInfo(), medicalCardRoot, doc);
        createElement(doc, medicalCardRoot, Constants.WORKPLACE_TAG, medicalCard.getWorkplace(), Constants.MEDICAL_CARD_NAMESPACE_URI);
        createElement(doc, medicalCardRoot, Constants.ADDRESS_TAG, medicalCard.getAddress(), Constants.MEDICAL_CARD_NAMESPACE_URI);
        createElement(doc, medicalCardRoot, Constants.EMAIL_TAG, medicalCard.getEmail(), Constants.MEDICAL_CARD_NAMESPACE_URI);
        createElement(doc, medicalCardRoot, Constants.PHONE_TAG, medicalCard.getPhone(), Constants.MEDICAL_CARD_NAMESPACE_URI);
        addIdentification(medicalCard.getIdentification(), medicalCardRoot, doc);
        addAppointments(medicalCard.getAppointments(), medicalCardRoot, doc);
        addResults(medicalCard.getResults(), medicalCardRoot, doc);
    }

    private void addPersonalInfo(PersonalInfo personalInfo, Node root, Document doc){
        Element personalInfoRoot = doc.createElementNS(Constants.MEDICAL_CARD_NAMESPACE_URI, Constants.PERSONAL_INFO_TAG);
        root.appendChild(personalInfoRoot);
        createElement(doc, personalInfoRoot, Constants.FIRST_NAME_TAG, personalInfo.getFirstName(), Constants.MEDICAL_CARD_NAMESPACE_URI);
        createElement(doc, personalInfoRoot, Constants.LAST_NAME_TAG, personalInfo.getLastName(), Constants.MEDICAL_CARD_NAMESPACE_URI);
        createElement(doc, personalInfoRoot, Constants.PATRONYMIC_TAG, personalInfo.getPatronymic(), Constants.MEDICAL_CARD_NAMESPACE_URI);
        createElement(doc, personalInfoRoot, Constants.BIRTH_DATE_TAG, personalInfo.getBirthDate(), Constants.MEDICAL_CARD_NAMESPACE_URI);
        createElement(doc, personalInfoRoot, Constants.GENDER_TAG, personalInfo.getGender(), Constants.MEDICAL_CARD_NAMESPACE_URI);
    }

    private void addIdentification(Identification identification, Node root, Document doc){
        Element identificationRoot = doc.createElementNS(Constants.MEDICAL_CARD_NAMESPACE_URI, Constants.IDENTIFICATION_TAG);
        root.appendChild(identificationRoot);
        if (identification.getDriverLicenseNumber() != null){
            createElement(doc, identificationRoot, Constants.DRIVER_LICENSE_NUMBER_TAG, identification.getDriverLicenseNumber(), Constants.MEDICAL_CARD_NAMESPACE_URI);
        }
        else if (identification.getIdCardNumber() != null){
            createElement(doc, identificationRoot, Constants.ID_CARD_NUMBER_TAG, identification.getIdCardNumber(), Constants.MEDICAL_CARD_NAMESPACE_URI);
        }
        else if (identification.getPassportNumber() != null){
            createElement(doc, identificationRoot, Constants.PASSPORT_NUMBER_TAG, identification.getPassportNumber(), Constants.MEDICAL_CARD_NAMESPACE_URI);
        }
    }

    private void addAppointments(MedicalCard.Appointments appointments, Node root, Document doc){
        Element appointmentsRoot = doc.createElementNS(Constants.MEDICAL_CARD_NAMESPACE_URI, Constants.APPOINTMENTS_TAG);
        root.appendChild(appointmentsRoot);
        for(Appointment appointment : appointments.getAppointment()){
            addAppointment(appointment, appointmentsRoot, doc, true);
        }
    }

    private void addResults(MedicalCard.Results results, Node root, Document doc){
        Element resultsRoot = doc.createElementNS(Constants.MEDICAL_CARD_NAMESPACE_URI, Constants.RESULTS_TAG);
        root.appendChild(resultsRoot);
        for(AppointmentResult appointmentResult : results.getAppointmentResult()){
            addAppointmentResult(appointmentResult, resultsRoot, doc, true);
        }
    }

    private Element getRootElementOfXmlFile(String xmlFileName, String xsdSchemaLocation) throws IOException, SAXException, ParserConfigurationException {
        InputStream in = new FileInputStream(xmlFileName);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(xsdSchemaLocation));

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setSchema(schema);

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        documentBuilder.setErrorHandler(new DefaultHandler() {
            @Override
            public void error(SAXParseException e) throws SAXException {
                System.err.println(e.getMessage());
                throw e;
            }
        });

        Document document = documentBuilder.parse(in);
        return document.getDocumentElement();
    }

    private Document createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        return documentBuilder.newDocument();
    }

    private void createElement(Document doc, Element parent, String tagName, XMLGregorianCalendar value, final String namespaceURI){
        createElement(doc, parent, tagName, value, namespaceURI, null);
    }

    private void createElement(Document doc, Element parent, String tagName, XMLGregorianCalendar value, final String namespaceURI, final String prefix) {
        if (value != null) {
            Element element = doc.createElementNS(namespaceURI, tagName);
            element.setTextContent(value.toXMLFormat());
            element.setPrefix(prefix);
            parent.appendChild(element);
        }
    }

    private void createElement(Document doc, Element parent, String tagName, String value, final String namespaceURI){
        createElement(doc, parent, tagName, value, namespaceURI, null);
    }

    private void createElement(Document doc, Element parent, String tagName, String value, final String namespaceURI, final String prefix) {
        Element element = doc.createElementNS(namespaceURI, tagName);
        element.setTextContent(value);
        element.setPrefix(prefix);
        parent.appendChild(element);
    }

    private void saveDocumentToFile(Document document, final String xmlFileName) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(xmlFileName));
        transformer.transform(source, result);
    }

    private void validateDocument(Document document, String xsdSchemaLocation) throws SAXException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(xsdSchemaLocation));
        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
    }
}
