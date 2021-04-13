package io.github.ititus.pdx.util.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class IteratorBufferTests {

    @Test
    @DisplayName("zero size buffer")
    void testEmptyBuffer() {
        Iterator<String> it = List.of("a", "b", "c").iterator();
        IteratorBuffer<String> buf = new IteratorBuffer<>(it, 0, 0);

        assertThat(buf.get()).isEqualTo("a");

        buf.next();
        assertThat(buf.get()).isEqualTo("b");

        buf.next();
        assertThat(buf.get()).isEqualTo("c");
    }

    @Test
    @DisplayName("size one forward buffer")
    void testForwardBuffer() {
        Iterator<String> it = List.of("a", "b", "c").iterator();
        IteratorBuffer<String> buf = new IteratorBuffer<>(it, 1, 0);

        assertThat(buf.get()).isEqualTo("a");
        assertThat(buf.get(1)).isEqualTo("b");

        buf.next();
        assertThat(buf.get()).isEqualTo("b");
        assertThat(buf.get(1)).isEqualTo("c");

        buf.next();
        assertThat(buf.get()).isEqualTo("c");
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> buf.get(1));
    }

    @Test
    @DisplayName("size one backward buffer")
    void testBackwardBuffer() {
        Iterator<String> it = List.of("a", "b", "c").iterator();
        IteratorBuffer<String> buf = new IteratorBuffer<>(it, 0, 1);

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> buf.get(-1));
        assertThat(buf.get()).isEqualTo("a");

        buf.next();
        assertThat(buf.get(-1)).isEqualTo("a");
        assertThat(buf.get()).isEqualTo("b");

        buf.next();
        assertThat(buf.get(-1)).isEqualTo("b");
        assertThat(buf.get()).isEqualTo("c");
    }

    @Test
    @DisplayName("size one buffer")
    void testBuffer() {
        Iterator<String> it = List.of("a", "b", "c").iterator();
        IteratorBuffer<String> buf = new IteratorBuffer<>(it, 1, 1);

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> buf.get(-1));
        assertThat(buf.get()).isEqualTo("a");
        assertThat(buf.get(1)).isEqualTo("b");

        buf.next();
        assertThat(buf.get(-1)).isEqualTo("a");
        assertThat(buf.get()).isEqualTo("b");
        assertThat(buf.get(1)).isEqualTo("c");

        buf.next();
        assertThat(buf.get(-1)).isEqualTo("b");
        assertThat(buf.get()).isEqualTo("c");
        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> buf.get(1));
    }
}
