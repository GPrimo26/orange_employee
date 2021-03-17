package com.maksat.uni.models;

import java.util.List;

public class EventProgramByDays {
    private String date;
    private List<EventProgram> eventProgram;

    public EventProgramByDays(String date, List<EventProgram> eventProgram) {
        this.date = date;
        this.eventProgram = eventProgram;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<EventProgram> getEventProgram() {
        return eventProgram;
    }

    public void setEventProgram(List<EventProgram> eventProgram) {
        this.eventProgram = eventProgram;
    }
}
