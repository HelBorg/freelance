package com.freelance.project.demo.models;

import org.json.JSONArray;
import org.json.JSONObject;

import lombok.Data;
import java.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.*;

@Data
public class Filter {

    private String findName;
    private Date dateFrom;
    private Date dateTo;
    private Date dueFrom;
    private Date dueTo;
    private Integer id; //my id for filtering
    private Integer author;
    private List<SkillFilter> skills;

    private Date dateConstructor(String date) throws ParseException {
        return date.length() > 0 ?
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                        .parse(date.replace("T", " ").replace("Z", ""))
                : new Date();
    }

    public Filter(Integer id, String findName, String dateFrom, String dateTo, String dueFrom, String dueTo,
                  Integer author, String skillsF) throws ParseException {
        this.skills = new ArrayList<>();
        //Retrieve data from request parameters and put it into Filter
        JSONArray json = new JSONArray("[" + skillsF + "]");
        for (int i = 0; i < json.length(); i++) {
            if (((JSONObject) json.get(i)).getInt("id") == -1) {
                skills.add(new SkillFilter(((JSONObject) json.get(i)).getInt("id"),
                        ((JSONObject) json.get(i)).getInt("value")));
            }
        }

        Date from = dateConstructor(dateFrom);
        Date to = dateConstructor(dateTo);
        Date due_from = dateConstructor(dueFrom);
        Date due_to = dateConstructor(dueTo);

        this.id = id;
        this.findName = findName;
        this.dateFrom = from;
        this.dateTo = to;
        this.dueFrom = due_from;
        this.dueTo = due_to;
        this.author = author;
    }

    public Filter(String findName, String dateFrom, String dateTo, String dueFrom, String dueTo,
                  Integer author, String skillsF) throws ParseException {
        this.skills = new ArrayList<>();
        //Retrieve data from request parameters and put it into Filter
        JSONArray json = new JSONArray("[" + skillsF + "]");
        for (int i = 0; i < json.length(); i++) {
            if (((JSONObject) json.get(i)).getInt("id") != 0) {
                skills.add(new SkillFilter(((JSONObject) json.get(i)).getInt("id"),
                        ((JSONObject) json.get(i)).getInt("value")));
            }
        }

        Date from = dateConstructor(dateFrom);
        Date to = dateConstructor(dateTo);
        Date due_from = dateConstructor(dueFrom);
        Date due_to = dateConstructor(dueTo);

        this.findName = findName;
        this.dateFrom = from;
        this.dateTo = to;
        this.dueFrom = due_from;
        this.dueTo = due_to;
        this.author = author;
    }
}
