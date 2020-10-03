package com.webcheckers.model;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Model class for a Row
 * @author Shubhang Mehrotra
 */
public class Row implements Iterable {
    private int index;

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public Spliterator spliterator() {
        return null;
    }

    public int getIndex() {
        return index;
    }
}
