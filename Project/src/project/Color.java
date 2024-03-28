package project;

public class Color {

	private int r;
	private int g;
	private int b;
	
	private static final int MAX = 255;
	private static final int MIN=0;
	
	public Color(int r, int g, int b){
		this.r=r;
    	if (r>MAX) r=r-256; //rotacion
    	else if(r<MIN) r=r+256;
    	this.g=g;
    	if (g>MAX) g=g-256; //rotacion
    	else if(g<MIN) g=g+256;
    	this.b=b;
    	if (b>MAX) b=b-256; //rotacion
    	else if(b<MIN) b=b+256;
	}
	
	public static final Color NEGRO(){
		return new Color(0,0,0);
	}

	public static final Color BLANCO(){
		return new Color(255,255,255);
	}
	
	public String toString(){		
		return "R"+r+"G"+g+"B"+b;
	}
	
	public void luminosidad (int lum) {
		r=r+lum;
    	if (r>MAX) r=r-256; //rotacion
    	else if(r<MIN) r=r+256;
    	g=g+lum;
    	if (g>MAX) g=g-256; //rotacion
    	else if(g<MIN) g=g+256;
    	b=b+lum;
    	if (b>MAX) b=b-256; //rotacion
    	else if(b<MIN) b=b+256;
	}
}