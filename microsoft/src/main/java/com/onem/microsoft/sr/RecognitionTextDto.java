package com.onem.microsoft.sr;


import com.onem.microsoft.sr.SegTimeDto;
import lombok.Data;

import java.util.List;

@Data
public class RecognitionTextDto {
    private List<SegTimeDto> childSegTimeDtoList;
    private List<SegTimeDto> parentSegTimeDtoList;
}
