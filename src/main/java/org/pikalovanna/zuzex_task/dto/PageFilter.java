package org.pikalovanna.zuzex_task.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageFilter {
    private int size = 10;
    private int page = 0;
}
