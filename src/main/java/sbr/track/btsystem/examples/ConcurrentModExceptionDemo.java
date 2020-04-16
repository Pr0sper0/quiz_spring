package sbr.track.btsystem.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class ConcurentModException {
    public static void main(String[] args) {
        List<String> listOfPhones = new ArrayList<String>(
                Arrays.asList("iPhone 6S", "iPhone 6", "iPhone 5", "Samsung Galaxy 4", "Lumia Nokia"));

        System.out.println("list of phones: " + listOfPhones);
        for (Iterator<String> itr = listOfPhones.iterator(); itr.hasNext();) {
            String temp = "Samsung";
            if (itr.next().startsWith(temp))
                itr.remove();
        }
        System.out.println("list of phones: " + listOfPhones);
    }
}