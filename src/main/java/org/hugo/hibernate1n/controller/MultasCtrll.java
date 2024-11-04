package org.hugo.hibernate1n.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hugo.hibernate1n.dao.MultaDAOImpl;
import org.hugo.hibernate1n.model.Coche;
import org.hugo.hibernate1n.model.Multa;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class MultasCtrll implements Initializable {

    @FXML
    private TextField inMatricula, inPrecio;

    @FXML
    private DatePicker inFecha;

    @FXML
    private TableView<Multa> tablaMultas;

    @FXML
    private TableColumn<Multa, String> colMatricula;
    @FXML
    private TableColumn<Multa, Double> colPrecio;
    @FXML
    private TableColumn<Multa, LocalDate> colFecha;

    private final ObservableList<Multa> multas = FXCollections.observableArrayList();

    private Multa multaCargada = null;

    private final MultaDAOImpl multaDAO = new MultaDAOImpl();

    private Session session;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

    }

    public void setMultas(Coche cocheCargado, Session session) {
        this.session = session;

        multas.addAll(multaDAO.listar(cocheCargado, session));
        tablaMultas.setItems(multas);

    }


    public void onLimpiar(ActionEvent actionEvent) {
        inMatricula.setText("");
        inPrecio.setText("");
        inFecha.setValue(null);
        multaCargada = null;
        tablaMultas.getSelectionModel().clearSelection();
    }

    public void onGuardar(ActionEvent actionEvent) {
        Multa m = new Multa();

        comprobarEntrada(m);

        if (!multaDAO.guardar(m, session)) return;

        multaCargada = m;

        multas.add(m);
        tablaMultas.getSelectionModel().select(m);

    }

    public void onActualizar(ActionEvent actionEvent) {
        if (multaCargada == null) {
            return;
        }

        comprobarEntrada(multaCargada);

        multaDAO.actualizar(multaCargada, session);

        tablaMultas.refresh();
    }

    public void onEliminar(ActionEvent actionEvent) {
        if (multaCargada == null){
            return;
        }

        multaDAO.eliminar(multaCargada, session);

        multas.remove(multaCargada);

        multaCargada = null;

        onLimpiar(actionEvent);

    }

    public void onClic(MouseEvent mouseEvent) {
        Multa m = tablaMultas.getSelectionModel().getSelectedItem();

        if (m == null){
            return;
        }

        inMatricula.setText(m.getMatricula());
        inPrecio.setText(String.valueOf(m.getPrecio()));
        inFecha.setValue(m.getFecha());

        multaCargada = m;

        System.out.println(m.getId_multa());
    }

    private void comprobarEntrada(Multa m) {
        m.setPrecio(Double.parseDouble(inPrecio.getText()));
        m.setFecha(inFecha.getValue());

//        c.setMatricula(inMatricula.getText().isEmpty() ? null : inMatricula.getText());

    }
}