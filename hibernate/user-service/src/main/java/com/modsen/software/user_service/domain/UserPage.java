package com.modsen.software.user_service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPage {

    private Integer pageNumber = 0;
    private Integer pageSize = 5;
}
