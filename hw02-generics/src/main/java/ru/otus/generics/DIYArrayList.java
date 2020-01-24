package ru.otus.generics;

import java.util.*;

public class DIYArrayList<E> implements List<E> {

    private static final int DEFAULT_INITIAL_ARRAY_LENGTH = 10;

    private int size = 0;
    private Object elements[];

    public DIYArrayList(){
        elements = new Object[DEFAULT_INITIAL_ARRAY_LENGTH];
    }

    public DIYArrayList(int initialArrayLength){
        if (initialArrayLength >= 0) {
            elements = new Object[initialArrayLength];
        } else {
            throw new IllegalArgumentException("Illegal Array length : " + initialArrayLength);
        }
    }

    @Override
    public int size() {
        if (size < 0)
            throw new IllegalStateException("size = " + size);
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        size++;
        return false;
    }

    @Override
    public boolean remove(Object o) {
        size--;
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        //ToDO correct size
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        //ToDO correct size
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        //ToDO correct size
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {
        size++;
    }

    @Override
    public E remove(int index) {
        size--;
        return null;
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
