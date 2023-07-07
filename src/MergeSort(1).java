// Code written by Peter Ye.
import java.util.ArrayList;
import java.util.List;

public class MergeSort {
    public static List mergeSort(List unsortedList) {
        if (unsortedList.size() == 1) {
            return unsortedList;
        } else {
            List firstHalf = unsortedList.subList(0, unsortedList.size() / 2);
            List secondHalf = unsortedList.subList(unsortedList.size() / 2, unsortedList.size());
            return merge(mergeSort(firstHalf), mergeSort(secondHalf));
        }
    }
    // This is the merge function that combines two sorted list into one larger sorted list.
    public static List merge(List list1, List list2) {
        List combinedList = new ArrayList(list1.size() + list2.size());
        int index1 = 0;
        int index2 = 0;
        while (index1 < list1.size() && index2 < list2.size()) {
            if (((Comparable) list1.get(index1)).compareTo( (list2.get(index2))) < 0) {
                combinedList.add(list1.get(index1));
                index1 += 1;
            } else {
                combinedList.add(list2.get(index2));
                index2 += 1;
            }
        }
        if (index1 == list1.size()) {
            combinedList.addAll(list2.subList(index2, list2.size()));
        } else {
            combinedList.addAll(list1.subList(index1, list1.size()));
        }
        return combinedList;
    }
}
