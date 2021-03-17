package com.maksat.uni.models;

import java.util.List;

public class ParticipantsByAlphabet {
    private Character letter;
    private List<ParticipantsModel.item> items;
    private List<Employee> employees;

    public ParticipantsByAlphabet(Character letter, List<ParticipantsModel.item> items, List<Employee> employees) {
        this.letter = letter;
        this.items = items;
        this.employees=employees;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public List<ParticipantsModel.item> getItems() {
        return items;
    }

    public void setItems(List<ParticipantsModel.item> items) {
        this.items = items;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
