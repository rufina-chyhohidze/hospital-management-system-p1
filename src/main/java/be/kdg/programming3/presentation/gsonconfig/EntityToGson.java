package be.kdg.programming3.presentation.gsonconfig;

import be.kdg.programming3.domain.Doctor;
import be.kdg.programming3.domain.Patient;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class that takes any type of entity for json transformation.
 * @param <T>
 */
public class EntityToGson<T> {
    private final Gson gson;
    private final Class<T> entityClass;


    public EntityToGson(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .setExclusionStrategies(new CustomExclusionStrategy(Patient.class, "doctors"),
                        new CustomExclusionStrategy(Doctor.class, "patients"))
                .create();
    }

    public <E> List<T> toEntities(E jsonSource) {
        Type listType = TypeToken.getParameterized(List.class, entityClass).getType();
        //The TypeToken.getParameterized(List.class, entityClass).getType()
        // ensures that Gson understands the type of the list you are serializing (a list of entities of type T).

        if (jsonSource instanceof String) {
            return gson.fromJson((String) jsonSource, listType);
        } else if (jsonSource instanceof BufferedReader) {
            return gson.fromJson((BufferedReader) jsonSource, listType);
        } else {
            throw new IllegalArgumentException("Unsupported source type. Must be String or BufferedReader.");
        }
    }

    public String toJson(T entity) {
        return gson.toJson(entity);
    }

    public void entitiesToJsonFile(String fileName, List<T> entities) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Serialize the entire list directly to JSON
            String json = gson.toJson(entities, TypeToken.getParameterized(List.class, entityClass).getType());
            writer.write(json);
        } catch (Exception e) {
            System.err.println("Error converting entity list to JSON file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
