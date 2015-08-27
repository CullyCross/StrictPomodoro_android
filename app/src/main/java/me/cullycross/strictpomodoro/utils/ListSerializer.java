package me.cullycross.strictpomodoro.utils;

import com.activeandroid.serializer.TypeSerializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by: cullycross
 * Date: 8/27/15
 * For my shining stars!
 */
public class ListSerializer extends TypeSerializer {

    private static final Gson gson = new Gson();

    @Override
    public Class<?> getDeserializedType() {
        return List.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public Object serialize(final Object o) {
        if (o == null) {
            return null;
        }

        // Maybe it's possible to make universal typeserializer, but I didn't find how to
        // so here is a glitch
        return gson.toJson(o, new TypeToken<List<String>>(){}.getType());
    }

    @Override
    public Object deserialize(final Object o) {
        if (o == null) {
            return null;
        }

        // And here, too
        return gson.fromJson(o.toString(), new TypeToken<List<String>>(){}.getType());
    }
}
