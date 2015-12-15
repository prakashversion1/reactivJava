package com.simple.tests.Part1;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by prakash on 12/15/15 for reactivJava Please contact prakashjoshiversion1@gmail.com
 */
public class IteratorVsObservable {

    public static void main(String[] args) {
//        runIterables();
//        runObservables();
        runObservableSubscription();
    }


    public static void runIterables() {
        List<String> list = Arrays.asList("one", "two", "three", "four");
        //Simple subscription to the observable (pull mechanism)
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public static void runObservables() {
        List<String> list = Arrays.asList("One", "Two", "Three", "Four", "Five");
        Observable<String> listObservable = Observable.from(list);
        //Simple subscription to the observable (Push mechanism)
        listObservable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }

    public static void runObservableSubscription() {
        List<String> list = Arrays.asList("One", "Two", "Three", "Four", "Five");
        Observable<String> observable = Observable.from(list);
        observable.subscribe(
                // on next
                new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                },
                // on error
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println(throwable.getCause().getLocalizedMessage());
                    }
                },
                // on completed
                new Action0() {
                    @Override
                    public void call() {
                        System.out.println("We've finished with the list. Thank you !!!!!!");
                    }
                }
        );
    }
}
