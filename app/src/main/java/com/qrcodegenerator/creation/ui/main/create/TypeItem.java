package com.qrcodegenerator.creation.ui.main.create;

public class TypeItem {

    private int image;
    private int title;
    private int color;

    public TypeItem(int image, int title, int color) {
        this.image = image;
        this.title = title;
        this.color = color;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
