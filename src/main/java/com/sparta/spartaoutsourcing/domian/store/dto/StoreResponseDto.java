package com.sparta.spartaoutsourcing.domian.store.dto;

import com.sparta.spartaoutsourcing.domian.store.entity.Store;
import lombok.Data;

import java.time.LocalTime;

@Data
public class StoreResponseDto {

    private Long id;

    private String storeName;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String minOrderPrice;



    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.minOrderPrice = store.getMinOrderPrice();
    }


}
