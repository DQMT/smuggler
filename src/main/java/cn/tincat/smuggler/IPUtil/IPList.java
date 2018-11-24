package cn.tincat.smuggler.IPUtil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class IPList {
    List<String> ips;
    public IPList(String ipList){
        String [] array = ipList.split(";");
        ips = Arrays.asList(array);
    }

    public boolean isValidIP(String ip){
        return ips.stream().anyMatch(p -> Pattern.matches(p, ip));
    }
}
