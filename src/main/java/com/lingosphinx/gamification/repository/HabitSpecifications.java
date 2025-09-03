package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Habit;
import com.lingosphinx.gamification.domain.RenewalType;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;

public class HabitSpecifications {

    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    public static Specification<Habit> incompleteProgress() {
        return (root, query, cb) -> {
            return cb.lessThan(
                    root.get("progress"),
                    root.get("definition").get("target")
            );
        };
    }

    public static Specification<Habit> isDailyDue() {
        return (root, query, cb) -> {
            var renewalType = root.get("definition").get("renewalType");
            var lastProgress = root.get("streak").get("lastProgress").as(Timestamp.class);
            var today = LocalDate.now(ZONE_ID);
            var startOfDayTimestamp = Timestamp.from(today.atStartOfDay(ZONE_ID).toInstant());
            return cb.and(
                    cb.equal(renewalType, RenewalType.DAILY),
                    cb.lessThan(lastProgress, startOfDayTimestamp)
            );
        };
    }

    public static Specification<Habit> isWeeklyDue() {
        return (root, query, cb) -> {
            var renewalType = root.get("definition").get("renewalType");
            var lastProgress = root.get("streak").get("lastProgress").as(Timestamp.class);
            var today = LocalDate.now(ZONE_ID);
            var startOfWeekTimestamp = Timestamp.from(today.with(DayOfWeek.MONDAY).atStartOfDay(ZONE_ID).toInstant());
            return cb.and(
                    cb.equal(renewalType, RenewalType.WEEKLY),
                    cb.lessThan(lastProgress, startOfWeekTimestamp)
            );
        };
    }

    public static Specification<Habit> isMonthlyDue() {
        return (root, query, cb) -> {
            var renewalType = root.get("definition").get("renewalType");
            var lastProgress = root.get("streak").get("lastProgress").as(Timestamp.class);
            var today = LocalDate.now(ZONE_ID);
            var startOfMonthTimestamp = Timestamp.from(today.withDayOfMonth(1).atStartOfDay(ZONE_ID).toInstant());
            return cb.and(
                    cb.equal(renewalType, RenewalType.MONTHLY),
                    cb.lessThan(lastProgress, startOfMonthTimestamp)

            );
        };
    }

    public static Specification<Habit> due() {
        return incompleteProgress().and(
                isDailyDue()
                        .or(isWeeklyDue())
                        .or(isMonthlyDue())
        );
    }

    public static Specification<Habit> byZoneNameAndNameAndContestant(String zone, String name, Contestant contestant) {
        return (root, query, cb) -> {
            var habitDefinition = root.get("definition");
            return cb.and(cb.equal(root.get("contestant"), contestant),
                    cb.equal(habitDefinition.get("zone").get("name"), zone),
                    cb.equal(habitDefinition.get("name"), name)
            );
        };
    }
}