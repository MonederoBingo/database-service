package com.monederobingo.database.common.db.util.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.concurrent.FutureTask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MemoizerTest
{
    private Memoizer<Integer, Object> memoizer;
    @Mock
    private FutureTask<Object> futureTask;

    @Before
    public void setUp() throws Exception
    {
        memoizer = new Memoizer<>(arg -> new Object());
    }

    @Test
    public void shouldReturnNonNull() throws Exception
    {
        // when
        Object result = memoizer.compute(1);

        // then
        assertNotNull(result);
    }

    @Test
    public void shouldReturnSameObjectForSameKey() throws Exception
    {
        // when
        Object result1 = memoizer.compute(1);
        Object result2 = memoizer.compute(1);

        // then
        assertEquals(result1, result2);
    }

    @Test
    public void shouldReturnDifferentObjectForDifferentKey() throws Exception
    {
        // when
        Object result1 = memoizer.compute(1);
        Object result2 = memoizer.compute(2);

        // then
        assertNotEquals(result1, result2);
    }

    @Test
    public void shouldRemoveCancelledTasksFromCacheAndReturnCorrectResult() throws Exception
    {
        // given
        given(futureTask.get()).willReturn("1");
        givenMemoizerWithTaskThatGetsCancelled();

        // when
        Object result1 = memoizer.compute(1);

        // then
        assertEquals("1", result1);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenAnExecutionExceptionIsThrown() throws Exception
    {
        // given
        memoizer = new Memoizer<>(arg -> {
            throw new RuntimeException();
        });

        try
        {
            // when
            memoizer.compute(1);
        }
        catch (Exception e)
        {
            // then
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenAnErrorIsThrown() throws Exception
    {
        // given
        memoizer = new Memoizer<>(arg -> {
            throw new Error();
        });

        try
        {
            // when
            memoizer.compute(1);
        }
        catch (Exception e)
        {
            // then
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    private void givenMemoizerWithTaskThatGetsCancelled()
    {
        memoizer = new Memoizer<Integer, Object>(arg -> "")
        {
            boolean returnCancelTask = true;

            @Override
            FutureTask<Object> createFutureTask(Integer arg)
            {
                return getObjectFutureTask();
            }

            private FutureTask<Object> getObjectFutureTask()
            {
                if (returnCancelTask)
                {
                    returnCancelTask = false;
                    return getAutoCancelFutureTask();
                }
                return futureTask;
            }
        };
    }

    private FutureTask<Object> getAutoCancelFutureTask()
    {
        return new FutureTask<Object>(() -> null)
        {
            @Override
            public void run()
            {
                this.cancel(true);
            }
        };
    }
}
