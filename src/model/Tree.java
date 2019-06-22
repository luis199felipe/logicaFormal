package model;

import java.util.HashMap;

import model.colas.Cola;

public class Tree <T extends Comparable<T>>
{
	private Nodo<T> raiz;
	private int peso;
	
	/**
	 * Verifica si un árbol está vacío
	 * @return true si está vacío
	 */
	public boolean estaVacio() {
		return raiz==null;
	}
	
	
	
	
	public void eliminar(Nodo<T> nodoActual, Nodo<T> nodoPadre, boolean eliminado, Nodo<T> nodoAux, T valor)
	{
		if(nodoActual == null)
		{
			return;
		}
		else 
		{
			if(!eliminado) 
			{
				if((int)(nodoActual.getElemento()) == (int)(valor)) 
				{					
					if(nodoActual.esHoja())
					{
						if(nodoPadre.getDerecho() == nodoActual) 
						{
							nodoPadre.setDerecho(null);
						}
						else 
						{
							nodoPadre.setIzquierdo(null);
						}
					}
					else if(nodoActual.tieneUnHijo())
					{
						if(nodoActual.getDerecho() != null) 
						{
							if(nodoPadre.getIzquierdo() == nodoActual) 
							{
								nodoPadre.setIzquierdo(nodoActual.getDerecho());
								nodoPadre.getIzquierdo().setPadre(nodoPadre);
							}
							else 
							{
								nodoPadre.setDerecho(nodoActual.getDerecho());
								nodoPadre.getDerecho().setPadre(nodoPadre);
							}
						}
						else 
						{
							if(nodoPadre.getIzquierdo() == nodoActual) 
							{
								nodoPadre.setIzquierdo(nodoActual.getIzquierdo());
								nodoPadre.getIzquierdo().setPadre(nodoPadre);
							}
							else 
							{
								nodoPadre.setDerecho(nodoActual.getIzquierdo());
								nodoPadre.getDerecho().setPadre(nodoPadre);
							}
							eliminar(nodoActual, nodoPadre, true, nodoAux, valor);
						}
					}
					else 
					{
						nodoAux = nodoActual.getDerecho();
						if(nodoPadre.getDerecho() == nodoActual) 
						{
							nodoPadre.setDerecho(nodoActual.getIzquierdo());
							nodoPadre.getDerecho().setPadre(nodoPadre);
						}
						else 
						{
							nodoPadre.setIzquierdo(nodoActual.getIzquierdo());
							nodoPadre.getIzquierdo().setPadre(nodoPadre);
						}
						eliminar(nodoActual.getIzquierdo(), nodoPadre, true, nodoAux, valor);
					}				
				}
				else if((int)(nodoActual.getElemento()) < (int)(valor)) 
				{
					eliminar(nodoActual.getDerecho(), nodoActual,false, nodoAux, valor);
				}
				else 
				{
					eliminar(nodoActual.getIzquierdo(), nodoActual,false, nodoAux, valor);
				}
			}
			else 
			{
				if(nodoActual.getDerecho() == null) 
				{
					nodoActual.setDerecho(nodoAux);
					nodoActual.getDerecho().setPadre(nodoActual);
				}
				else 
				{
					eliminar(nodoActual.getDerecho(), nodoActual, true, nodoAux, valor);
				}
			}				
		}		
	}
	
	
	public int contarHojas(Nodo<T> nodo, int cont)  
	{
		if(raiz != null) 
		{
			if(nodo == null) 
			{
				return 0;
			}
			else if(nodo.esHoja()) 
			{
				return 1;
			}
			else 
			{
				cont = contarHojas(nodo.getDerecho(), cont) + contarHojas(nodo.getIzquierdo(), cont);
				return cont;
			}			
		}
		return -1;
	}
	
	
	public int contarHojas() {
		
		return contarHojas(raiz,0);
	}

	public boolean isEqualto(Tree<T> arbol, Nodo<T> nodo1, Nodo<T> nodo2, boolean identicos) 
	{
		if(raiz != null && arbol.getRaiz() != null) 
		{
			if(nodo1 == null || nodo2 == null) 
			{
				if((nodo1 == null && nodo2 != null) || (nodo1 != null && nodo2 == null)) 
				{
					return false;
				}
				return identicos;
			}
			else if(!identicos) 
			{
				return false;
			}
			else 
			{
				//System.out.println("1: " + nodo1.getElemento());
				//System.out.println("2: " + nodo2.getElemento());
				//System.out.println("3: " + (nodo1.getElemento() == nodo2.getElemento()));
				if(nodo1.getElemento() == nodo2.getElemento()) 
				{
					//System.out.println("asd1");
					identicos = isEqualto(arbol, nodo1.getIzquierdo(), nodo2.getIzquierdo(), identicos) &&
							isEqualto(arbol, nodo1.getDerecho(), nodo2.getDerecho(), identicos);					
					return identicos;
				}
				else 
				{
					//System.out.println("asd2");
					return false;
				}
			}
			
		}
		return false;
	}

	public T obtenerMenorRec(Nodo<T> nodo) 
	{
		if(raiz != null) 
		{
			if(nodo.getIzquierdo() == null) 
			{
				return nodo.getElemento();
			}
			else 
			{
				return obtenerMenorRec(nodo.getIzquierdo());
			}			
		}
		return null;
	}

	public T obtenerMenorIte(Nodo<T> nodo) 
	{
		if(raiz != null) 
		{
			while(nodo.getIzquierdo() != null) 
			{
				nodo = nodo.getIzquierdo();
			}
			return nodo.getElemento();			
		}
		return null;
	}

		
	public int calcularAltura(Nodo<T> nodo, int mayor, int cont) 
	{
		if(nodo == null) 
		{
			if(cont > mayor) 
			{
				mayor = cont;
			}
			return mayor;
		}
		else 
		{
			mayor = calcularAltura(nodo.getIzquierdo(), mayor, cont + 1);
			mayor = calcularAltura(nodo.getIzquierdo(), mayor, cont + 1);
			return mayor + 1;
		}
	}

	
	/**
	 * Agrega un nuevo elemento al árbol
	 * @param elemento Nuevo dato
	 * @return true si lo pudo guardar
	 
	public void agregar(T elemento) {		
		if(estaVacio()) {
			raiz = new Nodo<>(elemento);
			peso++;
		}else if(raiz.agregar(elemento)){			
			peso++;
		}		
	}*/
	
	public void agregarOrd(T elemento,int ord) {		
		if(estaVacio()) {
			raiz = new Nodo<>(elemento);
			peso++;
		}else if(raiz.agregarOrd(elemento,ord)){			
			peso++;
		}		
	}
	public void agregarOrdNodo(Nodo<T> raiz2, int ord) {
		if(estaVacio()) {
			raiz = raiz2;
			peso++;
		}else if(raiz.agregarOrd(raiz2,ord)){			
			peso++;
		}
		
	}
	
	public void agregarIzq(T elemento) {		
		if(estaVacio()) {
			raiz = new Nodo<>(elemento);
			peso++;
		}else if(raiz.agregarIzq(elemento)){			
			peso++;
		}		
	}
	
	public void agregarDer(T elemento) {		
		if(estaVacio()) {
			raiz = new Nodo<>(elemento);
			peso++;
		}else if(raiz.agregarDer(elemento)){			
			peso++;
		}		
	}
	
	
	public void recorridoAmplitud(Cola<T> c,Nodo<T> n) {
		if (n==null) {
			return;
		}else {
			c.encolar((T)n.getElemento());
			recorridoAmplitud(c, n.getIzquierdo());
			recorridoAmplitud(c, n.getDerecho());
		}		
	}
	
	public int alturaArbol(int max,int localMax,Nodo<T> n) {
		if (n==null) {
			if (localMax>max) {
				max = localMax;
			}
			return max;
		}else {
			
			int n1 = alturaArbol(max,localMax+1,n.getIzquierdo());
			int n2 = alturaArbol(max,localMax+1,n.getDerecho());
			if (n1>n2) {
				return n1;
			}else {
				return n2;
			}
		}
	}
	
	public int alturaArbolIterativo() {
		
		Cola<Nodo<T>> cola = new Cola<>();
		cola.encolar(raiz);
		int max=0;
		
		
		while (!cola.estaVacia()) {
			

			Nodo<T> aux = cola.desencolar();
			max = Math.max(obtenerNivelElemento(aux.getElemento(), raiz, 0), max);
			if (aux.getIzquierdo()!=null) {
				cola.encolar(aux.getIzquierdo());
			}
			if (aux.getDerecho()!=null) {
				cola.encolar(aux.getDerecho());
			}
		}
		return max+1;
		
	}
	
	
	
	/**
	 * @return the raiz
	 */
	public Nodo<T> getRaiz() {
		return raiz;
	}
	/**
	 * @param raiz the raiz to set
	 */
	public void setRaiz(Nodo<T> raiz) {
		this.raiz = raiz;
	}
	/**
	 * @return the peso
	 */
	public int getPeso() {
		return peso;
	}
	public int setValorHojas(Nodo<T> nodo, int cont,HashMap<Character,Character> valores)  
	{
		if(raiz != null) 
		{
			if(nodo == null) 
			{
				return 0;
			}
			else if(nodo.esHoja()) 
			{
				nodo.setValor(valores.get(nodo.getElemento()));
				return 1;
			}
			else 
			{
				cont = setValorHojas(nodo.getDerecho(), cont,valores) + setValorHojas(nodo.getIzquierdo(), cont,valores);
				return cont;
			}			
		}
		return -1;
	}
	
	
	public boolean esAtomico(Character l) {
		
		if (l == '~' || l == 'p' || l == 'q'|| l == '1' || l == '0') {
			return true;
		} else {
			return false;
		}
	}

	public  T evaluarInPreOrder(Nodo<T> n,HashMap<Character,Character> valores) {
		if (n == null) {
			return null;
		} else {
			if (n.getIzquierdo().getIzquierdo()!=null) {
				//System.out.println("Entro a izq"+n.getIzquierdo().getElemento());
				evaluarInPreOrder(n.getIzquierdo(),valores);	
			}
			if (n.getDerecho().getDerecho()!=null) {
				//System.out.println("Entro a derecho "+n.getDerecho().getElemento());
				evaluarInPreOrder(n.getDerecho(), valores);	
			}
			//System.out.println("Va a empezar a verificar "+n.getElemento());
			Character izq = n.getIzquierdo().getValor();
			Character der = n.getDerecho().getValor();
			
			//System.err.println(izq+" izq ");
			//System.err.println(der+" der ");
			
			if (esAtomico(izq) || esAtomico(der) ) {
				if (n.getElemento().equals('^')) {
					//System.out.println("paso el if ^");
					if (izq.equals('1') && der.equals('1')) {
						n.setValor('1');
						//System.out.println("Guardo 1");
					}else {
						n.setValor('0');
						//System.out.println("Guardo 0");
					}
				}else if (n.getElemento().equals('v')) {
					//System.out.println("paso el if v");
					if (izq.equals('1') || der.equals('1')) {
						n.setValor('1');
						//System.out.println("Va a setear "+n.getValor()+" por 1");
					}else {
						n.setValor('0');
						//System.out.println("Va a setear "+n.getValor()+" por 1");
					}
				}
			}
						
			return n.getElemento();
		}
	}
	
	public void imprimirInOrder(Nodo<T> n) {
		if (n == null) {
			return;
		} else {
			imprimirInOrder(n.getIzquierdo());
			System.out.println(n.getElemento());
			imprimirInOrder(n.getDerecho());
		}
	}


	public int obtenerNivelElemento(T ele,Nodo<T> n,int nivel) {
		if (n==null) {
			
			return -1;
		}else {
			if ((int)ele<(int)n.getElemento()) {
				return obtenerNivelElemento(ele,n.getIzquierdo(),nivel+1);
			}else if ((int)ele>(int)n.getElemento()) {
				return obtenerNivelElemento(ele,n.getDerecho(),nivel+1);
			}else {
				return nivel;
			}
		}
	}

	
	public void imprimirArbolHorizontal(Nodo<T> n) {
		if (n==null) {
			return;
		}else {
			imprimirArbolHorizontal(n.getDerecho());
			String espacio =" ";
			
			for (int i = 0; i < 4*obtenerNivelElemento(n.getElemento(), raiz, 0); i++) {
				espacio+=" ";
			}
			
			//System.out.println(espacio+n.getElemento());
			imprimirArbolHorizontal(n.getIzquierdo());
		}
		
	}


	


	

}
