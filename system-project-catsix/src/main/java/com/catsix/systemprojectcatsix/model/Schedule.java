package com.catsix.systemprojectcatsix.model;

import java.sql.Date;

public class Schedule {
    private String schedule_ID;
    private Date schedule_start_date;
    private Date schedule_end_date;
    private String schedule_contents;
    private String schedule_team_ID;

    public String getSchedule_ID() {
        return schedule_ID;
    }

    public void setSchedule_ID(String schedule_ID) {
        this.schedule_ID = schedule_ID;
    }

    public Date getSchedule_start_date() {
        return schedule_start_date;
    }

    public void setSchedule_start_date(Date schedule_start_date) {
        this.schedule_start_date = schedule_start_date;
    }

    public Date getSchedule_end_date() {
        return schedule_end_date;
    }

    public void setSchedule_end_date(Date schedule_end_date) {
        this.schedule_end_date = schedule_end_date;
    }

    public String getSchedule_contents() {
        return schedule_contents;
    }

    public void setSchedule_contents(String schedule_contents) {
        this.schedule_contents = schedule_contents;
    }

    public String getSchedule_team_ID() {
        return schedule_team_ID;
    }

    public void setSchedule_team_ID(String schedule_team_ID) {
        this.schedule_team_ID = schedule_team_ID;
    }
}
