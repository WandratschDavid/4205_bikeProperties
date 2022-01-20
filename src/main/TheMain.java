package main;

import javafx.application.Application;
import javafx.stage.Stage;
import serial.Catalog;
import viewcontroller.BikePropertiesC;

public class TheMain extends Application
{
    @Override
    public void init()
    {
        Catalog.getInstance().restore();
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        BikePropertiesC.show(stage);
    }

    @Override
    public void stop() throws Exception
    {
        super.stop();
        Catalog.getInstance().persist();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}