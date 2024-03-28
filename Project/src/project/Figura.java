package project;

public abstract class Figura {

	protected Color color;

    private String posicion;
    public static String R = "R";
    public static String U = "U";
    public static String C = "C";
    public static String D = "D";
    public static String L = "L";
	
	protected Figura() {}
	
	protected Figura (Color color, String p) {
		this.color=color;
		posicion=p;
	}

    public Color getColor(){
    	return this.color;
    }

    public void setColor(Color color){
    	this.color=color;
    }
    
    public String toString() {
    	return color+","+posicion;
    }
}