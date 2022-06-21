package pl.sb.projekt;

public class ObjectAlreadyExistsException extends RuntimeException {

    public ObjectAlreadyExistsException() {
        super("Object already exist");
    }
}
