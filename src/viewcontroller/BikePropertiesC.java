package viewcontroller;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Bike;
import serial.Catalog;

import java.io.IOException;

public class BikePropertiesC
{
    @FXML
    private TextField tfFrameNr;

    @FXML
    private TextField tfBrandType;

    @FXML
    private TextField tfColor;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSelect;

    @FXML
    private Button btnSave;

    Bike bike;

    public static void show(Stage stage)
    {
        try
        {
            Parent root = FXMLLoader.load(BikePropertiesC.class.getResource("BikePropertiesV.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("BikeProperties");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Platform.exit();
        }
    }

    @FXML
    public void initialize()
    {
        // Enable/Disable Select-Button && Save-Button
        BooleanBinding frameNrEntered = tfFrameNr.textProperty().length().greaterThanOrEqualTo(5);
        BooleanBinding brandTypeEntered = tfBrandType.textProperty().length().greaterThanOrEqualTo(3);
        BooleanBinding colorEntered = tfColor.textProperty().length().greaterThanOrEqualTo(3);

        btnSelect.disableProperty().bind(frameNrEntered.and(brandTypeEntered).not());

        btnSave.disableProperty().bind(frameNrEntered.and(brandTypeEntered).and(colorEntered).not());
    }

    private void bindModel(Bike bikeNew)
    {
        // Verbindung zu altem Model lösen
        if (bike != null)
        {
            tfFrameNr.textProperty().unbindBidirectional(bike.frameNrProperty());
            tfBrandType.textProperty().unbindBidirectional(bike.brandTypeProperty());
            tfColor.textProperty().unbindBidirectional(bike.colorProperty());
        }

        //  neues Model merken
        bike = bikeNew;

        // Neues Model verbinden
        if (bike != null)
        {
            tfFrameNr.textProperty().bindBidirectional(bike.frameNrProperty());
            tfBrandType.textProperty().bindBidirectional(bike.brandTypeProperty());
            tfColor.textProperty().bindBidirectional(bike.colorProperty());
        }
    }

    private void action_save()
    {
        bike.save();
    }
}