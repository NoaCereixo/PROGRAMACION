package project;

public class Rectangulo extends Figura{

    private double b; //valor porcentual
    private double h; //valor porcentual
	
    public Rectangulo(double b, double h, Color color, String p){
    	super(color,p);
		this.b=b;
		this.h=h;
    }
    
    public Rectangulo (String ancho, String largo, Color color, String p){ //constructor para la lectura del fichero
		this (Integer.parseInt(ancho),Integer.parseInt(largo),color,p); //genera el rectangulo
	}

    public String toString(){
    	return "REC:{"+super.toString()+","+(int)b+","+(int)h+"}";
    }
}