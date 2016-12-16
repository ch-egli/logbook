package ch.egli.training.exception.error;

/**
 * @author Christian Egli
 * @since 2/1/16.
 */
public class ValidationError {

	private String code;
	private String message;
	private String rejectedValue;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRejectedValue() {
		return rejectedValue;
	}

	public void setRejectedValue(String rejectedValue) {
		this.rejectedValue = rejectedValue;
	}
}
