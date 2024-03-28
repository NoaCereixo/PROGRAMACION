package project;

public class Tesela implements Luminosity{
	
    protected Color color = Color.BLANCO(); //color del tesela dependiente del estado
    protected int estado=0;
    protected int luminosidad=0;
    
    protected Color cTesela = Color.BLANCO(); //color del tesela independiente del estado
	
    public static int wTesela;
    public static int hTesela;
    
    //crear

    Tesela(){ //constrtcutor de tesela por defecto en la creacion del mosaico
    }
	
    Tesela(Color color, int estado){ //constructor de tesela sin figura
		this.estado=estado;
		cTesela=color; //se guarda el color original del tesela
		if (estado==0) this.color=Color.NEGRO(); //si el estado es 0, la tesela se visualizara en negro
		else this.color=color; //en otro caso, se visualizara en el color especificado
    }
    
    //cambiar luminosidad
    
    public void changeLuminosity(int value) {
    	luminosidad=luminosidad+value; //aumentamos (value>0) o isminuimos (value<0) la luminosidad segun se precise
    	cTesela.luminosidad(value); //guardamos el color del tesela alterado
    	if(estado!=0) color=cTesela; //el tesela se visualizara con el nuevo color
    }
    
    //modificar estado
    
    public void cambiarEstado(int est) {
    	if (est!=estado) { //si el estado es distinto se procedera a realizar el cambio
    		estado=est; //se guarda el nuevo valor del estado
    		if(est==0) color=Color.NEGRO(); //el tesela se visualizara en negro
    		else color=cTesela; //el tesela se visualizara en el color original (o modificado por la luminosidad)
		}
    }
    
    //otros
	
    public Color getColor(){
    	return cTesela;
    }
    
    public int getEstado(){
    	return estado;
    }

    public String toString(){
    	return estado+"{"+color+"}";
    }
}
