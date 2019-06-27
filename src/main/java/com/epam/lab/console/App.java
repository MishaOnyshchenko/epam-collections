package com.epam.lab.console;

import com.epam.lab.collections.HashMap;
import com.epam.lab.collections.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;


public class App {
    public static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("Warm", "Spring");
        map.put("Hot", "Summer");
        map.put("Wet", "Autumn");
        map.put("Cold", "Winter");


        Iterator<Map.Entry<String, String>> iterator = map.iterator();
        while(iterator.hasNext()){
            log.info(iterator.next().value);
        }
    }
}