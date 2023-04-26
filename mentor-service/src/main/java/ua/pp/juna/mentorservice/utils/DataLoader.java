package ua.pp.juna.mentorservice.utils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ua.pp.juna.mentorservice.model.*;
import ua.pp.juna.mentorservice.service.CourseService;
import ua.pp.juna.mentorservice.service.LessonService;
import ua.pp.juna.mentorservice.service.MentorService;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final MentorService mentorService;
    private final CourseService courseService;
    private final LessonService lessonService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initMentors();
        initCourses();
        initLessons();
    }


    private void initMentors() {
        final Mentor mentor1 = Mentor.builder()
                .firstName("Andrew")
                .category(Category.JAVA)
                .lastName("Brovko")
                .description("As a Java course mentor on our employment resource, I am dedicated to providing comprehensive" +
                        " and practical training to help aspiring Java developers build a strong foundation in programming " +
                        "concepts and best practices. Through my engaging and informative courses, students can gain valuable" +
                        " hands-on experience with Java programming and develop the skills needed to succeed in the industry." +
                        " Whether you are new to Java or seeking to enhance your existing skills, I am committed to helping " +
                        "you achieve your goals and advance your career in the exciting field of software development.")
                .build();

        final Mentor mentor2 = Mentor.builder()
                .firstName("Kostya")
                .lastName("Shurigin")
                .category(Category.PYTHON)
                .description("As a Python course mentor on our employment resource, I am passionate about empowering " +
                        "learners to master this dynamic and popular programming language. Through my expert guidance" +
                        " and engaging instruction, students will gain hands-on experience in Python programming and" +
                        " develop the skills necessary to succeed in a variety of industries. Whether you are new to " +
                        "Python or looking to deepen your understanding of the language, I am dedicated to providing " +
                        "comprehensive and practical training that will help you achieve your goals and advance your " +
                        "career. Join me on this exciting journey to unlock the full potential of Python and become a " +
                        "proficient developer.")
                .build();

        final Mentor mentor3 = Mentor.builder()
                .firstName("Mark")
                .lastName("Johnson")
                .category(Category.JAVA)
                .description("As a Java mentor, I am dedicated to helping learners master this dynamic and popular " +
                        "programming language. Through my expert guidance and engaging instruction, students can gain " +
                        "valuable experience in Java programming and develop the skills necessary to succeed in a variety " +
                        "of industries. Whether you are new to Java or looking to enhance your existing skills, I am " +
                        "committed to providing comprehensive and practical training that will help you achieve your goals.")
                .build();

        final Mentor mentor4 = Mentor.builder()
                .firstName("John")
                .lastName("Smith")
                .category(Category.PYTHON)
                .description("As a Python mentor, I am passionate about empowering learners to master this dynamic and " +
                        "versatile programming language. Through my expert guidance and engaging instruction, students " +
                        "will gain hands-on experience in Python programming and develop the skills necessary to succeed " +
                        "in a variety of industries. Whether you are new to Python or looking to deepen your understanding " +
                        "of the language, I am dedicated to providing comprehensive and practical training that will help " +
                        "you achieve your goals and advance your career.")
                .build();

        final Mentor mentor5 = Mentor.builder()
                .firstName("Emma")
                .lastName("Johnson")
                .category(Category.DEVOPS)
                .description("As a Devops mentor, I am committed to helping learners unlock the full potential of this " +
                        "popular and versatile programming language. Through my expert guidance and engaging instruction, " +
                        "students can gain hands-on experience in Python programming and develop the skills necessary to " +
                        "succeed in a variety of industries. Whether you are new to Python or seeking to enhance your " +
                        "existing skills, I am dedicated to providing comprehensive and practical training that will help " +
                        "you achieve your goals.")
                .build();

        final Mentor mentor6 = Mentor.builder()
                .firstName("David")
                .lastName("Jones")
                .category(Category.OTHER)
                .description("As a Haskell mentor, I am passionate about helping learners explore the power and elegance " +
                        "of functional programming. Through my expert guidance and engaging instruction, students can " +
                        "gain hands-on experience with Haskell and develop the skills necessary to create elegant and " +
                        "efficient solutions to complex problems. Whether you are new to Haskell or looking to deepen " +
                        "your understanding of functional programming, I am committed to providing comprehensive and " +
                        "practical training that will help you achieve your goals and advance your career.")
                .build();

        final Mentor mentor7 = Mentor.builder()
                .firstName("Samantha")
                .lastName("Smith")
                .category(Category.JAVA)
                .description("As a Java mentor, I am passionate about helping learners build a strong foundation in Java " +
                        "programming. Through my expert guidance and engaging instruction, students will gain valuable " +
                        "hands-on experience and develop the skills necessary to succeed in the industry. Whether you are " +
                        "new to Java or looking to deepen your understanding of the language, I am committed to providing " +
                        "comprehensive and practical training that will help you achieve your goals and advance your career.")
                .build();


        mentorService.addMentor(mentor1);
        mentorService.addMentor(mentor2);
        mentorService.addMentor(mentor3);
        mentorService.addMentor(mentor4);
        mentorService.addMentor(mentor5);
        mentorService.addMentor(mentor6);
        mentorService.addMentor(mentor7);
    }


    private void initCourses() {
        final Course course1 = Course.builder()
                .name("Andrew's first Java course")
                .category(Category.JAVA)
                .description("This course is likely an introductory level course on Java programming taught by Andrew. " +
                        "Students will learn the basics of Java programming such as syntax, data types, control statements," +
                        " classes, and objects. This course may also cover basic programming concepts such as algorithms," +
                        " data structures, and software design principles.")
                .build();

        final Course course2 = Course.builder()
                .name("Andrew's second Java course")
                .category(Category.JAVA)
                .description(" This course is probably a continuation of Andrew's first Java course and is aimed at " +
                        "students who already have some experience with Java programming. Students will build on their" +
                        " existing knowledge to learn more advanced Java programming concepts, such as data handling," +
                        " multithreading, and network programming.")
                .build();

        final Course course3 = Course.builder()
                .name("Andrew's third Java course")
                .category(Category.JAVA)
                .description("This course is likely an advanced level course on Java programming taught by Andrew." +
                        " It may cover topics such as performance optimization, security, advanced data structures and" +
                        " algorithms, and design patterns. This course is intended for experienced Java developers who" +
                        " want to take their skills to the next level.")
                .build();

        final Course course4 = Course.builder()
                .name("Emma's first Docker course")
                .category(Category.DEVOPS)
                .description("This course is likely an introductory level course on Docker, a containerization technology." +
                        " Students will learn the basics of Docker, including how to create, run, and manage containers." +
                        " This course may also cover topics such as container orchestration and Docker best practices.")
                .build();

        final Course course5 = Course.builder()
                .name("Emma's Kubernetes course")
                .category(Category.DEVOPS)
                .description("This course is probably a more advanced course on containerization technology, focusing" +
                        " specifically on Kubernetes, an open-source container orchestration system. Students will learn" +
                        " how to deploy, manage, and scale containerized applications using Kubernetes. This course may" +
                        " also cover advanced Kubernetes topics such as load balancing, auto-scaling, and monitoring.")
                .build();

        final Course course6 = Course.builder()
                .name("David's Haskell course")
                .category(Category.OTHER)
                .description("This course is likely an introductory level course on the Haskell programming language," +
                        " which is known for its functional programming paradigm. Students will learn the basics of " +
                        "Haskell programming, including syntax, types, and functions. This course may also cover topics" +
                        " such as recursion, pattern matching, and list comprehension.")
                .build();


        courseService.addCourse(course1, 1L);
        courseService.addCourse(course2, 1L);
        courseService.addCourse(course3, 1L);
        courseService.addCourse(course4, 5L);
        courseService.addCourse(course5, 5L);
        courseService.addCourse(course6, 6L);
    }

    private void initLessons() {
        final Lesson lesson1 = Lesson.builder()
                .name("First Java Lesson")
                .text("Java is a popular programming language that was first released in 1995. It is an object-oriented language that is designed to be portable, meaning that code written in Java can be run on any platform that has a Java Virtual Machine (JVM) installed.\n" +
                        "\n" +
                        "One of the key features of Java is its automatic memory management. This means that Java takes care of memory allocation and deallocation for the programmer, which helps to prevent common programming errors such as memory leaks and segmentation faults.\n" +
                        "\n" +
                        "Java also has a rich set of libraries and frameworks that make it easy to develop complex applications. Some of the most popular Java frameworks include Spring, Hibernate, and Struts.\n" +
                        "\n" +
                        "To get started with Java programming, you'll need to install a Java Development Kit (JDK) on your computer. The JDK includes the Java compiler, which is used to compile Java source code into bytecode that can be run on the JVM.\n" +
                        "\n" +
                        "Once you have the JDK installed, you can write Java code using a text editor or an integrated development environment (IDE) such as Eclipse or IntelliJ IDEA. Here's an example of a simple Java program:\n" +
                        "\n" +
                        "typescript\n" +
                        "Copy code\n" +
                        "public class HelloWorld {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        System.out.println(\"Hello, world!\");\n" +
                        "    }\n" +
                        "}\n" +
                        "This program defines a class called HelloWorld with a single method called main. The main method is the entry point of the program, and it simply prints the string \"Hello, world!\" to the console using the System.out.println method.\n" +
                        "\n" +
                        "Java has a rich syntax that includes features such as classes, objects, inheritance, and polymorphism. Here's an example of a simple class definition in Java:\n" +
                        "\n" +
                        "arduino\n" +
                        "Copy code\n" +
                        "public class Rectangle {\n" +
                        "    private int width;\n" +
                        "    private int height;\n" +
                        "\n" +
                        "    public Rectangle(int width, int height) {\n" +
                        "        this.width = width;\n" +
                        "        this.height = height;\n" +
                        "    }\n" +
                        "\n" +
                        "    public int getWidth() {\n" +
                        "        return width;\n" +
                        "    }\n" +
                        "\n" +
                        "    public int getHeight() {\n" +
                        "        return height;\n" +
                        "    }\n" +
                        "\n" +
                        "    public int getArea() {\n" +
                        "        return width * height;\n" +
                        "    }\n" +
                        "}\n" +
                        "This class defines a Rectangle object with a width and height property, as well as methods for getting the width, height, and area of the rectangle.\n" +
                        "\n" +
                        "Java also supports interfaces, which define a set of methods that a class must implement. Here's an example of an interface definition:\n" +
                        "\n" +
                        "csharp\n" +
                        "Copy code\n" +
                        "public interface Drawable {\n" +
                        "    void draw();\n" +
                        "}\n" +
                        "This interface defines a single method called draw, which can be implemented by any class that implements the Drawable interface.\n" +
                        "\n" +
                        "Java also supports exception handling, which allows you to gracefully handle errors in your code. Here's an example of a try-catch block in Java:\n" +
                        "\n" +
                        "php\n" +
                        "Copy code\n" +
                        "try {\n" +
                        "    // some code that might throw an exception\n" +
                        "} catch (Exception e) {\n" +
                        "    // handle the exception\n" +
                        "}\n" +
                        "This code tries to execute some code that might throw an exception. If an exception is thrown, the catch block is executed, where you can handle the exception and take appropriate action.\n" +
                        "\n" +
                        "Java also has support for multithreading, which allows you to write code that can execute multiple threads of execution in parallel. Here's an example of creating a new thread in Java:\n" +
                        "\n" +
                        "java\n" +
                        "Copy code\n" +
                        "Thread thread = new Thread(new Runnable() {\n" +
                        "    @Override\n" +
                        "    public void run() {\n" +
                        "        // some code to execute in the new thread\n" +
                        "    }\n" +
                        "});\n" +
                        "\n" +
                        "thread.start();\n" +
                        "This code creates a new Thread object and passes in a Runnable object that defines the code to be executed in the new thread. The start method is called to start the new thread.\n" +
                        "\n" +
                        "In conclusion, Java is a powerful and versatile programming language that is widely used for developing a wide range of")
                .videoLinks(Arrays.asList(
                        VideoLink.builder().link("https://www.youtube.com/watch?v=z7bsNF2Dtf0&list=PLwvrYc43l1MyAmDss3HEYYBIQwY3pITeA&index=1").build(),
                        VideoLink.builder().link("https://www.youtube.com/watch?v=eQ90v7HQT-Q&list=PLwvrYc43l1MyAmDss3HEYYBIQwY3pITeA&index=2").build()))
                .build();

        final Lesson lesson2 = Lesson.builder()
                .name("First Java Lesson")
                .text("Java is an object-oriented programming language that is widely used for developing a variety of applications, including desktop applications, mobile apps, and web applications. Java code is compiled into bytecode, which can be run on any platform that has a Java Virtual Machine (JVM) installed.\n" +
                        "\n" +
                        "One of the key features of Java is its automatic memory management, which helps to prevent common programming errors such as memory leaks and segmentation faults. Java also has a rich set of libraries and frameworks that make it easy to develop complex applications.\n" +
                        "\n" +
                        "To get started with Java programming, you'll need to install a Java Development Kit (JDK) on your computer. The JDK includes the Java compiler, which is used to compile Java source code into bytecode that can be run on the JVM.\n" +
                        "\n" +
                        "Java has a rich syntax that includes features such as classes, objects, inheritance, and polymorphism. Java also supports interfaces, which define a set of methods that a class must implement.\n" +
                        "\n" +
                        "Java also has support for exception handling, which allows you to gracefully handle errors in your code. Java also has support for multithreading, which allows you to write code that can execute multiple threads of execution in parallel.\n" +
                        "\n" +
                        "Overall, Java is a powerful and versatile programming language that is widely used in the industry and can be used to develop a wide range of applications.")
                .videoLinks(Arrays.asList(
                        VideoLink.builder().link("https://www.youtube.com/watch?v=aR0pyhjeFRE&list=PLwvrYc43l1MyAmDss3HEYYBIQwY3pITeA&index=3").build(),
                        VideoLink.builder().link("https://www.youtube.com/watch?v=eQ90v7HQT-Q&list=PLwvrYc43l1MyAmDss3HEYYBIQwY3pITeA&index=2").build()))
                .build();

        lessonService.addLesson(lesson1, 1L);
        lessonService.addLesson(lesson2, 2L);
    }

}
