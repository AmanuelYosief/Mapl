package syntaxtree;

import java.util.List;
import visitor.Visitor;

public class ExpCall extends Exp {

    public final String id;
    public final List<Exp> es;

    public ExpCall(String aid, List<Exp> aes) {
        id = aid;
        es = aes;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
