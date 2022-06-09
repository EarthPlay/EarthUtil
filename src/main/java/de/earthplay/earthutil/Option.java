/* (C) 2022 EarthPlay */
package de.earthplay.earthutil;

import org.jetbrains.annotations.NotNull;

public class Option<T> {
    private final T value;

    public Option(T value) {
        this.value = value;
    }

    @NotNull
    public T unwrap() throws RuntimeException {
        if (this.isSome()) return value;
        else throw new RuntimeException("Unwrap failed because value is None");
    }

    @NotNull
    public T expect(String message) throws RuntimeException {
        if (this.isSome()) return value;
        else throw new RuntimeException(message);
    }

    public boolean isSome() {
        return value != null;
    }

    public boolean isNone() {
        return value == null;
    }
}
