package helpers;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

import static org.mockito.Mockito.doAnswer;

public class MoreAnswers {
    private MoreAnswers() {
        // Static class
    }

    @SafeVarargs
    public static <T> Stubber cycleOn(T... values) {
        return doAnswer(new Answer<T>() {
            int index = 0;

            @Override
            public T answer(InvocationOnMock invocationOnMock) {
                return values[index++ % values.length];
            }
        });
    }
}
