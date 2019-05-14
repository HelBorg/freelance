package com.freelance.project.demo.models;

import javafx.util.Pair;
import lombok.Data;
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

    public Filter(Integer id, String findName, Date dateFrom, Date dateTo, Date dueFrom, Date dueTo,
                  String author, List<SkillFilter> skills, Sort sort, String sortDir) {
        this.id = id;
        this.sort = sort;
        this.sortDir = sortDir;
        this.findName = findName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.dueFrom = dueFrom;
        this.dueTo = dueTo;
        this.author = author;
        this.skills = skills;
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
