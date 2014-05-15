package week1;

import com.google.common.primitives.Ints;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int[] a = load("c:\\work\\projects\\java\\coursera\\alg1\\src\\week1\\IntegerArray.txt");
        long count = new Main().countInversions(a);
        System.out.println(count);
    }

    public long countInversions(int[] array) {
        int[] helper = new int[array.length];
        return mergesort(array, helper, 0, array.length - 1);
    }

    private long mergesort(int[] array, int[] helper, int lo, int hi) {
        long count = 0;
        if (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            count += mergesort(array, helper, lo, mid);
            count += mergesort(array, helper, mid + 1, hi);
            count += merge(array, helper, lo, mid, hi);
        }
        return count;
    }

    private long merge(int[] array, int[] helper, int lo, int mid, int hi) {
        System.arraycopy(array, lo, helper, lo, hi + 1 - lo);
        long count = 0;
        int hL = lo;
        int hR = mid + 1;
        int current = lo;

        while (hL <= mid && hR <= hi) {
            if (helper[hL] <= helper[hR]) {
                array[current] = helper[hL];
                hL++;
            } else {
                count += (mid - hL + 1);
                array[current] = helper[hR];
                hR++;
            }
            current++;
        }
        while (hL <= mid) {
            array[current] = helper[hL];
            hL++;
            current++;
        }
        return count;
    }


    public static int[] load(String file) {
        List<Integer> list = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = r.readLine()) != null) {
                list.add(Integer.parseInt(line));
            }
        } catch (Exception ignored) {
            throw new RuntimeException(ignored);
        }
        return Ints.toArray(list);
    }
}
