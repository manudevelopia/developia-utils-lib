package info.developia.lib.values

import spock.lang.Specification

class TupleTest extends Specification {

    def "should return a tuple"() {
        given:
        String name = 'Manu'
        Integer age = 17
        when:
        def tuple = new Tuple<>(name, age)
        then:
        with(tuple) {
            one() == name
            two() == age
        }
    }

    def "should return a tuple of 3 elements"() {
        given:
        String name = 'Manu'
        Integer age = 17
        String email = 'email@mail.com'
        when:
        def tuple = new Tuple3<>(name, age, email)
        then:
        with(tuple) {
            one() == name
            two() == age
            three() == email
        }
    }
}
