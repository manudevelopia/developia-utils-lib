# Singleton

## Intent (GoF Definition)

Singleton ensures that a class has exactly one instance and provides a global access point to it.

## When Singleton Is Appropriate

Use Singleton only when all the following are true:

- There must be exactly one shared instance
- The instance represents a system-wide concern
- Multiple instances would cause incorrect behavior, not just inefficiency

Typical legitimate use cases:

- Configuration registry
- Application-wide cache
- Connection pool manager
- Thread scheduler
- Hardware or OS resource coordinator