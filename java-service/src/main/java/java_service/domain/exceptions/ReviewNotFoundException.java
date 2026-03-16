package java_service.domain.exceptions;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(String id) {
        super("La review con ID " + id + " no existe en el sistema.");
    }
}
