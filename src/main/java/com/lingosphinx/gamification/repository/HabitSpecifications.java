package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Contestant;
import com.lingosphinx.gamification.domain.Habit;
import com.lingosphinx.gamification.domain.IanaTimeZone;
import com.lingosphinx.gamification.domain.RenewalType;
import org.springframework.data.jpa.domain.Specification;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;

public class HabitSpecifications {

    private static final ZoneId zoneId = ZoneId.systemDefault();

    public static Specification<Habit> incompleteProgress() {
        return (root, query, cb) -> {
            return cb.lessThan(
                    root.get("progress"),
                    root.get("definition").get("target")
            );
        };
    }

    public static Specification<Habit> isDailyDue(ZoneId zoneId) {
        return (root, query, cb) -> {
            var renewalType = root.get("definition").get("renewalType");
            return cb.equal(renewalType, RenewalType.DAILY);
        };
    }

    public static Specification<Habit> isWeeklyDue(ZoneId zoneId) {
        return (root, query, cb) -> {
            var renewalType = root.get("definition").get("renewalType");
            var today = LocalDate.now(zoneId);
            if (today.getDayOfWeek() != DayOfWeek.MONDAY) {
                return cb.disjunction();
            }
            return cb.equal(renewalType, RenewalType.WEEKLY);
        };
    }

    public static Specification<Habit> isMonthlyDue(ZoneId zoneId) {
        return (root, query, cb) -> {
            var renewalType = root.get("definition").get("renewalType");
            var today = LocalDate.now(zoneId);
            if (today.getDayOfMonth() != 1) {
                return cb.disjunction();
            }
            return cb.equal(renewalType, RenewalType.MONTHLY);
        };
    }

    public static Specification<Habit> isInTimeZone(IanaTimeZone ianaTimeZone) {
        return (root, query, cb) -> {
            return cb.equal(root.get("contestant").get("timeZone"), ianaTimeZone);
        };
    }

    public static Specification<Habit> due(IanaTimeZone ianaTimeZone) {
        var zoneId = ianaTimeZone.zoneId();
        return isInTimeZone(ianaTimeZone).and(
                isDailyDue(zoneId)
                .or(isWeeklyDue(zoneId))
                .or(isMonthlyDue(zoneId))
        );
    }

    public static Specification<Habit> byZoneNameAndTypeNameAndContestant(String zone, String name, Contestant contestant) {
        return (root, query, cb) -> {
            var habitDefinition = root.get("definition");
            return cb.and(cb.equal(root.get("contestant"), contestant),
                    cb.equal(habitDefinition.get("zone").get("name"), zone),
                    cb.equal(habitDefinition.get("type").get("name"), name)
            );
        };
    }

    public static Specification<Habit> byZoneId(Long zoneId) {
        return (root, query, cb) -> cb.equal(root.get("definition").get("zone").get("id"), zoneId);
    }
}