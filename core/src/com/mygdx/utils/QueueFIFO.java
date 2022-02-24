package com.mygdx.utils;

import java.lang.UnsupportedOperationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * A First in first out queue
 *
 * @param <T> Data type to store
 */
public class QueueFIFO<T> implements Queue<T> {
    private final ArrayList<T> data;
    private int topIndex;

    /**
     * Initialise all properties
     */
    public QueueFIFO() {
        topIndex = -1;
        data = new ArrayList<>();
    }


    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return data.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        topIndex++;
        return data.add(t);
    }

    @Override
    public boolean remove(Object o) {
        if (isEmpty()) {
            return false;
        }
        int i = data.indexOf(o);
        if (i == -1) {
            return false;
        }
        topIndex--;
        data.remove(i);
        return true;
    }

    public void remove(int index) {
        if (isEmpty()) {
            return;
        }
        topIndex--;
        data.remove(index);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return data.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean suc = data.addAll(c);
        if (suc) {
            topIndex = data.size();
        }
        return suc;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean suc = data.removeAll(c);
        if (suc) {
            topIndex = data.size();
        }
        return suc;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean suc = data.retainAll(c);
        if (suc) {
            topIndex = data.size();
        }
        return suc;
    }

    @Override
    public void clear() {
        data.clear();
    }

    /**
     * Not implemented
     */
    @Override
    public boolean offer(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        T t = data.remove(topIndex);
        topIndex--;
        return t;
    }

    public T pop() {
        return remove();
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        topIndex--;
        return data.remove(topIndex + 1);
    }

    @Override
    public T element() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return data.get(topIndex);
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return data.get(topIndex);
    }
}
