/*
 * Copyright (C) 2017 Bruce Asu<bruceasu@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *  　　
 * 　　The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package me.asu.sort;

import java.lang.reflect.Array;
import java.util.Comparator;

/**
 * @author Suk Honzeon 增加泛型支持
 * @version 1.1
 * @since 2004-10-21
 */
public class SortUtil {
  public final static int INSERT = 1;
  public final static int BUBBLE = 2;
  public final static int SELECTION = 3;
  public final static int SHELL = 4;
  public final static int QUICK = 5;
  public final static int IMPROVED_QUICK = 6;
  public final static int MERGE = 7;
  public final static int IMPROVED_MERGE = 8;
  public final static int HEAP = 9;

  public static <T> void sort(T[] data) {
    sort(data, IMPROVED_QUICK);
  }

  private static String[] name = {"insert", "bubble", "selection", "shell",
      "quick", "improved_quick", "merge", "improved_merge", "heap"};

  private static AbstractSort[] impl = new AbstractSort[]{new InsertSort(),
      new BubbleSort(), new SelectionSort(), new ShellSort(),
      new QuickSort(), new ImprovedQuickSort(), new MergeSort(),
      new ImprovedMergeSort(), new HeapSort()};

  public static String toString(int algorithm) {
    return name[algorithm - 1];
  }

  public static <T> void sort(T[] data, int algorithm) {
    impl[algorithm - 1].sort(data);
  }

  public static <T> void sort(T[] data, String algorithm) {
    int index = -1;
    if (data == null || data.length == 0) {
      return;
    }

    for (int i = 0, j = data.length; i < j; i++) {
      if (name.equals(algorithm)) {
        index = i;
        break;
      }
    }
    if (index != -1) {
      impl[index].sort(data);
    }

  }

  public static <T> void sort(T[] data, AbstractSort sorter) {
    sorter.sort(data);
  }

  public static <T> void sort(T[] data, int algorithm, Comparator<? super T> c) {
    impl[algorithm - 1].sort(data, c);
  }

  public static <T> void sort(T[] data, String algorithm,
                              Comparator<? super T> c) {
    int index = -1;
    if (data == null || data.length == 0) {
      return;
    }

    for (int i = 0, j = data.length; i < j; i++) {
      if (name.equals(algorithm)) {
        index = i;
        break;
      }
    }
    if (index != -1) {
      impl[index].sort(data, c);
    }
  }

  public static <T> void sort(T[] data, AbstractSort sorter,
                              Comparator<? super T> c) {
    sorter.sort(data, c);
  }

  /**
   * @author Suk Honzeon
   * @version 1.0
   * @since 2004-10-21
   */
  public static class InsertSort extends AbstractSort {
    @SuppressWarnings("unchecked")
    public <T> void sort(T[] data) {
      for (int i = 1; i < data.length; i++) {
        for (int j = i; (j > 0)
            && (((Comparable) data[j]).compareTo(data[j - 1]) < 0); j--) {
          swap(data, j, j - 1);
        }
      }
    }

    public <T> void sort(T[] data, Comparator<? super T> c) {
      for (int i = 1; i < data.length; i++) {
        for (int j = i; (j > 0)
            && (c.compare(data[j], data[j - 1]) < 0); j--) {
          swap(data, j, j - 1);
        }
      }
    }

  }

  /**
   * @author Suk Honzeon
   * @version 1.0
   * @since 2004-10-21
   */
  public static class BubbleSort extends AbstractSort {
    @SuppressWarnings("unchecked")
    public <T> void sort(T[] data) {
      for (int i = 0; i < data.length; i++) {
        for (int j = data.length - 1; j > i; j--) {
          if (((Comparable) data[j]).compareTo(data[j - 1]) < 0) {
            swap(data, j, j - 1);
          }
        }
      }
    }

    public <T> void sort(T[] data, Comparator<? super T> c) {
      for (int i = 0; i < data.length; i++) {
        for (int j = data.length - 1; j > i; j--) {
          if (c.compare(data[j], data[j - 1]) < 0) {
            swap(data, j, j - 1);
          }
        }
      }
    }
  }

  /**
   * @author Suk Honzeon
   * @version 1.0
   * @since 2004-10-21
   */
  public static class SelectionSort extends AbstractSort {

    @SuppressWarnings("unchecked")
    public <T> void sort(T[] data) {
      for (int i = 0; i < data.length; i++) {
        int lowIndex = i;
        for (int j = data.length - 1; j > i; j--) {
          if (((Comparable) data[j]).compareTo(data[lowIndex]) < 0) {
            lowIndex = j;
          }
        }
        swap(data, i, lowIndex);
      }
    }

    public <T> void sort(T[] data, Comparator<? super T> c) {
      for (int i = 0; i < data.length; i++) {
        int lowIndex = i;
        for (int j = data.length - 1; j > i; j--) {
          if (c.compare(data[j], data[lowIndex]) < 0) {
            lowIndex = j;
          }
        }
        swap(data, i, lowIndex);
      }
    }
  }

  /**
   * @author Suk Honzeon
   * @version 1.0
   * @since 2004-10-21
   */
  public static class ShellSort extends AbstractSort {

    public <T> void sort(T[] data) {
      for (int i = data.length / 2; i > 2; i /= 2) {
        for (int j = 0; j < i; j++) {
          insertSort(data, j, i);
        }
      }
      insertSort(data, 0, 1);
    }

    public <T> void sort(T[] data, Comparator<? super T> c) {
      for (int i = data.length / 2; i > 2; i /= 2) {
        for (int j = 0; j < i; j++) {
          insertSort(data, j, i, c);
        }
      }
      insertSort(data, 0, 1, c);
    }

    /**
     * @param data
     * @param start
     * @param inc
     */
    @SuppressWarnings("unchecked")
    private <T> void insertSort(T[] data, int start, int inc) {
      for (int i = start + inc; i < data.length; i += inc) {
        for (int j = i; (j >= inc)
            && (((Comparable) data[j]).compareTo(data[j - inc]) < 0); j -= inc) {
          swap(data, j, j - inc);
        }
      }
    }

    /**
     * @param data
     * @param start
     * @param inc
     * @param c
     */
    private <T> void insertSort(T[] data, int start, int inc,
                                Comparator<? super T> c) {
      for (int i = start + inc; i < data.length; i += inc) {
        for (int j = i; (j >= inc)
            && (c.compare(data[j], data[j - inc]) < 0); j -= inc) {
          swap(data, j, j - inc);
        }
      }
    }

  }

  /**
   * @author Suk Honzeon
   * @version 1.0
   * @since 2004-10-21
   */
  public static class QuickSort extends AbstractSort {
    public <T> void sort(T[] data) {
      quickSort(data, 0, data.length - 1);
    }

    @Override
    public <T> void sort(T[] data, Comparator<? super T> c) {
      quickSort(data, 0, data.length - 1, c);
    }

    private <T> void quickSort(T[] data, int i, int j) {
      int pivotIndex = (i + j) / 2;
      // swap
      swap(data, pivotIndex, j);

      int k = partition(data, i - 1, j, data[j]);
      swap(data, k, j);
      if ((k - i) > 1)
        quickSort(data, i, k - 1);
      if ((j - k) > 1)
        quickSort(data, k + 1, j);

    }

    /**
     * @param data
     * @param l
     * @param r
     * @param pivot
     * @return int
     */
    @SuppressWarnings("unchecked")
    private <T> int partition(T[] data, int l, int r, T pivot) {
      do {
        while (((Comparable) data[++l]).compareTo(pivot) < 0)
          ;
        while ((r != 0)
            && (((Comparable) data[--r]).compareTo(pivot) > 0))
          ;
        swap(data, l, r);
      } while (l < r);
      swap(data, l, r);
      return l;
    }

    private <T> void quickSort(T[] data, int i, int j,
                               Comparator<? super T> c) {
      int pivotIndex = (i + j) / 2;
      // swap
      swap(data, pivotIndex, j);

      int k = partition(data, i - 1, j, data[j], c);
      swap(data, k, j);
      if ((k - i) > 1)
        quickSort(data, i, k - 1);
      if ((j - k) > 1)
        quickSort(data, k + 1, j);

    }

    /**
     * @param data
     * @param l
     * @param r
     * @param pivot
     * @return int
     */
    private <T> int partition(T[] data, int l, int r, T pivot,
                              Comparator<? super T> c) {
      do {
        while (c.compare(data[++l], pivot) < 0)
          ;
        while ((r != 0) && (c.compare(data[--r], pivot) > 0))
          ;
        swap(data, l, r);
      } while (l < r);
      swap(data, l, r);
      return l;
    }

  }

  /**
   * @author Suk Honzeon
   * @version 1.0
   * @since 2004-10-21
   */
  public static class ImprovedQuickSort extends AbstractSort {

    private static int MAX_STACK_SIZE = 4096;
    private static int THRESHOLD = 10;

    @SuppressWarnings("unchecked")
    public <T> void sort(T[] data) {
      int[] stack = new int[MAX_STACK_SIZE];

      int top = -1;
      T pivot;
      int pivotIndex, l, r;

      stack[++top] = 0;
      stack[++top] = data.length - 1;

      while (top > 0) {
        int j = stack[top--];
        int i = stack[top--];

        pivotIndex = (i + j) / 2;
        pivot = data[pivotIndex];

        swap(data, pivotIndex, j);

        // partition
        l = i - 1;
        r = j;
        do {
          while (((Comparable) data[++l]).compareTo(pivot) < 0)
            ;
          while ((r != 0)
              && (((Comparable) data[--r]).compareTo(pivot) > 0))
            ;
          swap(data, l, r);
        } while (l < r);
        swap(data, l, r);
        swap(data, l, j);

        if ((l - i) > THRESHOLD) {
          stack[++top] = i;
          stack[++top] = l - 1;
        }
        if ((j - l) > THRESHOLD) {
          stack[++top] = l + 1;
          stack[++top] = j;
        }

      }
      // new InsertSort().sort(data);
      insertSort(data);
    }

    public <T> void sort(T[] data, Comparator<? super T> c) {
      int[] stack = new int[MAX_STACK_SIZE];

      int top = -1;
      T pivot;
      int pivotIndex, l, r;

      stack[++top] = 0;
      stack[++top] = data.length - 1;

      while (top > 0) {
        int j = stack[top--];
        int i = stack[top--];

        pivotIndex = (i + j) / 2;
        pivot = data[pivotIndex];

        swap(data, pivotIndex, j);

        // partition
        l = i - 1;
        r = j;
        do {
          while (c.compare(data[++l], pivot) < 0)
            ;
          while ((r != 0) && (c.compare(data[--r], pivot) > 0))
            ;
          swap(data, l, r);
        } while (l < r);
        swap(data, l, r);
        swap(data, l, j);

        if ((l - i) > THRESHOLD) {
          stack[++top] = i;
          stack[++top] = l - 1;
        }
        if ((j - l) > THRESHOLD) {
          stack[++top] = l + 1;
          stack[++top] = j;
        }

      }
      // new InsertSort().sort(data);
      insertSort(data, c);
    }

    /**
     * @param data
     */
    @SuppressWarnings("unchecked")
    private <T> void insertSort(T[] data) {
      for (int i = 1; i < data.length; i++) {
        for (int j = i; (j > 0)
            && (((Comparable) data[j]).compareTo(data[j - 1]) < 0); j--) {
          swap(data, j, j - 1);
        }
      }
    }

    private <T> void insertSort(T[] data, Comparator<? super T> c) {
      for (int i = 1; i < data.length; i++) {
        for (int j = i; (j > 0)
            && (c.compare(data[j], data[j - 1]) < 0); j--) {
          swap(data, j, j - 1);
        }
      }
    }
  }

  /**
   * @author Suk Honzeon
   * @version 1.0
   * @since 2004-10-21
   */
  public static class MergeSort extends AbstractSort {

    @SuppressWarnings("unchecked")
    public <T> void sort(T[] data) {
      if (data == null || data.length == 0) {
        return;
      }
      T[] temp = (T[]) Array.newInstance(data[0].getClass(), data.length);
      mergeSort(data, temp, 0, data.length - 1);
    }

    @SuppressWarnings("unchecked")
    public <T> void sort(T[] data, Comparator<? super T> c) {
      if (data == null || data.length == 0) {
        return;
      }
      T[] temp = (T[]) Array.newInstance(data[0].getClass(), data.length);
      mergeSort(data, temp, 0, data.length - 1, c);
    }

    @SuppressWarnings("unchecked")
    private <T> void mergeSort(T[] data, T[] temp, int l, int r) {
      int mid = (l + r) / 2;
      if (l == r)
        return;
      mergeSort(data, temp, l, mid);
      mergeSort(data, temp, mid + 1, r);
      for (int i = l; i <= r; i++) {
        temp[i] = data[i];
      }
      int i1 = l;
      int i2 = mid + 1;
      for (int cur = l; cur <= r; cur++) {
        if (i1 == mid + 1)
          data[cur] = temp[i2++];
        else if (i2 > r)
          data[cur] = temp[i1++];
        else if (((Comparable) temp[i1]).compareTo(temp[i2]) < 0)
          data[cur] = temp[i1++];
        else
          data[cur] = temp[i2++];
      }
    }

    private <T> void mergeSort(T[] data, T[] temp, int l, int r,
                               Comparator<? super T> c) {
      int mid = (l + r) / 2;
      if (l == r)
        return;
      mergeSort(data, temp, l, mid);
      mergeSort(data, temp, mid + 1, r);
      for (int i = l; i <= r; i++) {
        temp[i] = data[i];
      }
      int i1 = l;
      int i2 = mid + 1;
      for (int cur = l; cur <= r; cur++) {
        if (i1 == mid + 1)
          data[cur] = temp[i2++];
        else if (i2 > r)
          data[cur] = temp[i1++];
        else if (c.compare(temp[i1], temp[i2]) < 0)
          data[cur] = temp[i1++];
        else
          data[cur] = temp[i2++];
      }
    }

  }

  /**
   * @author Suk Honzeon
   * @version 1.0
   * @since 2004-10-21
   */
  public static class ImprovedMergeSort extends AbstractSort {

    private static final int THRESHOLD = 10;

    @SuppressWarnings("unchecked")
    public <T> void sort(T[] data) {
      if (data == null || data.length == 0) {
        return;
      }
      T[] temp = (T[]) Array.newInstance(data[0].getClass(), data.length);
      mergeSort(data, temp, 0, data.length - 1);
    }

    @SuppressWarnings("unchecked")
    public <T> void sort(T[] data, Comparator<? super T> c) {
      if (data == null || data.length == 0) {
        return;
      }
      T[] temp = (T[]) Array.newInstance(data[0].getClass(), data.length);
      mergeSort(data, temp, 0, data.length - 1, c);
    }

    private <T> void mergeSort(T[] data, T[] temp, int l, int r,
                               Comparator<? super T> c) {
      int i, j, k;
      int mid = (l + r) / 2;
      if (l == r)
        return;
      if ((mid - l) >= THRESHOLD)
        mergeSort(data, temp, l, mid, c);
      else
        insertSort(data, l, mid - l + 1, c);
      if ((r - mid) > THRESHOLD)
        mergeSort(data, temp, mid + 1, r, c);
      else
        insertSort(data, mid + 1, r - mid, c);

      for (i = l; i <= mid; i++) {
        temp[i] = data[i];
      }
      for (j = 1; j <= r - mid; j++) {
        temp[r - j + 1] = data[j + mid];
      }
      T a = temp[l];
      T b = temp[r];
      for (i = l, j = r, k = l; k <= r; k++) {
        if (c.compare(a, b) < 0) {
          data[k] = temp[i++];
          a = temp[i];
        } else {
          data[k] = temp[j--];
          b = temp[j];
        }
      }
    }

    /**
     * @param data
     * @param start
     * @param len
     * @param c
     */
    private <T> void insertSort(T[] data, int start, int len,
                                Comparator<? super T> c) {
      for (int i = start + 1; i < start + len; i++) {
        for (int j = i; (j > start)
            && (c.compare(data[j], data[j - 1]) < 0); j--) {
          swap(data, j, j - 1);
        }
      }
    }

    @SuppressWarnings("unchecked")
    private <T> void mergeSort(T[] data, T[] temp, int l, int r) {
      int i, j, k;
      int mid = (l + r) / 2;
      if (l == r)
        return;
      if ((mid - l) >= THRESHOLD)
        mergeSort(data, temp, l, mid);
      else
        insertSort(data, l, mid - l + 1);
      if ((r - mid) > THRESHOLD)
        mergeSort(data, temp, mid + 1, r);
      else
        insertSort(data, mid + 1, r - mid);

      for (i = l; i <= mid; i++) {
        temp[i] = data[i];
      }
      for (j = 1; j <= r - mid; j++) {
        temp[r - j + 1] = data[j + mid];
      }
      T a = temp[l];
      T b = temp[r];
      for (i = l, j = r, k = l; k <= r; k++) {
        if (((Comparable) a).compareTo(b) < 0) {
          data[k] = temp[i++];
          a = temp[i];
        } else {
          data[k] = temp[j--];
          b = temp[j];
        }
      }
    }

    /**
     * @param data
     * @param start
     * @param len
     */
    @SuppressWarnings("unchecked")
    private <T> void insertSort(T[] data, int start, int len) {
      for (int i = start + 1; i < start + len; i++) {
        for (int j = i; (j > start)
            && ((Comparable) data[j]).compareTo(data[j - 1]) < 0; j--) {
          swap(data, j, j - 1);
        }
      }
    }

  }

  /**
   * @author Suk Honzeon
   * @version 1.0
   * @since 2004-10-21
   */
  public static class HeapSort extends AbstractSort {
    public <T> void sort(T[] data) {
      MaxHeap<T> h = new MaxHeap<T>();
      h.init(data);
      for (int i = 0; i < data.length; i++)
        h.remove();
      System.arraycopy(h.queue, 1, data, 0, data.length);
    }

    public <T> void sort(T[] data, Comparator<? super T> c) {
      MaxHeap<T> h = new MaxHeap<T>();
      h.init(data, c);
      for (int i = 0; i < data.length; i++)
        h.remove();
      System.arraycopy(h.queue, 1, data, 0, data.length);
    }

    private class MaxHeap<T> {
      private int size = 0;

      private T[] queue;

      private Comparator<? super T> c = null;

      @SuppressWarnings("unchecked")
      void init(T[] data, Comparator<? super T> c) {
        if (data == null || data.length == 0) {
          return;
        }

        this.queue = (T[]) Array.newInstance(data[0].getClass(),
            data.length + 1);

        this.c = c;

        for (int i = 0; i < data.length; i++) {
          queue[++size] = data[i];
          fixUp(size);
        }
      }

      @SuppressWarnings("unchecked")
      void init(T[] data) {
        if (data == null || data.length == 0) {
          return;
        }

        this.queue = (T[]) Array.newInstance(data[0].getClass(),
            data.length + 1);
        for (int i = 0; i < data.length; i++) {
          queue[++size] = data[i];
          fixUp(size);
        }
      }

      public void remove() {
        swap(queue, 1, size--);
        fixDown(1);
      }

      // fixdown
      @SuppressWarnings("unchecked")
      private void fixDown(int k) {
        int j;
        if (this.c == null) {
          while ((j = k << 1) <= size) {
            if (j < size
                && (((Comparable) queue[j])
                .compareTo(queue[j + 1]) < 0))
              j++;
            if (((Comparable) queue[k]).compareTo(queue[j]) > 0) // 不用交换
              break;
            swap(queue, j, k);
            k = j;
          }
        } else {
          while ((j = k << 1) <= size) {
            if (j < size && (c.compare(queue[j], queue[j + 1]) < 0))
              j++;
            if (c.compare(queue[k], queue[j]) > 0) // 不用交换
              break;
            swap(queue, j, k);
            k = j;
          }
        }
      }

      @SuppressWarnings("unchecked")
      private void fixUp(int k) {
        if (this.c == null) {
          while (k > 1) {
            int j = k >> 1;
            if (((Comparable) queue[k]).compareTo(queue[j]) < 0)
              break;
            swap(queue, j, k);
            k = j;
          }
        } else {
          while (k > 1) {
            int j = k >> 1;
            if (c.compare(queue[k], queue[j]) < 0)
              break;
            swap(queue, j, k);
            k = j;
          }

        }
      }

    }

  }

  public static void main(String[] args) {
    Integer[] data = new Integer[]{3, 2, 4, 6, 8, 9, 0, 1, 5, 7};
    SortUtil.sort(data, 7);
    printArray(data);

  }

  public static <T> void printArray(T[] data) {
    if (data == null || data.length == 0) {
      System.out.println("There's no data!");
    }

    for (T t : data) {
      System.out.print(t);
      System.out.print(" ");
    }
    System.out.println();
  }


}
