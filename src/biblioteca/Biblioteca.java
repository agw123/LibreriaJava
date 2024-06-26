package biblioteca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;



public class Biblioteca<K, V> {
	
	
	private static HashMap<Integer, Papers> libri = new HashMap<Integer, Papers>();

	
	public static void main(String[] args) {
		
		Biblioteca<Integer, Papers> b = new Biblioteca<Integer, Papers>();
		
		
		
		try {
			
			b.caricaLibro("libri.txt");
			
			System.out.println("""
					
					
					
						********************************************************************************************************************************************************************************
						*                                                                                                                                                                              *
						*                                             Gestore      libreria   :     Libri     e     Audio Libri                                                                        *
						*                                                                                                                                                                              *
						********************************************************************************************************************************************************************************
						
						
					""");
			
			b.menu();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	
	
	public void caricaLibro(String filePath) throws IOException{
		
		try (BufferedReader l = new BufferedReader( new FileReader(filePath) ) ){
			
			String line;
			
			String[] words;
			
			Integer id;
			
			String autore;
			
			String titolo;
			
			Integer durata;
			
			while( (line =l.readLine()) != null) {				
				
				line = line.trim();
				
				words = line.split(",");
				
				
				id = Integer.valueOf(words[0]);				
				
				autore = words[2].replace("-", " ").trim();
				
				titolo = words[1].replace("-", " ").trim();
								
				if(words.length == 4) {
					
					durata = Integer.valueOf(words[3]);			
					
					this.aggiungiLibro(id, new AudioBook(titolo,autore,durata ) ) ;					
					
				}
				else {
					
					this.aggiungiLibro(id, new Book(titolo,autore));
					
					}									
				
			}
			
		}
		
	}
	
	
	public boolean aggiungiLibro(Integer id, Object elem) {
		
		
		
		if( !libri.containsKey( id ) ) {
						
			if(elem.getClass().toString().equals("class biblioteca.Book"))
			
				libri.put((Integer)id, (Book) elem);
			
			else
				
				libri.put((Integer)id, (AudioBook) elem);
			
			
			
		}			
		
		else {
			
			System.out.println("Il libro con ID: "+ id + " già presente nella libreria");
			
		}
		
		return (true);
	}
	
    public void rimuoviLibri(Integer id) {
    	
    	if(libri.remove(id) == null) {
    		
    		System.out.println("Nessun libro trovato con Id " + id );
    		
    	}
    	else {
    		
    		System.out.println("Il libro con ID: "+ id + " è stato eliminato dalla libreria");
    		
    	}
    }
    
    public void salvaLibro(String filePath) throws IOException{
    	
    	 try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath))) {
    		     			
    		for (Entry<Integer, Papers> libro : libri.entrySet()){
        			
        		w.write(libro.getKey() + "," + libro.getValue().toString().replace("-", " ") + "\n");        			
    			
    		}
    		
    		System.out.println("Lista salvata su " + filePath);
    		
		}
    	 catch (IOException e) {
    		 
    		System.out.println(e.getLocalizedMessage());
			
			e.printStackTrace();
		}
    }
    
    public void lista() throws IOException{
    	
   	 	boolean cl ;
   		     			
   		for (Entry<Integer, Papers> libro : libri.entrySet()){
       		
   			cl = ((libro.getValue().getClass().toString().equals("class biblioteca.Book")) );
   			//cl = (libro instanceof Book) ; 
   			if(cl)
   				System.out.println( (libro.getKey() + "," + ( ((Book)libro.getValue()).toString() ) + "\n") );  
   			else
   				System.out.println( (libro.getKey() + "," + ( ((AudioBook)(libro).getValue()).toString()) + "\n") );  
   			
   		}
	}   
    
    @SuppressWarnings("resource")
	public void menu(){
    	
    	String menu = """
    			
    					- 1) lista
    			
    					- 2) inserisci elemento
    			
    					- 3) cancella elemento
    			
    					- 4) salva
    			
    					- 0) esci
    					
    					immetti la selezione
    			
    			
    			""";
    	
    	Integer id;
    	
    	String autore;
    	
    	String titolo;    
    	
    	Integer durata = 0;    
    	
    	while(true) {
    		
    		System.out.println(menu);    		
    		
    		Scanner l = new Scanner(System.in);
    		
    			
				switch(l.nextInt()) {
				
				case 1:
					try {
						this.lista();
					} catch (IOException e) {
						
						System.out.println(e.getMessage());
						
						e.printStackTrace();
						
					}
				break;    			
				case 2:
					System.out.println("Inserisci id");
					id = l.nextInt();
					
					System.out.println("Inserisci Titolo");
					l.nextLine();
					titolo = l.nextLine();					
					
					System.out.println("Inserisci Autore");
					
					autore = l.nextLine();
					
					System.out.println("Inserisci durata se è un'audiolibro ");
					
					String tmp = l.nextLine();
					
					durata = ( tmp == "" ? 0 : Integer.valueOf( tmp ) );
					
					if(durata == 0) {
						
						if (this.aggiungiLibro(id, new Book(titolo,autore)) ) {
							System.out.println("\nIl libro con ID: "+ id + " è stato inserito nella libreria\n");
						}
						
					}
					
					else {
						
						if (this.aggiungiLibro(id, new AudioBook(titolo,autore,durata)) ) {
							
							System.out.println("L'audiobook con ID: "+ id + " è stato inserito nella libreria");
							
						}
						
					}					
					
				break;	        	
				case 3:
					System.out.println("Inserisci id");
					id = l.nextInt();				
					this.rimuoviLibri(id);
				break;        	
				case 4:
								
					try {
						
						this.salvaLibro("output.txt");
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}    
					
					break;        	
				default:
					return;        	
				}
				
				l.close();
			    
    	}
    }
    	
    	
    	
    
    
}
