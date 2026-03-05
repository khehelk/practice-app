package ru.khehelk.practice.repository.clickhouse;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;
import ru.khehelk.practice.domain.dto.UserReportRow;

@Mapper
public interface ChUsersReportRepository {

    @Select("""
        SELECT id,
               email,
               balance,
               created_at
        FROM users
        ORDER BY created_at
    """)
    @Options(
        fetchSize = 16384,
        resultSetType = ResultSetType.FORWARD_ONLY
    )
    @ResultType(UserReportRow.class)
    void fetchStreamUsers(@Param("filter") String filter, ResultHandler<UserReportRow> handler);
}