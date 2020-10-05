package org.eda1.practica01;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.jupiter.api.Test;

public class Practica01JUnit5 {
	private String directorioEntrada = System.getProperty("user.dir") + File.separator +
							           "Practicas - src" + File.separator + 
							           "org" + File.separator +
							           "eda1" + File.separator + 
							           "practica01" + File.separator; 
							           
	@Test
	public void testDevice(){
 		Device.inicializaNumDevices(); //Inicializamos atributo estatico (contador de dispositivos)
		
 		Device d1 = new Device("iPhone X");
		Device d2 = new Device("iPhone 11");
		Device d3 = new Device("Nubia Lite");
		Device d4 = null;
		
		try {
			d4 = new Device(null); //Si el nombre del dispositivo es null, se lanza una excepción
		}catch(Exception e) {
			assertEquals(e.getMessage(), "El atributo name no puede ser nulo");
		}

		d4 = new Device(); //Constructor predeterminado --> name = "noname"
		
		assertEquals("1.- iphone x",   d1.toString()); //id.- name
		assertEquals("2.- iphone 11",  d2.toString());
		assertEquals("3.- nubia lite", d3.toString());
		assertEquals("4.- noname",     d4.toString());
		
		assertTrue(d4.getId() == 4);	
		
		for (int i=0; i<1000; i++) {
			new Device();
		}
		
		d4 = new Device();
		assertTrue(d4.getId() == 1005);
		
		d1 = d2 = d3 = d4 = null;
	}
	
	@Test
	public void testDevicesMesagges() {
 		Device.inicializaNumDevices(); 
 	
 		Device d1 = new Device("d1");
 		
 		d1.sendMessage("Hola amigo que tal");
 		d1.sendMessage("Hola hola hoLA amigo como estamos");
 		
 		String[] salidaEsperada = {"hola", "amigo", "que", "tal", "como", "estamos"}; //Se eliminan palabras repetidas; todas en minúsculas
 		
 		int cont=0;
 		for (String word: d1) { //Observad la forma en la que iteramos sobr d1 (sobre sus mensajes)
 			assertEquals(salidaEsperada[cont++], word);
 		}
 		
 		assertFalse(d1.contains("zapato")); //No contiene la palabra zapato (devuelve false)
 		assertTrue(d1.contains("hOLa")); //Contiene la palabra hola (devuelve true)
 		
 		
 		assertTrue(d1.substitute("amigo", "perla")); //Devuelve true ya que la palabra amigo existia --> devuelve true
  
 		assertFalse(d1.substitute("amigo", "perla")); //la palabra amigo no existe --> devuelve false
 
 		assertTrue(d1.substitute("estamos", "estas"));
 		
 		assertTrue(d1.substitute("que", null)); //borramos la palabra que
 		assertTrue(d1.substitute("tal", null)); //borramos la palabra tal
		 
 		
 		String[] salidaEsperada2 = {"hola", "perla", "como", "estas"};
 		
 		cont=0;
 		for (String word: d1) {
 			assertEquals(salidaEsperada2[cont++],word);
 		}
 		
 		d1.clear();
 		
 		d1 = null;
 		
	}
	
	@Test
	public void testUser(){
		User u1 = new User("   Bob   "); //Cuidado con los espacios en blanco delante y detrás del texto
		User u2 = new User("Alice  ");
		User u3 = null;
		
		try {
			u3 = new User(null);
		}catch (Exception e) {
			assertEquals("El atributo name no puede ser nulo", e.getMessage());
		}
		
		u3 = new User("Eve");
		
		
		assertEquals("Bob", u1.toString()); //Como podeis observar, se respetan las mayusculas y minusculas
		assertEquals("Alice", u2.toString());
		assertEquals("Eve", u3.toString());
		
		u1.clear();
		u2.clear();
		u3.clear();
		u1 = u2 = u3 = null;
	}
	
	@Test
	public void testUserDevices() {
 		Device.inicializaNumDevices(); 

		User u1 = new User("bob");
		User u2 = new User("alice");
		Device d1 = new Device("iPhone 11");
		Device d2 = new Device("Motorola MG8");
		Device d3 = new Device("iPhone 11");
		
		u1.addDevices(d1, d1, d2, d3, d3); //5 dispositivos
		assertTrue(u1.getNumDevices() == 2); //¿?
	
		for (int i=0; i<1000; i++) {
			u2.addDevices(new Device());
		}

		assertTrue(u2.getNumDevices() == 1); //¿¿???
		
		assertTrue(u1.sendMessage(d1, "este es un comentario de tranquilidad")); //d1 esta en la listas de dispositivos de u1
		assertTrue(u1.sendMessage(d2, "este es un segundo comentario"));
		assertFalse(u1.sendMessage(new Device(), "este es un segundo comentario")); //este nuevo dispositivo no esta en la lista de u1
		
		u1.substitute("comentario", "mensaje");
		u1.substitute("tranquilidad", null); //Eliminamos la palabra tranquilidad
		
		assertFalse(u1.contains("guerra"));
		assertTrue(u1.contains("mensaJe"));
		
		assertEquals("1.- iphone 11: este es un mensaje de \n" +
					 "2.- motorola mg8: este es un segundo mensaje \n",
	 			 	 u1.getWords());
		
		assertEquals("[de, es, este, mensaje, segundo, un]", u1.getOrderedWords().toString());
		
		u1.clear();
		u2.clear();
		
		u1 = u2 = null;
		d1 = d2 = d3 = null;
		
	}
	
	@Test
	public void testLoad() {
		User u1 = new User("u1");
		User u2 = new User("u2");
		Device d1 = new Device("d1");
		Device d2 = new Device("d2");
		Device d3 = new Device("d3");
		
		u1.addDevices(d1,d2);
		u2.addDevices(d3);
		
		assertTrue(u1.loadMessages (d1, directorioEntrada + "archivo1"));
		assertTrue(u1.loadMessages (d2, directorioEntrada + "archivo1"));
		assertFalse(u2.loadMessages(d1, directorioEntrada + "archivo2")); //d1 no pertenece a u3 
		assertTrue(u2.loadMessages(d3, directorioEntrada + "archivo2"));
		
		assertTrue(u1.getOrderedWords().size() == 107);
		assertTrue(u2.getOrderedWords().size() == 109);
		
		assertTrue(u1.contains("cielos"));
		u1.substitute("cielos", "infiernos");
		assertFalse(u1.contains("cielos"));
	}
}