import java.util.*;
import java.util.stream.*;
import java.util.function.*;

//Done in mock PA2
//Please mark PA2
public class LoggerImpl<T> implements Logger<T> {

	private final Optional<T> currValue;
	private final List<String> log;

	public LoggerImpl(T currValue) {

		this.currValue = Optional.ofNullable(currValue);

		log = new ArrayList<>();
		log.add("Value initialized. Value = " + this.currValue.orElse(null));
	}

	public LoggerImpl(T currValue, List<String> log) {
		this.currValue = Optional.ofNullable(currValue);
		this.log = log;
	}

    @Override
	public <U> Logger<U> map(Function<? super T, ? extends U> function) {
		List<String> copyLog = new ArrayList<>(log);

		Optional<U> copyVal = this.currValue.map(function);

		if (copyVal.equals(currValue)) {
			copyLog.add("Value unchanged. Value = " + currValue.orElse(null));
		} else {
			copyLog.add("Value changed! New value = " + copyVal.orElse(null));
		}

		return new LoggerImpl<>(copyVal.orElse(null), copyLog);

	}

	@Override
	public <U> Logger<U> flatMap(Function<? super T, ? extends Logger<? extends U>> function) {
		List<String> copyLog = new ArrayList<>(log);

		Logger<? extends U> newLogger = (LoggerImpl<? extends U>) this.currValue.map(function).orElse(null);

		for (String str : newLogger.getLog().subList(1,newLogger.getLog().size())) {
			copyLog.add(str);
		}

		return new LoggerImpl<>(newLogger.getVal().orElse(null), copyLog);
	}

	@Override
	public String toString() {
		T unwrappedVal = currValue.orElse(null);

		return String.format("Logger[%s]", unwrappedVal);
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		} else if (o instanceof LoggerImpl) {
			LoggerImpl<?> oLoggerImpl = (LoggerImpl<?>) o;

			return this.currValue.equals(oLoggerImpl.currValue) && this.log.equals(oLoggerImpl.log);
		} else {
			return false;
		}
	}

    @Override
	public void printlog() {
		log.stream().forEach(x -> System.out.println(x));
	}

    @Override
    public Optional<T> getVal() {
        return this.currValue;
    }

    @Override
    public List<String> getLog() {
        return this.log;
    }

    @Override
    public boolean test(Predicate<T> pred) {
        return pred.test(currValue.orElse(null));
    }

}
