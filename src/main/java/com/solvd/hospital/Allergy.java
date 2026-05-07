package com.solvd.hospital;

public class Allergy {

    private Long id;
    private String name;

    public Allergy() {
    }

    public Allergy(String name) {
        this.name = name;
    }

    public Allergy(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Allergy{id=" + id + ", name='" + name + "'}";
    }

}
