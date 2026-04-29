-- V1__init.sql
-- Baseline schema matching all Hibernate 6 / Spring Boot 3 entity mappings.
-- Table order respects FK dependencies (parent tables first).

-- 1. staff  (@Inheritance JOINED — base table for Driver and TripManager)
CREATE TABLE staff (
    id    BIGINT       NOT NULL AUTO_INCREMENT,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

-- 2. buses
CREATE TABLE buses (
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    vin                 VARCHAR(255) NOT NULL,
    registration_number VARCHAR(255) NOT NULL,
    capacity            INT          NOT NULL,
    status              VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_vin                 UNIQUE (vin),
    CONSTRAINT uq_registration_number UNIQUE (registration_number)
) ENGINE = InnoDB;

-- 3. bus_stations
CREATE TABLE bus_stations (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255) NOT NULL,
    city       VARCHAR(255) NOT NULL,
    is_damaged TINYINT(1),
    PRIMARY KEY (id),
    CONSTRAINT uq_bus_station_name_city UNIQUE (name, city)
) ENGINE = InnoDB;

-- 4. drivers  (JOINED inheritance; PK = FK → staff.id)
CREATE TABLE drivers (
    staff_id         BIGINT NOT NULL,
    experience_years INT    NOT NULL,
    PRIMARY KEY (staff_id),
    CONSTRAINT fk_drivers_staff FOREIGN KEY (staff_id) REFERENCES staff (id)
) ENGINE = InnoDB;

-- 5. trip_managers  (JOINED inheritance; PK = FK → staff.id)
CREATE TABLE trip_managers (
    staff_id      BIGINT       NOT NULL,
    employee_code VARCHAR(255) NOT NULL,
    PRIMARY KEY (staff_id),
    CONSTRAINT fk_trip_managers_staff FOREIGN KEY (staff_id) REFERENCES staff (id)
) ENGINE = InnoDB;

-- 6. routes  (FK → bus_stations x2)
CREATE TABLE routes (
    id                     BIGINT NOT NULL AUTO_INCREMENT,
    origin_station_id      BIGINT NOT NULL,
    destination_station_id BIGINT NOT NULL,
    distance               FLOAT  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_routes_origin      FOREIGN KEY (origin_station_id)      REFERENCES bus_stations (id),
    CONSTRAINT fk_routes_destination FOREIGN KEY (destination_station_id) REFERENCES bus_stations (id),
    CONSTRAINT uq_route              UNIQUE (destination_station_id, origin_station_id)
) ENGINE = InnoDB;

-- 7. bus_trips  (FK → routes, buses)
CREATE TABLE bus_trips (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    route_id   BIGINT       NOT NULL,
    bus_id     BIGINT       NOT NULL,
    start_time DATETIME(6),
    status     VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_bus_trips_route FOREIGN KEY (route_id) REFERENCES routes (id),
    CONSTRAINT fk_bus_trips_bus   FOREIGN KEY (bus_id)   REFERENCES buses  (id)
) ENGINE = InnoDB;

-- 8. passengers
CREATE TABLE passengers (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    currency VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

-- 9. trip_station  (ManyToMany join table: bus_trips ↔ bus_stations)
CREATE TABLE trip_station (
    trip_id    BIGINT NOT NULL,
    station_id BIGINT NOT NULL,
    PRIMARY KEY (trip_id, station_id),
    CONSTRAINT fk_trip_station_trip    FOREIGN KEY (trip_id)    REFERENCES bus_trips    (id),
    CONSTRAINT fk_trip_station_station FOREIGN KEY (station_id) REFERENCES bus_stations (id)
) ENGINE = InnoDB;

-- 10. tickets  (FK → bus_trips, passengers)
CREATE TABLE tickets (
    id           BIGINT           NOT NULL AUTO_INCREMENT,
    bus_trip_id  BIGINT           NOT NULL,
    passenger_id BIGINT           NOT NULL,
    seat_number  VARCHAR(255)     NOT NULL,
    price        DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_tickets_bus_trip  FOREIGN KEY (bus_trip_id)  REFERENCES bus_trips  (id),
    CONSTRAINT fk_tickets_passenger FOREIGN KEY (passenger_id) REFERENCES passengers (id),
    CONSTRAINT uq_ticket_seat_trip  UNIQUE (seat_number, bus_trip_id)
) ENGINE = InnoDB;

-- 11. duty_assignments  (FK → bus_trips, staff)
CREATE TABLE duty_assignments (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    trip_id  BIGINT       NOT NULL,
    staff_id BIGINT       NOT NULL,
    role     VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_duty_assignments_trip  FOREIGN KEY (trip_id)  REFERENCES bus_trips (id),
    CONSTRAINT fk_duty_assignments_staff FOREIGN KEY (staff_id) REFERENCES staff     (id)
) ENGINE = InnoDB;

