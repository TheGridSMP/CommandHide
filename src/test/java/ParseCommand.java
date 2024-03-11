public class ParseCommand {

    public static void main(String[] args) {
        System.out.println(parse("[-]command"));
    }

    public static CommandArg parse(String input) {
        Operation sign = Operation.fromChar(input.charAt(1));

        return new CommandArg(sign, input.substring(3));
    }

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

    public record CommandArg(Operation op, String name) {}
}
