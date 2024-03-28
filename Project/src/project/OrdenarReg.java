package project;

import java.util.Comparator;

public class OrdenarReg implements Comparator<Mosaico.Region>{

	public int compare(Mosaico.Region o1, Mosaico.Region o2) {
		if ((o1.area()-o2.area())!=0) return o1.area()-o2.area(); //devuelve un numero<0 si el area es menor y >0 si es mayor
		else return o1.getNombre().compareTo(o2.getNombre()); //si el area es igual, ordena en funcion del nombre de la region
	}
}