package model;

/**
 * Clase que representa un Nodo del árbol binario
 * 
 * @param <T>
 */
public class Nodo<T> {

	private Nodo<T> izquierdo, derecho;
	private Nodo<T> padre;
	private T elemento;
	private Character valor;

	/**
	 * Constructor de la clase
	 * 
	 * @param elemento Dato del nodo
	 */
	public Nodo(T elemento) {
		valor = '2';
		this.elemento = elemento;
	}
	
	

	public Character getValor() {
		return valor;
	}



	public void setValor(Character valor) {
		this.valor = valor;
	}



	public Nodo(T elemento, Nodo<T> padre) {
		this.elemento = elemento;
		this.padre = padre;
	}

	/**
	 * Agrega un nuevo elemento en el árbol
	 * 
	 * @param elemento Nuevo dato
	 * @return true si lo pudo guardar
	
	public boolean agregar(T nuevo) {
		if (nuevo.compareTo(elemento) < 0) {
			if (izquierdo == null) {
				izquierdo = new Nodo<>(nuevo, this);
				return true;
			} else {
				return izquierdo.agregar(nuevo);
			}
		} else {
			if (nuevo.compareTo(elemento) > 0) {
				if (derecho == null) {
					derecho = new Nodo<>(nuevo, this);
					return true;
				} else {
					return derecho.agregar(nuevo);
				}
			}
		}

		return false;
	}
 */
	public boolean agregarOrd(T nuevo, int ord) {
		if (ord == -1) {
			if (izquierdo == null) {
				izquierdo = new Nodo<>(nuevo, this);
				return true;
			} else {
				return izquierdo.agregarOrd(nuevo,ord);
			}
		} else {

			if (derecho == null) {
				derecho = new Nodo<>(nuevo, this);
				return true;
			} else {
				return derecho.agregarOrd(nuevo,ord);
			}

		}
	}
	
	public boolean agregarOrd(Nodo<T> nuevo, int ord) {
		if (ord == -1) {
			if (izquierdo == null) {
				izquierdo = nuevo;
				return true;
			} else {
				return izquierdo.agregarOrd(nuevo,ord);
			}
		} else {

			if (derecho == null) {
				derecho = nuevo;
				return true;
			} else {
				return derecho.agregarOrd(nuevo,ord);
			}

		}

	}


	public boolean agregarIzq(T nuevo) {
		if (izquierdo == null) {
			izquierdo = new Nodo<>(nuevo, this);
			return true;
		} else {
			return izquierdo.agregarIzq(nuevo);
		}
	}

	public boolean agregarDer(T nuevo) {

		if (derecho == null) {
			derecho = new Nodo<>(nuevo, this);
			return true;
		} else {
			return derecho.agregarDer(nuevo);
		}

	}

	/**
	 * Determina si un Nodo es una Hoja
	 * 
	 * @return true si es Hoja
	 */
	public boolean esHoja() {
		return izquierdo == null && derecho == null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean tieneUnHijo() {
		return (izquierdo != null && derecho == null) || (derecho != null && izquierdo == null);
	}

	/**
	 * @return the izq
	 */
	public Nodo<T> getIzquierdo() {
		return izquierdo;
	}

	/**
	 * @param izq the izq to set
	 */
	public void setIzquierdo(Nodo<T> izq) {
		this.izquierdo = izq;
	}

	/**
	 * @return the der
	 */
	public Nodo<T> getDerecho() {
		return derecho;
	}

	/**
	 * @param der the der to set
	 */
	public void setDerecho(Nodo<T> der) {
		this.derecho = der;
	}

	/**
	 * @return the elemento
	 */
	public T getElemento() {
		return elemento;
	}

	/**
	 * @param elemento the elemento to set
	 */
	public void setElemento(T elemento) {
		this.elemento = elemento;
	}

	/**
	 * @return the padre
	 */
	public Nodo<T> getPadre() {
		return padre;
	}

	/**
	 * @param padre the padre to set
	 */
	public void setPadre(Nodo<T> padre) {
		this.padre = padre;
	}

}