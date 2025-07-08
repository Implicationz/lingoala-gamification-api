package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.Habit;
import com.lingosphinx.gamification.domain.RenewalType;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;

public class HabitSpecifications {

    public static Specification<Habit> incompleteProgress() {
        return (root, query, cb) -> {
            var goal = root.join("goal");
            return cb.lessThan(
                    goal.get("progress"),
                    goal.get("definition").get("target")
            );
        };
    }

    public static Specification<Habit> isDailyDue() {
        return (root, query, cb) -> {
            var renewalType = root.get("streak").get("renewalType");
            var lastRenewal = root.get("streak").get("lastRenewal");
            var today = LocalDate.now(ZoneId.systemDefault());
            var lastRenewalDate = cb.function("date", LocalDate.class, lastRenewal);
            return cb.and(
                    cb.equal(renewalType, RenewalType.DAILY),
                    cb.lessThan(lastRenewalDate, today)
            );
        };
    }

    public static Specification<Habit> isWeeklyDue() {
        return (root, query, cb) -> {
            var renewalType = root.get("streak").get("renewalType");
            var lastRenewal = root.get("streak").get("lastRenewal");
            var currentWeek = LocalDate.now(ZoneId.systemDefault()).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            var currentYear = LocalDate.now(ZoneId.systemDefault()).getYear();
            var lastRenewalDate = cb.function("date", LocalDate.class, lastRenewal);
            var lastRenewalWeek = cb.function("week", Integer.class, lastRenewalDate);
            var lastRenewalYear = cb.function("year", Integer.class, lastRenewalDate);
            return cb.and(
                    cb.equal(renewalType, RenewalType.WEEKLY),
                    cb.or(
                            cb.lessThan(lastRenewalYear, currentYear),
                            cb.lessThan(lastRenewalWeek, currentWeek)
                    )
            );
        };
    }

    public static Specification<Habit> isMonthlyDue() {
        return (root, query, cb) -> {
            var renewalType = root.get("streak").get("renewalType");
            var lastRenewal = root.get("streak").get("lastRenewal");
            var currentMonth = LocalDate.now(ZoneId.systemDefault()).getMonthValue();
            var currentYear = LocalDate.now(ZoneId.systemDefault()).getYear();
            var lastRenewalDate = cb.function("date", LocalDate.class, lastRenewal);
            var lastRenewalMonth = cb.function("month", Integer.class, lastRenewalDate);
            var lastRenewalYear = cb.function("year", Integer.class, lastRenewalDate);
            return cb.and(
                    cb.equal(renewalType, RenewalType.MONTHLY),
                    cb.or(
                            cb.lessThan(lastRenewalYear, currentYear),
                            cb.lessThan(lastRenewalMonth, currentMonth)
                    )
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
}