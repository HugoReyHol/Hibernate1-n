package org.hugo.hibernate1n.util;

import javafx.scene.control.Alert;


public class AlertUtil {
    public static void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void mostrarInfo(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
