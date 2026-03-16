package java_service.infrastructure.persistence;

import java_service.infrastructure.persistence.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReviewRepository extends JpaRepository<ReviewEntity, String> {
    // Aquí podrías agregar métodos de consulta personalizados si los necesitas,
    // por ejemplo: List<ReviewEntity> findByUserId(Long userId);
}
