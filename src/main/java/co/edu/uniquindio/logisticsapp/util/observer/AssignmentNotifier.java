package co.edu.uniquindio.logisticsapp.util.observer;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AssignmentNotifier {
    private static final AssignmentNotifier INSTANCE = new AssignmentNotifier();

    private final SimpleBooleanProperty assignmentOccurred = new SimpleBooleanProperty(false);

    private AssignmentNotifier() {

    }

    public static AssignmentNotifier getInstance() {
        return INSTANCE;
    }

    public ReadOnlyBooleanProperty assignmentOccurredProperty() {
        return assignmentOccurred;
    }

    /**
     * Llama a este método para notificar que una asignación ha ocurrido.
     * Cambia el valor de la propiedad, disparando el evento.
     */
    public void notifyAssignmentChange() {

        assignmentOccurred.set(!assignmentOccurred.get());
    }
}
