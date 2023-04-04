package com.pawer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindPageRequestDto {


    Sort.Direction direction;
    int pageNumber;
    int pageSize;
    String sortingParameter;
}
