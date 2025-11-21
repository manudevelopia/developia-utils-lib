package info.developia.lib.values

import spock.lang.Specification

class TryTest extends Specification {

    def "should return 'value'"() {
        given:
        def function = { return 'value' }
        when:
        def result = Try.of(function)
        then:
        'value' == result.get()
    }

    def "should return 'value'"() {
        given:
        def function = { return 'value' }
        when:
        def result = Try.of(function)
        then:
        'value' == result.orElse('default')
    }

    def "should return 'default'"() {
        given:
        def function = { throw new RuntimeException() }
        when:
        def result = Try.of(function)
        then:
        'default' == result.orElse('default')
    }

    def "should throw 'UnknownError'"() {
        given:
        def function = { throw new RuntimeException('what the duck!') }
        when:
        Try.of(function).orElseThrow(UnknownError)
        then:
        thrown(UnknownError)
    }

    def "should throw 'UnknownError' with message"() {
        given:
        def function = { throw new RuntimeException() }
        when:
        Try.of(function).orElseThrow(() -> new UnknownError('Terrible error'))
        then:
        def exception = thrown(UnknownError)
        exception.message == 'Terrible error'
    }
}
