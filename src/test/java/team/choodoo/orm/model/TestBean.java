package team.choodoo.orm.model;

import java.util.Objects;

public class TestBean {
    private long id;
    private String name = "";
    private int year;

    public TestBean() {
    }

    public TestBean(long id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestBean bean)) return false;
        return getId() == bean.getId() && getYear() == bean.getYear() && Objects.equals(getName(), bean.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getYear());
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
