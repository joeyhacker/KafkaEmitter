import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class FluxTest {

    public static void main(String[] args) throws Throwable {

        AtomicInteger counter = new AtomicInteger();

//        Flux.interval(Duration.ofMillis(1000)).

//        Flux.range(1, 100).delayElements(Duration.ofMillis(1000)).subscribe(ret -> {
//            System.out.println(Thread.currentThread().getName() + " : " + ret);
//        });

//        Flux.generate(e -> {
//            e.next("str" + counter.getAndIncrement());
//        }).delaySequence(Duration.ofMillis(1000))
//                .subscribe(ret -> {
//                    System.out.println(ret);
//                });
//        Flux.create(e -> {
//            e.next(counter.getAndIncrement());
//        }).subscribe(ret -> {
//            System.out.println(ret);
//        });

        Thread.sleep(1000 * 30);

    }
}
