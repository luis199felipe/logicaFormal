package view;

import java.util.ArrayList;

import java.util.HashMap;

import application.Validador;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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
	private TableView<StringProperty> tablaVerificacionValidez;

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

	private Validador validador;

	// esta es la lista de premisas normalitas con las que se va a trabajar(estan
	// tal y como se ingresan en el campo)
	// para cuando esten aca ya debieron haber sido validadas
	private ArrayList<String> premisas;
	private ArrayList<String> premisasOperables;
	private ArrayList<String> atomosTotales;
	private int atomosParciales;
	private ArrayList<TextField> premisasVisuales;
	private String conclusion;
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
		premisasOperables = new ArrayList<>();
		premisasVisuales = new ArrayList<>();
		atomosTotales = new ArrayList<>();
		validador = new Validador();
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
		atomosParciales = 0;
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
					atomosParciales++;
					if (!atomosTotales.contains(atomo)) {
						atomosTotales.add(atomo);
					}
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
		atomosParciales = 0;
		botonGuardarPremisa.setDisable(true);
		botonGuardarConclusion.setDisable(true);

	}

	@FXML
	public void eliminarFormula() {

		if (!premisas.isEmpty()) {

			if (premisaElim != null) {
				String p = premisas.get(premisaAeliminar);
				premisas.remove(premisaAeliminar);
				actualizarFormaEstandar();
				// si elimina una premisa hay que tener en cuenta
				// la cantidad de atomos totales
				//

				if (p.contains("p")) {
					buscarActualizar("p");
				}

				if (p.contains("q")) {
					buscarActualizar("q");

				}
				if (p.contains("r")) {
					buscarActualizar("r");

				}
				if (p.contains("s")) {
					buscarActualizar("s");

				}
				if (p.contains("t")) {
					buscarActualizar("t");

				}
				if (p.contains("u")) {
					buscarActualizar("u");

				}

			} else {
				Alert a = new Alert(AlertType.ERROR, "Debe seleccionar una premisa", ButtonType.OK);
				a.showAndWait();
			}
		} else {
			Alert a = new Alert(AlertType.ERROR, "No hay premisas", ButtonType.OK);
			a.showAndWait();
		}
	}

	private void buscarActualizar(String s) {

		boolean esta = false;
		for (int i = 0; i < premisas.size() && esta == false; i++) {
			if (premisas.get(i).contains(s)) {
				esta = true;
			}
		}

		if (conclusion.contains(s)) {
			esta = true;
		}
		if (!esta) {
			atomosTotales.remove(s);
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
		atomosParciales = 0;
		atomosTotales.clear();
		
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

			try {
				validador.verificarFormulaBienFormada(campoFormula.getText(), atomosParciales);

				botonGuardarPremisa.setDisable(false);
				botonGuardarConclusion.setDisable(false);

			} catch (Exception e) {

			}
		} else {
			Alert a = new Alert(AlertType.ERROR, "El campo  de la formula no debe estar vacio", ButtonType.OK);
			a.showAndWait();
		}

	}

	@FXML
	public void verificarValidez() {

//		for (int i = 0; i < premisas.size(); i++) {
//
//			String prem = premisas.get(i);
//			prem = prem.replace("<->", "s");
//			prem = prem.replace("->", "e");
//			premisasOperables.add(prem);
//
//		}
//
//		conclusion = conclusion.replace("<->", "s");
//		conclusion = conclusion.replace("->", "e");
//		premisasOperables.add(conclusion);

		premisasOperables.add("(p)e(q)");
		premisasOperables.add("p");
		premisasOperables.add("q");

		boolean validez = validador.verificarValidezConjuntoFormulas(premisasOperables, 2);
		//cargarTabla(validador.getResultados());
		//validador.limpiarResultados();

		//mostrarConclusion(validez);

	}

	private void mostrarConclusion(boolean validez) {
		if (validez) {
			campoConclusionFinal
					.setText("EL argumento es valido, ya que la conclusion es consecuencia logica de las premsias");
		} else {
			campoConclusionFinal
			.setText("EL argumento  NO es valido, ya que la conclusion  NO es consecuencia logica de las premsias");
		}

	}

	private void cargarTabla(ArrayList<char[]> resultados) {

		ObservableList<StringProperty> x = FXCollections.observableArrayList();
		for (int i = 0; i < (int) Math.pow(2, 2); i++) {

			String a = "";
			for (int j = 0; j < resultados.size(); j++) {

				a += (resultados.get(j)[i]);
			}
			x.add(new SimpleStringProperty(a));

		}
		tablaVerificacionValidez.setItems(x);

		for (int i = 0; i < premisasOperables.size(); i++) {
			TableColumn<StringProperty, String> nuevaColumna = new TableColumn<>();
			tablaVerificacionValidez.getColumns().add(nuevaColumna);

			nuevaColumna.setText(premisasOperables.get(i));

			int a = i;
			nuevaColumna.setCellValueFactory(
					cellData -> new SimpleStringProperty(cellData.getValue().get().charAt(a) + ""));

		}

	}

}
