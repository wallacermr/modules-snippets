package br.com.wallace.rafael;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;


public class AppTest {

    @Test
    public void shouldAnswerWithTrue() {
        int b = 0;
        Integer b1 = 0;

        System.out.println(ClassLayout.parseClass(AppTest.class).toPrintable());
        System.out.println(VM.current().sizeOf(b));
        assertTrue( true );
    }
}
