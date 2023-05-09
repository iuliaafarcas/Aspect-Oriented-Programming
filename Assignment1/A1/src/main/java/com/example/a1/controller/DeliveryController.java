package com.example.a1.controller;

import com.example.a1.model.Delivery;


import com.example.a1.aspects.Observer;
import com.example.a1.service.DeliveryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@FxmlView("/mainUi.fxml")
public class DeliveryController {

    @Autowired
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
        Delivery newDelivery = new Delivery(null, nameField.getText(), Integer.valueOf(quantityField.getText()), Integer.valueOf(priceField.getText()));
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
            Delivery newDelivery = new Delivery(Long.valueOf(idUpdateField.getText()), nameUpdateField.getText(), Integer.valueOf(quantityUpdateField.getText()), Integer.valueOf(priceUpdateField.getText()));
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
