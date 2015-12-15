package com.simple.tests.Part2;

import com.simple.tests.Commons.Program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

/**
 * Created by prakash on 12/15/15 for reactivJava Please contact prakashjoshiversion1@gmail.com
 */
public final class ReactiveSumV1Lambda implements Program {

    public static final class ReactiveSum implements Observer<Double> {

        private CountDownLatch latch = new CountDownLatch(1);

        private double sum;
        private Subscription subscription = null;

        public ReactiveSum(Observable<Double> a, Observable<Double> b) {
            this.sum = 0;

            subscribe(a, b);
        }

        private void subscribe(Observable<Double> a, Observable<Double> b) {
            // combineLatest creates an Observable, sending notifications on changes of either of its sources.
            // This notifications are formed using a Func2.
            this.subscription = Observable.combineLatest(a, b, (a1, b1) -> a1 + b1).subscribeOn(Schedulers.io()).subscribe(this);
        }

        public void unsubscribe() {
            this.subscription.unsubscribe();
            this.latch.countDown();
        }

        public void onCompleted() {
            System.out.println("Exiting last sum was : " + this.sum);
            this.latch.countDown();
        }

        public void onError(Throwable e) {
            System.err.println("Got an error!");
            e.printStackTrace();
        }

        public void onNext(Double sum) {
            this.sum = sum;
            System.out.println("update : a + b = " + sum);
        }

        public CountDownLatch getLatch() {
            return latch;
        }
    }


    public static ConnectableObservable<String> from(final InputStream stream) {
        return from(new BufferedReader(new InputStreamReader(stream)));
    }

    public static ConnectableObservable<String> from(final BufferedReader bufferedReader) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    String line;
                    while (!subscriber.isUnsubscribed() && (line = bufferedReader.readLine()) != null) {
                        if (line == null || line.equalsIgnoreCase("exit")) {
                            break;
                        }
                        subscriber.onNext(line);
                    }
                } catch (IOException ex) {
                    subscriber.onError(ex);
                }
                if (!subscriber.isUnsubscribed())
                    subscriber.onCompleted();
            }
        }).publish();
    }


    public static Observable<Double> varStream(String character, ConnectableObservable<String> connection) {
        final Pattern pattern = Pattern.compile("^\\s*" + character + "\\s*[:|=]\\s*(-?\\d+\\.?\\d*)$");
        return connection
                .map(pattern::matcher)
                .filter(Matcher::matches)
                .map(matcher -> matcher.group(1))
                .map(Double::parseDouble);
    }


    @Override
    public void run() {
        ConnectableObservable<String> input = from(System.in);
        Observable<Double> a = varStream("a", input);
        Observable<Double> b = varStream("b", input);
        ReactiveSum addition = new ReactiveSum(a, b);
        input.connect();
        try {
            addition.getLatch().await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        System.out.println();
        System.out.println("Reacitve Sum. Type 'a: <number>' and 'b: <number>' to try it.");
        new ReactiveSumV1Lambda().run();
    }
}
