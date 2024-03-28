package project;

public class Circulo extends Figura{

    private double radio; //valor porcentual
	
    public Circulo(double radio, Color color, String p){
    	super(color,p);
    	this.radio=radio;
    }
    
    public Circulo(String radio, Color color, String p){ //constructor para la lectura del fichero
		this(Integer.parseInt(radio),color,p); //general el circulo
	}

    public String toString(){
    	return "CIR:{"+super.toString()+","+(int)radio+"}";
    }
}