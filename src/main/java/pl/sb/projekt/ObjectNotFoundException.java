package pl.sb.projekt;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException() {
        super("Object not found");
    }
}
