package ru.khehelk.practice.export.file.csv;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.khehelk.practice.export.file.CellType;

@Getter
@Setter
@Builder
public class CsvColumnDescriptor {
    private String header;
    private CellType cellType;
}
