package info.developia.lib

import info.developia.lib.value.Lazy
import spock.lang.Specification

class LazyTest extends Specification {
    def name = 'Manu'
    def function = () -> { return name }

    def "should initialized only after get method call"() {
        given:
        def lazyName = Lazy.of(function)
        when:
        def result = lazyName.get()
        then:
        result == 'Manu'
    }

    def "should return default if method call fails"() {
        given:
        def lazyName = Lazy.of({ throw new UnknownError('oh!') })
        when:
        def result = lazyName.getOrElse('default')
        then:
        result == 'default'
    }
}
