package project;

public class Coordenada implements Comparable<Coordenada>{

	private int fila;
	private int columna;
	
	public Coordenada(int fila, int columna) {
		this.fila=fila;
		this.columna=columna;
	}
	
	public int getFila() {
		return fila;
	}
	
	public int getColumna() {
		return columna;
	}
	
	public int compareTo(Coordenada c) {
		if (fila<c.getFila()) return -1; //si la fila es menor devuelve -1
		else if (fila>c.getFila()) return 1; //si la fila es mayor devuelve 1
		else { //si la fila es igual
			if(columna<c.getColumna()) return -1; //si la columna es menor devuelve -1
			else if (columna>c.getColumna()) return 1; //si la columna es mayor devuelve 1
		}
		return 0; //si la columna tambien es igual devuelve 0
	}
	
	public String toString() {
		return "("+fila+","+columna+")";
	}
}