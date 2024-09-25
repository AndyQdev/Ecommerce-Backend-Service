package app.ecommerce.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class CrudService {
    
    
    // Método genérico para eliminar una entidad por su ID
    // Método genérico para eliminar una entidad y devolverla
    public <T> Optional<T> deleteEntity(JpaRepository<T, String> repository, String id) {
        // Buscar la entidad por su ID
        Optional<T> entityOpt = repository.findById(id);
        
        if (entityOpt.isPresent()) {
            // Si existe, eliminamos la entidad
            repository.delete(entityOpt.get());
        }
        
        // Devolvemos la entidad eliminada, o un Optional vacío si no existe
        return entityOpt;
    }
    //T es el repositorio que se modificara
    //D es el modelo 
    public <T, D> Optional<T> patchEntity(
            JpaRepository<T, String> repository, 
            String id, 
            D updatedDetails, 
            Function<D, T> relationLoader
    ) 
    {
        
        // Buscar la entidad existente
        Optional<T> entityOpt = repository.findById(id);

        if (entityOpt.isPresent()) {
            // Aplicar la lógica de carga de relaciones y devolver la entidad modificada
            T updatedEntity = relationLoader.apply(updatedDetails);

            // Copiar las propiedades no nulas del DTO a la entidad
            BeanUtils.copyProperties(updatedDetails, updatedEntity, getNullPropertyNames(updatedDetails));

            // Guardar la entidad actualizada en la base de datos
            repository.save(updatedEntity);

            return Optional.of(updatedEntity);
        }

        return Optional.empty();
    }
    
    // Método que devuelve los nombres de las propiedades nulas
    private String[] getNullPropertyNames(Object source) {
        return java.util.stream.Stream.of(source.getClass().getDeclaredFields())
            .filter(field -> {
                field.setAccessible(true);
                try {
                    return field.get(source) == null;
                } catch (IllegalAccessException e) {
                    return false;
                }
            })
            .map(field -> field.getName())
            .toArray(String[]::new);
    }
}