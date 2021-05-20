import java.util.*;
import java.util.stream.*;
import java.util.function.*;

//Done in mock PA2.
//Please mark PA2
public interface Logger<T>{

    public static <T> Logger<T> make(T currValue) {
        if(currValue == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
        if(currValue instanceof Logger) {
            throw new IllegalArgumentException("already a Logger");
        }
        return new LoggerImpl<T>(currValue);
    }
    
    public String toString();
    public boolean equals(Object o);
    public void printlog();
    public <U> Logger<U> map(Function<? super T, ? extends U> function);
    public <U> Logger<U> flatMap(Function<? super T, ? extends Logger<? extends U>> function);
    public List<String> getLog();
    public Optional<T> getVal();
    public boolean test(Predicate<T> pred);
}
