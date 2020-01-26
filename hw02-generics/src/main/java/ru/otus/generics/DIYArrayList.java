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

    public DIYArrayList(int initialCapacity){
        if (initialCapacity > 0) {
            capacity = initialCapacity;
            elements = new Object[capacity];
            size = 0;
        } else {
            throw new IllegalArgumentException("Illegal Array length : " + initialCapacity);
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
        return (indexOf(o) >= 0) ;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyItr();
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
        capacity = DEFAULT_INITIAL_ARRAY_LENGTH;
        elements = new Object[capacity];
        size = 0;
    }

    private void checkIndex(int index) {
        if (index >= size()) {
            throw new IllegalArgumentException("Index must be < size. Index = " + index + ". Size = " + size);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) elements[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E oldElement = (E) elements[index];
        elements[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);
        if (index == size - 1) {
            add(element);
        } else {
            growIfNeeded();
            int remainingSize = size - index;
            System.arraycopy(elements, index , elements, index + 1, remainingSize);
            elements[index] = element;
            size++;
        }
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked")
        E removedElement = (E) elements[index];
        int remainingSize = size - index - 1;
        System.arraycopy(elements, index + 1, elements, index, remainingSize);
        size--;
        return removedElement;
    }

    @Override
    public int indexOf(Object o) {
        int i = 0;
        for (Object element : elements) {
            if (element.equals(o)) {
                return i;
            }
            i++;
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
        return new MyListItr(index);
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
            return (E) elements[cursor++];
        }
    }

    private class MyListItr extends MyItr implements ListIterator<E> {

        MyListItr() {
        }

        MyListItr(int index) {
            if (index >= size)
                throw new IllegalArgumentException("Index must be < size.");
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return (cursor > 0);
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            lastReturned = previousIndex();
            return (E) elements[lastReturned];
        }

        @Override
        public void remove() {
            if (lastReturned == -1)
                throw new IllegalStateException("No elements were returned yet. Not know which to remove.");
            DIYArrayList.this.remove(lastReturned);
            if (lastReturned < cursor) {
                cursor--;
            } else if (size() == cursor){
                cursor = size;
            }
            lastReturned = -1;
        }

        @Override
        public void set(E e) {
            DIYArrayList.this.set(lastReturned, e);
        }

        @Override
        public void add(E e) {
            DIYArrayList.this.add(nextIndex(), e);
        }
    }

}
