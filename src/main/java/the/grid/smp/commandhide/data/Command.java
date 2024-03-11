package the.grid.smp.commandhide.data;

public record Command(int priority, Operation operation, String context) {

    public Command merge(Command other) {
        if (this.isOpposite(other)) {
            return this.priority() >= other.priority() ? this : other;
        }

        return this.hasHigherPriority(other) ? this : other;
    }

    boolean isOpposite(Command other) {
        return this.operation() != other.operation() && this.context().equals(other.context());
    }

    boolean hasHigherPriority(Command other) {
        return this.priority() > other.priority() && this.context().equals(other.context());
    }

    public static Command parse(int priority, String input) {
        Operation sign = Operation.fromChar(input.charAt(1));
        return new Command(priority, sign, input.substring(3));
    }
}
