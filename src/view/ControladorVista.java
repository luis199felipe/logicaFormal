package view;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.HashMap;

import application.Main;
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

	/**
	 * 
	 * @param event
	 */
	@FXML
	public void guardarFormula(ActionEvent event) {

		if (!campoFormula.getText().isEmpty()) {

			if (verificarFormulaBienFormada()) {

				insertarAtomos();

				if (event.getSource().equals(botonGuardarConclusion)) {
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

				} else {
					premisas.add(campoFormula.getText());
				}
				campoFormula.clear();
//				tablaVerificacionValidez.getColumns().clear();	
//				validador.limpiarResultados();

				actualizarFormaEstandar();
			} else {
				Alert a = new Alert(AlertType.ERROR, "La formula no esta bien formada", ButtonType.OK);
				a.showAndWait();
			}

		} else {
			Alert a = new Alert(AlertType.ERROR, "El campo  de la formula no debe estar vacio", ButtonType.OK);
			a.showAndWait();
		}

	}

	private void insertarAtomos() {

		String ex = campoFormula.getText();
		for (int i = 0; i < ex.length(); i++) {
			String a = "";
			if ((ex.charAt(i) + "").equals("p") || (ex.charAt(i) + "").equals("q") || (ex.charAt(i) + "").equals("r")
					|| (ex.charAt(i) + "").equals("s") || (ex.charAt(i) + "").equals("t")
					|| (ex.charAt(i) + "").equals("u")) {
				a = ex.charAt(i) + "";

			}

			if (!a.isEmpty() && !atomosTotales.contains(a)) {
				System.out.println("EB");
				atomosTotales.add(a);
			}
		}
		for (int i = 0; i < atomosTotales.size(); i++) {

			System.out.println(atomosTotales.get(i));
		}

	}

	/**
	 * 
	 */

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

	/**
	 * 
	 * @param event
	 */
	@FXML
	public void insertarOperador(ActionEvent event) {

		if (campoFormula.isDisabled() == false) {
			try {
				boolean flag = true;
				if (campoFormula.getCaretPosition() != 0 || !campoFormula.getText().isEmpty()) {
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
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			Alert a = new Alert(AlertType.ERROR,
					"Debe Limpiar todo para ingresar un nuevo argumento (Opciones/limpiar)", ButtonType.OK);
			a.showAndWait();
		}
	}

	/**
	 * 
	 * @param event
	 */
	@FXML
	public void insertarAtomo(ActionEvent event) {
		String atomo = "";

		if (campoFormula.isDisabled() == false) {
			try {

				int posSig = campoFormula.getCaretPosition() + 1;
				int posAnt = campoFormula.getCaretPosition() - 1;
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
				if (campoFormula.getText(posAnt, posSig).equals("()")) {

					if (!atomosTotales.contains(atomo)) {
//					atomosTotales.add(atomo);
						desbloquearBoton(atomo);
					}
					campoFormula.insertText(campoFormula.getCaretPosition(), atomo + "");
				}
			} catch (IndexOutOfBoundsException e) {
				if (campoFormula.getText().isEmpty()) {
					campoFormula.setText(atomo);
					if (!atomosTotales.contains(atomo)) {
//					atomosTotales.add(atomo);
						desbloquearBoton(atomo);

					}
				}

			}
		} else {
			Alert a = new Alert(AlertType.ERROR,
					"Debe Limpiar todo para ingresar un nuevo argumento (Opciones/limpiar)", ButtonType.OK);
			a.showAndWait();
		}

	}

	/**
	 * 
	 * @param a
	 */
	private void desbloquearBoton(String a) {
		switch (a) {
		case "p":
			botonAtomoQ.setDisable(false);
			break;
		case "q":
			botonAtomoR.setDisable(false);

			break;
		case "r":
			botonAtomoS.setDisable(false);

			break;
		case "s":
			botonAtomoT.setDisable(false);

			break;
		case "t":
			botonAtomoU.setDisable(false);

			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * @param event
	 */
	@FXML
	public void moverCaret(ActionEvent event) {
		if (event.getSource().equals(botonAnt)) {

			int a = campoFormula.getCaretPosition();

			if (a > 0) {

				int pos = a - 1;

				campoFormula.positionCaret(pos);
			}

		} else {
			int a = campoFormula.getCaretPosition();

			if (a < campoFormula.getText().length() + 1) {

				int pos = a + 1;
				campoFormula.positionCaret(pos);
			}
		}
		campoFormula.requestFocus();
	}

	/**
	 * 
	 */

	@FXML
	public void eliminarAtomo() {
		campoFormula.setText("");
//		botonGuardarPremisa.setDisable(true);
//		botonGuardarConclusion.setDisable(true);

	}

	@FXML
	public void salir() {
		System.exit(0);

	}

	@FXML
	public void verInfo() {
		Alert a = new Alert(AlertType.INFORMATION, "Elaborado por " + "\n" + "Luis Felipe Tejada" + "\n"
				+ "Neyder Figueroa" + "\n" + "Melissa Alvarez" + "\n" + "V1.0", ButtonType.OK);
		a.setHeaderText("Acerca de");
		a.showAndWait();

	}

	@FXML
	public void verManual() {
		try {
			File temp = new File(System.getProperty("java.io.tmpdir") + "manualUsuario.pdf");
			InputStream flujoEntrada = this.getClass().getResourceAsStream("/res/manual_usuario_1.pdf");
			FileOutputStream flujoSalida = new FileOutputStream(temp);
			FileWriter fw = new FileWriter(temp);
			byte[] buffer = new byte[1024 * 512];

			int control;

			while ((control = flujoEntrada.read(buffer)) != -1) {
				flujoSalida.write(buffer, 0, control);
			}

			fw.close();
			flujoSalida.close();
			flujoEntrada.close();

			Desktop.getDesktop().open(temp);
		} catch (IOException ex) {
		}

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
				premisaElim = null;

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
		for (int i = 0; i < atomosTotales.size(); i++) {

			System.err.println(atomosTotales.get(i));
		}

	}

	@FXML
	public void limpiarTodo() {

		premisas.clear();
		campoConclusion.clear();
		campoFormula.setText("");
		conclusion = "";
		campoConclusionFinal.setText("");
		tablaVerificacionValidez.getColumns().clear();
		atomosTotales.clear();
		validador.limpiarResultados();
		premisasOperables.clear();
		premisasVisuales.clear();
		premisaAeliminar = 0;
		premisaElim = null;
		actualizarFormaEstandar();

		botonAtomoQ.setDisable(true);
		botonAtomoR.setDisable(true);
		botonAtomoS.setDisable(true);
		botonAtomoT.setDisable(true);
		botonAtomoU.setDisable(true);

		separadorConclusion.setVisible(false);
		campoFormula.setDisable(false);
		campoConclusion.setVisible(false);

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
			System.out.println(premisaElim.getId());

			premisaAeliminar = Integer.parseInt(premisaElim.getId());
		}

	};

	/*
	 * Aqui inicia la parte de validacion de fbf y consecuencia logica
	 */

	private boolean verificarFormulaBienFormada() {

		return !campoFormula.getText().contains("()");

	}

	@FXML
	public void verificarValidez() {

		if (premisas.size() >= 2 && !conclusion.isEmpty()) {
			for (int i = 0; i < premisas.size(); i++) {

				String prem = premisas.get(i);
				prem = prem.replace("<->", "y");
				prem = prem.replace("->", "e");
				premisasOperables.add(prem);

			}

			conclusion = conclusion.replace("<->", "y");
			conclusion = conclusion.replace("->", "e");
			premisasOperables.add(conclusion);

//		premisasOperables.add("(p)e(q)");
//		premisasOperables.add("p");
//		premisasOperables.add("q");

			System.out.println("Numero de  atomos" + atomosTotales.size());
			boolean validez = validador.verificarValidezConjuntoFormulas(premisasOperables, atomosTotales.size());
			System.err.println(validez);
			cargarTabla(validador.getResultados());
			validador.limpiarResultados();

			mostrarConclusion(validez);

			campoFormula.setDisable(true);
		} else {
			Alert a = new Alert(AlertType.ERROR, "Recuerde que minimo debe ingresar 2 premisas y una conclusion",
					ButtonType.OK);
			a.showAndWait();
		}

	}

	private void mostrarConclusion(boolean validez) {
		if (validez) {
			campoConclusionFinal
					.setText("EL argumento es valido, ya que la conclusion es consecuencia logica de las premsias");
		} else {
			campoConclusionFinal.setText(
					"EL argumento  NO es valido, ya que la conclusion  NO es consecuencia logica de las premsias");
		}

	}

	private void cargarTabla(ArrayList<char[]> resultados) {

		ObservableList<StringProperty> x = FXCollections.observableArrayList();
		for (int i = 0; i < (int) Math.pow(2, atomosTotales.size()); i++) {

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

			String p = premisasOperables.get(i);
			p = p.replace("y", "<->");
			p = p.replace("e", "->");

			nuevaColumna.setText(p);

			int a = i;
			nuevaColumna.setCellValueFactory(
					cellData -> new SimpleStringProperty(cellData.getValue().get().charAt(a) + ""));

		}

	}

}
