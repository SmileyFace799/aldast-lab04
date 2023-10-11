package no.ntnu.idata2302.lab04;

import java.util.ArrayList;

public class Heap {

    public static Heap fromValues(int... values) {
        var heap = new Heap();
        for (var each : values) {
            heap.insert(each);
        }
        return heap;
    }

    private final ArrayList<Integer> array;

    public Heap() {
        array = new ArrayList<>();
        array.add(0);
    }

    private void topHeapify(int size, int keyIndex) {
        int smallestIndex = keyIndex;
        int leftIndex = leftChildOf(keyIndex);
        int rightIndex = rightChildOf(keyIndex);

        if (leftIndex < size && array.get(leftIndex) < array.get(smallestIndex)) {
            smallestIndex = leftIndex;
        }
        if (rightIndex < size && array.get(rightIndex) < array.get(smallestIndex)) {
            smallestIndex = rightIndex;
        }

        if (smallestIndex != keyIndex) {
            swap(smallestIndex, keyIndex);
            topHeapify(size, smallestIndex);
        }
    }

    private void topHeapify() {
        topHeapify(array.size(), 1);
    }

    private void bottomHeapify(int keyIndex) {
        if (keyIndex > 1) {
            int parentIndex = parentOf(keyIndex);
            if (array.get(keyIndex) < array.get(parentIndex)) {
                swap(keyIndex, parentIndex);
                bottomHeapify(parentIndex);
            }
        }
    }

    private void bottomHeapify() {
        bottomHeapify(array.size() - 1);
    }

    public void insert(int k) {
        array.add(k);
        bottomHeapify();
    }

    public int takeMinimum() {
        int minimum = array.get(1);
        array.set(1, array.get(array.size() - 1));
        topHeapify();
        return minimum;
    }

    public void decreaseKey(int i, int k) {
        // TODO: Implement this operation
        throw new RuntimeException("Not yet implemented");
    }

    private int parentOf(int index) {
        return index / 2;
    }

    private int leftChildOf(int index) {
        return index * 2;
    }

    private int rightChildOf(int index) {
        return index * 2 + 1;
    }

    void swap(int pos1, int pos2) {
        int temp = array.get(pos1);
        array.set(pos1, array.get(pos2));
        array.set(pos2, temp);
    }
}
