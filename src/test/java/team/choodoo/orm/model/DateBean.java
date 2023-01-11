package team.choodoo.orm.model;

import java.time.LocalDate;
import java.util.Objects;

public class DateBean {
    private long id;
    private String title = "";
    private LocalDate localDate = LocalDate.now();

    public DateBean() {
    }

    public DateBean(long id, String title, LocalDate localDate) {
        this.id = id;
        this.title = title;
        this.localDate = localDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateBean dateBean)) return false;
        return getId() == dateBean.getId() && Objects.equals(getTitle(), dateBean.getTitle()) && Objects.equals(getLocalDate(), dateBean.getLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getLocalDate());
    }

    @Override
    public String toString() {
        return "DateBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + localDate +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
