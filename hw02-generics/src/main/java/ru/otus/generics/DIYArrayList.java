package ru.otus.generics;

import java.util.*;

public class DIYArrayList<E> implements List<E> {

    private static final int DEFAULT_INITIAL_ARRAY_LENGTH = 10;

    private int size = 0;
    private int capacity;
    private Object elements[];

    public DIYArrayList(){
        capacity = DEFAULT_INITIAL_ARRAY_LENGTH;
        elements = new Object[capacity];
        size = 0;
    }

    public DIYArrayList(int initialArrayLength){
        if (initialArrayLength >= 0) {
            capacity = initialArrayLength;
            elements = new Object[capacity];
            size = 0;
        } else {
            throw new IllegalArgumentException("Illegal Array length : " + initialArrayLength);
        }
    }

    public DIYArrayList(Collection<? extends E> initialCollection){
        this();
        if (initialCollection.size() > 0) {
            elements = initialCollection.toArray();
            size = elements.length;
            capacity = size;
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
        for (Object element : elements) {
            if (element.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        a = (T[]) Arrays.copyOf(elements , size, a.getClass());
        return a;
    }

    @Override
    public boolean add(E e) {
        if (size == capacity) {
            capacity = capacity * 2;
            elements = Arrays.copyOf(elements, capacity);
        }
        elements[size++] = e;
        return false;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        capacity = DEFAULT_INITIAL_ARRAY_LENGTH;
        elements = new Object[capacity];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        return (E) elements[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E element) {
        if (index >= size) {
            throw new IllegalArgumentException("Index must be less than size.  Index = " + index + ". Size = " + size);
        }
        E oldElement = (E) elements[index];
        elements[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
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
