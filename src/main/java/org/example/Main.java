package org.example;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.example.entity.*;

import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

public class Main {
    private static final URI BASE_URI = getBaseURI(Constants.BASE_URI, Constants.PORT, Constants.APPLICATION_PATH, Constants.MEDICAL_CARDS_CONTROLLER_PATH);

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient()
                .register(JAXBContextProvider.class);

        WebTarget target = client.target(BASE_URI);

        checkCardDoesNotExistError(target);
        addMedicalCard(target);
        addMedicalCard(target);
        requestCardsXML(target);
        requestCardsJSON(target);
        requestCardsAsObjectsFromXML(target);
        requestCardsAsObjectsFromJSON(target);
        checkAppointmentDoesNotExistError(target);
        addAppointment(target, 111);
        addAppointment(target, 222);
        addAppointment(target, 113);
        updateAppointment(target, 1, 333);
        getAppointments(target, 0);
        getAppointments(target, 222);
        deleteAppointment(target, 1);
        getAppointments(target, 0);
        checkAppointmentResultDoesNotExistError(target);
        addAppointmentResult(target);
        updateAppointmentResult(target);
        requestCardsAsObjectsFromJSON(target);
    }

    private static void checkCardDoesNotExistError(WebTarget target){
        System.out.println("----------------------------------------");
        System.out.println("CHECKING CARD DOES NOT EXISTS ERROR");
        Invocation.Builder request = target.path("12345").request().accept(MediaType.APPLICATION_JSON);
        Response response = request.get();
        System.out.println(response.readEntity(String.class));
    }

    private static void addMedicalCard(WebTarget target){
        System.out.println("----------------------------------------");
        System.out.println("ADDING MEDICAL CARD");
        MedicalCard newMedicalCard = getDummyMedicalCard();
        Invocation.Builder request = target.request(MediaType.APPLICATION_XML);
        Response response = request.post(Entity.entity(newMedicalCard, MediaType.APPLICATION_XML));
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            System.out.println("Medical card added successfully");
            MedicalCard addedCard = response.readEntity(MedicalCard.class);
            printMedicalCard(addedCard);
        }
        else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    private static void checkAppointmentDoesNotExistError(WebTarget target){
        System.out.println("----------------------------------------");
        System.out.println("CHECKING APPOINTMENT DOES NOT EXISTS ERROR");
        Appointment newAppointment = new Appointment();
        newAppointment.setPatientCardNumber(1);
        Invocation.Builder request = target.path("1/appointment").request(MediaType.APPLICATION_JSON);
        Response response = request.put(Entity.entity(newAppointment, MediaType.APPLICATION_JSON));
        System.out.println(response.readEntity(String.class));
    }

    private static void addAppointment(WebTarget target, int cabinetNumber){
        System.out.println("----------------------------------------");
        System.out.println("ADDING APPOINTMENT");
        Appointment newAppointment = getDummyAppointment();
        newAppointment.setPatientCardNumber(1);
        newAppointment.setRoomNumber(cabinetNumber);
        Invocation.Builder request = target.path("1/appointment").request(MediaType.APPLICATION_JSON);
        Response response = request.post(Entity.entity(newAppointment, MediaType.APPLICATION_JSON));
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            System.out.println("Appointment added successfully");
            int addedAppointmentId = response.readEntity(int.class);
            System.out.println("Appointment id: " + addedAppointmentId);
        }
        else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    private static void updateAppointment(WebTarget target, int id, int newRoomNumber){
        System.out.println("----------------------------------------");
        System.out.println("UPDATING APPOINTMENT");
        Appointment newAppointment = new Appointment();
        newAppointment.setId(id);
        newAppointment.setPatientCardNumber(1);
        newAppointment.setRoomNumber(newRoomNumber);
        Invocation.Builder request = target.path("1/appointment").request(MediaType.APPLICATION_JSON);
        Response response = request.put(Entity.entity(newAppointment, MediaType.APPLICATION_JSON));
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            System.out.println("Appointment updated successfully");
            Appointment updatedAppointment = response.readEntity(Appointment.class);
            printAppointment(updatedAppointment);
        }
        else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    private static void getAppointments(WebTarget target, int roomNumber){
        System.out.println("----------------------------------------");
        System.out.println("GETTING APPOINTMENTS");
        Invocation.Builder request = target.path("1/appointments").queryParam("roomNumber", roomNumber).request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            System.out.println("Appointments:");
            List<Appointment> appointments = response.readEntity(new GenericType<List<Appointment>>() {});
            for (Appointment appointment : appointments) {
                printAppointment(appointment);
            }
        }
        else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    private static void deleteAppointment(WebTarget target, int appointmentId){
        System.out.println("----------------------------------------");
        System.out.println("DELETING APPOINTMENT");
        Invocation.Builder request = target.path("1/appointment/" + appointmentId).request(MediaType.APPLICATION_JSON);
        Response response = request.delete();
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            System.out.println("Appointment deleted successfully");
            Appointment deletedAppointment = response.readEntity(Appointment.class);
            printAppointment(deletedAppointment);
        }
        else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    private static void checkAppointmentResultDoesNotExistError(WebTarget target){
        System.out.println("----------------------------------------");
        System.out.println("CHECKING APPOINTMENT RESULT DOES NOT EXISTS ERROR");
        AppointmentResult newAppointmentResult = getDummyAppointmentResult();
        newAppointmentResult.setAppointmentId(1);
        Invocation.Builder request = target.path("1/appointmentResult").request(MediaType.APPLICATION_JSON);
        Response response = request.put(Entity.entity(newAppointmentResult, MediaType.APPLICATION_JSON));
        System.out.println(response.readEntity(String.class));
    }

    private static void addAppointmentResult(WebTarget target){
        System.out.println("----------------------------------------");
        System.out.println("ADDING APPOINTMENT RESULT");
        AppointmentResult newAppointmentResult = getDummyAppointmentResult();
        newAppointmentResult.setPatientCardNumber(1);
        Invocation.Builder request = target.path("1/appointmentResult").request(MediaType.APPLICATION_JSON);
        Response response = request.post(Entity.entity(newAppointmentResult, MediaType.APPLICATION_JSON));
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            System.out.println("Appointment result added successfully");
            int addedAppointmentResultId = response.readEntity(int.class);
            System.out.println("Appointment result id: " + addedAppointmentResultId);
        }
        else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    private static void updateAppointmentResult(WebTarget target){
        System.out.println("----------------------------------------");
        System.out.println("UPDATING APPOINTMENT RESULT");
        AppointmentResult newAppointmentResult = getDummyAppointmentResult();
        newAppointmentResult.setId(1);
        newAppointmentResult.setRadiationDose(new BigDecimal(9000));
        newAppointmentResult.setPatientCardNumber(1);
        Invocation.Builder request = target.path("1/appointmentResult").request(MediaType.APPLICATION_JSON);
        Response response = request.put(Entity.entity(newAppointmentResult, MediaType.APPLICATION_JSON));
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            System.out.println("Appointment result updated successfully");
            AppointmentResult updatedAppointmentResult = response.readEntity(AppointmentResult.class);
            printAppointmentResult(updatedAppointmentResult);
        }
        else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    private static void requestCardsXML(WebTarget target){
        System.out.println("----------------------------------------");
        System.out.println("REQUESTING ALL MEDICAL CARDS IN XML");
        Invocation.Builder request = target.request().accept(MediaType.APPLICATION_XML);
        Response response = request.get();
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            System.out.println("All medical cards in XML:");
            System.out.println(response.readEntity(String.class));
        }
        else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    private static void requestCardsJSON(WebTarget target){
        System.out.println("----------------------------------------");
        System.out.println("REQUESTING ALL MEDICAL CARDS IN JSON");
        Invocation.Builder request = target.request().accept(MediaType.APPLICATION_JSON);
        Response response = request.get();
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            System.out.println("All medical cards in JSON:");
            System.out.println(response.readEntity(String.class));
        }
        else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    private static void requestCardsAsObjectsFromXML(WebTarget target){
        System.out.println("----------------------------------------");
        System.out.println("REQUESTING ALL MEDICAL CARDS IN XML AND READING AS OBJECTS");
        List<MedicalCard> cards;
        Invocation.Builder request = target.request().accept(MediaType.APPLICATION_XML);
        Response response = request.get();
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            System.out.println("All medical cards read from XML:");
            cards = response.readEntity(new GenericType<List<MedicalCard>>() {});
            for (MedicalCard card : cards) {
                printMedicalCard(card);
            }
        }
        else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    private static void requestCardsAsObjectsFromJSON(WebTarget target){
        System.out.println("----------------------------------------");
        System.out.println("REQUESTING ALL MEDICAL CARDS IN JSON AND READING AS OBJECTS");
        List<MedicalCard> cards;
        Invocation.Builder request = target.request().accept(MediaType.APPLICATION_JSON);
        Response response = request.get();
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            System.out.println("All medical cards read from JSON:");
            cards = response.readEntity(new GenericType<List<MedicalCard>>() {});
            for (MedicalCard card : cards) {
                printMedicalCard(card);
            }
        }
        else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    public static URI getBaseURI(String basePath, int port, String... path) {
        UriBuilder builder = UriBuilder.fromUri(basePath).port(port);
        for (String part : path) {
            builder.path(part);
        }
        URI uri = builder.build();
        System.out.println("uri: " + uri);
        return uri;
    }

    public static void printMedicalCard(MedicalCard medicalCard) {
        System.out.println("Card number: " + medicalCard.getCardNumber());
        System.out.println("Card address: " + medicalCard.getAddress());
        System.out.println("Card email: " + medicalCard.getEmail());
        PersonalInfo personalInfo = medicalCard.getPersonalInfo();
        System.out.println("Card first name: " + personalInfo.getFirstName());
        System.out.println("Card last name: " + personalInfo.getLastName());
        System.out.println("Card patronymic: " + personalInfo.getPatronymic());
        System.out.println("Card birth date: " + personalInfo.getBirthDate());
        System.out.println("Card gender: " + personalInfo.getGender());
        Identification identification = medicalCard.getIdentification();
        System.out.println("Card id card number: " + identification.getIdCardNumber());
        System.out.println("Card phone: " + medicalCard.getPhone());
        System.out.println("Card workplace: " + medicalCard.getWorkplace());
        MedicalCard.Appointments appointments = medicalCard.getAppointments();
        if (appointments != null) {
            for (Appointment appointment : appointments.getAppointment()) {
                printAppointment(appointment);
            }
        }
        MedicalCard.Results results = medicalCard.getResults();
        if (results != null) {
            for (AppointmentResult result : results.getAppointmentResult()) {
                printAppointmentResult(result);
            }
        }
    }

    public static AppointmentResult getDummyAppointmentResult(){
        AppointmentResult newAppointmentResult = new AppointmentResult();
        DatatypeFactory datatypeFactory = null;
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        newAppointmentResult.setAppointmentId(1);
        newAppointmentResult.setDiagnosis("Diagnosis");
        newAppointmentResult.setAppointmentDate(datatypeFactory.newXMLGregorianCalendarDate(2021, 12, 12, 0));
        newAppointmentResult.setAppointmentTime(datatypeFactory.newXMLGregorianCalendarTime(12, 0, 0, 0));
        newAppointmentResult.setActions("Actions");
        newAppointmentResult.setAnamnesis("Anamnesis");
        newAppointmentResult.setConclusion("Conclusion");
        newAppointmentResult.setObjectively("Objectively");
        newAppointmentResult.setDoctorID(1);
        newAppointmentResult.setId(1);
        newAppointmentResult.setReason("Reason");
        newAppointmentResult.setPatientCardNumber(1);
        newAppointmentResult.setPrescription("Prescription");
        newAppointmentResult.setRecommendations("Recommendations");
        newAppointmentResult.setRadiationDose(new BigDecimal(1));
        return newAppointmentResult;
    }

    public static Appointment getDummyAppointment(){
        DatatypeFactory datatypeFactory = null;
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Appointment newAppointment = new Appointment();
        newAppointment.setDate(datatypeFactory.newXMLGregorianCalendarDate(2021, 12, 12, 0));
        newAppointment.setStartTime(datatypeFactory.newXMLGregorianCalendarTime(12, 0, 0, 0));
        newAppointment.setEndTime(datatypeFactory.newXMLGregorianCalendarTime(13, 0, 0, 0));
        newAppointment.setRoomNumber(1);
        newAppointment.setPatientCardNumber(1);
        return newAppointment;
    }

    public static MedicalCard getDummyMedicalCard(){
        DatatypeFactory datatypeFactory = null;
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MedicalCard newMedicalCard = new MedicalCard();
        newMedicalCard.setAddress("New address");
        newMedicalCard.setEmail("emal@gmail.com");
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setFirstName("First name");
        personalInfo.setLastName("Last name");
        personalInfo.setPatronymic("Patronymic");
        personalInfo.setBirthDate(datatypeFactory.newXMLGregorianCalendarDate(1999, 12, 12, 0));
        personalInfo.setGender("Male");
        newMedicalCard.setPersonalInfo(personalInfo);
        newMedicalCard.setPhone("+380974521198");
        newMedicalCard.setWorkplace("Workplace");
        Identification identification = new Identification();
        identification.setIdCardNumber("1111");
        newMedicalCard.setIdentification(identification);
        newMedicalCard.setAppointments(new MedicalCard.Appointments());
        newMedicalCard.setResults(new MedicalCard.Results());
        return newMedicalCard;
    }

    public static void printAppointment(Appointment appointment){
        System.out.println("Appointment id: " + appointment.getId());
        System.out.println("Appointment date: " + appointment.getDate());
        System.out.println("Appointment start time: " + appointment.getStartTime());
        System.out.println("Appointment end time: " + appointment.getEndTime());
        System.out.println("Appointment room number: " + appointment.getRoomNumber());
        System.out.println("Appointment patient card number: " + appointment.getPatientCardNumber());
        System.out.println();
    }

    public static void printAppointmentResult(AppointmentResult result){
        System.out.println("Appointment result id: " + result.getId());
        System.out.println("Appointment result appointment id: " + result.getAppointmentId());
        System.out.println("Appointment result diagnosis: " + result.getDiagnosis());
        System.out.println("Appointment result prescription: " + result.getPrescription());
        System.out.println("Appointment result recommendations: " + result.getRecommendations());
        System.out.println("Appointment result radiation dose: " + result.getRadiationDose());
        System.out.println();
    }
}