package ru.khehelk.practice.export.file.xlsx;

import java.util.function.Function;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.khehelk.practice.export.file.CellType;
import ru.khehelk.practice.export.file.ColumnDescriptor;

@Getter
@Setter
@Builder
public class XlsxColumnDescriptor<T> implements ColumnDescriptor<T> {
    private final String header;
    private final Function<T, Object> extractor;
    private final CellType cellType;

    public Object extract(T row) {
        return extractor.apply(row);
    }
}
