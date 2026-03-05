package ru.khehelk.practice.export.file;

public interface ColumnDescriptor<T> {
    Object extract(T row);
}
