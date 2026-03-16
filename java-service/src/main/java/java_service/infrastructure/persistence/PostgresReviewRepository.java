package java_service.infrastructure.persistence;

import java_service.domain.model.Review;
import java_service.domain.model.ports.ReviewRepository;
import java_service.infrastructure.persistence.mapper.ReviewPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostgresReviewRepository implements ReviewRepository {

    private final JpaReviewRepository jpaRepository; // La interfaz que extiende JpaRepository
    private final ReviewPersistenceMapper mapper;

    @Override
    public Review save(Review review) {
        var entity = mapper.toEntity(review);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Review> findById(String id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Review> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
