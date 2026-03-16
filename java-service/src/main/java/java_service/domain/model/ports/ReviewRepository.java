package java_service.domain.model.ports;

import java.util.List;
import java.util.Optional;
import java_service.domain.model.Review;

public interface ReviewRepository {
    Optional<Review> findById(String id);
    Review save(Review review);
    List<Review> findAll();
}
