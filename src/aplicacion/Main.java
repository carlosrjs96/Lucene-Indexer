package aplicacion;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.tartarus.snowball.ext.SpanishStemmer;

import Controller.PantallaController;
import View.Pantalla;

public class Main{
	
	public static void limpiarPaginas() {
		File dir = new File(".\\");
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File child : directoryListing) {
		      String[] extensiones = child.getAbsolutePath().split("\\.");
		      String extension = extensiones[extensiones.length-1];
		      if(extension.contentEquals("html")) {
		    	  child.delete();
		      }
		    }
		  }
	}

    public static void main(String[] args) throws IOException {
    	
        FileAnalyzer.fillStopWords(); //crear la lista estatica de stopwords
        
        //Este codigo es para crear los indices, esta comentado porque los indices ya vienen creados
    	/*String indexp1 = ".\\indexp1";
    	String indexp2 = ".\\indexp2";
    	String indexg1 = ".\\indexg1";
    	String indexg2 = ".\\indexg2";
    	Indexer.createIndex(".\\archivos\\wiki-p1.txt",indexp1);
    	Indexer.createIndex(".\\archivos\\wiki-p2.txt",indexp2);
    	Indexer.createIndex(".\\archivos\\wiki-g1.txt",indexg1);
    	Indexer.createIndex(".\\archivos\\wiki-g2.txt",indexg2);*/
        
        Pantalla frame= new Pantalla();
        
        PantallaController controller = new PantallaController(frame);
        frame.setVisible(true);
    }
}
