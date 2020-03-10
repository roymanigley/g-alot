/*
<!-- https://mvnrepository.com/artifact/org.jmockit/jmockit -->
<dependency>
    <groupId>org.jmockit</groupId>
    <artifactId>jmockit</artifactId>
    <version>1.49</version>
    <scope>test</scope>
</dependency>
*/

// We can mock this static method using JMockit's anonymous 
// class mockit.MockUp.MockUp<T> (where T will be the class name) and @Mock annotation:

// PUBLIC STATIC	
@Test
public void givenAppManager_whenStaticMethodCalled_thenValidateExpectedResponse() {
    new MockUp<AppManager>() {
        @Mock
        public boolean isResponsePositive(String value) {
            return false;
        }
    };

    assertFalse(appManager.managerResponse("Some string..."));
}

// PRIVATE STATIC
@Test
public void givenAppManager_whenPrivateStaticMethod_thenValidateExpectedResponse() {
    int response = Deencapsulation.invoke(AppManager.class, "stringToInteger", "110");
    assertEquals(110, response);
}

public class AppManager {
 
    public boolean managerResponse(String question) {
        return AppManager.isResponsePositive(question);
    }
 
    public static boolean isResponsePositive(String value) {
        if (value == null) {
            return false;
        }
        int length = value.length();
        int randomNumber = randomNumber();
        return length == randomNumber ? true : false;
    }
    
    private static Integer stringToInteger(String num) {
        return Integer.parseInt(num);
    }
 
    private static int randomNumber() {
        return new Random().nextInt(7);
    }
}
