package com.epam.repository.query;


public class QuerySQL {
    //Query for Gift
    public static final String SELECT_BY_CERTIFICATES_ID = "SELECT gc.id," +
            " gc.name," +
            " gc.description," +
            " gc.price," +
            "  gc.duration," +
            "   gc.create_date," +
            "   gc.last_update_date," +
            "   t.tag_id," +
            "   t.tag_name" +
            " FROM gift_tags gt" +
            "  JOIN gift_certificates gc ON gt.gift_id = gc.id" +
            "  JOIN tag t ON gt.tag_id = t.tag_id" +
            "   WHERE gc.id = ?";

    public static final String SELECT_ALL_GIFT_CERTIFICATES = "SELECT gc.id," +
            "    gc.name," +
            "   gc.description," +
            "   gc.price," +
            "   gc.duration," +
            "    gc.create_date," +
            "    gc.last_update_date," +
            "   t.tag_id," +
            "   t.tag_name" +
            "    FROM gift_tags gt" +
            "     JOIN gift_certificates gc ON gt.gift_id = gc.id" +
            "      JOIN tag t ON gt.tag_id = t.tag_id";
    public static final String DELETE_GIFT_CERTIFICATE_BY_ID = " DELETE FROM gift_certificates WHERE id = ?";

    public static final String CREATE_GIFT_CERTIFICATE = "INSERT INTO gift_certificates " +
            " (name, description, price, duration, create_date, last_update_date)" +
            " VALUES(?,?,?,?,?,?)";

    //Sql Queries for TAG TABLE
    public static final String SELECT_TAG_BY_ID = " SELECT tag_id, tag_name  FROM tag WHERE tag_id = ?";

    public static final String SELECT_TAG_BY_NAME = "SELECT tag_id,  tag_name    FROM tag   WHERE tag_name = ?";


    public static final String SELECT_ALL_TAGS = "SELECT tag_id, tag_name  FROM tag";

    public static final String CREATE_TAG = "INSERT INTO tag (tag_name) VALUES(?)";

    public static final String DELETE_TAG = " DELETE  FROM tag  WHERE tag_id = ?";
    //Others
    public static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificates SET name=?," +
            "  description=?," +
            "  price=?," +
            "  duration=?," +
            "   last_update_date=?" +
            "  WHERE id =?";

    public static final String CREATE_GIFT_WITH_TAG = " INSERT INTO gift_tags  (gift_id , tag_id) VALUES(?,?)";

    public static final String UPDATE_GIFT_CERTIFICATE_TAG = "UPDATE gift_tags SET tag_name= ? WHERE id= ?";
    public static final String UPDATE_TAG = "UPDATE tag SET tag_name= ? " +
            " WHERE tag_id=?";
    public static final String DELETE_GIFT_CERTIFICATE_TAG_BY_ID = " DELETE FROM gift_tags WHERE gift_id = ?";
}
