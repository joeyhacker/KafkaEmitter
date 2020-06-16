import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Sender {
    private String name;

    Sender(String name) {
        this.name = name;
    }

    public void send(String msg) {
        System.out.println(this.name + " send msg " + msg);
    }
}


public class FluxTest {


    public static void main(String[] args) throws Throwable {

        List<Sender> senders = Arrays.asList(new Sender("sender-1"), new Sender("sender-2"), new Sender("sender-3"));

//        Flux.create(sink -> {
//            System.out.println("gen...");
//            sink.next()
//        })

        Flux.generate(() -> new AtomicInteger(0), (counter, sink) -> {
            System.out.println("gen...");
//            if (counter.get() == 10) {
//                sink.complete();
//            } else {
            sink.next("msg-" + counter.incrementAndGet());
//            }
            return counter;
        }).delayElements(Duration.ofMillis(500)).doOnComplete(() -> {
            System.out.println("done");
        }).subscribe(ret -> {
            System.out.println(ret);
        });
//        Flux.generate()

//        Flux.ge
//        Flux.interval(Duration.ofMillis(2000)).map(x -> {
//            Long n = x % senders.size();
//            senders.get(n.intValue()).send("msg-" + x);
//            return n;
//        }).subscribe(ret -> {
//            System.out.println(ret);
//        });

//        AtomicInteger counter = new AtomicInteger();

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
