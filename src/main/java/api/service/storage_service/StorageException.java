package api.service.storage_service;


public class StorageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4429090182987473480L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
