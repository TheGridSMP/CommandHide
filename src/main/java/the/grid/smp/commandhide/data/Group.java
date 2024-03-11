package the.grid.smp.commandhide.data;

import java.util.*;
import java.util.stream.Stream;

public record Group(String name, int priority, List<Command> commands, Set<String> inherits) {

    private static List<Command> merge(List<Command> list1, List<Command> list2) {
        Map<String, Command> mergedMap = new HashMap<>();

        Stream.of(list1, list2)
                .flatMap(List::stream)
                .forEach(command -> mergedMap.merge(command.context(), command, Command::merge));

        return new ArrayList<>(mergedMap.values());
    }

    public void inherit(Group other) {
        this.commands.clear();
        this.commands.addAll(merge(this.commands, other.commands));

        this.inherits.remove(other.name);
    }
}
