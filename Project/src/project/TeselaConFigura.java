package project;

public class TeselaConFigura extends Tesela{

    private Figura figura;
    private Color cFigura; //color de la figura independiente del estado
	
    public TeselaConFigura(Color color, Figura figura, int estado){ //constructor de tesela con figura
		super(color,estado);
    	this.figura=figura;
		cFigura=figura.getColor(); //se guarda el color orginal de la figura
		if (estado!=1) { //si el estado es 0
			this.figura.setColor(Color.NEGRO()); //la figura se visualizara en negro
		}
    }
    
    public void cambiarEstado(int est) {
    	if (est!=estado) { //si el estado es distinto se procedera a realizar el cambio
    		estado=est; //se guarda el nuevo valor del estado
    		if(est==0) { //si el estado es 0,
    			color=Color.NEGRO(); //el tesela se visualizara en negro
    			if (figura!=null) figura.setColor(Color.NEGRO()); //la figura se visualizara en negro
    		}
    		else if (est==1) { //si el estado es 1,
    			color=cTesela; //el tesela se visualizara en el color original (o modificado por la luminosidad)
    			if (figura!=null) figura.setColor(cFigura); //la figura se visualizara en el color original (o modificado por la luminosidad)
    		}
    		else { //si el estado es 2,
    			color=cTesela; //el tesela se visualizara en el color original (o modificado por la luminosidad)
    			if (figura!=null) figura.setColor(Color.NEGRO()); //la figura se visualizara en negro
    		}
    	}
    }
    
    public void changeLuminosity(int value) {
    	super.changeLuminosity(value);
    	cFigura.luminosidad(value); //guardamos el color de la figura alterado
    	if(estado==1) figura.setColor(cFigura); //si el estado es 1, la figura se visualizara con el nuevo color
    }
    
    public String toString(){
		String salida=estado+"{"+color;
		if(figura instanceof Circulo) return salida+","+(Circulo)figura+"}";
		else return salida+","+(Rectangulo)figura+"}";
    }
}