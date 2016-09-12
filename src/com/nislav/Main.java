package com.nislav;

public class Main {

    public static void main(String[] args) {

        StationParser.Reader("out/production/jin01/com/nislav/LEN.txt");
        StationParser.Reader("out/production/jin01/com/nislav/ASL.txt");
        Station.putAllStations();
//        for(Station s : Station.allStations){
//            s.parseNearStations();
//        }
        Station.listAllStations();
        for(Station s : Station.allStations){
            System.out.println(s);
        }
//        System.out.println("Расстояние от Ховрино до Моссельмаш: "+ Station.getDistance(Station.searchByName("Ховрино"), Station.searchByName("Моссельмаш"))+" м");//1460
//        System.out.println("Расстояние от Моссельмаш до Нати: "+ Station.getDistance(Station.searchByName("Моссельмаш"), Station.searchByName("Нати"))+" м");//1900
//
//        Station.printStationsByNapr("Ленинградское");
//        Station.printStationsByNapr("Алексеевская Соединительная Линия");


        System.out.println(Station.getPath( "Москва Октябрьская", "Клин"));
        System.out.println(Station.getPath("Клин", "Москва Октябрьская"));
        System.out.println(Station.getPath("Москва Курская", "Москва Белорусская"));
        System.out.println(Station.getPath("Москва Белорусская", "Москва Курская"));
    }
}
