package domain;

import dto.WeekDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Week extends Entity<Week>{

    private Integer id;
    private String name;

    public static Week instanceOf (WeekDTO dto) {
        return new Week(dto.getId(),
                dto.getName());
    }

    @Override
    public Week instanceOf(Week week) {
        this.setId(week.id);
        this.setName(week.name);
        return this;
    }
}
