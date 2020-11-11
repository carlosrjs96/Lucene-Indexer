package aplicacion;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.es.SpanishLightStemmer;
import org.apache.lucene.analysis.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.tartarus.snowball.ext.PorterStemmer;
import org.tartarus.snowball.ext.SpanishStemmer;

public class Searcher {
	
	private static Analyzer analyzer = new SpanishAnalyzer();
	private static IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

	private Searcher() {}
	
  
	public static  ArrayList<Document> searchFile(String _queryString,String index,int _hitsPerPage) throws IOException, ParseException {
		System.out.println("********** INICIO DE LA BUSQUEDA **********"); 
		String field = "texto";
		String queries = null;
		int repeat = 0;
		boolean raw = false;
		String queryString = _queryString;// stemming(_queryString);
		int hitsPerPage = _hitsPerPage;
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		BufferedReader in = null;
		QueryParser parser = new QueryParser("texto", analyzer);
		Query query = parser.parse(queryString);
		System.out.println("Consulta: " + query.toString(field));
		System.out.println("Busqueda por tipo: " + query.toString());
		ArrayList<Document> listTitle = new ArrayList<Document>();
		listTitle = doSearch(searcher, query, hitsPerPage);    
		reader.close();
		System.out.println("********** FIN DE LA BUSQUEDA **********"); 
		return listTitle;
	}

	static ArrayList<Document> doSearch(IndexSearcher searcher, Query query,int hitsPerPage) throws IOException {
		ArrayList<Document> listTitle = new ArrayList<Document>();
		
		listTitle.clear();//revisar
		
		TopDocs results = searcher.search(query, hitsPerPage);
		//System.out.println(" RESULTADOS: "+results.scoreDocs.length);

		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total de documentos encontrados");

		int start = 0;
		int end = Math.min(numTotalHits, hitsPerPage);

		for (int i = start; i < end; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String title = doc.get("titulo");
			if (title != null) {
				listTitle.add(doc);
				String text = doc.get("texto");
				if (text != null) {
				}
			} else {
				System.out.println((i+1) + ". " + "No path for this document");
			}
		}
		return listTitle;
	}
	
	
	public static void abrirPagina(String titulo,String refs,String coleccion) throws IOException {
		InputStream is = new FileInputStream(".\\archivos\\" + coleccion); 
    	BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
    	String line = buf.readLine(); 
    	StringBuilder sb = new StringBuilder(); 
    	while(line != null){ 
    		sb.append(line).append("\n"); line = buf.readLine(); 
    	} 
    	String docToString = sb.toString();
    	String[] paginasSeparadas = docToString.split("<html");
    	for(int i=0; i < paginasSeparadas.length;i++) {
    		paginasSeparadas[i] = "<html" + paginasSeparadas[i];
    		String[] separarLineas = paginasSeparadas[i].split("\n");
    		String lineaCache = "";
    		for(int j=0; j < separarLineas.length-1;j++) {
    			lineaCache += separarLineas[j]+"\n";
    		}
    		paginasSeparadas[i] = lineaCache;
    	}
    	String paginaBuscada = "";
    	for(String pagina:paginasSeparadas) {
    		String referencias = FileAnalyzer.sacarRefs(pagina);
    		if(FileAnalyzer.sacarTitle(pagina).equals(titulo) && referencias.equals(refs)) {
    			paginaBuscada = pagina;
    			break;
    		}
    	}
    	File htmlFile = new File(".\\" + titulo + ".html");
    	FileWriter fileWriter = new FileWriter(titulo+".html");
    	fileWriter.write(paginaBuscada);
    	fileWriter.close();
    	Desktop desktop = Desktop.getDesktop();
    	desktop.open(htmlFile);
	}
	
}
