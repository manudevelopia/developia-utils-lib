package info.developia.lib.value

import info.developia.lib.value.tries.Try
import spock.lang.Specification

class TryLazyTest extends Specification {
    public static final String terribleError = 'Terrible error'
    public static final String value = 'value'
    public static final String whatTheDuck = 'what the duck!'
    public static final String defaultValue = 'default'

    class MyApplicationError extends RuntimeException {
        MyApplicationError() {}

        MyApplicationError(String message) { super(message) }
    }

    def "should return 'value'"() {
        given:
        def function = { return value }
        when:
        def result = Try.lazyOf(function)
                .get()
        then:
        value == result
    }

    def "should return transformed 'value' to 'VALUE'"() {
        given:
        def function = { return value }
        when:
        def result = Try.lazyOf(function)
                .map(v -> v.toUpperCase())
                .orElse(defaultValue)
                .get()
        then:
        'VALUE'.toUpperCase() == result
    }

    def "should return transformed value to 26"() {
        given:
        def function = { return '26' }
        when:
        def result = Try.lazyOf(function)
                .map(v -> Integer.valueOf(v))
                .orElse(128)
                .get()
        then:
        26 == result
    }

    def "should fail transformation and return 128 value"() {
        given:
        def function = { return 'twenty six' }
        when:
        def result = Try.lazyOf(function)
                .map(v -> Integer.valueOf(v))
                .orElse(128)
                .get()
        then:
        128 == result
    }

    def "should return 'value'"() {
        given:
        def function = { return value }
        when:
        def result = Try.lazyOf(function)
                .orElse(defaultValue)
                .get()
        then:
        value == result
    }

    def "should return 'default'"() {
        given:
        def function = { throw new MyApplicationError() }
        when:
        def result = Try.lazyOf(function)
                .orElse(defaultValue)
                .get()
        then:
        defaultValue == result
    }

    def "should retry"() {
        given:
        def function = { throw new MyApplicationError(whatTheDuck) }
        when:
        def result = Try.lazyOf(function)
                .retries(3)
                .orElse('no luck!')
                .get()
        then:
        'no luck!' == result
    }

    def "should retry and finally succeed"() {
        given:
        def attempt = 0
        def function = { if (attempt++ < 2) throw new RuntimeException(whatTheDuck) else return 'it is done' }
        when:
        def result = Try.lazyOf(function)
                .retries(3)
                .orElse('it is done')
                .get()
        then:
        'it is done' == result
    }

    def "should retry with no success and throw exception"() {
        given:
        def function = { throw new RuntimeException(whatTheDuck) }
        when:
        Try.lazyOf(function)
                .retries(3)
                .orElseThrow(() -> new MyApplicationError('what happened?'))
                .get()
        then:
        def exception = thrown(MyApplicationError)
        exception.message == 'what happened?'
    }

    def "should throw 'UnknownError' with message"() {
        given:
        def function = { throw new RuntimeException(whatTheDuck) }
        when:
        Try.lazyOf(function)
                .orElseThrow(() -> new MyApplicationError(terribleError))
                .get()
        then:
        def exception = thrown(MyApplicationError)
        exception.message == terribleError
    }

    def "should print error message"() {
        given:
        def function = { throw new MyApplicationError(whatTheDuck) }
        def out = new ByteArrayOutputStream()
        def original = System.out
        System.setOut(new PrintStream(out))
        when:
        Try.lazyOf(function)
                .onError((error) -> System.out.println(error.getMessage()))
                .get()
        then:
        out.toString().trim() == whatTheDuck
        cleanup:
        System.setOut(original)
    }

    def "should print error message and throw 'UnknownError' with message from exception"() {
        given:
        def function = { throw new MyApplicationError(whatTheDuck) }
        def out = new ByteArrayOutputStream()
        def original = System.out
        System.setOut(new PrintStream(out))
        when:
        Try.lazyOf(function)
                .onError((error) -> System.out.println(error.getMessage()))
                .orElseThrow(() -> new MyApplicationError(terribleError))
                .get()
        then:
        def exception = thrown(MyApplicationError)
        exception.message == terribleError
        out.toString().trim() == whatTheDuck
        cleanup:
        System.setOut(original)
    }

}
