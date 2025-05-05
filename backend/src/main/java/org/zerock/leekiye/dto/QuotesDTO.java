package org.zerock.leekiye.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotesDTO {

    private Long qno;

    private String author;

    private String quotes;

    // private boolean delFlag;

}
