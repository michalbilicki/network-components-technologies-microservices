package rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbNillable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonbNillable
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRestDTO implements Serializable {

    private String id;
    private String clientId;
    private String sportsFacilityId;
    @JsonbDateFormat(JsonbDateFormat.DEFAULT_FORMAT)
    private LocalDateTime startDate;
    @JsonbDateFormat(JsonbDateFormat.DEFAULT_FORMAT)
    private LocalDateTime endDate;
    private boolean active;

}
