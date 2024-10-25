package be.kdg.programming3.presentation.converters;

import be.kdg.programming3.domain.Department;
import be.kdg.programming3.domain.Gender;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDepartmentConverter implements Converter<String, Department> {
    @Override
    public Department convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        try {
            return Department.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid department value: " + source);
        }
    }
}
