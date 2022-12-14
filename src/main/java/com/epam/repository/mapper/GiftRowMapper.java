package com.epam.repository.mapper;

import com.epam.model.entity.GiftCertificates;


import com.epam.model.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import org.springframework.stereotype.Component;

import java.security.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static com.epam.repository.mapper.ColumnName.*;


@Component
public class GiftRowMapper implements ResultSetExtractor<List<GiftCertificates>> {
     @Override
    public List<GiftCertificates> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificates> giftCertificates = new ArrayList<>();
        rs.next();
        while (!rs.isAfterLast()) {

            GiftCertificates giftCertificate = GiftCertificates.builder()
                    .id(rs.getLong(GIFT_ID))
                    .name(rs.getString(GIFT_NAME))
                    .description(rs.getString(GIFT_DESCRIPTION))
                    .price(rs.getDouble(GIFT_PRICE))
                    .duration(rs.getInt(GIFT_DURATION))
                    .create_date(rs.getTimestamp(GIFT_CREATE_DATE).toLocalDateTime())
//                    .last_update_date(rs.getTimestamp(GIFT_LAST_UPDATE_DATE).toLocalDateTime())
                    .build();
            List<Tag> tags = new ArrayList<>();
            while (!rs.isAfterLast() && rs.getInt(GIFT_ID) == giftCertificate.getId()) {
                Tag tag = Tag.builder()
                        .id(rs.getLong(TAG_ID))
                        .name(rs.getString(TAG_NAME))
                        .build();
                tags.add(tag);
                rs.next();
            }
            giftCertificate.setTags(tags);
            giftCertificates.add(giftCertificate);
        }
        return giftCertificates;
    }
}
