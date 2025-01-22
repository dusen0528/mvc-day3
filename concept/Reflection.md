# Java Reflection

## Reflection 이란?
- 실행중인 Java 프로그램이 자신의 구조와 동작을 검사하고 수정할 수 있게 해주는 기능

## 리플렉션을 이용한 클래스 생성하기 

**기본 생성자를 이용한 객체 생성**
```java
package com.nhnacademy.day3.student.domain;

public class User {
    private String userName;
    private int userAge;


    public User(){
    }

    public User(String userName, int userAge){
        this.userAge = userAge;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    @Override
    public String toString(){
        return "User{"+
                "userName="+userName+'\''+
                ", userAge=" +userAge +
                '}';
    }
}
```
- 일반적으로 객체를 생성할 때
```java
User user = new User("최인호", 40);
```

- 리플렉션을 이용해서 객체를 생성할 때

```java
package com.nhnacademy.day3.student.domain;

import com.nhnacademy.day3.user.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {

        try {

            // forName -> 물리적인 클래스 파일명을 인자로 넘겨주면 이에 해당하는 클래스 반환
            Class userClass = Class.forName(User.class.getName());

            // public 접근자를 가진 생성자 반환
            Constructor<?> constructor = userClass.getConstructor();

            User user = (User) constructor.newInstance();
            System.out.println(user);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
```

- Class.forName(String className) 
  - 클래스의 전체 경로(패키지명 + 클래스명)를 문자열로 받아 해당하는 Class 객체 반환
  - 동적으로 클래스 로드시 사용

```java
Class userClass = Class.forName("com.nhnacademy.day3.user.User");
```

- getConstructor()
  - 클래스의 public 생성자 반환
  - 🌟매개변수가 있는 생성자의 경우 매개변수 타입 명시
```java
Constructor<?> constructor = userClass.getConstructor(); // 기본 생성자
Constructor<?> constructorWithParams = userClass.getConstructor(String.class, int.class); // 매개변수 있는 생성자
```

**다른 예시**
```java
// 기본 생성자만 가져오기
Constructor<?> defaultConstructor = userClass.getConstructor();

// String, int 파라미터를 가진 생성자만 가져오기
Constructor<?> paramConstructor = userClass.getConstructor(String.class, int.class);

// 모든 public 생성자 가져오기
Constructor<?>[] constructors = userClass.getConstructors();

// 모든 생성자(private 포함) 가져오기
Constructor<?>[] allConstructors = userClass.getDeclaredConstructors();
```
- 만약 존재하지 않는 파라미터 타입의 생성자를 가져오려고 한다면 NoSuchMethodException 발생


- newInstance()
  - InvocationTargetException()
    - 생성자나 메소드 호출중 예외에 발생하며 실제 발생한 예외는 이 예외의 cause로 포함
  - InstantiationException()
    - 추상 클래스나 인터페이스의 인스턴스를 생성하려고 할 때 발생
  - IllegalAccessException()
    - 접근 권한이 없는 생성자나 메소드에 접근하려고 할 때 발생 
  

### Arguments 전달되는 객체 생성하기
위에 방식보다 체이닝 방식으로 좀 더 간결히 작성 가능

```java
package com.nhnacademy.day3.student.domain;

import com.nhnacademy.day3.user.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {

        try {
            Constructor cUser =
                    Class.forName(User.class.getName()).getConstructor(String.class, Integer.TYPE);
            User user = (User) cUser.newInstance("inho", 26);
            System.out.println(user);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
```

- 여기서 중요한 부분은 자바 자료형에서 String은 **객체** int, double, boolean, char 등은 Primitive Type이기 때문에
- String.class , Integer.TYPE 으로 작성해주는 것이 맞다
- 자바에서는 레퍼런스 타입, 프리마티브 타입을 내부적으로 다르게 처리하기 때문 !

---
## 리플렉션을 이용한 메소드 호출
- 일반적인 메소드 호출
```java
    user.getUserName();
    user.getUserAge();
```

- 리플렉션 api를 이용한 메소드 호출 
  - User객체 생성 후 getDeclaredMethod를 이용해 getter, setter 메소드를 실행하는 예제

```java
package com.nhnacademy.day3.student.domain;

import com.nhnacademy.day3.user.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {

        try {
            Class clazz = Class.forName(User.class.getName());
            Object user = clazz.getDeclaredConstructor().newInstance();
            Method setUserNameMethod = clazz.getDeclaredMethod("setUserName", String.class);
            setUserNameMethod.invoke(user, "최인호");
            Method getUserNameMethod = clazz.getDeclaredMethod("getUserName");
            String userName = (String) getUserNameMethod.invoke(user);
            Method setUserAgeMethod = clazz.getDeclaredMethod("setUserAge", Integer.TYPE);
            setUserAgeMethod.invoke(user, 26);
            Method getUserAgeMethod = clazz.getDeclaredMethod("getUserAge");
            int userAge = (int) getUserAgeMethod.invoke(user);

            System.out.println("userName :" + userName);
            System.out.println("userAge :" + userAge);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
```

---

# 리플렉션 API를 이용한 의존성 주입
- 의존성 주입을 위한 @Autowired annotation 정의하기

### Autowired.java
```java
package com.nhnacademy.day3.student.domain;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
}
```


### UserRepository.java

```java
package com.nhnacademy.day3.student.repository;

import com.nhnacademy.day3.user.User;
import com.nhnacademy.day3.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<com.nhnacademy.day3.user.User> users = new ArrayList<>();

    public com.nhnacademy.day3.user.User findByName(String userName) {
        return users.stream()
                // 1단계 : 각 user객체 o에 대해 o.getUserName과 파라미터로 받은 userName이 같은지
                .filter(o -> o.getUserName().equals(userName))
                // 2단계 : 필터를 통과한 첫번째 요소를 Optional<User>로 반환
                .findFirst()
                // 3단계 : Optional이 비어있으면 null, 값이 있다면 그 값을 반환
                .orElse(null);
        
        /*
                public User findByName(String userName) {
                    for (User user : users) {
                        if (user.getUserName().equals(userName)) {
                            return user;
                        }
                    }
                    return null;
                }
         */
    }

    public void save(com.nhnacademy.day3.user.User user) {
        this.users.add(user);
    }

}
```
- users라는 ArrayList에 user를 등록하고 조회하는 역할
- 저장 : void save(User user) user객체를 파라미터로 전달받아 ArrayList에 등록
- 조회 : User findByName (String username) 파라미터로 userName을 전달받고 users리스트에 해당 이름 일치시 user 객체 반환


### UserService.java

```java
package com.nhnacademy.day3.student.domain;

import com.nhnacademy.day3.user.UserRepository;
import com.nhnacademy.day3.user.Autowired;
import com.nhnacademy.day3.user.User;

public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User getUser(String userName) {
        return userRepository.findByName(userName);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }
}
```

- UserRepository를 주입 받고 UserRepository로 부터 user 조회 후 추가하는 메소드 제공

### InjectUtil
```java
package com.nhnacademy.day3.user;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public final class InjectUtil {

    public static <T> T getObject(Class<T> classType) {
        // 1. 클래스를 받아 Object 생성
        T instance = createInstance(classType);
        // 2. 클래스의 부품(Fields)들을 확인
        Field[] fields = classType.getDeclaredFields();
        for (Field field : fields) {
            // 3. 특별한 표시 @Autowired 가 있는 필드 찾기
            if (field.getAnnotation(Autowired.class) != null) {
                // 4. 필요한 필드들을 만들어 끼워 넣는 과정
                Object fieldInstance = createInstance(field.getType());
                field.setAccessible(true);
                try {
                    field.set(instance, fieldInstance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return instance;
    }

    private static <T> T createInstance(Class<T> classType) {
        try {
            return classType.getConstructor(null).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```
- UserService 에 UserRepository 를 자동으로 주입합니다.
- UserService 를 Reflection API 를 이용해서 모든 fields 를 조회해서 field[] 배열로 반환합니다.
- 반환된 field[] 배열을 이용해서 순회하면서 Autowired.class 즉 @Autowired annotation 이 있는 field 를 조회합니다. 해당 field 는 createInstance() method 에 의해서 객체를 생성합니다.
- 즉 객체의 생성이 프로그램에 의해서 컨트롤 되었습니다. 객체 간의 의존성을 프로그래머가 직접 제어하기 보다는 InjectUtil 에 의해서 제어되었습니다.

###  왜 이렇게 하나요?

- 자동화:
레고 조각을 일일이 손으로 끼우지 않아도 돼요
프로그램이 알아서 필요한 부품을 찾아서 끼워넣어줍니다
- 유연성:
나중에 부품을 바꾸기 쉬워요
마치 레고 조각을 쉽게 교체할 수 있는 것처럼!
- 관리 용이성:
모든 부품 조립을 한 곳(InjectUtil)에서 관리해요
레고 조립 설명서를 한 곳에 모아두는 것과 같죠!

### DiTest.java
```java
package com.nhnacademy.day3.user;

public class DiTest {
    public static void main(String[] args) {
        UserService userService = InjectUtil.getObject(UserService.class);

        User user = new User("inho1", 10);

        userService.addUser(user);

        System.out.println(userService.getUser("inho1"));;
    }
}
```

---
> 💡FrontServlet에서 처리받던 *.do 가 이젠 필요없을거 같아 이렇게 수정해도 되지 않을까?
가능하다 @WebServlet(name = "frontServlet", urlPatterns = "/")  // 기존 "*.do" 에서 변경
단.. 
> private boolean isStaticResource(String path) {
return path.matches(".+\\.(css|js|png|jpg|gif|ico)$");
}
과 같이 정적 리소스(CSS, JS, 이미지 등) 처리를 위한 ResourceHandler 설정이 필요하고
DefaultServlet으로 정적 리소스 요청을 위임하는 로직을 FrontServlet에 구현
Spring Security 사용 시 정적 리소스에 대한 보안 예외 설정 등이 필요하다

