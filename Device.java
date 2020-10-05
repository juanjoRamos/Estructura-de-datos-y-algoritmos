package org.eda1.practica01;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Device implements Iterable<String> {
	private static int numDevices = 0; // contador de dispositivos...atributo estatico
	private String name;
	private int id;
	private LinkedList<String> words;

	public Device() {
		this.name = "noname";
		this.id = ++numDevices; // Identificador autonumerico
		this.words = new LinkedList<String>();
	}

	public Device(String name) {
		// Si name es null se lanza una excepcion de tipo RunTimeException(mensaje)
		// Haced uso de trim() y toLowerCase() para eliminar espacios en blanco y
		// convertir a minusculas
		// El resto, igual que el constructor predeterminado
		// ...
		if (name == null)
			throw new RuntimeException("El atributo name no puede ser nulo");
		this.name = name.trim().toLowerCase();
		this.id = ++numDevices;
		this.words = new LinkedList<String>();
	}

	public static void inicializaNumDevices() {
		numDevices = 0;
	}

	public int getId() {
		return this.id;
	}

	public void clear() {
		this.words.clear();
	}

	public void sendMessage(String msg) {
		// Como entrada tenemos una frase; haciendo uso del metodo split(" ")
		// convertimos el String en un array de String
		// En este array tenemos las palabras que iremos insertando en words, siempre y
		// cuando ya no existan
		// Cuidado con las mayusculas y minusculas --> todo en minusculas
		// 1 solo for()

		// for (String word : //...){
		// ...
		// }
		for (String word : msg.split(" ")) {
			if (!contains(word.toLowerCase()))
				this.words.add(word.toLowerCase().trim());
		}

	}

	public boolean contains(String word) {
		// 1 unica instruccion
		// Determina si contiene o no la palabra, independientemente de si word contiene
		// mayusculas o no
		return (this.words.contains(word.toLowerCase())) ? true : false;
	}

	public boolean substitute(String word1, String word2) {
		// Sustituimos la palabra word1 por word2
		// Si word2 es igual a null --> eliminamos la palabra word1
		// Vamos a intentar implementar un metodo eficiente. Para ello vamos a hacer uso
		// del metodo indexOf() en
		// lugar de hacer una busqueda secuencial...palabra por palabra
		// Si indexOf() devuelve -1 significa que no existe la palabra --> devuelve
		// false
		// Si indexOf() devuelve un valor valido, lo vamos a utilizar para acceder
		// directamente a la palabra
		// haciendo uso de un objeto de tipo ListIterator --> listIterator(pos)

		int pos = this.words.indexOf(word1.toLowerCase());

		if (pos == -1)
			return false;

		ListIterator<String> it = this.words.listIterator(pos);
		it.next();
		if (word2 == null) {
			it.remove();
		} else {
			it.set(word2);
		}
		return true;
	}

	@Override
	public String toString() {
		// formato id.- name
		// 1 unica linea
		return this.getId() + ".- " + this.name;
	}

	@Override
	public boolean equals(Object o) {
		// Dos dispositivos son iguales si su nombre es igual (this.name == o.name)
		// 1 unica linea
		return ((Device) o).name.equals(this.name);
	}

	@Override
	public Iterator<String> iterator() {
		// Devolvemos el iterador de la estructura word (leer guion)
		// 1 unica linea
		return this.words.iterator();
	}
}