package com.senla.hotel.dao;

import com.senla.hotel.annotation.Singleton;

@Singleton
public class TableColumns {

    private TableColumns(){}

    public static final String LODGER_ID = "id";
    public static final String LODGER_FIRST_NAME = "first_name";
    public static final String LODGER_LAST_NAME = "last_name";
    public static final String LODGER_PHONE = "phone_number";

    public static final String ROOM_ID = "id";
    public static final String ROOM_NUMBER = "number";
    public static final String ROOM_COST = "cost";
    public static final String ROOM_CAPACITY = "capacity";
    public static final String ROOM_STARS = "stars";
    public static final String ROOM_REPAIRED = "repaired";

    public static final String SERVICE_ID = "id";
    public static final String SERVICE_NAME = "name";
    public static final String SERVICE_COST = "cost";

    public static final String RESERVATION_ID = "id";
    public static final String RESERVATION_START_DATE = "start_date";
    public static final String RESERVATION_END_DATE = "end_date";
    public static final String RESERVATION_LODGER_ID = "lodger_id";
    public static final String RESERVATION_ROOM_ID = "room_id";

    public static final String SERVICE_ORDER_ID = "id";
    public static final String SERVICE_ORDER_DATE = "date";
    public static final String SERVICE_ORDER_LODGER_ID = "lodger_id";
    public static final String SERVICE_ORDER_SERVICE_ID = "service_id";
}
