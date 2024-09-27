package g61411.qwirkle.model;

/**
 * The QwirkleException class is a custom exception class that is thrown when an error occurs in the Qwirkle game.
 * It extends the RuntimeException class and provides a constructor to set an error message.
 */
public class QwirkleException extends RuntimeException {

    /**
     * Constructs a new QwirkleException with the specified error message.
     *
     * @param message the error message to be displayed
     */
    public QwirkleException(String message) {
        super(message);
    }
}
