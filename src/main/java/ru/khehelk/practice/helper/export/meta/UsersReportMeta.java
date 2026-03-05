package ru.khehelk.practice.helper.export.meta;

import java.util.List;

import lombok.experimental.UtilityClass;
import ru.khehelk.practice.domain.dto.UserReportRow;
import ru.khehelk.practice.export.file.CellType;
import ru.khehelk.practice.export.file.xlsx.XlsxColumnDescriptor;
import ru.khehelk.practice.jooq.tables.pojos.Users;

@UtilityClass
public class UsersReportMeta {

    public static final List<XlsxColumnDescriptor<UserReportRow>> CH_XLSX;
    public static final List<XlsxColumnDescriptor<Users>> PG_XLSX;

    static {
        CH_XLSX = List.of(
            XlsxColumnDescriptor.<UserReportRow>builder()
                .header("ID")
                .cellType(CellType.NUMBER)
                .extractor(UserReportRow::id)
                .build(),
            XlsxColumnDescriptor.<UserReportRow>builder()
                .header("Email")
                .cellType(CellType.STRING)
                .extractor(UserReportRow::email)
                .build(),
            XlsxColumnDescriptor.<UserReportRow>builder()
                .header("Balance")
                .cellType(CellType.NUMBER)
                .extractor(row -> row.balance().doubleValue())
                .build(),
            XlsxColumnDescriptor.<UserReportRow>builder()
                .header("Created")
                .cellType(CellType.DATETIME)
                .extractor(UserReportRow::createdAt)
                .build()
        );
        PG_XLSX = List.of(
            XlsxColumnDescriptor.<Users>builder()
                .header("ID")
                .cellType(CellType.NUMBER)
                .extractor(Users::getId)
                .build(),
            XlsxColumnDescriptor.<Users>builder()
                .header("Email")
                .cellType(CellType.STRING)
                .extractor(Users::getEmail)
                .build(),
            XlsxColumnDescriptor.<Users>builder()
                .header("Age")
                .cellType(CellType.NUMBER)
                .extractor(Users::getAge)
                .build(),
            XlsxColumnDescriptor.<Users>builder()
                .header("FirstName")
                .cellType(CellType.STRING)
                .extractor(Users::getFirstName)
                .build()
        );
    }

}
