package info.developia.lib.bitree

import info.developia.lib.tree.BiTree
import spock.lang.Specification

class BiTreeTest extends Specification {

    List<String> names = ['John', 'Malcom', 'Steve', 'Dave']
    List<Integer> numbers = [91, 51, 1, 14]

    def "should retrieve Integer values in 'in-pre-post order'"() {
        given:
        var biTree = new BiTree(new Comparator<Integer>() {
            @Override
            int compare(Integer o1, Integer o2) {
                return o1 <=> o2
            }
        })
        when:
        numbers.each { biTree.push(it) }
        then:
        biTree.inOrder() == [1, 14, 51, 91]
        biTree.preOrder() == [91, 51, 1, 14]
        biTree.postOrder() == [14, 1, 51, 91]
    }

    def "should retrieve String values in 'in-pre-post order'"() {
        given:
        var biTree = new BiTree(new Comparator<String>() {
            @Override
            int compare(String o1, String o2) {
                return o1 <=> o2
            }
        })
        when:
        names.each { biTree.push(it) }
        then:
        biTree.inOrder() == ['Dave', 'John', 'Malcom', 'Steve']
        biTree.preOrder() == ['John', 'Dave', 'Malcom', 'Steve']
        biTree.postOrder() == ['Dave', 'Steve', 'Malcom', 'John']
    }
}