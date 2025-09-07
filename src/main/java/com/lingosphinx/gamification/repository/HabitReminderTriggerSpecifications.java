package com.lingosphinx.gamification.repository;

import com.lingosphinx.gamification.domain.HabitReminder;
import com.lingosphinx.gamification.domain.HabitReminderTrigger;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;

public class HabitReminderTriggerSpecifications {

    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    public static Specification<HabitReminderTrigger> isNextDueBeforeNow() {
        return (root, query, cb) -> {
            var nextDue = root.get("nextDue").as(Timestamp.class);
            return cb.and(cb.lessThan(nextDue, Timestamp.from(Instant.now())));
        };
    }

    public static Specification<HabitReminderTrigger> incompleteProgress() {
        return (root, query, cb) -> {
            var habit = root.get("habit");
            return cb.lessThan(
                    habit.get("progress"),
                    habit.get("definition").get("target")
            );
        };
    }

    public static Specification<HabitReminderTrigger> due() {
        return incompleteProgress().and(isNextDueBeforeNow());
    }

    public static Specification<HabitReminderTrigger> noReminderExists() {
        return (root, query, cb) -> {
            var subquery = query.subquery(Long.class);
            var reminderRoot = subquery.from(HabitReminder.class);
            subquery
                    .select(cb.literal(1L))
                    .where(cb.equal(root.get("habit"), reminderRoot.get("habit")));
            return cb.not(cb.exists(subquery));
        };
    }
}