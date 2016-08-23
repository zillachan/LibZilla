package zilla.libcore.db.exception;

/**
 * Created by Zilla on 23/8/2016.
 */
public class NoDBOperationAnnotationException extends Exception {

    public NoDBOperationAnnotationException() {
        super("No database operation annotation.");
    }

    public NoDBOperationAnnotationException(String detailMessage) {
        super(detailMessage);
    }
}
