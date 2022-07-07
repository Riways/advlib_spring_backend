package api.entity;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class APIError {
	
	private String error = "";
	private HttpStatus status;
	private LocalDateTime timestamp = LocalDateTime.now();
	private String message;
	private String debugMessage;
	
	public APIError(HttpStatus status, String message, String debugMessage) {
		super();
		this.status = status;
		this.message = message;
		this.debugMessage = debugMessage;
	}

	
	public APIError(String eror, String message) {
		super();
		this.error = eror;
		this.message = message;
	}



	public APIError(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public APIError(String message) {
		super();
		this.message = message;
	}

	
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}
	
	
	
	
}
