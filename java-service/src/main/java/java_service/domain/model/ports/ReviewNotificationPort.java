package java_service.domain.model.ports;

import java_service.domain.model.Review;

public interface ReviewNotificationPort {
    void notifyForAnalysis(Review review);
}
