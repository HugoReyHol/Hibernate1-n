module org.hugo.hibernate1n {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires java.persistence;

    opens org.hugo.hibernate1n to javafx.fxml;
    exports org.hugo.hibernate1n;
}