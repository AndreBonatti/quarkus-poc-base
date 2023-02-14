package br.com.project.model.external.rest.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BrasilApiBanksV1Dto {

    @JsonProperty("ispb")
    private String ispb;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("fullName")
    private String fullName;
}
