package info.developia.lib.bitree

import info.developia.lib.tree.BiTree
import spock.lang.Specification

class BiTreeTest extends Specification {

    List<String> names = ['John', 'Malcom', 'Steve', 'Dave']
    List<Integer> numbers = [91, 51, 1, 14]

    def "should retrieve Integer values in 'in-order'"() {
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
        biTree.popInOrder() == 1
        biTree.popInOrder() == 14
        biTree.popInOrder() == 51
        biTree.popInOrder() == 91
        biTree.popInOrder() == null
    }

    def "should retrieve Integer values in 'pre-order'"() {
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
        biTree.preOrder() == [91, 51, 1, 14]
//        biTree.popInOrder() == 91
//        biTree.popInOrder() == 51
//        biTree.popInOrder() == 1
//        biTree.popInOrder() == 14
//        biTree.popInOrder() == null
    }

    def "should retrieve Integer values in 'pre-order'"() {
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
        biTree.postOrder() == [14, 1, 51, 91]
//        biTree.popInOrder() == 14
//        biTree.popInOrder() == 1
//        biTree.popInOrder() == 51
//        biTree.popInOrder() == 91
//        biTree.popInOrder() == null
    }

    def "should retrieve String values in 'in-order'"() {
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
//        biTree.preOrder() == ['John', 'Dave', 'Malcom', 'Steve']
//        biTree.postOrder() == ['Dave', 'Steve', 'Malcom', 'John']
//        biTree.popInOrder() == 'Dave'
//        biTree.popInOrder() == 'John'
//        biTree.popInOrder() == 'Malcom'
//        biTree.popInOrder() == 'Steve'
//        biTree.popInOrder() == null
    }
}