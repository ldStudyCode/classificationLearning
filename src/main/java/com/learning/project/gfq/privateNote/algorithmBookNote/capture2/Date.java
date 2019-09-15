package com.learning.project.gfq.privateNote.algorithmBookNote.capture2;

public class Date implements Comparable<Date> {

    private final int day;
    private final int month;
    private final int year;
    public Date(int d, int m, int y)
    {
        day = d;
        month = m;
        year = y;
    }
    public int day() {
        return day;
    }
    public int month() {
        return month;
    }
    public int year() {
        return year;
    }
    /*
    1、compareTo() 必须实 现一个完整的比较序列，即：
         自反性，对于所有的 v，v=v；
         反对称性，对于所有的 v<w 都 有 v>w，且 v=w 时 w=v；
         传递性，对于所有的 v、w 和 x， 如果 v<=w 且 w<=x，则 v<=x。

    2、如果 v 和 w 无法比较或者两者之 一是 null，v.compareTo(w) 将会抛出 一个异常

    3、v.compareTo(w)比较情况与返回值
        v<w、v=w 和 v>w
        -1、0 和 1
    */
    public int compareTo(Date that){
        if (this.year > that.year ) return +1;
        if (this.year < that.year ) return -1;
        if (this.month > that.month) return +1;
        if (this.month < that.month) return -1;
        if (this.day > that.day ) return +1;
        if (this.day < that.day ) return -1;
        return 0;
    }
    public String toString()
    {
        return month + "/" + day + "/" + year;
    }
}
