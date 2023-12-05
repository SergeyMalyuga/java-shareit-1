package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ItemRequestDto {
    private int id;

    private String name;

    private String description;

    private Boolean available;
    private Integer requestId;

}
