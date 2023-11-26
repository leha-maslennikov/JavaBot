package com.main;

import com.gameprocessor.GameProcessor;
import com.gameprocessor.Request;
import com.gameprocessor.Response;

import java.util.LinkedList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        GameProcessor gameProcessor = new GameProcessor();
        LinkedList<String> callbackData = new LinkedList<>();
        while(true) {
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            var request = Request.builder()
                    .userId(1)
                    .callbackData(s);
            if(!callbackData.isEmpty()) {
                try {
                    request.callbackData(callbackData.get(Integer.parseInt(s)));
                }
                catch (Exception e) {

                }
                callbackData.clear();
            }
            Response response = gameProcessor.handleRequest(request.build());
            System.out.println(response.text);
            int k = 0;
            for(var i: response.objects){
                System.out.println(k++ + " " + i.text);
                callbackData.add(i.callbackData);
            }
        }
    }
}