package com.simple.tests.Part2;

import com.simple.tests.Commons.Program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by prakash on 12/15/15 for reactivJava Please contact prakashjoshiversion1@gmail.com
 */
public class MapperExample implements Program{

    interface Mapper<V,M>{
        M map(V value);
    }

    public static <V,M> List<M> map(List<V> list,Mapper<V,M> mapper){
        List<M> mapped = new ArrayList<M>(list.size());
        for (V v: list){
            mapped.add(mapper.map(v));
        }
        return mapped;
    }








    @Override
    public void run() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> mapped = map(numbers, new Mapper<Integer, Integer>() {
            @Override
            public Integer map(Integer value) {
                return value*value;
            }
        });
        System.out.println("Mapped values are  :" + mapped);
    }


    public static void main(String[] args) {
      new MapperExample().run();
    }
}
