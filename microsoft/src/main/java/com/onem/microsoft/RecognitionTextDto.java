package com.onem.microsoft;


import lombok.Data;

import java.util.List;

@Data
public class RecognitionTextDto {
    private List<SegTimeDto> childSegTimeDtoList;
    private List<SegTimeDto> parentSegTimeDtoList;
}
