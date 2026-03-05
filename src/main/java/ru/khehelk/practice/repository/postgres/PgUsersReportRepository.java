package ru.khehelk.practice.repository.postgres;

import static ru.khehelk.practice.jooq.Tables.USERS;

import lombok.RequiredArgsConstructor;
import org.jooq.Cursor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PgUsersReportRepository {

    private final DSLContext ctx;

    public Cursor<? extends Record> streamUsersReport() {
        return ctx
            .select(
                USERS.ID,
                USERS.USERNAME,
                USERS.EMAIL,
                USERS.FIRST_NAME,
                USERS.LAST_NAME,
                USERS.AGE,
                USERS.STATUS,
                USERS.CREATED_AT,
                USERS.UPDATED_AT,
                USERS.LAST_LOGIN
            )
            .from(USERS)
            .orderBy(USERS.ID)
            .fetchSize(50)
            .fetchLazy();
    }

}
