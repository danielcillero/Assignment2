package assignment2;

import java.util.Comparator;





public class DistanceComparator implements Comparator<Sol_Kmeans> {

	@Override
    public int compare(Result a, Result b) {
       return a.distance < b.distance ? -1 : a.distance == b.distance ? 0 : 1;
    }

}
