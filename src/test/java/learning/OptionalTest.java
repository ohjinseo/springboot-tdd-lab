package learning;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Optional.nullable에 null이 주어지면 map이 호출되지 않고, 5L이 반환
// null이 아닌 값이 주어지면 map이 호출되고, RuntimeExcpetion 예외 던짐

public class OptionalTest {
    @Test
    void doesNotInvokeOptionalInnerFunctionByOuterNullValueTest() {
        // given, when
        Long result = Optional.ofNullable(null)
                .map(id -> Optional.ofNullable((Long) null).orElseThrow(RuntimeException::new)).orElse(5L);

        // then
        assertThat(result).isEqualTo(5L);
    }

    @Test
    void catchWhenExceptionIsThrownInOptionalInnerFunctionTest() { // 2
        // given, when, then
        assertThatThrownBy(
                () -> Optional.ofNullable(5L)
                        .map(id -> Optional.ofNullable((Long)null).orElseThrow(RuntimeException::new))
                        .orElse(1L))
                .isInstanceOf(RuntimeException.class);
    }
}
