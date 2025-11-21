package info.developia.lib

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
}
