package com.freelance.project.demo.models;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.util.Pair;
import lombok.Data;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
public class Filter {

    private String findName;
    private Sort sort;
    private String sortDir;
    private Date dateFrom;
    private Date dateTo;
    private Date dueFrom;
    private Date dueTo;
    private Integer id; //my id for filtering
    private String author;
    private List<SkillFilter> skills;
    private List<SkillFilter> filterSkillsBy;  //here presented all satisfying variants


    public Filter() throws ParseException {
        this.findName = "";
        this.dateFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .parse("2019-01-01 00:00:00.000");
        this.dateTo = new Date();
        this.dueFrom = new Date();
        this.dueTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .parse("3000-01-01 00:00:00.000");
    }

    private Date dateConstructor(String date) throws ParseException {
        return date.length() > 0 ?
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                        .parse(date.replace("T", " ").replace("Z", ""))
                : new Date();
    }

    public Filter(Integer id, String findName, String dateFrom, String dateTo, String dueFrom, String dueTo,
                  String author, String skillsF, Sort sort, String sortDir) throws ParseException {
        this.skills = new ArrayList<>();
        //Retrieve data from request parameters and put it into Filter
        JSONArray json = new JSONArray("[" + skillsF + "]");
        for (int i = 0; i < json.length(); i++) {
            if (!((JSONObject) json.get(i)).getString("name").equals("")) {
                skills.add(new SkillFilter(((JSONObject) json.get(i)).getString("name"),
                        ((JSONObject) json.get(i)).getInt("value")));
            }
        }

        Date from = dateConstructor(dateFrom);
        Date to = dateConstructor(dateTo);
        Date due_from = dateConstructor(dueFrom);
        Date due_to = dateConstructor(dueTo);

        this.id = id;
        this.sort = sort;
        this.sortDir = sortDir;
        this.findName = findName;
        this.dateFrom = from;
        this.dateTo = to;
        this.dueFrom = due_from;
        this.dueTo = due_to;
        this.author = author;
        this.FilterSkillsBy();
    }

    //Создаем список skill-значение, которые нам подходят
    private void FilterSkillsBy() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "bad");
        map.put(1, "semi-good");
        map.put(2, "good");
        map.put(3, "semi-profi");
        map.put(4, "profi");

        filterSkillsBy = new ArrayList<>();
        for (int i = 0; i < this.skills.size(); i++) {
            for (int j = 0; j <= this.skills.get(i).getValue(); j++) {
                filterSkillsBy.add(new SkillFilter(this.skills.get(i).getName(), map.get(j)));
            }
        }
    }
}
