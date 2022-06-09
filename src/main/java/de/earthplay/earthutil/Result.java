/* (C) 2022 EarthPlay */
package de.earthplay.earthutil;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Result<T, E extends Exception> {
    private final T ok;
    private final E error;

    public Result(@Nullable T value, @Nullable E error) {
        this(value, error, true);
    }

    private Result(@Nullable T ok, @Nullable E error, boolean validate) {
        this.ok = ok;
        this.error = error;
        if (validate) this.validate();
    }

    public void validate() throws IllegalArgumentException {
        if (!this.isOk() && !this.isError())
            throw new IllegalArgumentException("Result must be an Error or have a value");
    }

    @NotNull
    public T unwrap() throws RuntimeException {
        if (this.isOk()) return this.ok;
        else throw new RuntimeException("Unwrap failed because value is None");
    }

    @NotNull
    public T unwrapOr(T def) {
        return this.isOk() ? this.ok : def;
    }

    @NotNull
    public T unwrapOrElse(Function<E, T> mapper) {
        return this.isOk() ? this.ok : mapper.apply(this.error);
    }

    @Nullable
    public T unwrapUnchecked() {
        return this.ok;
    }

    @Nullable
    public E unwrapErrorUnchecked() {
        return this.error;
    }

    @NotNull
    public T expect(String message) throws RuntimeException {
        if (this.isOk()) return this.ok;
        else throw new RuntimeException(message);
    }

    @NotNull
    public <O extends Exception> Result<T, O> mapError(Function<E, O> mapper) {
        return new Result<>(this.ok, this.isError() ? mapper.apply(this.error) : null, false);
    }

    @NotNull
    public <O> Result<O, E> map(Function<T, O> mapper) {
        return new Result<>(this.isOk() ? mapper.apply(this.ok) : null, this.error, false);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <O extends Exception, F extends Exception> Result<T, F> orUnchecked(
            Result<T, O> result) {
        return this.isOk() ? (Result<T, F>) this : (Result<T, F>) result;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <O extends Exception> Result<T, Exception> or(Result<T, O> result) {
        return this.isOk() ? (Result<T, Exception>) this : (Result<T, Exception>) result;
    }

    @NotNull
    public <O> O mapOr(O def, Function<T, O> mapper) {
        return this.isOk() ? mapper.apply(this.ok) : def;
    }

    @NotNull
    public <O> O mapOrElse(Function<E, O> elseMapper, Function<T, O> mapper) {
        return this.isOk() ? mapper.apply(this.ok) : elseMapper.apply(this.error);
    }

    @NotNull
    public Option<T> ok() {
        return new Option<>(this.ok);
    }

    @NotNull
    public Option<E> error() {
        return new Option<>(this.error);
    }

    public boolean isError() {
        return this.error != null;
    }

    public boolean isErrorAnd(Function<E, Boolean> other) {
        return this.isError() && other.apply(this.error);
    }

    public boolean isOk() {
        return this.ok != null;
    }

    public boolean contains(T ok) {
        return this.ok == ok;
    }

    public boolean containsError(E error) {
        return this.error == error;
    }

    public boolean isOkAnd(Function<T, Boolean> other) {
        return this.isOk() && other.apply(this.ok);
    }
}
