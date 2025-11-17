package info.developia.lib.pile

import spock.lang.Specification

class PileTest extends Specification {

    List<String> names = ['John', 'Malcom', 'Steve', 'Dave']

    def "should add an element"() {
        given:
        Pile pile = new Pile<String>()
        when:
        names.each { pile.push(it) }
        then:
        pile.pop() == 'Dave'
        pile.pop() == 'Steve'
        pile.pop() == 'Malcom'
        pile.pop() == 'John'
        pile.pop() == null
    }
}
