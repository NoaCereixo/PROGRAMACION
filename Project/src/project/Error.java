package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Error {
	
	public Error(String e){
		PrintWriter salida=null;

		try{
			salida=new PrintWriter(new FileOutputStream(new File("error.txt"))); //abre el fichero Error.txt
		}catch(FileNotFoundException exc){
			System.out.println("Error abriendo el fichero");
			System.exit(-1);
		}
		salida.print(e); //escribe en el fichero la excepcion
		salida.close();
		System.exit(-1); //finaliza la ejecucion
	}
}