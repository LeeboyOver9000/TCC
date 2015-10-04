package jamder.exceptions;

public class NormConflictException extends RuntimeException {
	public NormConflictException(String conflictType) {
		super(conflictType);
	}
}
