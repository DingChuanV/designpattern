# 设计模式实战

![image-20220425203248829](https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204252032811.png)

👾设计模式demo实战。

项目地址：https://github.com/bearbrick0/designpattern

## 🫑创建型设计模式

### 🥦单例设计模式

#### 🥝懒汉模式实现单例模式

##### 懒汉式（**多线程下线程不安全**）

(具体参考`com/uin/creationPattern/Singleton/LazyBones/LazyMan`)

这种方式在单线程的环境下使用，对于多线程是无法保证单例的，这里列出来是为了和后面使用锁保证线程安全做对比。

```java
package com.uin.creationPattern.Singleton.LazyBones.LazyMan;

/**
 * 第一种方法 懒汉(线程不安全)
 */
public class Person {

    private String name;
    private int age;

    /**
     * 懒汉--需要的时候在创建对象
     */
    private static Person instance;

    /**
     * 构造器私有，外部不能实例化
     */
    private Person() {

    }

    /**
     * 提供一个静态的公有方法，当使用该方法时才去创建instance
     */
    public static Person wanglufeiPerson() {
        //没有在去创建
        if (instance == null) {
            instance = new Person();
        }
        return instance;
    }
}
```

```java
package com.uin.creationPattern.Singleton.LazyBones.LazyMan;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月27日 14:59
 * \* Description:
 * \
 */
public class MainTest {
    public static void main(String[] args) {
        //测试
        Person person1 = Person.wanglufeiPerson();
        Person person2 = Person.wanglufeiPerson();
        System.out.println(person1 == person2);

        //测试
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());

        /**
         * 总结: 懒汉式（线程不安全）优缺点
         *     (1) 起到了Lazy Loadding的效果，但是只能在单线程的情况下使用
         *     (2) 如果在多线程下，一个线程进入了if(instance==null)判断语句块，还未来得及往下执行，另一个线程也通过了
         *         这个判断语句这时就会产生多个实例。所以在多线程的情况下不可使用这种方式。
         * 结论： 在实际开发中，不要使用这种方式。
         */
    }
}
```

- 优点： 懒加载
- 缺点： 线程不安全

##### 懒汉(加入同步锁线程安全)

(参考代码`com/uin/creationPattern/Singleton/LazyBones/AddSynchronized/Singleton.`
`java)`

懒汉式单例如何保证线程安全呢？通过 `synchronized` 关键字加锁保证线程安全， `synchronized` 可以添加在**⽅法上⾯，也可以添加在代码块上⾯**，这⾥演示添加在⽅法上⾯，存在的问题是每⼀次调⽤ `getInstance()` 获取实例时都需要加锁和释放锁，这样是⾮常影响性能的。

```java
package com.uin.creationPattern.Singleton.LazyBones.AddSynchronized;


/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月27日 16:58
 * \* Description: 第二种方法 懒汉（解决线程不安全--加入了同步锁）
 * \
 */
public class Singleton {

    //1. 构造器私有化,外部不能创建实例化对象（new）
    private Singleton() {

    }

    //2. 懒汉--需要的时候在创建对象
    private static Singleton instance;

    //3. 提供一个静态的公有方法，加入了同步处理的代码(同步锁)，解决了线程不安全的问题
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public static void main(String[] args) {
        //测试
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance1==instance2);//true

        System.out.println(instance1.hashCode());//460141958
        System.out.println(instance2.hashCode());//460141958

       /**
        * 总结: 懒汉式（线程安全 加synchronized）优缺点
        *   (1) 解决了线程安全的问题
        *   (2) 效率太低了，每个线程在想获得类的实例的时候，执行getInstance()方法都要进行同步。而其实这个方法只执行一次实例
        *   化代码就够了，后面的项获得该类的实例，直接return就行了。但是你在getInstance()后面加了synchronized，在每一次都
        *   会同步。方法进行的同步效率太低。
        *   (3) 结论：在实际开发中，不推荐使用这种方式
        *
        */
    }
}
```

- 优点：懒加载，线程安全
- 缺点：效率低

##### 懒汉式(**同步代码块**)

（参考代码`com/uin/creationPattern/Singleton/LazyBones/SynchonizedCodeBlock/Singleton.java`）

```java
package com.uin.creationPattern.Singleton.LazyBones.SynchonizedCodeBlock;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月27日 17:22
 * \* Description: 第三种方法 懒汉式(同步代码块)
 * \
 */
public class Singleton {

    //1. 构造器私有化,外部不能创建实例化对象（new）
    private Singleton() {

    }

    //2. 懒汉--需要的时候在创建对象
    private static Singleton instance;

    //3. 提供一个静态的公有方法，加入了同步代码块，解决不了线程安全的问题 但是还是效率太低
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                instance = new Singleton();
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //测试
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance1 == instance2);//true

        System.out.println(instance1.hashCode());//460141958
        System.out.println(instance2.hashCode());//460141958

        /**
         * 总结: 懒汉式（线程安全 加synchronized同步代码块）优缺点
         *   (1) 这种方式，本意是想对在静态方法加synchronized的该进，因为前面的同步方法的效率太低了，改为同步产生实例化
         *   的代码块
         *   (2) 但是这种同步并不能起到线程安全的作用。和懒汉式LazyMan与遇到的情形一致。假如一个线程进入了	
         			if(instance==null)判断语句模块，还未来的及往下执行，另个一个线程也通过了这个判断语句，这时就会产生多个实例。
         *   (3) 结论：在实际开发中，不能使用这种方式
         */
    }
}
```

##### 懒汉式双重检查(DCL)

双重检查锁（**DCL**， 即 **double-checked locking**）

参考代码(`com/uin/creationPattern/Singleton/LazyBones/DoubbleCheck/Singleton.java)`

Double-Check概念是多线程开发中常使用到的，如代码所示，我们进行了两次`if(singleton==null)`检查，这样就保证了线程安全。

这样，实例化代码只用执行一次，后面再次访问时，直接`if(singleton==null)`，为`FALSE`，直接`return`实例化对象，也避免了反复进行方法同步。

这⾥的双重检查是指两次⾮空判断，锁指的是 `synchronized` 加锁，为什么要进⾏双重判断，其实很简单，第⼀重判断，如果实例已经存在，那么就不再需要进⾏同步操作，⽽是直接返回这个实例，如果没有创建，才会进⼊同步块，同步块的⽬的与之前相同，⽬的是为了防⽌有多个线程同时调⽤时，导致⽣成多个实例，有了同步块，每次只能有⼀个线程调⽤访问同步块内容，当第⼀个抢到锁的调⽤获取了实例之后，这个实例就会被创建，之后的所有调⽤都不会进⼊同步块，直接在第⼀重判断就返回了单例。

关于内部的第⼆重空判断的作⽤，当多个线程⼀起到达锁位置时，进⾏锁竞争，其中⼀个线程获取锁，如果是第⼀次进⼊则为 null，会进⾏单例对象的创建，完成后释放锁，其他线程获取锁后就会被空判断拦截，直接返回已创建的单例对象。

其中最关键的⼀个点就是 volatile 关键字的使⽤，关于 volatile 的详细介绍可以在我的博客中搜索即可，这⾥不做详细介绍，简单说明⼀下，双重检查锁中使⽤ `volatile` 的两个重要特性：可⻅性、禁⽌指令重排序

这⾥为什么要使⽤ `volatile` ？

这是因为 new 关键字创建对象不是原⼦操作，创建⼀个对象会经历下⾯的步骤：

1. 在堆内存开辟内存空间

2. 调⽤构造⽅法，初始化对象

3. 引⽤变量指向堆内存空间

对应字节码指令如下：

<img src="https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204252024949.png" alt="image-20220425202421265" style="zoom:50%;" />

为了提⾼性能，编译器和处理器常常会对既定的代码执⾏顺序进⾏指令重排序，从源码到最终执⾏指令会经历如下流程：

源码编译器优化重排序指令级并⾏重排序内存系统重排序最终执⾏指令序列所以经过指令重排序之后，创建对象的执⾏顺序可能为 1 2 3 或者 1 3 2 ，因此当某个线程在乱序运⾏ 1 3 2 指令的时候，引⽤变量指向堆内存空间，这个对象不为 null，但是没有初始化，其他线程有可能这个时候进⼊了 getInstance 的第⼀个 if(instance == null) 判断不为 nulll ，导致错误使⽤了没有初始化的⾮ null 实例，这样的话就会出现异常，这个就是著名的DCL 失效问题。

当我们在引⽤变量上⾯添加 volatile 关键字以后，会通过在创建对象指令的前后添加内存屏障来禁⽌指令重排序，就可以避免这个问题，⽽且对volatile 修饰的变量的修改对其他任何线程都是可⻅的。

```java
package com.uin.creationPattern.Singleton.LazyBones.DoubbleCheck;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月27日 17:39
 * \* Description: 第四种 懒汉双重检查
 * \
 */
public class Singleton {

    /**
     * 当一个变量定义为volatile之后，它将具备两种特性
     *     1. 保证此变量对所有线程的可见性
     *        这里不做过多解释，简单的说就是，当一个线程修改了volatile变量之后，它先写入它的工作内存中，
     *        然后立刻写入主内存，并且刷新其他线程中的工作内存，这样其他线程再去读取他们工作内存中的变量时，
     *        确保能够拿到最新的。但是如果是普通变量的话，它不会立即写入主内存中，所有其他线程的工作内存中保存的是旧的值。
     *        所有volatile变量可以保证可见性。
     *     2. 禁止指令重排序优化
     */
    private static volatile Singleton instance;

    //1. 构造器私有化，外部不能通过new来实例化对象
    private Singleton() {

    }
    //提供一个静态的公有方法，加入双重检查代码，解决线程安全问题，同时解决懒加载问题
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        //测试
        System.out.println(instance1==instance2);

        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
    }
}
```

- 优点：线程安全：延迟加载；效率较高
- 结论： 在实际开发中，推荐使用这种的单例设计模式

#### 🥥恶汉模式实现单例模式

##### 恶汉式(静态变量)



```java
package com.uin.creationPattern.Singleton.BadMash.StaticConstants;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月27日 15:07
 * \* Description: 第一种 恶汉式(静态变量)
 * \
 */
public class singleton {

    //1. 构造器私有化,外部不能创建实例化对象（new）
    private singleton() {

    }

    //2. 在类内部创建实例化对象
    private static final singleton instance = new singleton();

    //3. 对外提供一个公有的静态方法，返回实例对象
    public static singleton getInstance() {
        return instance;
    }
}
```



##### 恶汉静态代码块







### 🍅原型设计模式

### 🍆抽象工厂设计模式

### 🥑建造者设计模式

## 🌽结构型设计模式


## 🥕行为型设计模式
