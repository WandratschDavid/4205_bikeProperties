package model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import serial.Catalog;

import java.io.*;

public class Bike implements Externalizable
{
    StringProperty frameNr = new SimpleStringProperty();
    StringProperty brandType = new SimpleStringProperty();
    StringProperty color = new SimpleStringProperty();

    private final BooleanBinding btnSelectEnabled = frameNr.isNotEmpty().and(frameNr.length().greaterThanOrEqualTo(5));
    private final BooleanBinding btnSaveEnabled = (frameNr.isNotEmpty().and(frameNr.length().greaterThanOrEqualTo(5)))
            .and(brandType.isNotEmpty().and(brandType.length().greaterThanOrEqualTo(3)));
    private final BooleanBinding tfEnabled = (frameNr.isNotEmpty().and(frameNr.length().greaterThanOrEqualTo(5)))
            .and(brandType.isNotEmpty().and(brandType.length().greaterThanOrEqualTo(3)));


    private Catalog catalog;

    public Bike()
    {
        if (frameNr.getValue() == null)
        {
            frameNr.setValue(" ");
        }
        if (brandType.getValue() == null)
        {
            brandType.setValue(" ");
        }
    }

    public static Bike select(String frameNr)
    {
        return Catalog.getInstance().selectBikeByFrameNr(frameNr);
    }

    public void save()
    {
        catalog.getInstance().save(this);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(frameNr.get());
        out.writeObject(brandType.get());
        out.writeObject(color.get());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        frameNr.setValue((String)in.readObject());
        brandType.setValue((String)in.readObject());
        color.setValue((String)in.readObject());

        System.out.println(frameNr.getValue());
        System.out.println(brandType.getValue());
        System.out.println(color.getValue());
    }

    //Generated Methods
    public String getFrameNr() {
        return frameNr.get();
    }

    public StringProperty frameNrProperty() {
        return frameNr;
    }

    public void setFrameNr(String frameNr) {
        this.frameNr.set(frameNr);
    }

    public String getBrandType() {
        return brandType.get();
    }

    public StringProperty brandTypeProperty() {
        return brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType.set(brandType);
    }

    public String getColor() {
        return color.get();
    }

    public StringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public BooleanBinding btnSaveEnabledProperty() {
        return btnSaveEnabled;
    }

    public BooleanBinding btnSelectEnabledProperty() {
        return btnSelectEnabled;
    }

    public BooleanBinding tfEnabledProperty() {
        return tfEnabled;
    }
}