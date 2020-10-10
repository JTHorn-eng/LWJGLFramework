package exceptions;

public class UniformNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	public String toString() {
		return "shader variable could not be found";
	}
}
