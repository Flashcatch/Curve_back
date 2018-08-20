package dto;

import domain.Week;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeekDTO implements AbstractDTO<Week> {

    private Integer id;
    private String name;

    public WeekDTO(Week data) {
        this.id = data.getId();
        this.name = data.getName();
    }

    @Override
    public Week instanceOf() {return Week.instanceOf(this);}
}
