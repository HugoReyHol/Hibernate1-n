package org.hugo.hibernate1n.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hugo.hibernate1n.dao.CocheDAOImpl;
import org.hugo.hibernate1n.model.Coche;
import org.hugo.hibernate1n.util.HibernateUtil;
import org.hugo.hibernate1n.util.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;


public class CocheCtrll implements Initializable {

    @FXML
    private TextField inMatricula, inMarca, inModelo;

    @FXML
    private ChoiceBox<String> inTipo;

    @FXML
    private TableView<Coche> tablaCoches;

    @FXML
    private TableColumn<Coche, String> colMatricula, colMarca, colModelo, colTipo;

    private final ObservableList<Coche> coches = FXCollections.observableArrayList();
    private final ObservableList<String> tipos = FXCollections.observableArrayList();

    private Coche cocheCargado = null;

    private final CocheDAOImpl cocheDAO = new CocheDAOImpl();

    private final SessionFactory factory = HibernateUtil.getSessionFactory();
    private final Session session = HibernateUtil.getSession();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        coches.addAll(cocheDAO.listarCoches(session));
        tablaCoches.setItems(coches);

        String[] listaTipos = new String[]{"Familiar", "Deportivo", "Todo Terreno"};

        tipos.addAll(new ArrayList<>(List.of(listaTipos)));
        inTipo.setItems(tipos);

    }


    public void onLimpiar(ActionEvent actionEvent) {
        inMatricula.setText("");
        inMarca.setText("");
        inModelo.setText("");
        inTipo.setValue("");
        cocheCargado = null;
        tablaCoches.getSelectionModel().clearSelection();
    }

    public void onGuardar(ActionEvent actionEvent) {
        Coche c = new Coche();

        comprobarEntrada(c);

        if (!cocheDAO.guardarCoche(c, session)) return;


        cocheCargado = c;

        coches.add(c);
        tablaCoches.getSelectionModel().select(c);

    }

    public void onActualizar(ActionEvent actionEvent) {
        if (cocheCargado == null) {
            return;
        }

        comprobarEntrada(cocheCargado);

        cocheDAO.actualizarCoche(cocheCargado, session);

        tablaCoches.refresh();
    }

    public void onEliminar(ActionEvent actionEvent) {
        if (cocheCargado == null){
            return;
        }

        cocheDAO.eliminarCoche(cocheCargado, session);

        coches.remove(cocheCargado);

        cocheCargado = null;

        onLimpiar(actionEvent);

    }

    public void onClic(MouseEvent mouseEvent) {
        Coche c = tablaCoches.getSelectionModel().getSelectedItem();

        if (c == null){
            return;
        }

        inMatricula.setText(c.getMatricula());
        inMarca.setText(c.getMarca());
        inModelo.setText(c.getModelo());
        inTipo.setValue(c.getTipo());

        cocheCargado = c;

        System.out.println(c.getId_coche());
    }

    public void onMultas(ActionEvent actionEvent) throws IOException {
        if (cocheCargado == null) return;

        FXMLLoader fxmlLoader = new FXMLLoader(R.getUI("multas.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        MultasCtrll multasCtrll = fxmlLoader.getController();
        multasCtrll.setMultas(cocheCargado, session);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Multas");
        stage.showAndWait();

    }

    public void close() {
        System.out.println("Saliendo de la aplicación");

        session.close();
        factory.close();

    }

    private void comprobarEntrada(Coche c) {
        c.setMarca(inMarca.getText().isEmpty() ? null : inMarca.getText());
        c.setMatricula(inMatricula.getText().isEmpty() ? null : inMatricula.getText());
        c.setModelo(inModelo.getText().isEmpty() ? null : inModelo.getText());
        c.setTipo(inTipo.getValue());

    }
}