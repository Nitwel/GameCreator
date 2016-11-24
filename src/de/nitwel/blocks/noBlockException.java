package de.nitwel.blocks;

public class noBlockException extends RuntimeException {

	private static final long serialVersionUID = -894063398631340052L;
	
	public noBlockException() {}
	public noBlockException(String error) {
		super(error);
	}
}
