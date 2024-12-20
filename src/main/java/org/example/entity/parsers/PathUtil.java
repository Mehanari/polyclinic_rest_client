package org.example.entity.parsers;

import java.net.URL;

public class PathUtil {
    private String getPathToResource(String resourceName){
        ClassLoader loader = getClass().getClassLoader();
        URL url = loader.getResource(resourceName);
        return url.getPath();
    }

    public String getAppointmentXsdPath(){
        return getPathToResource(Constants.APPOINTMENT_XSD_RESOURCE_NAME);
    }

    public String getMedicalCardXsdPath(){
        return getPathToResource(Constants.MEDICAL_CARD_XSD_RESOURCE_NAME);
    }

    public String getAppointmentResultXsdPath(){
        return getPathToResource(Constants.APPOINTMENT_RESULT_XSD_RESOURCE_NAME);
    }
}
