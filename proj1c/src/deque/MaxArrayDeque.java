package deque;

import java.util.Comparator;
import java.util.List;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    Comparator<T> cp;
    public MaxArrayDeque(Comparator<T> c){
        cp = c;
    }

    public MaxArrayDeque(){}

    public T max(){
        if(size == 0){
            return null;
        }
        List<T> a = toList();
        T max = a.get(0);
        for(int i = 1;i < size;i++){
            if(cp.compare(max,a.get(i)) < 0){
                max = a.get(i);
            }
        }
        return max;
    }

    public T max(Comparator<T> c){
        if(size == 0){
            return null;
        }
        List<T> a = toList();
        T max = a.get(0);
        for(int i = 1;i < size;i++){
            if(c.compare(max,a.get(i)) < 0){
                max = a.get(i);
            }
        }
        return max;
    }
}
