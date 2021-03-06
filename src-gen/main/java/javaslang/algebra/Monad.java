/*     / \____  _    _  ____   ______  / \ ____  __    _ _____
 *    /  /    \/ \  / \/    \ /  /\__\/  //    \/  \  / /  _  \   Javaslang
 *  _/  /  /\  \  \/  /  /\  \\__\\  \  //  /\  \ /\\/  \__/  /   Copyright 2014-now Daniel Dietrich
 * /___/\_/  \_/\____/\_/  \_/\__\/__/___\_/  \_//  \__/_____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.algebra;

/*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*\
   G E N E R A T O R   C R A F T E D
\*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/

import java.util.function.BiFunction;
import java.util.function.Function;
import javaslang.*;

/**
 * Defines a Monad by generalizing the flatMap function.
 * <p>
 * A {@code Monad} is a {@link Functor} with a {@code flatMap} method that satisfies the Monad laws, also known
 * as the three control laws:
 * <p>
 * Let
 * <ul>
 * <li>{@code A}, {@code B}, {@code C} be types</li>
 * <li>{@code unit: A -> Monad<A>} a constructor</li>
 * <li>{@code f: A -> Monad<B>}, {@code g: B -> Monad<C>} functions</li>
 * <li>{@code a} be an object of type {@code A}</li>
 * <li>{@code m} be an object of type {@code Monad<A>}</li>
 * </ul>
 * Then all instances of the {@code Monad} interface should obey the three control laws:
 * <ul>
 * <li><strong>Left identity:</strong> {@code unit(a).flatMap(f) ≡ f a}</li>
 * <li><strong>Right identity:</strong> {@code m.flatMap(unit) ≡ m}</li>
 * <li><strong>Associativity:</strong> {@code m.flatMap(f).flatMap(g) ≡ m.flatMap(x -> f.apply(x).flatMap(g))}</li>
 * </ul>
 *
 * <strong>The left identity `unit(a).flatMap(f) ≡ f.apply(a)` can't be satisfied for single-valued monads.</strong>
 * <p>
 * Example:
 *
 * <pre>
 * <code>
 * // = Try(1)
 * Try.success(20).flatMap(i -&gt; List.of(1, 2, 3));
 * </code>
 * </pre>
 *
 * To fix this, we change the flatMap method by adding a <em>unit</em> function:
 *
 * <pre>
 * <code>flatMap(f) := unit(map(f).flatten())</code>
 * </pre>
 *
 * where <em>unit</em>
 *
 * <ul>
 * <li>takes the first element (if present), if the underlying Monad is single-valued</li>
 * <li>takes all elements (if any is present), if the underlying Monad is multi-valued</li>
 * </ul>
 *
 * To read further about monads in Java please refer to
 * <a href="http://java.dzone.com/articles/whats-wrong-java-8-part-iv">What's Wrong in Java 8, Part IV: Monads</a>.
 *
 * @param <T> component type of this monad
 * @author Daniel Dietrich
 * @since 1.1.0
 */
public interface Monad<T> extends Functor<T>, Iterable<T> {

    /**
     * Lifts a {@code Function} to a higher {@code Function1} that operates on Monads.
     *
     * @param <T> 1st argument type of f
     * @param <R> result type of f
     * @param f a Function
     * @return a new Function1 that lifts the given function f in a layer that operates on monads.
     */
    static <T, R> Function1<? super Monad<T>, Monad<R>> lift(Function<? super T, ? extends R> f) {
        return mT -> mT.map(f::apply);
    }

    /**
     * Lifts a {@code BiFunction} to a higher {@code Function2} that operates on Monads.
     *
     * @param <T1> 1st argument type of f
     * @param <T2> 2nd argument type of f
     * @param <R> result type of f
     * @param f a BiFunction
     * @return a new Function2 that lifts the given function f in a layer that operates on monads.
     */
    static <T1, T2, R> Function2<Monad<T1>, Monad<T2>, Monad<R>> lift(BiFunction<? super T1, ? super T2, ? extends R> f) {
        return (mT1, mT2) ->
                mT1.flatMap(t1 ->
                mT2.map(t2 -> f.apply(t1, t2)));
    }

    /**
     * Lifts a {@code Function3} to a higher {@code Function3} that operates on Monads.
     *
     * @param <T1> 1st argument type of f
     * @param <T2> 2nd argument type of f
     * @param <T3> 3rd argument type of f
     * @param <R> result type of f
     * @param f a Function3
     * @return a new Function3 that lifts the given function f in a layer that operates on monads.
     */
    static <T1, T2, T3, R> Function3<Monad<T1>, Monad<T2>, Monad<T3>, Monad<R>> lift(Function3<? super T1, ? super T2, ? super T3, ? extends R> f) {
        return (mT1, mT2, mT3) ->
                mT1.flatMap(t1 ->
                mT2.flatMap(t2 ->
                mT3.map(t3 -> f.apply(t1, t2, t3))));
    }

    /**
     * Lifts a {@code Function4} to a higher {@code Function4} that operates on Monads.
     *
     * @param <T1> 1st argument type of f
     * @param <T2> 2nd argument type of f
     * @param <T3> 3rd argument type of f
     * @param <T4> 4th argument type of f
     * @param <R> result type of f
     * @param f a Function4
     * @return a new Function4 that lifts the given function f in a layer that operates on monads.
     */
    static <T1, T2, T3, T4, R> Function4<Monad<T1>, Monad<T2>, Monad<T3>, Monad<T4>, Monad<R>> lift(Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> f) {
        return (mT1, mT2, mT3, mT4) ->
                mT1.flatMap(t1 ->
                mT2.flatMap(t2 ->
                mT3.flatMap(t3 ->
                mT4.map(t4 -> f.apply(t1, t2, t3, t4)))));
    }

    /**
     * Lifts a {@code Function5} to a higher {@code Function5} that operates on Monads.
     *
     * @param <T1> 1st argument type of f
     * @param <T2> 2nd argument type of f
     * @param <T3> 3rd argument type of f
     * @param <T4> 4th argument type of f
     * @param <T5> 5th argument type of f
     * @param <R> result type of f
     * @param f a Function5
     * @return a new Function5 that lifts the given function f in a layer that operates on monads.
     */
    static <T1, T2, T3, T4, T5, R> Function5<Monad<T1>, Monad<T2>, Monad<T3>, Monad<T4>, Monad<T5>, Monad<R>> lift(Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> f) {
        return (mT1, mT2, mT3, mT4, mT5) ->
                mT1.flatMap(t1 ->
                mT2.flatMap(t2 ->
                mT3.flatMap(t3 ->
                mT4.flatMap(t4 ->
                mT5.map(t5 -> f.apply(t1, t2, t3, t4, t5))))));
    }

    /**
     * Lifts a {@code Function6} to a higher {@code Function6} that operates on Monads.
     *
     * @param <T1> 1st argument type of f
     * @param <T2> 2nd argument type of f
     * @param <T3> 3rd argument type of f
     * @param <T4> 4th argument type of f
     * @param <T5> 5th argument type of f
     * @param <T6> 6th argument type of f
     * @param <R> result type of f
     * @param f a Function6
     * @return a new Function6 that lifts the given function f in a layer that operates on monads.
     */
    static <T1, T2, T3, T4, T5, T6, R> Function6<Monad<T1>, Monad<T2>, Monad<T3>, Monad<T4>, Monad<T5>, Monad<T6>, Monad<R>> lift(Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> f) {
        return (mT1, mT2, mT3, mT4, mT5, mT6) ->
                mT1.flatMap(t1 ->
                mT2.flatMap(t2 ->
                mT3.flatMap(t3 ->
                mT4.flatMap(t4 ->
                mT5.flatMap(t5 ->
                mT6.map(t6 -> f.apply(t1, t2, t3, t4, t5, t6)))))));
    }

    /**
     * Lifts a {@code Function7} to a higher {@code Function7} that operates on Monads.
     *
     * @param <T1> 1st argument type of f
     * @param <T2> 2nd argument type of f
     * @param <T3> 3rd argument type of f
     * @param <T4> 4th argument type of f
     * @param <T5> 5th argument type of f
     * @param <T6> 6th argument type of f
     * @param <T7> 7th argument type of f
     * @param <R> result type of f
     * @param f a Function7
     * @return a new Function7 that lifts the given function f in a layer that operates on monads.
     */
    static <T1, T2, T3, T4, T5, T6, T7, R> Function7<Monad<T1>, Monad<T2>, Monad<T3>, Monad<T4>, Monad<T5>, Monad<T6>, Monad<T7>, Monad<R>> lift(Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> f) {
        return (mT1, mT2, mT3, mT4, mT5, mT6, mT7) ->
                mT1.flatMap(t1 ->
                mT2.flatMap(t2 ->
                mT3.flatMap(t3 ->
                mT4.flatMap(t4 ->
                mT5.flatMap(t5 ->
                mT6.flatMap(t6 ->
                mT7.map(t7 -> f.apply(t1, t2, t3, t4, t5, t6, t7))))))));
    }

    /**
     * Lifts a {@code Function8} to a higher {@code Function8} that operates on Monads.
     *
     * @param <T1> 1st argument type of f
     * @param <T2> 2nd argument type of f
     * @param <T3> 3rd argument type of f
     * @param <T4> 4th argument type of f
     * @param <T5> 5th argument type of f
     * @param <T6> 6th argument type of f
     * @param <T7> 7th argument type of f
     * @param <T8> 8th argument type of f
     * @param <R> result type of f
     * @param f a Function8
     * @return a new Function8 that lifts the given function f in a layer that operates on monads.
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, R> Function8<Monad<T1>, Monad<T2>, Monad<T3>, Monad<T4>, Monad<T5>, Monad<T6>, Monad<T7>, Monad<T8>, Monad<R>> lift(Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> f) {
        return (mT1, mT2, mT3, mT4, mT5, mT6, mT7, mT8) ->
                mT1.flatMap(t1 ->
                mT2.flatMap(t2 ->
                mT3.flatMap(t3 ->
                mT4.flatMap(t4 ->
                mT5.flatMap(t5 ->
                mT6.flatMap(t6 ->
                mT7.flatMap(t7 ->
                mT8.map(t8 -> f.apply(t1, t2, t3, t4, t5, t6, t7, t8)))))))));
    }

    /**
     * FlatMaps this Monad to a new Monad with different component type.
     * <p>
     * FlatMap is the sequence operation for functions and behaves like the imperative {@code ;}.
     * <p>
     * If the previous results are needed, flatMap cascades:
     * <pre>
     * <code>
     * m1().flatMapM(result1 -&gt;
     *      m2(result1).flatMapM(result2 -&gt;
     *          m3(result1, result2).flatMapM(result3 -&gt;
     *              ...
     *          )
     *      )
     * );
     * </code>
     * </pre>
     * If only the last result is needed, flatMap may be used sequentially:
     * <pre>
     * <code>
     * m1().flatMapM(this::m2)
     *     .flatMapM(this::m3)
     *     .flatMapM(...);
     * </code>
     * </pre>
     *
     * @param mapper A mapper
     * @param <U>    Component type of the mapped {@code Monad}
     * @return a mapped {@code Monad}
     * @throws NullPointerException if {@code mapper} is null
     */
    <U> Monad<U> flatMap(Function<? super T, ? extends Iterable<? extends U>> mapper);

    /**
     * Creates a new instance of this Monad. If this Monad is single-valued (like Option an Try),
     * only the first value of the given {@code iterable} is taken.
     *
     * @param <U> component type of the resulting Monad
     * @param iterable an iterable
     * @return a new {@code Monad} instance
     * @throws NullPointerException if {@code mapper} is null
     */
    <U> Monad<U> unit(Iterable<? extends U> iterable);

    // -- adjusting return types of super interface methods

    @Override
    <U> Monad<U> map(Function<? super T, ? extends U> mapper);
}