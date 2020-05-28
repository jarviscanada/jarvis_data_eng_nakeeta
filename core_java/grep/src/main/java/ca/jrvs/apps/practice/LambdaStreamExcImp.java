
package ca.jrvs.apps.practice;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp  implements LambdaStreamExc{
    @Override
    Stream<String> createStrStream(String... strings) {

        return Arrays.stream(strings);

    }

    @Override
    Stream<String> toUpperCase(String... strings) {
        return Arrays.stream(strings).map(String::toUpperCase);
    }

    @Override
    Stream<String> filter(Stream<String> stringStream, String pattern) {
        return stringStream.filter(string-> !string.contains(pattern));
    }

    @Override
    IntStream createIntStream(int[] arr) {

        return Arrays.stream(arr);
    }

    @Override
    <E> List<E> toList(Stream<E> stream) {

        return stream.collect(Collectors.toList());
    }

    @Override
    List<Integer> toList(IntStream intStream) {

        return  intStream.boxed().collect(Collectors.toList());
    }

    @Override
    IntStream createIntStream(int start, int end) {

        return IntStream.range(start,end) ;
    }

    @Override
    DoubleStream squareRootIntStream(IntStream intStream) {

        return intStream.mapToDouble(Math :: sqrt);
    }

    @Override
    IntStream getOdd(IntStream intStream) {

        return intStream.filter(x -> x % 2 == 1);
    }

    @Override
    Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return (x -> sysytem.out.println(prefix +x + suffix));
    }

    @Override
    void printMessages(String[] messages, Consumer<String> printer) {
        Arrays.stream(messages).forEach(printer);
    }

    @Override
    void printOdd(IntStream intStream, Consumer<String> printer) {
        printMessages( getOdd(intStream).mapToObj(String ::valueOf).toArray(String[]::new),printer);
    }

    @Override
    Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
        return ints.flatMap(list -> list.stream().map(x-> x * x));
    }


}