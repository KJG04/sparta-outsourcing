package com.sparta.spartaoutsourcing.menu.dto.request;

import com.sparta.spartaoutsourcing.category.dto.request.CategoryRequestDto;
import com.sparta.spartaoutsourcing.optionGroup.dto.request.OptionGroupRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
public class MenuRequestDto {
    private String name;
    private int price;
    private CategoryRequestDto category;
    private List<OptionGroupRequestDto> optionGroups;
}