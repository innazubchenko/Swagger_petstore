package API.response.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryDTO {
    private Integer sold;
    private Integer string;
    private Integer unavailable;
    private Integer pending;
    private Integer available;
    private Integer program;
    private Integer expectation;
    private Integer capacitor;
    private Integer feed;
    private Integer driver;
    private Integer array;
    @JsonProperty("not available")
    private Integer notAvailable;
    private Integer send;
    private Integer transmitter;
    @JsonProperty("hard drive")
    private Integer hardDrive;
}
