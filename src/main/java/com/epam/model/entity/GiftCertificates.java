package com.epam.model.entity;


import lombok.*;


import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificates {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private LocalDateTime create_date;
    private LocalDateTime last_update_date;
    private List<Tag> tags;

//    public LocalDateTime getLast_update_date(LocalDateTime now) {
//        return last_update_date;
//    }
//
//    public void setLast_update_date(LocalDateTime last_update_date) {
//        this.last_update_date = last_update_date;
//    }
}
