package social.raider.server.place.exception;

import lombok.Getter;

@Getter
public class PlaceApiCommunicationException extends TechnicalException {

    private Integer statusCode;

    public PlaceApiCommunicationException(String message) {
        super(message);
    }

    public PlaceApiCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlaceApiCommunicationException(Throwable cause, Integer statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }
}
