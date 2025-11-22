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
        def result = Try.of(function).orElse('default')
        then:
        'value' == result
    }

    def "should return 'default'"() {
        given:
        def function = { throw new RuntimeException() }
        when:
        def result = Try.of(function).orElse('default')
        then:
        'default' == result
    }

    def "should retry"() {
        given:
        def function = { throw new RuntimeException('what the duck!') }
        when:
        def result = Try.of(function).retries(3).orElse('no luck!')
        then:
        result == 'no luck!'
    }

    def "should retry and finally succeed"() {
        given:
        def attempt = 0
        def function = { if (attempt++ < 2) throw new RuntimeException('what the duck!') else return 'it is done' }
        when:
        def result = Try.of(function).retries(3).orElse('it is done')
        then:
        result == 'it is done'
    }

    def "should retry and finally succeed or"() {
        given:
        def attempt = 0
        def function = { if (attempt++ < 2) throw new RuntimeException('what the duck!') else return 'it is done' }
        when:
        def result = Try.of(function).retries(3).orElseThrow(() -> new UnknownError('what happened?'))
        then:
        result == 'it is done'
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
        def function = { throw new RuntimeException('what the duck!') }
        when:
        Try.of(function).orElseThrow(() -> new UnknownError('Terrible error'))
        then:
        def exception = thrown(UnknownError)
        exception.message == 'Terrible error'
    }
}
