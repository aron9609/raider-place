package social.raider.server.place.validator;

public interface Validator<T> {

    void validate(T objToValidate);
}
