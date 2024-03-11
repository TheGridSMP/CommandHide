package the.grid.smp.commandhide.data;

public enum Operation {
    ADD,
    SUBTRACT;

    public static Operation fromChar(char c) {
        return switch (c) {
            case '+' -> Operation.ADD;
            case '-' -> Operation.SUBTRACT;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }
}