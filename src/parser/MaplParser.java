/* Generated By:JavaCC: Do not edit this line. MaplParser.java */
  package parser;

  import syntaxtree.*;
  import java.util.List;
  import java.util.LinkedList;
  public class MaplParser implements MaplParserConstants {
    private Token tagToken;
    private <T extends AST> T tag(T ast, Token t) {
      ast.tag(t.beginLine, t.beginColumn);
      return ast;
    }
    private <T extends AST> T tag(T ast, AST sub) {
        ast.tag(sub.getTags());
        return ast;
    }

  final public Program nt_Program() throws ParseException {
  ProcDecl pd;
  MethodDecl md;
  List<MethodDecl> mds = new LinkedList<MethodDecl>();
    pd = nt_ProcDecl();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PROC:
      case FUN:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      md = nt_MethodDecl();
                                         mds.add(md);
    }
    jj_consume_token(0);
    {if (true) return new Program(pd, mds);}
    throw new Error("Missing return statement in function");
  }

  final public MethodDecl nt_MethodDecl() throws ParseException {
  MethodDecl md;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case FUN:
      md = nt_FunDecl();
      break;
    case PROC:
      md = nt_ProcDecl();
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
                                         {if (true) return md;}
    throw new Error("Missing return statement in function");
  }

  final public FunDecl nt_FunDecl() throws ParseException {
  Type type;
  Token t;
  List<Formal> fs;
  Stm s;
  List<Stm> ss = new LinkedList<Stm>();
  Exp re;
    jj_consume_token(FUN);
    type = nt_Type();
    t = jj_consume_token(ID);
    jj_consume_token(LPAREN);
    fs = nt_FormalList();
    jj_consume_token(RPAREN);
    jj_consume_token(LBRACE);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INTEGER_LITERAL:
      case LPAREN:
      case INT:
      case TRUE:
      case FALSE:
      case NOT:
      case LBRACE:
      case ISNULL:
      case BOOLEAN:
      case ARRAYOF:
      case NEW:
      case IF:
      case WHILE:
      case OUTPUT:
      case OUTCHAR:
      case ID:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      s = nt_Statement();
                        ss.add(s);
    }
    jj_consume_token(RETURN);
    re = nt_Exp();
    jj_consume_token(SEMICOLON);
    jj_consume_token(RBRACE);
    {if (true) return tag(new FunDecl(type, t.image, fs, ss, re), t);}
    throw new Error("Missing return statement in function");
  }

  final public ProcDecl nt_ProcDecl() throws ParseException {
  Token t;
  List<Formal> fs;
  Stm s;
  List<Stm> ss = new LinkedList<Stm>();
  Exp re;
    jj_consume_token(PROC);
    t = jj_consume_token(ID);
    jj_consume_token(LPAREN);
    fs = nt_FormalList();
    jj_consume_token(RPAREN);
    jj_consume_token(LBRACE);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INTEGER_LITERAL:
      case LPAREN:
      case INT:
      case TRUE:
      case FALSE:
      case NOT:
      case LBRACE:
      case ISNULL:
      case BOOLEAN:
      case ARRAYOF:
      case NEW:
      case IF:
      case WHILE:
      case OUTPUT:
      case OUTCHAR:
      case ID:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      s = nt_Statement();
                        ss.add(s);
    }
    jj_consume_token(RBRACE);
    {if (true) return tag(new ProcDecl(t.image, fs, ss), t);}
    throw new Error("Missing return statement in function");
  }

  final public List<Formal> nt_FormalList() throws ParseException {
  Formal f;
  List<Formal> fs = new LinkedList<Formal>();
  Type type;
  Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT:
    case BOOLEAN:
    case ARRAYOF:
      type = nt_Type();
      t = jj_consume_token(ID);
      f = new Formal(type, t.image); fs.add(tag(f, t));
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[4] = jj_gen;
          break label_4;
        }
        f = nt_FormalRest();
                         fs.add(f);
      }
      {if (true) return fs;}
      break;
    default:
      jj_la1[5] = jj_gen;

      {if (true) return fs;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Formal nt_FormalRest() throws ParseException {
  Token t;
  Type type;
    jj_consume_token(COMMA);
    type = nt_Type();
    t = jj_consume_token(ID);
    {if (true) return tag(new Formal(type, t.image), t);}
    throw new Error("Missing return statement in function");
  }

  final public Type nt_Type() throws ParseException {
  Token t;
  Type type;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT:
      t = jj_consume_token(INT);
      type = new TypeInt(); tag(type, t); {if (true) return type;}
      break;
    case BOOLEAN:
      t = jj_consume_token(BOOLEAN);
      type = new TypeBoolean(); tag(type, t); {if (true) return type;}
      break;
    case ARRAYOF:
      t = jj_consume_token(ARRAYOF);
      jj_consume_token(LPAREN);
      type = nt_Type();
      jj_consume_token(RPAREN);
      {if (true) return tag(new TypeArray(type), t);}
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public StmBlock nt_Block() throws ParseException {
  Stm s;
  List<Stm> ss = new LinkedList<Stm>();
    jj_consume_token(LBRACE);
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INTEGER_LITERAL:
      case LPAREN:
      case INT:
      case TRUE:
      case FALSE:
      case NOT:
      case LBRACE:
      case ISNULL:
      case BOOLEAN:
      case ARRAYOF:
      case NEW:
      case IF:
      case WHILE:
      case OUTPUT:
      case OUTCHAR:
      case ID:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_5;
      }
      s = nt_Statement();
                                ss.add(s);
    }
    jj_consume_token(RBRACE);
    {if (true) return new StmBlock(ss);}
    throw new Error("Missing return statement in function");
  }

  final public Stm nt_Statement() throws ParseException {
  StmBlock b, b1, b2;
  List<Exp> es = new LinkedList<Exp>();
  Exp e, e1, e2;
  Type type;
  Token t, t1;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRACE:
      b = nt_Block();
      {if (true) return b;}
      break;
    case INT:
    case BOOLEAN:
    case ARRAYOF:
      type = nt_Type();
      t = jj_consume_token(ID);
      jj_consume_token(SEMICOLON);
      StmVarDecl svd = new StmVarDecl(type, t.image); tag(svd, t); {if (true) return svd;}
      break;
    default:
      jj_la1[8] = jj_gen;
      if (jj_2_1(2)) {
        t = jj_consume_token(ID);
        t1 = jj_consume_token(ASSIGN);
        e = nt_Exp();
        jj_consume_token(SEMICOLON);
      Var v = new Var(t.image); tag(v, t); {if (true) return tag(new StmAssign(v, e), t1);}
      } else if (jj_2_2(2)) {
        t = jj_consume_token(ID);
        jj_consume_token(LPAREN);
        es = nt_ExpList();
        jj_consume_token(RPAREN);
        jj_consume_token(SEMICOLON);
      {if (true) return tag(new StmCall(t.image, es), t);}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case INTEGER_LITERAL:
        case LPAREN:
        case TRUE:
        case FALSE:
        case NOT:
        case ISNULL:
        case NEW:
        case ID:
          e = nt_PrimaryExp();
          jj_consume_token(LSQBR);
          e1 = nt_Exp();
          jj_consume_token(RSQBR);
          t = jj_consume_token(ASSIGN);
          e2 = nt_Exp();
          jj_consume_token(SEMICOLON);
      {if (true) return tag(new StmArrayAssign(e, e1, e2), t);}
          break;
        case IF:
          t1 = jj_consume_token(IF);
          jj_consume_token(LPAREN);
          e = nt_Exp();
          jj_consume_token(RPAREN);
          jj_consume_token(THEN);
          b1 = nt_Block();
          jj_consume_token(ELSE);
          b2 = nt_Block();
      {if (true) return tag(new StmIf(e, b1, b2), t1);}
          break;
        case WHILE:
          t1 = jj_consume_token(WHILE);
          jj_consume_token(LPAREN);
          e = nt_Exp();
          jj_consume_token(RPAREN);
          jj_consume_token(DO);
          b = nt_Block();
      {if (true) return tag(new StmWhile(e, b), t1);}
          break;
        case OUTPUT:
          t1 = jj_consume_token(OUTPUT);
          e = nt_Exp();
          jj_consume_token(SEMICOLON);
      {if (true) return tag(new StmOutput(e), t1);}
          break;
        case OUTCHAR:
          t1 = jj_consume_token(OUTCHAR);
          e = nt_Exp();
          jj_consume_token(SEMICOLON);
      {if (true) return tag(new StmOutchar(e), t1);}
          break;
        default:
          jj_la1[9] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp nt_Exp() throws ParseException {
  Token t, t1;
  Exp e1, e2;
  ExpOp.Op op;
  List<Exp> es;
    if (jj_2_3(2)) {
      t = jj_consume_token(ID);
      jj_consume_token(LPAREN);
      es = nt_ExpList();
      jj_consume_token(RPAREN);
        {if (true) return tag(new ExpCall(t.image, es), t);}
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INTEGER_LITERAL:
      case LPAREN:
      case TRUE:
      case FALSE:
      case NOT:
      case ISNULL:
      case NEW:
      case ID:
        e1 = nt_PrimaryExp();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LSQBR:
          t = jj_consume_token(LSQBR);
          e2 = nt_Exp();
          jj_consume_token(RSQBR);
          {if (true) return tag(new ExpArrayLookup(e1, e2), t);}
          break;
        case DOT:
          t = jj_consume_token(DOT);
          jj_consume_token(LENGTH);
          {if (true) return tag(new ExpArrayLength(e1), t);}
          break;
        case AND:
        case LESSTHAN:
        case EQUALS:
        case DIV:
        case PLUS:
        case MINUS:
        case TIMES:
          op = nt_Op();
          e2 = nt_PrimaryExp();
          {if (true) return tag(new ExpOp(e1, op, e2), tagToken);}
          break;
        default:
          jj_la1[10] = jj_gen;

          {if (true) return e1;}
        }
        break;
      default:
        jj_la1[11] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    throw new Error("Missing return statement in function");
  }

  final public ExpOp.Op nt_Op() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AND:
      tagToken = jj_consume_token(AND);
                      {if (true) return ExpOp.Op.AND;}
      break;
    case LESSTHAN:
      tagToken = jj_consume_token(LESSTHAN);
                           {if (true) return ExpOp.Op.LESSTHAN;}
      break;
    case EQUALS:
      tagToken = jj_consume_token(EQUALS);
                         {if (true) return ExpOp.Op.EQUALS;}
      break;
    case DIV:
      tagToken = jj_consume_token(DIV);
                      {if (true) return ExpOp.Op.DIV;}
      break;
    case PLUS:
      tagToken = jj_consume_token(PLUS);
                       {if (true) return ExpOp.Op.PLUS;}
      break;
    case MINUS:
      tagToken = jj_consume_token(MINUS);
                        {if (true) return ExpOp.Op.MINUS;}
      break;
    case TIMES:
      tagToken = jj_consume_token(TIMES);
                        {if (true) return ExpOp.Op.TIMES;}
      break;
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp nt_PrimaryExp() throws ParseException {
  Token t, t1;
  Exp e;
  List<Exp> es;
  Type type;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
      t = jj_consume_token(INTEGER_LITERAL);
      {if (true) return tag(new ExpInteger(Integer.parseInt(t.image)), t);}
      break;
    case TRUE:
      t = jj_consume_token(TRUE);
      {if (true) return tag(new ExpTrue(), t);}
      break;
    case FALSE:
      t = jj_consume_token(FALSE);
      {if (true) return tag(new ExpFalse(), t);}
      break;
    case ID:
      t = jj_consume_token(ID);
      {if (true) return tag(new ExpVar(tag(new Var(t.image), t)), t);}
      break;
    case NOT:
      t = jj_consume_token(NOT);
      e = nt_PrimaryExp();
      {if (true) return tag(new ExpNot(e), t);}
      break;
    case ISNULL:
      t = jj_consume_token(ISNULL);
      e = nt_PrimaryExp();
      {if (true) return tag(new ExpIsnull(e), t);}
      break;
    case NEW:
      t = jj_consume_token(NEW);
      jj_consume_token(ARRAYOF);
      jj_consume_token(LPAREN);
      type = nt_Type();
      jj_consume_token(RPAREN);
      jj_consume_token(LSQBR);
      e = nt_Exp();
      jj_consume_token(RSQBR);
      {if (true) return tag(new ExpNewArray(type, e), t);}
      break;
    case LPAREN:
      t = jj_consume_token(LPAREN);
      e = nt_Exp();
      jj_consume_token(RPAREN);
      {if (true) return e;}
      break;
    default:
      jj_la1[13] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public List<Exp> nt_ExpList() throws ParseException {
  Exp e;
  List<Exp> es = new LinkedList<Exp>();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
    case LPAREN:
    case TRUE:
    case FALSE:
    case NOT:
    case ISNULL:
    case NEW:
    case ID:
      e = nt_Exp();
                   es.add(e);
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[14] = jj_gen;
          break label_6;
        }
        e = nt_ExpRest();
                                                   es.add(e);
      }
      {if (true) return es;}
      break;
    default:
      jj_la1[15] = jj_gen;

         {if (true) return es;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp nt_ExpRest() throws ParseException {
  Exp e;
    jj_consume_token(COMMA);
    e = nt_Exp();
    {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  private boolean jj_3_3() {
    if (jj_scan_token(ID)) return true;
    if (jj_scan_token(LPAREN)) return true;
    return false;
  }

  private boolean jj_3_2() {
    if (jj_scan_token(ID)) return true;
    if (jj_scan_token(LPAREN)) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_scan_token(ID)) return true;
    if (jj_scan_token(ASSIGN)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public MaplParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[16];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x1800,0x1800,0x8009e300,0x8009e300,0x0,0x2000,0x2000,0x8009e300,0x82000,0x8001c300,0x7f820000,0x8001c300,0x3f800000,0x8001c300,0x0,0x8001c300,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x2d9c,0x2d9c,0x2,0xc,0xc,0x2d9c,0xc,0x2d90,0x0,0x2010,0x0,0x2010,0x2,0x2010,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[3];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public MaplParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public MaplParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new MaplParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public MaplParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new MaplParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public MaplParser(MaplParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(MaplParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[46];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 16; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 46; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 3; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

  }
