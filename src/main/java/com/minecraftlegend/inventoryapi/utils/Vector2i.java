package com.minecraftlegend.inventoryapi.utils;


import java.util.Objects;

/**
 * @Author Sauerbier | Jan
 * @Copyright 2016 by Jan Hof
 * All rights reserved.
 **/
public class Vector2i implements Cloneable {

    private int x, y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i(Vector2i vec) {
        this.x = vec.getX();
        this.y = vec.getY();
    }

    public void add(Vector2i vector2i) {
        x += vector2i.getX();
        y += vector2i.getY();
    }


    public void sub(Vector2i vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
    }

    public void multiply(Vector2i vec) {
        x *= vec.getX();
        y *= vec.getY();
    }

    public void multiply(int m) {
        x *= m;
        y *= m;
    }

    public void divide(Vector2i vec) {
        x /= vec.getX();
        y /= vec.getY();
    }

    public void divide(int m) {
        x /= m;
        y /= m;
    }

    public int scalar(Vector2i vec) {
        return (x + vec.getX()) * (y + vec.getY());
    }

    public double distance(Vector2i vec) {
        int dx = vec.getX() - x;
        int dy = vec.getY() - y;
        return new Vector2i(dx, dy).length();
    }

    public double angle(Vector2i vec) {
        double dx = vec.getX() - x;
        double dy = vec.getY() - y;
        return Math.atan2(dy, dx);
    }


    public Vector2i toPixelPrecision(int pixelMask) {
        return new Vector2i((int) x << pixelMask, (int) y << pixelMask);
    }

    public Vector2i toBlockPrecision(int pixelMask) {
        return new Vector2i((int) x >> pixelMask, (int) y >> pixelMask);
    }

    public int toInventoryPosition() {
        return x + y * 9;
    }

    public Vector2i max(Vector2i vec) {
        if (x + y > vec.getX() + vec.getY()) {
            return this;
        } else return vec;
    }

    public Vector2i min(Vector2i vec) {

        if (x + y < vec.getX() + vec.getY()) {
            return this;
        } else return vec;
    }


    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public Vector2i clone() {
        try {
            return (Vector2i) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        Objects.requireNonNull(obj);
        if (!(obj instanceof Vector2i)) throw new RuntimeException("Object has to be from type Vector2i");

        Vector2i other = (Vector2i) obj;

        if (other.getX() != x) return false;
        if (other.getY() != y) return false;
        return true;
    }

    @Override
    public String toString() {
        return "[x:" + x + ",y:" + y + "]";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}