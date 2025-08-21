package br.com.dio.persistence.entity;

import java.util.stream.Stream;

/**
 * Represents the operations that are logged at the employee_audit table.
 */
public enum OperationEnum {
    INSERT,
    UPDATE,
    DELETE;

    /**
     * Finds an {@link OperationEnum} by the given database operation.
     *
     * @param dbOperation The database operation, either 'I' (insert), 'U' (update) or 'D' (delete).
     * @return The {@link OperationEnum} corresponding to the given database operation.
     * @throws NoSuchFieldException If the database operation is invalid.
     */
    public static OperationEnum getByDbOperation(final String dbOperation) throws NoSuchFieldException {
        return Stream.of(OperationEnum.values())
                .filter(operationEnum -> operationEnum.name().startsWith(dbOperation.toUpperCase()))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchFieldException(
                                "Operation '%s' is invalid. Use either 'I' (insert), 'U' ".formatted(dbOperation) +
                                        "(update) or 'D' (delete)."
                        )
                );
    }
}
