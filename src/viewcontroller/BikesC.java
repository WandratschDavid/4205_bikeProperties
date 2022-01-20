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

import java.io.IOException;

public class BikesC
{
    @FXML
    private TextField tfFrameNr;

    @FXML
    private TextField tfBrandType;

    @FXML
    private TextField tfColor;

    //@FXML
    //private Button btnCancel;

    @FXML
    private Button btnSelect;

    @FXML
    private Button btnSave;

    private Bike bike;

    public static void show(Stage stage)
    {
        try
        {
            Parent root = FXMLLoader.load(BikesC.class.getResource("BikesV.fxml"));

            Scene scene = new Scene(root);
            stage.setTitle("Bikes");
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
        bindModel();
    }

    private void bindModel()
    {
        // Verbindung zu altem Model lösen
        if (bike != null)
        {
            tfFrameNr.textProperty().unbindBidirectional(bike.frameNrProperty());
            tfBrandType.textProperty().unbindBidirectional(bike.brandTypeProperty());
            tfColor.textProperty().unbindBidirectional(bike.colorProperty());
        }

        // neues Model merken
        bike = new Bike();

        // Neues Model verbinden
        bike.frameNrProperty().bindBidirectional(tfFrameNr.textProperty());
        bike.brandTypeProperty().bindBidirectional(tfBrandType.textProperty());
        bike.colorProperty().bindBidirectional(tfColor.textProperty());

        // Enable/Disable Select-Button && Save-Button
        BooleanBinding frameNrEntered = tfFrameNr.textProperty().length().greaterThanOrEqualTo(5);
        BooleanBinding brandTypeEntered = tfBrandType.textProperty().length().greaterThanOrEqualTo(3);
        BooleanBinding colorEntered = tfColor.textProperty().length().greaterThanOrEqualTo(3);

        btnSelect.disableProperty().bind(frameNrEntered.and(brandTypeEntered).not());

        btnSave.disableProperty().bind(frameNrEntered.and(brandTypeEntered).and(colorEntered).not());
    }

    @FXML
    private void action_save()
    {
        bike.save();
    }

    @FXML
    private void action_select()
    {
        Bike newBike = Bike.select(tfFrameNr.getText());

        if(newBike == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bike not found!");
            alert.setResizable(true);
            alert.setContentText(String.format("The bike with the frameNr %s was not found!", tfFrameNr.getText()));
            alert.showAndWait();
        }
        else
        {
            tfFrameNr.textProperty().unbind();
            newBike.frameNrProperty().bind(tfFrameNr.textProperty());

            tfBrandType.textProperty().unbind();
            tfBrandType.textProperty().bind(newBike.brandTypeProperty());

            tfColor.textProperty().unbind();
            tfColor.textProperty().bind(newBike.colorProperty());

            btnSave.disableProperty().unbind();
            btnSave.disableProperty().bind(newBike.btnSaveEnabledProperty().not());

            btnSelect.disableProperty().unbind();
            btnSelect.disableProperty().bind(newBike.btnSelectEnabledProperty().not());

            tfBrandType.disableProperty().unbind();
            tfBrandType.disableProperty().bind(newBike.tfEnabledProperty().not());

            tfColor.disableProperty().unbind();
            tfColor.disableProperty().bind(newBike.tfEnabledProperty().not());
        }
    }

    @FXML
    private void action_cancel()
    {
        Platform.exit();
    }
}