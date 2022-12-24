package jw.piano.api.managers;


import java.util.Collection;
import java.util.List;

public interface AbstractManager<T>
{
    T getCurrent();

    List<T> getItems();

    void setCurrent(String name);

    void setCurrent(T value);

    void register(T value);

    void unregister(T value);
}
