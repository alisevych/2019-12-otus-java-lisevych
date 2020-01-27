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
        return (indexOf(o) >= 0) ;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyItr();
    }

    @Override
    public E[] toArray() {
        @SuppressWarnings("unchecked") E[] newArray = (E[]) Arrays.copyOf(elements, size);
        return newArray;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        throw new UnsupportedOperationException();
    }

    private void growIfNeeded() {
        if (size == capacity) {
            capacity = capacity * 2;
            elements = Arrays.copyOf(elements, capacity);
        }
    }

    @Override
    public boolean add(E e) {
        growIfNeeded();
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
        throw new UnsupportedOperationException();
    }

    private void checkIndex(int index) {
        if (index >= size()) {
            throw new IllegalArgumentException("Index must be < size. Index = " + index + ". Size = " + size);
        }
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked") E element = (E) elements[index];
        return element;
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        @SuppressWarnings("unchecked")E oldElement = (E) elements[index];
        elements[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked") E removedElement = (E) elements[index];
        int remainingSize = size - index - 1;
        System.arraycopy(elements, index + 1, elements, index, remainingSize);
        size--;
        return removedElement;
    }

    @Override
    public int indexOf(Object o) {
        for (int i=0; i < size(); i++) {
            if (elements[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListItr();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private class MyItr implements Iterator<E> {

        protected int cursor = 0;
        protected int lastReturned = -1;

        public boolean hasNext() {
            return cursor < size();
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            lastReturned = cursor;
            @SuppressWarnings("unchecked") E element = (E) elements[cursor++];
            return element;
        }
    }

    private class MyListItr extends MyItr implements ListIterator<E> {

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            DIYArrayList.this.set(lastReturned, e);
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }

}
