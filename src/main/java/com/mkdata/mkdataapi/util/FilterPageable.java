package com.mkdata.mkdataapi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterPageable {

    private Integer page = 0;
    private Integer linesPerPage = 10;
    private String orderBy = "id";
    private String direction = "ASC";

    public PageRequest listByPage() {
        return PageRequest.of(
                getPage(),
                getLinesPerPage(),
                Sort.Direction.valueOf(getDirection().toUpperCase()),
                getOrderBy()
        );
    }
}