package info.myone.api.common.handler;

@SuppressWarnings("serial")
public class BusinessExceptionHandler extends RuntimeException{

	public BusinessExceptionHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusinessExceptionHandler(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BusinessExceptionHandler(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BusinessExceptionHandler(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BusinessExceptionHandler(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
