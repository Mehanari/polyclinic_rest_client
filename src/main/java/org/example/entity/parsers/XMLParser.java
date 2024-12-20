package org.example.entity.parsers;

import org.example.entity.Appointment;
import org.example.entity.AppointmentResult;
import org.example.entity.MedicalCard;

public interface XMLParser extends XMLLoader {
    public void saveAppointment(Appointment appointment, final String xmlFileName) throws Exception;

    public void saveAppointmentResult(AppointmentResult appointmentResult, final String xmlFileName) throws Exception;

    public void saveMedicalCard(MedicalCard medicalCard, final String xmlFileName) throws Exception;
}
