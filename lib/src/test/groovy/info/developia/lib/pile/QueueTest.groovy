package info.developia.lib.pile

import info.developia.lib.queue.Queue
import spock.lang.Specification

class QueueTest extends Specification {

    List<String> names = ['John', 'Malcom', 'Steve', 'Dave']

    def "should add an element"() {
        given:
        Queue queue = new Queue<String>()
        when:
        names.each { queue.push(it) }
        then:
        queue.pop() == 'John'
        queue.pop() == 'Malcom'
        queue.pop() == 'Steve'
        queue.pop() == 'Dave'
        queue.pop() == null
    }
}
