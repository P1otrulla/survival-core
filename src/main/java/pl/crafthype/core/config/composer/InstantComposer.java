package pl.crafthype.core.config.composer;

import panda.std.Result;

import java.time.Instant;

public class InstantComposer implements SimpleComposer<Instant> {

    @Override
    public Result<Instant, Exception> deserialize(String source) {
        return Result.ok(Instant.parse(source));
    }

    @Override
    public Result<String, Exception> serialize(Instant entity) {
        return Result.ok(entity.toString());
    }
}
