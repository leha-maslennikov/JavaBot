package com.main;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args)throws Exception {
        System.out.println("Hello and welcome!");
        ExecutorService poolExecutor = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> f = CompletableFuture.runAsync(()->{work("f");}, poolExecutor).thenRun(()->{print("then");});
        CompletableFuture<Void> f2 = CompletableFuture.runAsync(()->{work("f2");}, poolExecutor).thenRun(()->{print("then2");});
        print("main");
        //f.get();
    }

    public static void print(String n){
        System.out.println(Thread.currentThread().getId()+" "+n);
    }
    public static void work(String n){
        print(n);
        try {
            System.out.println("hi");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("bye");
        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}