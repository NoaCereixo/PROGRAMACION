package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Mosaico implements Luminosity{

	private int filas=1; //entre 1 y 20
	private int columnas=1; //entre 1 y 20
	private Map<Coordenada,Tesela> mapaTeselas;
	private ArrayList<Region> regiones = new ArrayList<Region>();

	private static final int MIN_filas=1;
	private static final int MAX_filas=20;

	private static final int MIN_columnas=1;
	private static final int MAX_columnas=20;
	
	public Mosaico() {
		
	}
	
	//lectura
	
	public Mosaico (String fichero) throws Exception{
		
		mapaTeselas = new TreeMap<Coordenada, Tesela>();
		
		Scanner entrada= null;
		
		try{
			entrada = new Scanner(new FileInputStream(new File (fichero)));
		} catch (FileNotFoundException e){ //si no puede abrir el fichero
			throw e; //genera FileNotFoundException
		}
		
		while(entrada.hasNextLine()){
			String linea=entrada.nextLine();
			if(linea.indexOf("*")!=0){ //si la linea no es un comentario se lee
				if (linea.contains("x")){ //si indica las dimensiones, las leemos
					leerDim(linea); //linea=<filas>x<columnas>,<anchoTesela>x<altoTesela>
				}
				else if (linea.contains("(")){ //si describen las teselas, leemos la coordenada
					int i=Integer.parseInt(linea.substring(linea.indexOf("(")+1,linea.indexOf(","))); //i=f
					if ((i<MIN_filas)||(i>filas)) throw new TileOutOfBoundsException("TileOutOfBoundsException");

					int j=Integer.parseInt(linea.substring(linea.indexOf(",")+1,linea.indexOf(")"))); //i=c
					if ((j<MIN_columnas)||(j>columnas)) throw new TileOutOfBoundsException("TileOutOfBoundsException"); //si no se encuentra en el mosaico genera TileOutOfBoundsException

					mapaTeselas.remove(new Coordenada(i,j)); //elimina la tesela por defecto en la coordenada seleccionada y la sustituye por la descrita
					mapaTeselas.put(new Coordenada(i,j), leerTesela(linea.substring(linea.indexOf(":")+1,linea.length()).trim())); //linea=<Estado>{<ColorRGB>,<TipoFigura>:{<ColorRGB>,<Posicion>,<Dim1>,<Dim2>}}
				}
			}
		}
		entrada.close();
		ordenarTeselas(); //se ordenan los teselas
	}
	
	private void leerDim (String linea) throws TileOutOfBoundsException{
		String[] p=linea.split(",");
		
		String[] mosaico=p[0].split("x");
		filas=Integer.parseInt(mosaico[0]); //mosaico[0]=<filas>
		if ((filas<MIN_filas)||(filas>MAX_filas))throw new TileOutOfBoundsException("TileOutOfBoundsException"); //si no se encuentra en el rango aceptado genera TileOutOfBoundsException

		columnas=Integer.parseInt(mosaico[1]); //mosaico[1]=<columnas>
		if ((columnas<MIN_columnas)||(columnas>MAX_columnas)) throw new TileOutOfBoundsException("TileOutOfBoundsException"); //si no se en el rango aceptado genera TileOutOfBoundsException

		mosaico=null;

		for(int i=1;i<=filas;i++){
			for(int j=1;j<=columnas;j++){
				mapaTeselas.put(new Coordenada(i,j),new Tesela()); //genera el mosaico como un conjunto de teselas blancas
			}
		}
		
		String[] tesela=p[1].split("x");
		Tesela.wTesela=Integer.parseInt(tesela[0].trim()); //tesela[0]=<anchoTesela>
		Tesela.hTesela=Integer.parseInt(tesela[1]); //tesela[1]=<altoTesela>
	}
	
	private Tesela leerTesela(String linea){
		Color color;
		
		int estado=Integer.parseInt(linea.substring(0,1)); //estado=<Estado>
		
		if(linea.indexOf("REC")!=-1){ //si contiene una figura rectangulo
			
			color=leerColor(linea.substring(linea.indexOf("{")+1, linea.indexOf(",")).trim()); //color=<ColorRGB>
			
			String rec=linea.substring(linea.indexOf(":")+2, linea.length()-2).trim(); //rec=<ColorRGB>,<Posicion>,<Dim1>,<Dim2>
			String[] caract=rec.split(",");
			
			Rectangulo rectangulo= new Rectangulo(caract[2].trim(),caract[3],leerColor(caract[0]),caract[1]); //genera el rectangulo
			
			return new TeselaConFigura(color,rectangulo,estado); //genera el tesela
		}
		
		else if(linea.indexOf("CIR")!=-1){ //si contiene una figura circulo
			
			color=leerColor(linea.substring(linea.indexOf("{")+1, linea.indexOf(",")).trim()); //color=<ColorRGB>
			
			String cir=linea.substring(linea.indexOf(":")+2, linea.length()-2).trim(); //cir=<ColorRGB,<Posicion>,<Dim1>
			
			String[] caract=cir.split(",");
			
			Circulo circulo=new Circulo(caract[2],leerColor(caract[0]),caract[1]); //genera el circulo
			
			return new TeselaConFigura(color,circulo,estado); //genera el tesela
		}
		
		else{ //si no contiene figura
			color=leerColor(linea.substring(linea.indexOf("{")+1, linea.indexOf("}")).trim()); //color=<ColorRGB>
			return new Tesela(color,estado); //genera el tesela
		}
	}

	private Color leerColor(String linea){
		String aux=linea.toUpperCase();
		
		int R=Integer.parseInt(aux.substring(aux.indexOf("R")+1,aux.indexOf("G"))); //lee el componente rojo
		int G=Integer.parseInt(aux.substring(aux.indexOf("G")+1,aux.indexOf("B"))); //lee el componente verde
		int B=Integer.parseInt(aux.substring(aux.indexOf("B")+1)); //lee el componenete azul
		
		return new Color(R,G,B); //genera el color
	}
	
	//escritura
	
	public void salvarAFichero(String fichero) throws FileNotFoundException{
		PrintWriter salida=null;

		try{
			salida=new PrintWriter(new FileOutputStream(new File(fichero)));
		}catch(FileNotFoundException e){ //si no se puede abrir el fichero
			throw e; //genera una excepcion
		}
		
		salida.print(this.toString()); //escribe en el fichero
		salida.close();
	}
	
	public String toString(){
		ArrayList<Coordenada> coord = new ArrayList<Coordenada>();
		String dim=filas+"x"+columnas+","+Tesela.wTesela+"x"+Tesela.hTesela+"\n"; //escribe las dimensiones del mosaico
		
		String salida="";
		
		for(Map.Entry<Coordenada, Tesela> t: mapaTeselas.entrySet()) {
			coord.add(t.getKey()); //genera un array con las coordenadas de las teselas
		}
		for (int i=0;i<coord.size();i++) { //recorre el mapa con las teselas
			if (mapaTeselas.get(coord.get(i)) instanceof TeselaConFigura) salida=salida+coord.get(i)+":"+(TeselaConFigura)(mapaTeselas.get(coord.get(i)))+"\n"; //escribe la tesela seleccionada
			salida=salida+coord.get(i)+":"+mapaTeselas.get(coord.get(i))+"\n";
		}
		
		return dim+salida;
	}
	
	//cambiar luminosidad
	
	public void changeLuminosity(int value) {
		ArrayList<Coordenada> coord = new ArrayList<Coordenada>();
		for(Map.Entry<Coordenada, Tesela> t: mapaTeselas.entrySet()) {
			coord.add(t.getKey()); //genera un array con las coordenadas de las teselas
		}
		for (int i=0;i<coord.size();i++) { //recorre el mapa con las teselas
			if (mapaTeselas.get(coord.get(i)) instanceof TeselaConFigura) ((TeselaConFigura)mapaTeselas.get(coord.get(i))).changeLuminosity(value); //modifica la luminosidad de la tesela seleccionada
			else mapaTeselas.get(coord.get(i)).changeLuminosity(value);
		}
	}
	
	//modificar estado
	
	public void cambiarEstado (int est) {
		ArrayList<Coordenada> coord = new ArrayList<Coordenada>();
		for(Map.Entry<Coordenada, Tesela> t: mapaTeselas.entrySet()) {
			coord.add(t.getKey()); //genera un array con las coordenadas de las teselas
		}
		for (int i=0;i<coord.size();i++) { //recorre el mapa con las teselas
			if (mapaTeselas.get(coord.get(i)) instanceof TeselaConFigura) ((TeselaConFigura)mapaTeselas.get(coord.get(i))).cambiarEstado(est); //modifica el estado de la tesela seleccionada
			else mapaTeselas.get(coord.get(i)).cambiarEstado(est);
		}
	}
	
	//otros
	
	public void anadirRegion(Region r) {
		regiones.add(r);
	}
	
	public void ordenarRegionesXAreaAsc() {
		Collections.sort(regiones, new OrdenarReg());
	}
	
	public Region getRegion(String nombre) {
		for (int i=0;i<regiones.size();i++) {
			if (regiones.get(i).getNombre().equals(nombre)) return regiones.get(i);
		}
		return null;
	}
	
	public String toStringRegiones() {
		String salida="";
		for (int i=0;i<regiones.size();i++) {
			salida=salida+regiones.get(i).toString()+"\n";
		}
		return salida;
	}
	
	public Tesela getTesela(Coordenada c) {
		return mapaTeselas.get(c);
	}
	
	//metodos internos
	
	private void ordenarTeselas() {
		ArrayList<Coordenada> coord = new ArrayList<Coordenada>();
		Map<Coordenada,Tesela> tes = new TreeMap<Coordenada,Tesela>();
		for(Map.Entry<Coordenada, Tesela> t: mapaTeselas.entrySet()) {
			coord.add(t.getKey());
		}
		Collections.sort(coord);
		for (int i=0;i<coord.size();i++) {
			tes.put(coord.get(i),mapaTeselas.get(coord.get(i)));
		}
	}
	
	public class Region implements Luminosity{

		private String nombre; //max 30
		private Coordenada origen;
		private int w; //de izquierda a derecha
		private int h; //de arriba a abajo
		
		//crear
		
		public Region(String nombre, int f0, int c0, int h, int w) {
			this.nombre=nombre;
			origen=new Coordenada(f0, c0);
			this.w=w;
			this.h=h;
		}
		
		//cambiar luminosidad
		
		public void changeLuminosity (int value) {
			for(int i=0;i<h;i++) { //recorre las filas
				for(int j=0;j<w;j++) { //recorre las columnas
					if (mapaTeselas.get(new Coordenada(origen.getFila()+i,origen.getColumna()+j)) instanceof TeselaConFigura) ((TeselaConFigura)(mapaTeselas.get(new Coordenada(origen.getFila()+i,origen.getColumna()+j)))).changeLuminosity(value); //cambia la luminosidad de la tesela seleccionada
					else mapaTeselas.get(new Coordenada(origen.getFila()+i,origen.getColumna()+j)).changeLuminosity(value);
				}
			}
		}
		
		//modificar estado
		
		public void cambiarEstado (int est) {
			for(int i=0;i<h;i++) { //recorre las filas
				for(int j=0;j<w;j++) { //recorre las columnas
					if (mapaTeselas.get(new Coordenada(origen.getFila()+i,origen.getColumna()+j)) instanceof TeselaConFigura) ((TeselaConFigura)(mapaTeselas.get(new Coordenada(origen.getFila()+i,origen.getColumna()+j)))).cambiarEstado(est); //cambia el estado de la tesela seleccionada
					else mapaTeselas.get(new Coordenada(origen.getFila()+i,origen.getColumna()+j)).cambiarEstado(est);
				}
			}
		}
		
		//otros
		
		public String toString() {
			int cont=0;
			String salida=nombre+":("+origen.getFila()+","+origen.getColumna()+"),"+h+"-"+w+":["; //escribe el origen, el ancho y el ato de l aregion
			for(int i=0;i<h;i++) { //recorre las filas
				for(int j=0;j<w;j++) { //recorre las columnas
					cont=cont+1;
					salida=salida+new Coordenada(origen.getFila()+i,origen.getColumna()+j).toString(); //escribe las coordenadas de la tesela seleccionada
					if (cont<area()) salida=salida+", ";
				}
			}
			return salida+"]";
		}
		
		public String getNombre() {
			return nombre;
		}
		
		public int area() {
			return w*h; //numero de teselas de la region
		}
	}
}