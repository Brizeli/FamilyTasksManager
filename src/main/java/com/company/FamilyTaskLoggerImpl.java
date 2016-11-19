package com.company;

/**
 * Created by Next on 19.11.2016.
 */
public class FamilyTaskLoggerImpl implements FamilyTaskLogger {
    @Override
    public void log(String dateTime, String message) {
        System.out.println(dateTime + " " + message);
    }

    @Override
    public void log(Object o) {
        System.out.println(o);
    }
}
