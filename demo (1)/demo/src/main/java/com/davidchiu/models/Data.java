package com.davidchiu.models;

import java.util.List;

@lombok.Data
public class Data {
    public int offset;
    public int limit;
    public int total;
    public int count;
    public List<Character> results;


}
