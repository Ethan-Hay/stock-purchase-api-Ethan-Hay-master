package exception;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String resource, String param, String value) {
        super(String.format("%s not found for %s: %s", resource, param, value));
    }

    public ResourceNotFoundException(String resource, String param, int value) {
        this(resource, param, Integer.toString(value));
    }
}
