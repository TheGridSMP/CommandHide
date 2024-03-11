```yaml
groups:
  # DO NOT remove the default group, or shit will break
  default:
    priority: 0
    commands:
      - '[-]*'
      - '[+]msg'
  op:
    priority: 99
    commands:
      - '[+]*'
      - '[-]supersecretcommand'
    inherits:
      - default
```
_(in the default configuration, the `[+]*` cancels out the `[-]*` rule made by `default` group for the `op` group. this needs to be done because the `op` group inherits the `default` group for educational purposes..!)_


Each group has its name (i.e. `default`, `op`), `priority` and list of `commands` rules it has.
You can customize groups however you want BUT you can't remove the `default` group.

To give a player a group, give them `commandhide.group.<name>` permission.

## Command Rules
To allow a command use the `[+]` prefix, to disallow a command use `[-]` prefix.

### Wildcards
Wildcards are allowed, so if you want to disallow all commands you can use `[-]*`.

Wildcards can be used with normal command rules to make a whitelist! (i.e. see the `default` group's default configuration; it disallows all commands but `/msg`!).

And they can be used to make a blacklist (i.e. see the `op` group's default configuration; it allows all commands except for the super secret one!)