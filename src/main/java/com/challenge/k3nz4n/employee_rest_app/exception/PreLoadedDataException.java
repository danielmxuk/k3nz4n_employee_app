package com.challenge.k3nz4n.employee_rest_app.exception;

/**
 * <pre>
 * This class defined a custom exception in case the Employee data could not be loaded
 * </pre>
 * @author Daniel Beltran Roman - daniel.beltran-roman@amdocs.com
 * @since Oct 8, 2019
 */
public class PreLoadedDataException extends Exception{

	private static final long serialVersionUID = 1L;

	public PreLoadedDataException(String errorMessage){
		super(errorMessage);
	}
}
