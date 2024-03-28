package project;

public class TileOutOfBoundsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public TileOutOfBoundsException (String m) {
		super(m);
	}
}