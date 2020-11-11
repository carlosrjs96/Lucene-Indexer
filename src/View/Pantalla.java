package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.Font;
import Controller.*;
import aplicacion.Main;

public class Pantalla extends JFrame {

	public javax.swing.JPanel contentPane;
	public javax.swing.JTextField textSearch;
	public javax.swing.JTable table;
	

	public javax.swing.JScrollPane scrollPane;
	public javax.swing.JButton btnSearch;
	public javax.swing.JButton btnExtendedSearch;
	public javax.swing.JLabel lblLucene;
	public javax.swing.JCheckBox chckbxWikip1;
	public javax.swing.JCheckBox chckbxWikip2;
	public javax.swing.JCheckBox chckbxWikig1;
	public javax.swing.JCheckBox chckbxWikig2;
	
	
	public DefaultTableModel modelTable;
	public ListSelectionModel listSelectionModel;
	
	public String texto;

	/**
	 * Create the frame.
	 */
	public Pantalla() {
		modelTable= new DefaultTableModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textSearch = new JTextField();
		textSearch.setBounds(51, 110, 535, 20);
		contentPane.add(textSearch);
		textSearch.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 171, 634, 237);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnSearch = new JButton("Buscar");
		
		btnSearch.setBounds(185, 141, 89, 23);
		contentPane.add(btnSearch);
		
		lblLucene = new JLabel("LUCENE");
		lblLucene.setFont(new Font("Tahoma", Font.BOLD, 50));
		lblLucene.setBounds(222, 1, 205, 72);
		contentPane.add(lblLucene);
		
		 chckbxWikip1 = new JCheckBox("wiki-p1");
		chckbxWikip1.setBounds(117, 80, 97, 23);
		contentPane.add(chckbxWikip1);
		
		 chckbxWikip2 = new JCheckBox("wiki-p2");
		chckbxWikip2.setBounds(222, 80, 97, 23);
		contentPane.add(chckbxWikip2);
		
		 chckbxWikig1 = new JCheckBox("wiki-g1");
		chckbxWikig1.setBounds(325, 80, 97, 23);
		contentPane.add(chckbxWikig1);
		
		 chckbxWikig2 = new JCheckBox("wiki-g2");
		chckbxWikig2.setBounds(431, 80, 97, 23);
		contentPane.add(chckbxWikig2);
		
		btnExtendedSearch = new JButton("B\u00FAsqueda extendida");
		btnExtendedSearch.setBounds(344, 141, 131, 23);
		contentPane.add(btnExtendedSearch);
		
		this.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                Main.limpiarPaginas();;
                System.exit(0);
            }
        } );
	}
}
