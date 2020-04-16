package sbr.track.btsystem.tests;

import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectControllerTest {

    @Test
    public void listReversals() {
        final List<Integer> givenList = Arrays.asList(1, 2, 3, 4, 5);
        final List<Integer> expectedList = Arrays.asList(5, 4, 3, 2, 1);
        assertEquals(expectedList.size(), reverseRecursive(givenList).size());
        assertEquals(expectedList.size(), reverseIterative(givenList).size());
    }

    private List<Integer> reverseRecursive(List<Integer> list) {
        if (list.size() <= 1) {
            return list;
        } else {
            List<Integer> reversed = new ArrayList<>();
            reversed.add(list.get(list.size() - 1));
            reversed.addAll(reverseRecursive(list.subList(0, list.size() - 1)));
            return reversed;
        }
    }

    private List<Integer> reverseIterative(final List<Integer> list) {
        for (int i = 0; i < list.size() / 2; i++) {
            final int tmp = list.get(i);
            list.set(i, list.get(list.size() - i - 1));
            list.set(list.size() - i - 1, tmp);
        }
        return list;
    }

    @Test
    public void weakReferenceStackManipulation() {
        final WeakReferenceStack<ValueContainer> stack = new WeakReferenceStack<>();
        final ValueContainer expected = new ValueContainer("Value for the stack");
        stack.push(new ValueContainer("Value for the stack"));
        ValueContainer peekedValue = stack.peek();
/*        assertEquals(expected, peekedValue);
        assertEquals(expected, stack.peek());*/
        peekedValue = null;
        System.gc();
        assertNull(stack.peek());
    }

    public class ValueContainer {
        private final String value;

        public ValueContainer(final String value) {
            this.value = value;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.printf("Finalizing for [%s]%n", toString());
        }
        /* equals, hashCode and toString omitted */
    }

}

class WeakReferenceStack<E> {
    private final List<WeakReference<E>> stackReferences;
    private int stackPointer = 0;

    public WeakReferenceStack() {
        this.stackReferences = new ArrayList<>();
    }

    public void push(E element) {
        this.stackReferences.add(stackPointer, new WeakReference<>(element));
        stackPointer++;
    }

    public E pop() {
        stackPointer--;
        //Interoperability between the JVM and the Java Language ❘155
        return this.stackReferences.get(stackPointer).get();
    }

    public E peek() {
        return this.stackReferences.get(stackPointer - 1).get();
    }
}
