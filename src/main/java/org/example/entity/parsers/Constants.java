package org.example.entity.parsers;

public class Constants {
    public static final String APPOINTMENT_XSD_RESOURCE_NAME = "appointment.xsd";
    public static final String APPOINTMENT_RESULT_XSD_RESOURCE_NAME = "appointmentResult.xsd";
    public static final String MEDICAL_CARD_XSD_RESOURCE_NAME = "medicalCard.xsd";
    public static final String APPOINTMENT_SCHEMA_LOCATION_URI = "http://it.nure.ua/clinic/appointment appointment.xsd";
    public static final String APPOINTMENT_RESULT_SCHEMA_LOCATION_URI = "http://it.nure.ua/clinic/appointmentResult appointmentResult.xsd";
    public static final String MEDICAL_CARD_SCHEMA_LOCATION_URI = "http://it.nure.ua/clinic/medicalCard medicalCard.xsd";

    //Tags and attributes for appointment
    public static final String ID_ATTRIBUTE = "id";
    public static final String DATE_TAG = "date";
    public static final String START_TIME_TAG = "startTime";
    public static final String END_TIME_TAG = "endTime";
    public static final String PATIENT_CARD_NUMBER_TAG = "patientCardNumber";
    public static final String ROOM_NUMBER_TAG = "roomNumber";

    //Tags and attributes for appointment result
    public static final String APPOINTMENT_ID_ATTRIBUTE = "appointmentId";
    public static final String REASON_TAG = "reason";
    public static final String ANAMNESIS_TAG = "anamnesis";
    public static final String OBJECTIVELY_TAG = "objectively";
    public static final String RADIATION_DOSE_TAG = "radiationDose";
    public static final String DIAGNOSIS_TAG = "diagnosis";
    public static final String PRESCRIPTION_TAG = "prescription";
    public static final String RECOMMENDATIONS_TAG = "recommendations";
    public static final String ACTIONS_TAG = "actions";
    public static final String CONCLUSION_TAG = "conclusion";
    public static final String APPOINTMENT_TIME_TAG = "appointmentTime";
    public static final String APPOINTMENT_DATE_TAG = "appointmentDate";
    public static final String DOCTOR_ID_TAG = "doctorID";

    //Tags and attributes for medical card
    public static final String CARD_NUMBER_TAG = "cardNumber";
    public static final String PERSONAL_INFO_TAG = "personalInfo";
    public static final String FIRST_NAME_TAG = "firstName";
    public static final String LAST_NAME_TAG = "lastName";
    public static final String PATRONYMIC_TAG = "patronymic";
    public static final String BIRTH_DATE_TAG = "birthDate";
    public static final String GENDER_TAG = "gender";
    public static final String WORKPLACE_TAG = "workplace";
    public static final String ADDRESS_TAG = "address";
    public static final String EMAIL_TAG = "email";
    public static final String PHONE_TAG = "phone";
    public static final String IDENTIFICATION_TAG = "identification";
    public static final String ID_CARD_NUMBER_TAG = "idCardNumber";
    public static final String DRIVER_LICENSE_NUMBER_TAG = "driverLicenseNumber";
    public static final String PASSPORT_NUMBER_TAG = "passportNumber";
    public static final String APPOINTMENTS_TAG = "appointments";
    public static final String RESULTS_TAG = "results";

    public static final String APPOINTMENT_NAMESPACE_URI = "http://it.nure.ua/clinic/appointment";
    public static final String APPOINTMENT_TAG = "appointment";
    public static final String APPOINTMENT_RESULT_NAMESPACE_URI = "http://it.nure.ua/clinic/appointmentResult";
    public static final String APPOINTMENT_RESULT_TAG = "appointmentResult";
    public static final String MEDICAL_CARD_NAMESPACE_URI = "http://it.nure.ua/clinic/medicalCard";
    public static final String MEDICAL_CARD_TAG = "medicalCard";

    public static final String APPOINTMENT_NAMESPACE_PREFIX = "app";
    public static final String APPOINTMENT_RESULT_NAMESPACE_PREFIX = "res";

    public static final int APPOINTMENT_TAGS_COUNT = 6;
    public static final int APPOINTMENT_RESULT_TAGS_COUNT = 14;
}
