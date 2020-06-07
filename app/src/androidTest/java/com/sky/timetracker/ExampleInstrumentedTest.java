package com.sky.timetracker;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.sky.timetracker.Model.DAO.DaoImpl;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void testQuery(){
        Context context = InstrumentationRegistry.getContext();

        DaoImpl dao = new DaoImpl(context);

        boolean b = dao.queryType("学习");
        Log.d("测试", String.valueOf(b));
    }
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.sky.timetracker", appContext.getPackageName());
    }
}
