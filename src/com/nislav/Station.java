package com.nislav;

import java.util.*;

public class Station implements Iterable<Station> {

    public static ArrayList<Station> allStations = new ArrayList<>();

    protected Station nextStation;
    protected Station prevStation;
    private Station[] near = new Station[4];

    public static ArrayList<Station> sp1;
    public static ArrayList<Station> sp2;
    public static Station sf;
    public static Station st;
    private static volatile int use;

    private String name;
    private String napr;
    private int tariffZone;
    private int platformCount;
    private double coord_X;
    private double coord_Y;
    private ArrayList<String> nearStations = new ArrayList<>();

    public void parseNearStations(){
        for(String station : nearStations){
            for(Station s : allStations){
                if(s.getName().equals(station)) {
                    break;
                }
            }
        }
    }

    public Station(String name, String napr, int zone, int platformCount, double X, double Y, String prev, String next, String Rnear, Station Mnear){
        this.name=name;
        this.napr = napr;
        this.tariffZone = zone;
        this.platformCount = platformCount;
        this.coord_X=X;
        this.coord_Y=Y;
        this.prevStation = (prev!=null)?new Station(prev):getNullStation();
        this.nextStation = (next!=null)?new Station(next):getNullStation();
        String[] rnear = {""};
        if(!Rnear.equals("null"))
            rnear = Rnear.split(",");
            for(String station : rnear){
                nearStations.add(station);
            }
        allStations.add(this);
    }
    public void finalize(){
        allStations.remove(this);
    }
    public void setNextStation(Station next){this.nextStation=next;}

    public static void putAllStations(){
        for(Station rs : allStations){
            Station.getKey(rs).setNext(rs.getNextStation());
            Station.getKey(rs).setPrev(rs.getPrevStation());
            Station.getKey(rs).setNear(rs.getNearStations());
        }
    }

    public double getCoord_X() {return this.coord_X;}
    public double getCoord_Y() {return this.coord_Y;}
    public String getName() {return this.name;}
    public String getNapr(){return this.napr;}
    public int getTariffZone(){return this.tariffZone;}
    public int getPlatformCount(){return this.platformCount;}

    public Station getNextStation() {
        return (nextStation!=null)? nextStation:getNullStation();
    }
    public Station getPrevStation() {
        return (prevStation!=null)? prevStation:getNullStation();
    }

//    public ArrayList<Station> getNearStations(){
//        return nearStations;
//    }
    public String getNearStations(){
        String s="";
        for(String st : nearStations){
            s+="\t"+st+"\n";
        }
        return s;
    }

    public String toString() {
        if (this.equals(getNullStation())) return "Станции не существует";
        else {
            String s = "";
            //s += "========================================\n";
            s += "Станция " + this.getName() + "\n";
            s += "Направление: " + this.getNapr() + "\n";
            s += "Тарифная зона: " + this.getTariffZone() + "\n";
            s += "Количество платформ: " + this.getPlatformCount() + "\n";
            s += "Следующая станция: " + this.getNextStation().getName() + "\n";
            s += "Предыдущая станция: " + this.getPrevStation().getName() + "\n";
            s += "Координаты: \n\tx=" + this.getCoord_X() + "\n\ty=" + this.getCoord_Y() + "\n";
            if (!(this.nearStations.get(0)==""))
                s += "Пересадка на станции:\n" + this.getNearStations();
            // if (!this.getNearMetroStation().equals(""))
            //     s+= "Ближайшая станция метро:\n" + this.getNearMetroStation();
            s += "========================================";
            return s;
        }
    }

    public static ArrayList<Station> searchByNapr(String napr){
        ArrayList<Station> a = new ArrayList<>();
        Station st=getNullStation();
        for (Station s : allStations) {
            if (s.getNapr().equals(napr) && s.getPrevStation().equals(getNullStation())) {
                a.add(s);
                st=s.getNextStation();
                break;
            }
        }
        if (!st.equals(getNullStation())) {
            while (!st.equals(getNullStation())) {
                a.add(st);
                st = (Station) st.getNextStation();
            }
        }
        return a;
    }

    public static void printStationsByNapr(String napr) {
        for(Station s : searchByNapr(napr)){
            System.out.println("\t"+s.getName());
        }
    }

    public StationIterator iterator() {
        return new StationIterator(this);
    }

    public Station() {
    }

    public Station(String s) {
        this.name = s;
    }

    public static Station getNullStation() {
        return new Station("Неизвестно");
    }

    public static void listAllStations() {
        for (Station s : allStations) {
            Station sPrev = searchByName(s.getPrev().getName());
            Station sNext = searchByName(s.getNext().getName());
            s.setPrev(sPrev);
            s.setNext(sNext);
        }
    }

    public static double getDistance(Station sa, Station sb) {
        return 111100 * Math.acos((Math.sin(sa.getCoord_Y()) * Math.sin(sb.getCoord_Y())) + (Math.cos(sa.getCoord_Y()) * Math.cos(sb.getCoord_Y()) * Math.cos(sb.getCoord_X() - sa.getCoord_X())));
    }

    public static Station searchByName(String name) {
        for (Station s : allStations) {
            if (s.getName().equals(name)) return s;
        }
        return getNullStation();
    }

    public static String searchByName(String name, char rmb) {
        String s = "";
        for (Station st : allStations) {
            if (st.equals(name)) s+=st.getName();
        }
        if (s.equals("")) s += getNullStation();
        return s;
    }

    public Station[] getNear() {return near;}

    public void setNext(Station next) {
        this.nextStation = next;
    }

    public void setPrev(Station previous) {
        this.prevStation = previous;
    }

    public void setNear(String near){
        ///
    }

    public static Station getKey(Station key) {
        if (allStations.contains(key)) {
            return key;
        }
        return null;
    }

    public static synchronized void setUse(int use){Station.use=use;}
    public static synchronized int getUse(){return Station.use;}

    public boolean equals(Station s) {
        return this.name.equals(s.getName());
    }

    public boolean equals(String s) {
        return this.name.equals(s);
    }

    public Station getNext() {
        return (nextStation != null) ? nextStation : getNullStation();
    }

    public Station getPrev() {
        return (prevStation != null) ? prevStation : getNullStation();
    }

    public static String getPath(String from, String sto) {
        String path = "";
        setUse(0);
        if (from.equals("") || sto.equals("")) {
            if (from.equals("")) path += "Отсутствует название станции отпправки\n";
            if (sto.equals("")) path += "Отсутствует название станции назначения\n";
        } else {
            sf = searchByName(from);
            st = searchByName(sto);
            if (sf.equals(getNullStation()) || st.equals(getNullStation())) {
                if (sf.equals(getNullStation())) path += "Неверное название станции отправки\n";
                if (st.equals(getNullStation())) path += "Неверное название станции назначения\n";
            } else {
                if (!sf.equals(getNullStation()) || !st.equals(getNullStation())) {


                    sp1 = new ArrayList<>();
                    sp2 = new ArrayList<>();
//                    try {
//                        Thread t1 = new Thread(new StationRunnableFwd(sf));
//                        Thread t2 = new Thread(new StationRunnableBack(sf));
//
//                        t1.start();
//                        t2.start();
//                        t1.join();
//                        t2.join();
//                    }catch(InterruptedException e){}

                    fwdPath(sf, st, sp1);
                    backPath(sf, st, sp2);
                    if(getUse()==1) {
                        sp1.add(0,sf);
                        for (Station s : sp1) {
                            path += s.getName() + " -> ";
                        }
                    } else if(getUse()==2){
                        sp2.add(0,sf);
                        for (Station s : sp2) {
                            path += s.getName() + " -> ";
                        }
                    }
                    return path.substring(0, path.length() - 4);
                }
            }
        }
        return path;
    }

    public static void fwdPath(Station sf, Station to, ArrayList<Station> sp) {
        StationIterator it = sf.iterator();
        while (it.hasNext()) {
            sp.add(it.next());
            if (sp.get(sp.size() - 1).equals(to)) {
                Station.setUse(1);
                break;
            }
        }
    }

    public static void backPath(Station sf, Station to, ArrayList<Station> sp) {
        StationIterator it = sf.iterator();
        while(!it.get().equals(getNullStation())){
            sp.add(it.previous());
            if (sp.get(sp.size() - 1).equals(to)) {
                Station.setUse(2);
                break;
            }
        }
    }

}


class StationRunnableFwd implements Runnable{
    Station starter;

    public StationRunnableFwd(Station s){this.starter = s;}

    public void run(){
        Station.fwdPath(starter, Station.st, Station.sp1);
    }
}
class StationRunnableBack implements Runnable{
    Station starter;

    public StationRunnableBack(Station s){this.starter = s;}

    public void run(){
        Station.backPath(Station.st, starter, Station.sp2);
    }
}

class StationIterator implements ListIterator<Station>{

    public Station elem;
    public boolean jumped;

    public StationIterator(Station s){this.elem=s;}

    @Override
    public boolean hasNext(){
        if (!elem.getNext().equals(Station.getNullStation())) return true;
        return false;
    }
    public boolean hasNear(){
        if(elem.getNear().length!=0) return true;
        return false;
    }
    public Station jump(int i){
        if(hasNear()&&!jumped){
            elem=elem.getNear()[i];
            jumped=true;
            return elem;
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean hasPrevious(){
        if (!elem.getPrev().equals(Station.getNullStation())) return true;
        return false;
    }


    public Station next(){
        if (hasNext()) {
            elem = elem.getNext();
            jumped=false;
            return elem;
        }
        throw new NoSuchElementException();
    }

    public int nextIndex(){throw new UnsupportedOperationException();}
    public void set(Station s){throw new UnsupportedOperationException();}
    public int previousIndex(){throw new UnsupportedOperationException();}
    public void remove(){throw new UnsupportedOperationException();}
    public void add(Station s){throw new UnsupportedOperationException();}

    public Station previous(){
        if (hasPrevious()){
            elem = elem.getPrev();
            jumped=false;
        }
        else
            elem = Station.getNullStation();
        return elem;
    }

    public Station get(){return elem;}
}
