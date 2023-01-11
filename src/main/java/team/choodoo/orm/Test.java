package team.choodoo.orm;

import java.util.Objects;

public class Test {
    private long id;
    private String name;
    private int year;

    public Test() {
    }

    public Test(long id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test test)) return false;
        return getId() == test.getId() && getYear() == test.getYear() && Objects.equals(getName(), test.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getYear());
    }

    @Override
    public String toString() {
        return "Test{" +
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
