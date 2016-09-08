package com.nislav;


import java.util.ArrayList;

public class MStation extends Station{

    /**
     * RED: "Сокольническая";
     * GREEN: "Замоскворецкая";
     * BLUE: "Арбатско-Покровская";
     * CYAN: "Филевская";
     * BROWN: "Кольцевая";
     * GRAY: "Серпуховско-Тимирязевская";
     * VIOLET: "Таганско-Краснопресненская";
     * YELLOW: "Калининско-Солнцевская";
     * ORANGE: "Калужско-Рижская";
     * SALAD: "Люблинско-Дмитровская";
     * TURQUOISE: "Каховская";
     * GRAYBLUE: "Бутовская";
     * PURPLE: "Монорельс";
     */
    public static enum mLines {RED, GREEN, BLUE, CYAN, BROWN, GRAY, VIOLET, YELLOW, ORANGE, SALAD, TURQUOISE, GRAYBLUE, PURPLE}
    private mLines[] ml=mLines.values();

    public static ArrayList<MStation> allMStations = new ArrayList<>();
    private String name;
    private double coord_X;
    private double coord_Y;
    private mLines line;
    private MStation nextStation;
    private MStation prevStation;

    public MStation(String s){this.name=s;}

    public MStation(String name, int line, double X, double Y){
        this.name = name;
        this.line=ml[line-1];
        this.coord_X=X;
        this.coord_Y=Y;
        allMStations.add(this);
        allStations.add(this);
    }
    public void finalize(){
        allMStations.remove(this);
        allStations.remove(this);
    }

    public static MStation getNullStation() {
        return new MStation("Неизвестно");
    }

    public double getCoord_X() {return this.coord_X;}
    public double getCoord_Y() {return this.coord_Y;}
    public String getName() {return this.name;}
    public MStation getNextStation() {
        return (nextStation!=null)?nextStation:getNullStation();
    }
    public MStation getPrevStation() {
        return (prevStation!=null)?prevStation:getNullStation();
    }

    public String getLine(){
        switch(this.line){
            case RED: return "Сокольническая";
            case GREEN: return "Замоскворецкая";
            case BLUE: return "Арбатско-Покровская";
            case CYAN: return "Филевская";
            case BROWN: return "Кольцевая";
            case GRAY: return "Серпуховско-Тимирязевская";
            case VIOLET: return "Таганско-Краснопресненская";
            case YELLOW: return "Калининско-Солнцевская";
            case ORANGE: return "Калужско-Рижская";
            case SALAD: return "Люблинско-Дмитровская";
            case TURQUOISE: return "Каховская";
            case GRAYBLUE: return "Бутовская";
            case PURPLE: return "Монорельс";
        }
        return "Неизвестно";
    }

    public String toString() {
        if (this.getName().equals(getNullStation().getName())) return "Станции не существует";
        else {
            String s = "";
            s += "========================================\n";
            s += "Станция метро " + this.getName() + "\n";
            s += "Линия метро: "+ this.getLine() +"\n";
            s += "Следующая станция: " + this.getNextStation().getName() + "\n";
            s += "Предыдущая станция: " + this.getPrevStation().getName() + "\n";
            s += "Координаты: \n\tx=" + this.getCoord_X() + "\n\ty=" + this.getCoord_Y() + "\n";
            //if (!this.getNearStations().equals(""))
            //    s += "Пересадка на станции:\n" + this.getNearStations();
            s += "========================================";
            return s;
        }
    }

}
