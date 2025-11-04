package co.edu.uniquindio.logisticsapp.controller;

import co.edu.uniquindio.logisticsapp.model.Address;
import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;
import co.edu.uniquindio.logisticsapp.util.adapter.CoordinateAdapter;
import co.edu.uniquindio.logisticsapp.util.adapter.CoordinateAdapterImpl;
import co.edu.uniquindio.logisticsapp.util.adapter.DMSConverter;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class UserAdressesController {
    @FXML
    private TableView<Address> tblAddresses;
    @FXML
    private TableColumn<Address, String> colAlias;
    @FXML
    private TableColumn<Address, String> colStreet;
    @FXML
    private TableColumn<Address, String> colCity;
    @FXML
    private TableColumn<Address, String> colLat;
    @FXML
    private TableColumn<Address, String> colLon;
    @FXML
    private TableColumn<Address, Void> colActions;

    @FXML
    private TextField txtAlias, txtStreet, txtCity, txtLat, txtLon;

    private final LogisticsRepository repository = LogisticsRepository.getInstance();
    private User currentUser;
    private ObservableList<Address> addressList;

    public void setUser(User user) {
        this.currentUser = user;
        loadAddresses();
    }

    @FXML
    public void initialize() {
        colAlias.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAlias()));
        colStreet.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStreet()));
        colCity.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCity()));
        colLat.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getLatitude())));
        colLon.setCellValueFactory(data -> new ReadOnlyStringWrapper(String.valueOf(data.getValue().getLongitude())));
    }

    private void loadAddresses() {
        if (currentUser != null) {
            addressList = FXCollections.observableArrayList(currentUser.getFrequentAddresses());
            tblAddresses.setItems(addressList);
            addActionButtons();
        }
    }

    private void addActionButtons() {
        colActions.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button("✏️");
            private final Button btnDelete = new Button("🗑");

            {
                btnEdit.setOnAction(event -> {
                    Address selected = getTableView().getItems().get(getIndex());
                    editAddress(selected);
                });

                btnDelete.setOnAction(event -> {
                    Address selected = getTableView().getItems().get(getIndex());
                    currentUser.getFrequentAddresses().remove(selected);
                    repository.updateUser(currentUser);
                    loadAddresses();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, btnEdit, btnDelete));
                }
            }
        });
    }

    private void editAddress(Address address) {
        TextInputDialog dialog = new TextInputDialog(address.getAlias());
        dialog.setTitle("Editar dirección");
        dialog.setHeaderText("Editar alias para: " + address.getStreet());
        dialog.setContentText("Nuevo alias:");
        dialog.showAndWait().ifPresent(newAlias -> {
            address.setAlias(newAlias);
            repository.updateUser(currentUser);
            loadAddresses();
        });
    }

    @FXML
    private void onAddAddress() {
        String alias = txtAlias.getText().trim();
        String street = txtStreet.getText().trim();
        String city = txtCity.getText().trim();
        String latText = txtLat.getText().trim();
        String lonText = txtLon.getText().trim();

        if (alias.isEmpty() || street.isEmpty() || city.isEmpty() || latText.isEmpty() || lonText.isEmpty()) {
            showAlert("Campos incompletos", "Debe llenar todos los campos.");
            return;
        }

        try {
            CoordinateAdapter adapter = new CoordinateAdapterImpl(new DMSConverter());
            double lat = adapter.convert(latText);
            double lon = adapter.convert(lonText);

            Address newAddress = new Address(null, alias, street, city, lat, lon);
            currentUser.addAddress(newAddress);
            repository.updateUser(currentUser);
            loadAddresses();

            txtAlias.clear();
            txtStreet.clear();
            txtCity.clear();
            txtLat.clear();
            txtLon.clear();

        } catch (NumberFormatException e) {
            showAlert("Error de formato", "Latitud y longitud deben ser numéricos.");
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
