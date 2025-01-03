package be.kdg.programming3.presentation.gsonconfig;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * This strategy ensures that specific fields (like doctors and patients) are excluded during JSON conversion.
 */
public class CustomExclusionStrategy implements ExclusionStrategy {
    private final Class<?> clazz;
    private final String fieldName;

    public CustomExclusionStrategy(Class<?> clazz, String fieldName) {
        this.clazz = clazz;
        this.fieldName = fieldName;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getDeclaringClass() == clazz && f.getName().equals(fieldName);
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
