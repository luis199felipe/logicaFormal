package view;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Tree;

public class ControladorVista {

	@FXML
	private Button botonGuardarConclusion;

	@FXML
	private Button botonSig;

	@FXML
	private Button botonDisyuncion;

	@FXML
	private Separator separadorConclusion;

	@FXML
	private Button botonVerificarFBF;

	@FXML
	private Button botonVerificarValidez;

	@FXML
	private Button botonEliminarTodo;

	@FXML
	private Button botonAtomoP;

	@FXML
	private Button botonAtomoQ;

	@FXML
	private Button botonAtomoR;

	@FXML
	private Button botonAtomoS;

	@FXML
	private Button botonAtomoT;

	@FXML
	private Button botonAtomoU;

	@FXML
	private VBox boxFormulas;

	@FXML
	private Button botonBicondicional;

	@FXML
	private Button botonEliminarAtomo;

	@FXML
	private Button botonAnt;

	@FXML
	private TextField premisa1Prueba;

	@FXML
	private Button botonNegar;

	@FXML
	private TextArea campoFormula;

	@FXML
	private TableView<String> tablaVerificacionValidez;

	@FXML
	private Button botonCondicional;

	@FXML
	private Button botonGuardarPremisa;

	@FXML
	private Button botonConjuncion;

	@FXML
	private TextArea campoConclusionFinal;

	@FXML
	private TextField campoConclusion;

	@FXML
	private Button botonEliminarFormula;

	private ArrayList<String> operadores;

	// esta es la lista de premisas normalitas con las que se va a trabajar(estan
	// tal y como se ingresan en el campo)
	// para cuando esten aca ya debieron haber sido validadas
	private ArrayList<String> premisas;

	private ArrayList<TextField> premisasVisuales;

	private String conclusion;

	// este numero de atomos solo se puede usar en la verficacion de fbf
	private int numeroAtomos;

	private int premisaAeliminar;

	private TextField premisaElim;

	/*
	 * duda: pueden haber premisas repetidas?
	 * 
	 * 
	 */

	@FXML
	public void initialize() {

		operadores = new ArrayList<>();
		operadores.add("~(),2");
		operadores.add("()->(),1");
		operadores.add("()<->(),1");
		operadores.add("()^(),1");
		operadores.add("()v(),1");
		campoFormula.addEventFilter(KeyEvent.ANY, filter);
		conclusion = "";
		premisas = new ArrayList<>();
		premisasVisuales = new ArrayList<>();
	}

	@FXML
	public void guardarFormula(ActionEvent event) {

		if (event.getSource().equals(botonGuardarPremisa)) {

			premisas.add(campoFormula.getText());

		} else {
			if (conclusion.isEmpty()) {
				conclusion = campoFormula.getText();

			} else {
				Alert a = new Alert(AlertType.CONFIRMATION, "Desea sobreescribir la conclusion", ButtonType.NO,
						ButtonType.YES);
				a.showAndWait();
				if (a.getResult().equals(ButtonType.YES)) {
					conclusion = campoFormula.getText();
				}
			}
		}
		botonGuardarPremisa.setDisable(true);
		botonGuardarConclusion.setDisable(true);

		campoFormula.clear();
		actualizarFormaEstandar();
	}

	private void actualizarFormaEstandar() {
		campoConclusion.setText(conclusion);
		boxFormulas.getChildren().clear();
		premisasVisuales.clear();

		for (int i = 0; i < premisas.size(); i++) {

			TextField nuevaPremisa = new TextField(premisas.get(i));
			nuevaPremisa.setEditable(false);
			nuevaPremisa.setCursor(Cursor.DISAPPEAR);
			nuevaPremisa.setId("" + i);
			nuevaPremisa.setAlignment(Pos.TOP_CENTER);
			boxFormulas.getChildren().add(nuevaPremisa);
			nuevaPremisa.addEventFilter(MouseEvent.MOUSE_CLICKED, filter2);

			premisasVisuales.add(nuevaPremisa);
		}
		separadorConclusion.setVisible(true);
		campoConclusion.setVisible(true);
		boxFormulas.getChildren().add(separadorConclusion);
		boxFormulas.getChildren().add(campoConclusion);

	}

	@FXML
	public void insertarOperador(ActionEvent event) {

		boolean flag = true;
		if (campoFormula.getCaretPosition() != 0) {
			int posSig = campoFormula.getCaretPosition() + 1;
			int posAnt = campoFormula.getCaretPosition() - 1;

			if (!campoFormula.getText(posAnt, posSig).equals("()")) {
				flag = false;
			}
		}
		if (flag) {
			int op = 0;
			if (event.getSource().equals(botonConjuncion)) {
				op = 3;

			} else if (event.getSource().equals(botonDisyuncion)) {
				op = 4;

			} else if (event.getSource().equals(botonNegar)) {
				op = 0;

			} else if (event.getSource().equals(botonCondicional)) {
				op = 1;

			} else if (event.getSource().equals(botonBicondicional)) {
				op = 2;

			}

			String a = operadores.get(op);
			int posCaretPreInsertion = campoFormula.getCaretPosition();
			campoFormula.insertText(campoFormula.getCaretPosition(), a.split(",")[0] + "");

			int posToMove = Integer.parseInt(a.split(",")[1]);
			campoFormula.positionCaret(posCaretPreInsertion + posToMove);
			campoFormula.requestFocus();
		}

	}

	@FXML
	public void insertarAtomo(ActionEvent event) {

		try {
			String atomo = "";
			if (!campoFormula.getText().isEmpty()) {

				int posSig = campoFormula.getCaretPosition() + 1;
				int posAnt = campoFormula.getCaretPosition() - 1;

				if (campoFormula.getText(posAnt, posSig).equals("()")) {

					if (event.getSource().equals(botonAtomoP)) {
						atomo = "p";
					} else if (event.getSource().equals(botonAtomoQ)) {
						atomo = "q";

					} else if (event.getSource().equals(botonAtomoR)) {
						atomo = "r";

					} else if (event.getSource().equals(botonAtomoS)) {
						atomo = "s";

					} else if (event.getSource().equals(botonAtomoU)) {
						atomo = "u";

					} else if (event.getSource().equals(botonAtomoT)) {
						atomo = "t";

					}
					numeroAtomos++;
					campoFormula.insertText(campoFormula.getCaretPosition(), atomo + "");
				} else {

				}
			}
		} catch (IndexOutOfBoundsException e) {

		}
	}

	@FXML
	public void moverCaret(ActionEvent event) {
		if (event.getSource().equals(botonAnt)) {

			int a = campoFormula.getCaretPosition();

			if (a > 0) {
				campoFormula.positionCaret(a - 1);
			}

		} else {
			int a = campoFormula.getCaretPosition();

			if (a < campoFormula.getText().length() + 1) {
				campoFormula.positionCaret(a + 1);
			}
		}
		campoFormula.requestFocus();
	}

	@FXML
	public void eliminarAtomo() {
		campoFormula.setText("");
		numeroAtomos = 0;
		botonGuardarPremisa.setDisable(true);
		botonGuardarConclusion.setDisable(true);

	}

	@FXML
	public void eliminarFormula() {

		if (!premisas.isEmpty()) {

			if (premisaElim != null) {
				premisas.remove(premisaAeliminar);
				actualizarFormaEstandar();
				System.out.println("ebtra");
			} else {
				Alert a = new Alert(AlertType.ERROR, "Debe seleccionar una premisa", ButtonType.OK);
				a.showAndWait();
			}
		} else {
			Alert a = new Alert(AlertType.ERROR, "No hay premisas", ButtonType.OK);
			a.showAndWait();
		}
	}

	@FXML
	public void limpiarTodo() {

		premisas.clear();
		campoConclusion.clear();
		conclusion = "";
		actualizarFormaEstandar();
		separadorConclusion.setVisible(false);
		campoConclusion.setVisible(false);
		numeroAtomos = 0;

	}

	private EventHandler<KeyEvent> filter = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			event.consume();
		}

	};
	private EventHandler<MouseEvent> filter2 = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			premisaElim = (TextField) event.getSource();

			premisaAeliminar = Integer.parseInt(premisaElim.getId());
		}

	};

	/*
	 * Aqui inicia la parte de validacion de fbf y consecuencia logica
	 */
	@FXML
	public void verificarFBF() {

		if (!campoFormula.getText().isEmpty()) {

			System.out.println(numeroAtomos);

			try {
				String campoFormula = "(p)^((q)v(p))";
				int atomicosIntroducidos = 3;
				verificarFormulaBienFormada(campoFormula, atomicosIntroducidos) ;
				
				botonGuardarPremisa.setDisable(false);
				botonGuardarConclusion.setDisable(false);
				
			} catch (Exception e) {
				
			}
		} else {
			Alert a = new Alert(AlertType.ERROR, "El campo  de la formula no debe estar vacio", ButtonType.OK);
			a.showAndWait();
		}

	}

	public Tree<Character> verificarFormulaBienFormada(String exp, int atomicosIntroducidos) {
		Tree<Character> arbol = new Tree<>();
		crearArbol(arbol, exp);

		if (arbol.contarHojas() >= atomicosIntroducidos) {
			return arbol;
		}
		return null;
	}

	private Character crearArbol(Tree<Character> arbol, String exp) {

		int contPar = 0;
		char simbolo = 0;
		boolean flag = true;
		for (int i = 0; i < exp.length() && flag; i++) {
			char l = exp.charAt(i);
			contPar += compararParentesis(l);
			if (contPar == 0 && i > 0 && i + 1 < exp.length() - 1) {
				simbolo = exp.charAt(i + 1);
				if (arbol.estaVacio()) {
					arbol.agregarIzq(exp.charAt(i + 1));
				}
				String exp1 = exp.substring(1, i);
				String exp2 = exp.substring(i + 3, exp.length() - 1);
				System.out.println("Parte 1:" + exp1);
				System.out.println("Parte 2:" + exp2);
				if (esAtomico(exp1.charAt(0))) {
					arbol.agregarOrd(exp1.charAt(0), -1);
				} else {
					Tree<Character> subArbol = new Tree<>();
					crearArbol(subArbol, exp2);
					arbol.agregarOrdNodo(subArbol.getRaiz(), 1);
				}

				if (esAtomico(exp2.charAt(0))) {
					arbol.agregarOrd(exp1.charAt(0), 1);
				} else {
					Tree<Character> subArbol = new Tree<>();
					crearArbol(subArbol, exp2);
					arbol.agregarOrdNodo(subArbol.getRaiz(), 1);
				}

				flag = false;
			}
		}
		return simbolo;

	}

	public int compararParentesis(char l) {
		if (l == '(') {
			return 1;
		} else if (l == ')') {
			return -1;
		}
		return 0;
	}

	public boolean esAtomico(char l) {

		if (l == '~' || l == 'p' || l == 'q') {
			return true;
		} else {
			return false;
		}
	}

	@FXML
	public void verificarValidez() {
		ArrayList<String> formulas = new ArrayList<>();
		String conclusion = "";
		int[][] matriz = new int[(int) Math.pow(2, formulas.size())][formulas.size() + 1];

		String exp = "(p)^((q)v(p))";
		HashMap<Character, Character> valores = new HashMap<>();
		valores.put('p', '0');
		valores.put('q', '1');
		valores.put('1', '1');
		valores.put('0', '0');

		Tree<Character> arbol = verificarFormulaBienFormada(exp, 2);
		arbol.setValorHojas(arbol.getRaiz(), 0, valores);
		arbol.evaluarInPreOrder(arbol.getRaiz(), valores);
		System.out.println(arbol.getRaiz().getValor());
	}

}
