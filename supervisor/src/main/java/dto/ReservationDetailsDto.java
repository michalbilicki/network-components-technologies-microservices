package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDetailsDto implements Serializable {

    @NotNull
    public String clientId;

    @NotNull
    public String sportsFacilityId;

    @NotNull
    @JsonbDateFormat("yyyy-MM-dd HH:mm")
    public LocalDateTime startDate;

    @JsonbDateFormat("yyyy-MM-dd HH:mm")
    @NotNull
    public LocalDateTime endDate;
}
