package Controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

import View.Pantalla;
import aplicacion.Searcher;

public class PantallaController implements ActionListener,ListSelectionListener{
	private Pantalla vista;
	private ArrayList<Document> listaDocs;	
	
	String index = ".\\indexp1";
	
	String indexp1 = ".\\indexp1";
	String indexp2 = ".\\indexp2";
	String indexg1 = ".\\indexg1";
	String indexg2 = ".\\indexg2";
	
	String wiki = "wiki-p1.txt";
	
	String wikip1 = "wiki-p1.txt";
	String wikip2 = "wiki-p2.txt";
	String wikig1 = "wiki-g1.txt";
	String wikig2 = "wiki-g2.txt";
	
	
	public PantallaController(Pantalla _vista) {
		this.vista=_vista;
		this.vista.modelTable.addColumn("Titulos");
		_init_();
    }
    
    public void _init_(){
    	vista.btnSearch.addActionListener(this);
    	vista.btnExtendedSearch.addActionListener(this);
        vista.chckbxWikig1.addActionListener(this);
        vista.chckbxWikig2.addActionListener(this);
        vista.chckbxWikip1.addActionListener(this);
        vista.chckbxWikip2.addActionListener(this);
        
        vista.listSelectionModel = vista.table.getSelectionModel();
        vista.listSelectionModel.addListSelectionListener(this);
        
        
        vista.table.setSelectionModel(vista.listSelectionModel);
    }
	
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() ==vista.btnSearch){
        	String query = vista.textSearch.getText();
        	limpiarTabla();
        	llenarTabla(query);
        }else if (e.getSource() ==vista.btnExtendedSearch){
        	String query = vista.textSearch.getText();
        	limpiarTabla();
        	extenderTabla(query);
        }else if (e.getSource() == vista.chckbxWikig1) {
        	 index = indexg1;
        	 wiki = wikig1;
             vista.chckbxWikig2.setSelected(false);
             vista.chckbxWikip1.setSelected(false);
             vista.chckbxWikip2.setSelected(false);
        }else if (e.getSource() == vista.chckbxWikig2) {
        	index = indexg2;
        	wiki = wikig2;
        	vista.chckbxWikig1.setSelected(false);
            vista.chckbxWikip1.setSelected(false);
            vista.chckbxWikip2.setSelected(false);
        }else if (e.getSource() == vista.chckbxWikip1) {
        	index = indexp1;
        	wiki = wikip1;
        	vista.chckbxWikig1.setSelected(false);
            vista.chckbxWikig2.setSelected(false);      
            vista.chckbxWikip2.setSelected(false);
        }else if (e.getSource() == vista.chckbxWikip2) {
        	index = indexp2;
        	wiki = wikip2;
        	vista.chckbxWikig1.setSelected(false);
            vista.chckbxWikig2.setSelected(false);
            vista.chckbxWikip1.setSelected(false);
        }else{
        	System.out.println("ACTION LISTENER");
        }
    }
    
    @Override
	public void valueChanged(ListSelectionEvent e) {
    	if(!e.getValueIsAdjusting()) {
    	// print first column value from selected row
            System.out.println("********** DOCUMENTO SELECCIONADO **********"); 
            System.out.println("TITULO: "+this.listaDocs.get(vista.table.getSelectedRow()).get("titulo"));
            System.out.println("ENCABEZADO: "+this.listaDocs.get(vista.table.getSelectedRow()).get("encab"));
            System.out.println("REFERENCIA: "+this.listaDocs.get(vista.table.getSelectedRow()).get("ref"));
            System.out.println("TEXTO: "+this.listaDocs.get(vista.table.getSelectedRow()).get("texto"));
            System.out.println("********************************************"); 
            try {
				Searcher.abrirPagina(this.listaDocs.get(vista.table.getSelectedRow()).get("titulo"), this.listaDocs.get(vista.table.getSelectedRow()).get("ref"),wiki);
            	//Searcher.abrirPagina("alon harazi wikipedia la enciclopedia libre "," alon harazi wikipedi enciclopedi libr curi views navegaci�n busc idiom",wiki);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
        }
	}
    
    public void limpiarTabla() {
    	int fila = this.vista.modelTable.getRowCount();
    	for(int i = 0 ; i < fila; i++) {
    		this.vista.modelTable.removeRow(0);
    	}
    } 
    
    public void llenarTabla(String query) {
		
		try {
			this.listaDocs = Searcher.searchFile(query,index,20);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String []info = new String[1];
		
		for( int i = 0; i < this.listaDocs.size(); i++ ) {
			info[0]=String.valueOf(i+1)+") "+this.listaDocs.get(i).get("titulo");
			// doc.get("texto")
			this.vista.modelTable.addRow(info);
		}
		
		this.vista.table.setModel(this.vista.modelTable);
	}
    
    
public void extenderTabla(String query) {
		try {
			this.listaDocs = Searcher.searchFile(query,index,40);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String []info = new String[1];
		
		for( int i = 0; i < this.listaDocs.size(); i++ ) {
			info[0]=String.valueOf(i+1)+") "+this.listaDocs.get(i).get("titulo");
			// doc.get("texto")
			this.vista.modelTable.addRow(info);
		}
		
		this.vista.table.setModel(this.vista.modelTable);
	}
}
