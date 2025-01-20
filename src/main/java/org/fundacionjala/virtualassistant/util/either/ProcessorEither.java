package org.fundacionjala.virtualassistant.util.either;

import java.util.function.Function;

public interface ProcessorEither<L, R> {
  <T> Function<T, Either<L, R>> lift(Function<T, Either<L, R>> function);
}
