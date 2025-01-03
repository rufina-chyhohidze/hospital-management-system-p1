package be.kdg.programming3.service;

import be.kdg.programming3.presentation.gsonconfig.EntityToGson;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to use in Export controller
 * to perform export in Json Format
 */
@Service
public class ExportService {
    public <T> void exportEntitiesToFile(Class<T> entityClass, List<T> entities, String fileName) {
        EntityToGson<T> entityToJSON = new EntityToGson<>(entityClass);
        entityToJSON.entitiesToJsonFile(fileName, entities);
    }
}
