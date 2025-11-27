package info.developia.lib.value

import info.developia.lib.value.tries.Try
import spock.lang.Specification

class TryLazyTest extends Specification {
    def value = 'value'
    def whatTheDuck = 'what the duck!'

    def "should return 'value'"() {
        given:
        def function = { return value }
        when:
        def result = Try.lazyOf(function)
        then:
        value == result.get()
    }

    def "should return transformed value to 'VALUE'"() {
        given:
        def function = { return value }
        when:
        def result = Try.lazyOf(function)
                .map(v -> v.toUpperCase())
                .orElse('default')
        then:
        'VALUE'.toUpperCase() == result.get()
    }

    def "should return transformed value to 26"() {
        given:
        def function = { return '26' }
        when:
        def result = Try.lazyOf(function)
                .map(v -> Integer.valueOf(v))
                .orElse(128)
        then:
        26 == result.get()
    }

    def "should return 'value'"() {
        given:
        def function = { return value }
        when:
        def result = Try.lazyOf(function).orElse('default')
        then:
        value == result.get()
    }

    def "should return 'default'"() {
        given:
        def function = { throw new RuntimeException() }
        when:
        def result = Try.lazyOf(function).orElse('default')
        then:
        'default' == result.get()
    }

    def "should retry"() {
        given:
        def function = { throw new RuntimeException(whatTheDuck) }
        when:
        def result = Try.lazyOf(function).retries(3).orElse('no luck!')
        then:
        'no luck!' == result.get()
    }

    def "should retry and finally succeed"() {
        given:
        def attempt = 0
        def function = { if (attempt++ < 2) throw new RuntimeException(whatTheDuck) else return 'it is done' }
        when:
        def result = Try.lazyOf(function).retries(3).orElse('it is done')
        then:
        'it is done' == result.get()
    }

    def "should retry and finally succeed or"() {
        given:
        def attempt = 0
        def function = { if (attempt++ < 2) throw new RuntimeException(whatTheDuck) else return 'it is done' }
        when:
        def result = Try.lazyOf(function).retries(3).orElseThrow(() -> new UnknownError('what happened?'))
        then:
        'it is done' == result.get()
    }

    def "should throw 'UnknownError' with message"() {
        given:
        def function = { throw new RuntimeException(whatTheDuck) }
        when:
        Try.lazyOf(function).orElseThrow(() -> new RuntimeException('Terrible error')).get()
        then:
        def exception = thrown(RuntimeException)
        exception.message == 'Terrible error'
    }

    def "should throw 'UnknownError' with message from exception"() {
        given:
        def function = { throw new RuntimeException(whatTheDuck) }
        when:
        Try.lazyOf(function).orElseThrowWithMessage(UnknownError).get()
        then:
        def exception = thrown(UnknownError)
        exception.message == whatTheDuck
    }

    def "should print error message and throw 'UnknownError' with message from exception"() {
        given:
        def function = { throw new RuntimeException(whatTheDuck) }
        def out = new ByteArrayOutputStream()
        def original = System.out
        System.setOut(new PrintStream(out))
        when:
        Try.lazyOf(function)
                .onError((error) -> System.out.println(error.getMessage()))
                .orElseThrowWithMessage(UnknownError)
                .get()
        then:
        def exception = thrown(UnknownError)
        exception.message == whatTheDuck
        out.toString().trim() == whatTheDuck
        cleanup:
        System.setOut(original)
    }
}
