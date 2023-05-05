package com.cyx.search.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsData {
    Long id;
    String title;
    String text;
    String type;
}
