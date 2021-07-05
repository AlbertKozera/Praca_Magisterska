import generator.Generator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        var generator = new Generator();
        generator.generate();

    }
}
