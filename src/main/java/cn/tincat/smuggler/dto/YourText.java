package cn.tincat.smuggler.dto;

public class YourText {
    private static  String s;
    private static long datetime ;

    public static  String get(){
        if( datetime == 0){
            datetime = System.currentTimeMillis();
            s = "";
            return "";
        }
        if(System.currentTimeMillis() - datetime > 1000 * 60){
            s = "";
            return "";
        }
        return s;
    }
    public static void set(String string){
        s = string;
        datetime = System.currentTimeMillis();
    }
    public static void setTIme(){
        datetime = System.currentTimeMillis();
    }
    public static int getKey(){
        return (int) (datetime/(1000*60*11));
    }
}
