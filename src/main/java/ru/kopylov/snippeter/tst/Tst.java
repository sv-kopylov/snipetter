package ru.kopylov.snippeter.tst;

import ru.kopylov.snippeter.context.ApplicationInitializer;
import ru.kopylov.snippeter.context.Context;
import ru.kopylov.snippeter.view.ResearchView;

public class Tst {
    public static void main(String[] args) {
        Context ctx = Context.getInstance();
        ctx.put(new Tst());
        System.out.println(ctx.get(Tst.class.getName()));
    }
}
