# developia-utils-lib
Common utils

## Data structures
- Vector
- LinkedList
- Stack
- Queue
- Graph
  - Tree
    - binary tree 
      - heap
      - trie


## Design Pattern
Beginner Level

### Creational
- Singleton: Ensures a single instance of a class and provides a global access point.
- Factory Method: Delegates object creation to subclasses.
- Builder Constructs complex objects step by step.

### Structural
Adapter Makes incompatible interfaces work together.
Decorator Adds behavior dynamically without modifying the original class.
Facade Provides a simplified interface to a complex subsystem.

### Behavioral
Observer Notifies dependent objects automatically when state changes.
Strategy Encapsulates interchangeable algorithms.

🟡 Intermediate Level

### Creational
Abstract Factory Creates families of related objects without specifying concrete classes.

Prototype Creates new objects by cloning existing ones.
Structural

Composite
Treats individual objects and compositions uniformly.

Proxy
Controls access to another object.

Bridge
Decouples abstraction from implementation.

Behavioral

Command
Encapsulates a request as an object.

Template Method
Defines an algorithm skeleton, deferring steps to subclasses.

Iterator
Traverses collections without exposing internal structure.

State
Alters behavior when internal state changes.

🔴 Advanced Level

High abstraction, subtle intent, often misused if poorly understood.

Structural

Flyweight
Reduces memory usage by sharing fine-grained objects.

Behavioral

Mediator
Centralizes complex communication between objects.

Memento
Captures and restores object state without violating encapsulation.

Chain of Responsibility
Passes requests along a chain of handlers.

Visitor
Adds operations to object structures without modifying them.

Interpreter
Defines a grammar and interprets sentences of a language.

Recommended Learning Path

Given your strong backend and library-building background, I would suggest:

Start with
Factory Method → Strategy → Observer → Decorator

Then move to
Abstract Factory → Command → Template Method → State

Finish with
Mediator → Visitor → Interpreter

This path minimizes cognitive overload and aligns well with real-world usage in Java, Go, and Clojure ecosystems.