import java.util.Comparator;

public class PQ {
    // Heap based implementation of PriorityQueue
    // It is done by implementing the following helper functions as well as
    // swim, sing, swap, resize

    private City[] heap; // The heap used to store the data in
    private int size; // current size of the queue
    private Comparator<City> comparator; //the comparator to use between the objects
    
    // Serves for O[logn] time complexity for remove method
    private int[] index; // additional array to store the position of each city in the heap
    // Allows to quickly locate a city's position in the heap and update it efficiently

    private static final int DEFAULT_CAPACITY = 10; // default capacity

    // Queue constructor
    public PQ(Comparator<City> comparator) {
        this.heap = new City[DEFAULT_CAPACITY + 1];
        this.size = 0;
        this.comparator = comparator;
        this.index = new int[1000];
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Return number of active items in the queue
    public int size() {
        return size + 1;
    }

    // Retrieves, but doesn't remove, the smallest item (max priority), or returns null if the queue is empty
    public City min() {
        // Ensure not empty
        if (isEmpty()) return null;

        // return root without removing
        return heap[0];
    }

    // Retrieves and removes the head of the queue, or returns null if the queue is empty
    public City getMin() {
        // Ensure not empty
        if (isEmpty()) return null;

        // Keep a reference to the root item
        City root = heap[1];

        // Replace root item with the one at rightmost leaf
        heap[1] = heap[size];
        index[root.getID()] = 0; // update the index of the removed city
        size--;

        // Dispose of the rightmost leaf
        // Sink the new root element
        sink(1);

        // Return the int removed
        return root;
    }

    // Insert new item in the priority queue
    // If queue takes up 75% or more of the heap, double its size
    public void insert(City item) {
        // check if there is space available
        if (size >= heap.length * 0.75) {
            resize();
        }

        // Place item at the next available position
        size++;
        heap[size] = item;
        index[item.getID()] = size; // update the index of the inserted city

        // Let the newly added item swim, so as not to violate heap's identity
        swim(size);
    }

    public City remove(int id) {
        if (isEmpty()) return null;

        int indexToRemove = index[id];

        if (indexToRemove == 0) {
            // City with the specified ID is not in the heap
            return null;
        }

        City removedCity = heap[indexToRemove];
        heap[indexToRemove] = heap[size];
        index[removedCity.getID()] = 0; // update the index of the removed city
        size--;

        sink(indexToRemove);

        return removedCity;
    }

    /**  Helper function to swim items to the top
     * @param i The index of the item to swim
    */
    private void swim(int i) {
        // if i is the root (i==1) return
        if (i == 1) return;

        // find parent
        int parent = i / 2;

        // compare parent with child i
        while (i != 1 && comparator.compare(heap[i], heap[parent]) > 0) {
            swap(i, parent);
            i = parent;
            parent = i / 2;
        }
    }

    /**
     * Helper function to swim items to the bottom
     *
     * @param i the index of the item to sink
     */
    private void sink(int i) {
        // determine left, right child
        int left = 2 * i;
        int right = left + 1;

        // if 2*i > size, node i is a leaf return
        if (left > size)
            return;

        // while haven't reached the leafs
        while (left <= size) {
            // Determine the largest child of node i
            int max = left;
            if (right <= size) {
                if (comparator.compare(heap[left], heap[right]) < 0)
                    max = right;
            }

            // If the heap condition holds, stop. Else swap and go on.
            // child smaller than parent
            if (comparator.compare(heap[i], heap[max]) >= 0)
                return;
            else {
                swap(i, max);
                i = max;
                left = i * 2;
                right = left + 1;
            }
        }
    }

    /** Helper function to swap two elements in the heap
     * 
     * @param i the first element's index
     * @param j the second element's index
     */
    private void swap(int i, int j) {
        City tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;

        // Update the index array after swapping
        index[heap[i].getID()] = i;
        index[heap[j].getID()] = j;
    }

    // Inspect item in PQ, without removing it
    public City peekItem(int index) {
        if (index >= 0 && index < size) {
            return heap[index];
        }
        return null;
    }
    
    // Helper function to grow the size of the heap
    public void resize() {
        City[] newHeap = new City[2 * heap.length];

        // copy array
	    //(notice: in the priority queue, elements are located in the array slots with positions in [1, size])
        for (int i = 0; i <= size; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }
}
