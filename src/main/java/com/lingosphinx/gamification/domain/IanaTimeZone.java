package com.lingosphinx.gamification.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@JsonSerialize(using = IanaTimeZoneSerializer.class)
@JsonDeserialize(using = IanaTimeZoneDeserializer.class)
public class IanaTimeZone {

    private final String id;

    public static IanaTimeZone of(ZoneId zoneId) {
        return new IanaTimeZone(zoneId.getId());
    }

    public static IanaTimeZone of(String zoneId) {
        return new IanaTimeZone(ZoneId.of(zoneId).getId());
    }

    public static IanaTimeZone of(String region, String city) {
        return new IanaTimeZone(region + "/" + city);
    }

    public static IanaTimeZone systemDefault() {
        return new IanaTimeZone(ZoneId.systemDefault().getId());
    }

    public ZoneId zoneId() {
        return ZoneId.of(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IanaTimeZone)) return false;
        IanaTimeZone that = (IanaTimeZone) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
