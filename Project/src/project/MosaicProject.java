package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class MosaicProject {

	public static void main(String[] args) throws Exception {

		Mosaico mosaico = new Mosaico();
		
		Scanner entrada= null;
		
		try{
			entrada = new Scanner(new FileInputStream(new File (args[0])));
		} catch (FileNotFoundException e){
			new Error("Error abriendo el fichero");
		}
		
		while(entrada.hasNextLine()) {
			
			String linea=entrada.nextLine();
			
			if (linea.contains("ReadMosaic")) {
				try{
					mosaico=new Mosaico(linea.substring(linea.indexOf(' ')+1));
				}catch(FileNotFoundException e) {
					new Error("FileNotFoundException");
				}catch(TileOutOfBoundsException e) {
					new Error("TileOutOfBoundsException");
				}
			}
			
			else if (linea.contains("CreateRegion")) mosaico.anadirRegion(mosaico.new Region(linea.substring(linea.indexOf(" ")+1, linea.indexOf(",")), Integer.parseInt(linea.substring(linea.indexOf(",")+1,linea.indexOf(",")+2)), Integer.parseInt(linea.substring(linea.indexOf(",")+3,linea.indexOf(",")+4)), Integer.parseInt(linea.substring(linea.indexOf(",")+5,linea.indexOf(",")+6)), Integer.parseInt(linea.substring(linea.indexOf(",")+7,linea.indexOf(",")+8))));
			
			else if (linea.contains("ChangeLuminosityMosaic")) mosaico.changeLuminosity(Integer.parseInt(linea.substring(linea.indexOf(" ")+1)));
			else if (linea.contains("ChangeLuminosityRegion")) mosaico.getRegion(linea.substring(linea.indexOf(",")+1)).changeLuminosity(Integer.parseInt(linea.substring(linea.indexOf(" ")+1,linea.indexOf(","))));
			else if (linea.contains("ChangeLuminosityTile")) {
				String p[]=linea.substring(linea.indexOf(" ")+1).split(",");
				if (mosaico.getTesela(new Coordenada(Integer.parseInt(p[1]),Integer.parseInt(p[2]))) instanceof TeselaConFigura) ((TeselaConFigura)mosaico.getTesela(new Coordenada(Integer.parseInt(p[1]),Integer.parseInt(p[2])))).changeLuminosity(Integer.parseInt(p[0]));
				else mosaico.getTesela(new Coordenada(Integer.parseInt(p[1]),Integer.parseInt(p[2]))).changeLuminosity(Integer.parseInt(p[0]));
				p=null;
			}
			
			else if (linea.contains("ChangeStatusMosaic")) mosaico.cambiarEstado(Integer.parseInt(linea.substring(linea.indexOf(" ")+1)));
			else if (linea.contains("ChangeStatusRegion")) mosaico.getRegion(linea.substring(linea.indexOf(",")+1)).cambiarEstado(Integer.parseInt(linea.substring(linea.indexOf(" ")+1,linea.indexOf(","))));
			else if (linea.contains("ChangeStatusTile")) {
				String p[]=linea.substring(linea.indexOf(" ")+1).split(",");
				if (mosaico.getTesela(new Coordenada(Integer.parseInt(p[1]),Integer.parseInt(p[2]))) instanceof TeselaConFigura) ((TeselaConFigura)mosaico.getTesela(new Coordenada(Integer.parseInt(p[1]),Integer.parseInt(p[2])))).cambiarEstado(Integer.parseInt(p[0]));
				else mosaico.getTesela(new Coordenada(Integer.parseInt(p[1]),Integer.parseInt(p[2]))).cambiarEstado(Integer.parseInt(p[0]));
				p=null;
			}
			
			else if (linea.contains("SortRegionsByArea")) {
				mosaico.ordenarRegionesXAreaAsc();
				PrintWriter salida=null;
				try{
					salida=new PrintWriter(new FileOutputStream(new File(linea.substring(linea.indexOf(" ")+1))));
				}catch(FileNotFoundException exc){
					new Error("FileNotFoundException");
				}
				salida.println(mosaico.toStringRegiones());
				salida.close();
			}
			
			else if (linea.contains("SaveMosaic")) {
				try{
					mosaico.salvarAFichero(linea.substring(linea.indexOf(" ")+1));
				}catch(FileNotFoundException e) {
					new Error("FileNotFoundException");
				}
			}			
		}
	}
}