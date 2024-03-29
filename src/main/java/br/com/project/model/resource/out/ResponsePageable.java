package br.com.project.model.resource.out;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ResponsePageable {

    List<Object> content;
    Integer pageCount;
    Long count;
}
