package co.flexidev.theta.helper;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MapConverter implements AttributeConverter<Object, String> {
    public MapConverter() {
    }

    @Override
    public String convertToDatabaseColumn(Object object) {
        StringBuilder sb = new StringBuilder();
        if (object != null) {
            sb.append(Utility.gson.toJson(object));
        }
        return sb.toString();
    }

    @Override
    public Object convertToEntityAttribute(String objectString) {
        if (objectString == null || objectString.isEmpty()) {
            return null;
        }
        return Utility.gson.fromJson(objectString, Utility.typeMapOfStringObject);
    }
}
