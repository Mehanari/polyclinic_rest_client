package org.example.entity.parsers;


import org.example.entity.Appointment;
import org.example.entity.AppointmentResult;
import org.example.entity.MedicalCard;

public interface XMLLoader {
    public Appointment loadAppointment(final String xmlFileName) throws Exception;

    public AppointmentResult loadAppointmentResult(final String xmlFileName) throws Exception;

    public MedicalCard loadMedicalCard(final String xmlFileName) throws Exception;
}
