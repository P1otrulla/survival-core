package pl.crafthype.core.shared;

import panda.std.Option;
import panda.std.stream.PandaStream;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtil {

    private static final Random RANDOM = ThreadLocalRandom.current();

    private RandomUtil() {
    }

    public static <T> Option<T> randomElement(Collection<T> collection) {
        if (collection.isEmpty()) {
            return Option.none();
        }

        return PandaStream.of(collection)
            .skip(RANDOM.nextInt(collection.size()))
            .head();
    }
}
