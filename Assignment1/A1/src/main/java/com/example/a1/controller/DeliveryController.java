package com.example.a1.controller;

import com.example.a1.Delivery;
import com.example.a1.service.DeliveryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.MouseEvent;
import java.util.stream.IntStream;

@Component
@FxmlView("/mainUi.fxml")
public class DeliveryController {
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @FXML
    public void initialize() {
        data = FXCollections.observableList(deliveryService.getList());
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView.setItems(data);
    }

    public void addDelivery() {
        Delivery newDelivery = Delivery.builder()
                .name(nameField.getText())
                .quantity(Integer.valueOf(quantityField.getText()))
                .price(Integer.valueOf(priceField.getText()))
                .build();
        try {
            Delivery savedDelivery = deliveryService.addDelivery(newDelivery);
            data.add(savedDelivery);
        } catch (Exception e) {
            showError(e, "Delivery not added!");
        }
    }
    private int getPositionInListForId(Long id) {
        return IntStream.range(0, data.size())
                .filter(position -> data.get(position).getId().equals(id))
                .findFirst()
                .getAsInt();

}
    public void updateDelivery() {

        try {
            Delivery newDelivery = Delivery.builder()
                    .id(Long.valueOf(idUpdateField.getText()))
                    .name(nameUpdateField.getText())
                    .quantity(Integer.valueOf(quantityUpdateField.getText()))
                    .price(Integer.valueOf(priceUpdateField.getText()))
                    .build();
            Delivery savedDelivery = deliveryService.updateDelivery(newDelivery);
            int deliveryIndex= getPositionInListForId(Long.valueOf(idUpdateField.getText()));
            data.set(deliveryIndex, savedDelivery);
        } catch (Exception e) {
            showError(e, "Delivery not updated!");
        }
    }

    public void deleteDelivery() {
        try {
            Long id = Long.valueOf(idDeleteField.getText());
            deliveryService.deleteDelivery(id);
            data.remove(getPositionInListForId(id));
            idDeleteField.clear();
        } catch (Exception e) {
            showError(e, "Delivery not deleted!");
        }
    }


    private void showError(Exception e, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(e.getMessage());
        alert.show();
    }

    private final DeliveryService deliveryService;
    @FXML
    private TableView<Delivery> tableView;

    @FXML
    private TableColumn<Delivery, Long> idTableColumn;

    @FXML
    private TableColumn<Delivery, String> nameTableColumn;
    @FXML
    private TableColumn<Delivery, Integer> quantityTableColumn;

    @FXML
    private TableColumn<Delivery, Integer> priceTableColumn;

    @FXML
    private TextField idDeleteField;
    @FXML
    private TextField idUpdateField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;

    @FXML
    private TextField nameUpdateField;
    @FXML
    private TextField priceUpdateField;
    @FXML
    private TextField quantityUpdateField;

    private ObservableList<Delivery> data;


}
