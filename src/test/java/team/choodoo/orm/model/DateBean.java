package team.choodoo.orm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateBean {
    private long id;
    private String title = "";
    private LocalDate localDate = LocalDate.now();
}
